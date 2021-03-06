package life.lxd.community.dto;

import life.lxd.community.model.User;
import lombok.Data;

/**
 * Created by codedrinker on 2019/12/2.
 * 这是从服务端到客户端的数据类型
 */
@Data
public class CommentDTO {
    private Integer id;
    private Integer parentId;
    private Integer type;
    private Integer commentator;
    private Integer commentCount;
    private Long gmtCreator;
    private Long gmtModified;
    private Long likeCount;
    private String cotent;
    private User user;
}
