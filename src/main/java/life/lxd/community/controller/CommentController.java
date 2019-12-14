package life.lxd.community.controller;

import life.lxd.community.dto.CommentDTO;
import life.lxd.community.dto.ResultDTO;
import life.lxd.community.exception.CustomizeErrorCode;
import life.lxd.community.mapper.CommentMapper;
import life.lxd.community.model.Comment;
import life.lxd.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.jnlp.UnavailableServiceException;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CommentController {
    @Autowired
    private CommentMapper commentMapper;
    @ResponseBody
    @PostMapping("/comment")
    public Object post(@RequestBody CommentDTO commentDTO,
                       HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        //注意这里我内容打错英文用的是cotent
        comment.setCotent(commentDTO.getCotent());
        comment.setType(commentDTO.getType());
        comment.setLikeCount(0L);
        comment.setGmtModified(System.currentTimeMillis());
        comment.setGmtCreator(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        commentMapper.insert(comment);
        return ResultDTO.okOf();
    }
}
