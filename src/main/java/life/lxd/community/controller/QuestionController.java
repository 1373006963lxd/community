package life.lxd.community.controller;

import life.lxd.community.dto.CommentDTO;
import life.lxd.community.dto.QuestionDTO;
import life.lxd.community.enums.CommentTypeEnum;
import life.lxd.community.service.CommentService;
import life.lxd.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id,
    Model model){
        //查询出该问题
        QuestionDTO questionDTO = questionService.getById(id);
        //根据当前问题的标签查询出所有与该标签相关的问题
        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);
        //查询出当前问题的评论
        List<CommentDTO> comments = commentService.listByTargetId(id,CommentTypeEnum.QUESTION);
        questionService.incView(id);
        model.addAttribute("question", questionDTO);
        model.addAttribute("comments", comments);
        model.addAttribute("relatedQuestions",relatedQuestions);
        return "question";
    }
}

