package life.lxd.community.schedule;


import life.lxd.community.cache.HotTagCache;
import life.lxd.community.mapper.QuestionMapper;
import life.lxd.community.model.Question;
import life.lxd.community.model.QuestionExample;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by codedrinker on 2019/12/14.
 */
@Component
@Slf4j
public class HotTagTasks {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private HotTagCache hotTagCache;
//定时更新缓存
    @Scheduled(fixedRate = 1000 * 60 * 60 * 3)
    public void hotTagSchedule() {
        int offset = 0;
        int limit = 20;
        log.info("hotTagSchedule start {}", new Date());
        List<Question> list = new ArrayList<>();

        Map<String, Integer> priorities = new HashMap<>();
        while (offset == 0 || list.size() == limit) {
            //查询出当前页问题
            list = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offset, limit));
            //计算权重值（双重循环每一个问题都有很多标签，对每一个标签进行权重值设置）
            for (Question question : list) {
                String[] tags = StringUtils.split(question.getTag(), ",");
                for (String tag : tags) {
                    Integer priority = priorities.get(tag);
                    if (priority != null) {
                        //权重值设置规则根据自己需求设置
                        priorities.put(tag, priority + 5 + question.getCommentCount());
                    } else {
                        priorities.put(tag, 5 + question.getCommentCount());
                    }
                }
            }
            //下一页问题查询
            offset += limit;
        }
        //计算出所有标签的权重值以后需要更新热门标签缓存
        hotTagCache.updateTags(priorities);
        log.info("hotTagSchedule stop {}", new Date());
    }
}
