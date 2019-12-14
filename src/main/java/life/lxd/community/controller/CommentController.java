package life.lxd.community.controller;

import life.lxd.community.dto.CommentCreateDTO;
import life.lxd.community.dto.ResultDTO;
import life.lxd.community.exception.CustomizeErrorCode;
import life.lxd.community.mapper.CommentMapper;
import life.lxd.community.model.Comment;
import life.lxd.community.model.User;
import life.lxd.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @ResponseBody
    @PostMapping("/comment")
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        //注意这里我内容打错英文用的是cotent
        comment.setCotent(commentCreateDTO.getCotent());
        comment.setType(commentCreateDTO.getType());
        comment.setLikeCount(0L);
        comment.setGmtModified(System.currentTimeMillis());
        comment.setGmtCreator(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        commentService.insert(comment);
        return ResultDTO.okOf();
    }
}
