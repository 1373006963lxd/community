package life.lxd.community.mapper;

import life.lxd.community.model.Comment;

public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}