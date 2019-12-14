package life.lxd.community.mapper;

import life.lxd.community.model.Question;

public interface QuestionExtMapper {
    int incView(Question record);
    int incCommentCount(Question record);
}