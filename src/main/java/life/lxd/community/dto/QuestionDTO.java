package life.lxd.community.dto;

import life.lxd.community.model.User;
import lombok.Data;

/**
 * Created by codedrinker on 2019/5/7.
 */
@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private User user;
}
