package life.lxd.community.service;

import life.lxd.community.dto.PaginationDTO;
import life.lxd.community.dto.QuestionDTO;
import life.lxd.community.mapper.QuestionMapper;
import life.lxd.community.mapper.UserMapper;
import life.lxd.community.model.Question;
import life.lxd.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    public PaginationDTO list(Integer page, Integer size) {


        PaginationDTO paginationDTO = new PaginationDTO();
        //查出问题的总条数
        Integer totalCount = questionMapper.count();
        //进行paginationDTO的封装操作
        paginationDTO.setPagination(totalCount, page,size);
        if(page<1){
            page=1;
        }
        if(page>paginationDTO.getTotalPage()){
            page = paginationDTO.getTotalPage();
        }
        //size*(page-1)
        Integer offset = size*(page-1);
        //分页操作-当前页展示的问题数据
        List<Question> questionList = questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        //对QuestionDTO赋值
        for (Question question:questionList) {
            //通过question的creator返回user，因为question的creator字段就是user里面的id
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);
        return paginationDTO;
    }

    public PaginationDTO list(Integer accountId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        //查出问题的总条数
        Integer totalCount = questionMapper.countByUserId(accountId);
        //进行paginationDTO的封装操作
        paginationDTO.setPagination(totalCount, page,size);
        if(page<1){
            page=1;
        }
        if(page>paginationDTO.getTotalPage()){
            page = paginationDTO.getTotalPage();
        }
        //size*(page-1)
        Integer offset = size*(page-1);
        //分页操作-当前页展示的问题数据
        List<Question> questionList = questionMapper.listByUserId(accountId,offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        //对QuestionDTO赋值
        for (Question question:questionList) {
            //通过question的creator返回user，因为question的creator字段就是user里面的id
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);
        return paginationDTO;
    }
}
