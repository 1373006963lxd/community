package life.lxd.community.controller;

import life.lxd.community.dto.CommentCreateDTO;
import life.lxd.community.dto.CommentDTO;
import life.lxd.community.dto.ResultDTO;
import life.lxd.community.enums.CommentTypeEnum;
import life.lxd.community.exception.CustomizeErrorCode;
import life.lxd.community.mapper.CommentMapper;
import life.lxd.community.model.Comment;
import life.lxd.community.model.User;
import life.lxd.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
        if(commentCreateDTO==null|| StringUtils.isBlank(commentCreateDTO.getCotent())){
            return ResultDTO.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }
        /*创建评论插入到数据库中*/
        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        //注意这里我内容打错英文用的是cotent
        comment.setCotent(commentCreateDTO.getCotent());
        comment.setType(commentCreateDTO.getType());
        comment.setLikeCount(0L);
        comment.setGmtModified(System.currentTimeMillis());
        comment.setGmtCreator(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setCommentCount(0);
        commentService.insert(comment,user);
        return ResultDTO.okOf();
    }

    @ResponseBody
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public ResultDTO<List<CommentDTO>> comments(@PathVariable(name = "id") Integer id) {
        List<CommentDTO> commentDTOS = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
        return ResultDTO.okOf(commentDTOS);
    }
}
