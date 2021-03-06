package life.lxd.community.service;

import life.lxd.community.dto.PaginationDTO;
import life.lxd.community.dto.QuestionDTO;
import life.lxd.community.dto.QuestionQueryDTO;
import life.lxd.community.enums.SortEnum;
import life.lxd.community.exception.CustomizeErrorCode;
import life.lxd.community.exception.CustomizeException;
import life.lxd.community.mapper.QuestionExtMapper;
import life.lxd.community.mapper.QuestionMapper;
import life.lxd.community.mapper.UserMapper;
import life.lxd.community.model.Question;
import life.lxd.community.model.QuestionExample;
import life.lxd.community.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;
    @Autowired
    private UserMapper userMapper;
    /*首页显示所有问题*/
    public PaginationDTO list(String sort,String search,String tag,Integer page, Integer size) {

        if (StringUtils.isNotBlank(search)){
            String[] tags = StringUtils.split(search, " ");
            search = Arrays
                    .stream(tags)
                    .filter(StringUtils::isNotBlank)
                    .map(t -> t.replace("+", "").replace("*", "").replace("?", ""))
                    .filter(StringUtils::isNotBlank)
                    .collect(Collectors.joining("|"));
        }



        PaginationDTO paginationDTO = new PaginationDTO();
        //总页数
        Integer totalPage;

        QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
        questionQueryDTO.setSearch(search);
        if (StringUtils.isNotBlank(tag)) {
            tag = tag.replace("+", "").replace("*", "").replace("?", "");
            questionQueryDTO.setTag(tag);
        }

        for (SortEnum sortEnum : SortEnum.values()) {
            if (sortEnum.name().toLowerCase().equals(sort)) {
                questionQueryDTO.setSort(sort);

                if (sortEnum == SortEnum.HOT7) {
                    questionQueryDTO.setTime(System.currentTimeMillis() - 1000L * 60 * 60 * 24 * 7);
                }
                if (sortEnum == SortEnum.HOT30) {
                    questionQueryDTO.setTime(System.currentTimeMillis() - 1000L * 60 * 60 * 24 * 30);
                }
                break;
            }
        }
//        questionQueryDTO.setTime(System.currentTimeMillis());
        //1.查询总数据个数
        Integer totalCount = questionExtMapper.countBySearch(questionQueryDTO);
        //2.计算总页数
        if(totalCount%size==0){
            totalPage = totalCount/size;
        }else{
            totalPage = totalCount/size+1;
        }

        //判断页数边界值判断
        if(page<1){
            page=1;
        }
        if(page>totalPage){
            page = totalPage;
        }
        //进行paginationDTO的封装操作（页面需要显示的页码及上一页下一页第一页最后一页是否显示设置）
        paginationDTO.setPagination(totalPage, page);
        //size*(page-1)
        Integer offset = page < 1 ? 0 : size * (page - 1);
        //分页操作-当前页展示的问题数据
       /* QuestionExample questionExample = new QuestionExample();
        questionExample.setOrderByClause("gmt_create desc");*/
        questionQueryDTO.setSize(size);
        questionQueryDTO.setPage(offset);
        //根据当前页展示的数据数，及第几条数据开始----查询出当前页所要显示的问题数据
        List<Question> questionList = questionExtMapper.selectBySearch(questionQueryDTO);
        //对所有问题进行DTO的封装后存入这个列表中
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        //对QuestionDTO赋值
        for (Question question:questionList) {
            //通过question的creator返回user，因为question的creator字段就是user里面的id
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        //封装paginationDTO的最后一个参数---当前页相关数据
        paginationDTO.setData(questionDTOList);
        return paginationDTO;
    }

    /*查询当前用户提问的问题*/
    public PaginationDTO list(Integer userId, Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();
        //总页数
        Integer totalPage;

        //查出问题的总条数
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(userId);
        Integer totalCount = (int)questionMapper.countByExample(questionExample);
        //计算总页数
        if(totalCount%size==0){
            totalPage = totalCount/size;
        }else{
            totalPage = totalCount/size+1;
        }

        if(page<1){
            page=1;
        }
        if(page>totalPage){
            page = totalPage;
        }
        //进行paginationDTO的封装操作
        paginationDTO.setPagination(totalPage, page);
        //size*(page-1)
        Integer offset = size*(page-1);
        //分页操作-当前页展示的问题数据
        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(userId);
        example.setOrderByClause("gmt_create desc");
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));

        List<QuestionDTO> questionDTOList = new ArrayList<>();
        //对QuestionDTO赋值
        for (Question question:questionList) {
            //通过question的creator返回user，因为question的creator字段就是user里面的id
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);
        return paginationDTO;
    }

    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if(question==null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();

        BeanUtils.copyProperties(question, questionDTO);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        /*当没有这个id的时候就是发布新的问题*/
        if(question.getId()==null){
            //创建

            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setViewCount(0);
            question.setLikeCount(0);
            question.setCommentCount(0);
            questionMapper.insertSelective(question);
            /*否则就是在原来的问题上进行修改更新该问题*/
        }else {
            //更新
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria().andIdEqualTo(question.getId());
            int updated = questionMapper.updateByExampleSelective(updateQuestion, questionExample);
            /*如果更新失败*/
            if(updated!=1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void incView(Integer id) {
        Question question= new Question();
        question.setId(id);
        /*这里不要用查询问题后再＋1的形式，不然并发情况会导致数据错误，使用直接在数据库中的数据+1的形式*/
        question.setViewCount(1);
        questionExtMapper.incView(question);
    }

    public List<QuestionDTO> selectRelated(QuestionDTO queryDTO) {
        if (StringUtils.isBlank(queryDTO.getTag())) {
            return new ArrayList<>();
        }
        String[] tags = StringUtils.split(queryDTO.getTag(), ",");
        String regexpTag = Arrays
                .stream(tags)
                .filter(StringUtils::isNotBlank)
                .map(t -> t.replace("+", "").replace("*", "").replace("?", ""))
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(queryDTO.getId());
        question.setTag(regexpTag);

        List<Question> questions = questionExtMapper.selectRelated(question);
        List<QuestionDTO> questionDTOS = questions.stream().map(q -> {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q, questionDTO);
            return questionDTO;
        }).collect(Collectors.toList());
        return questionDTOS;
    }
}
