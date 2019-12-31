package life.lxd.community.dto;

import lombok.Data;
/*
* 这是从页面到服务端的数据类型
* */
@Data
public class CommentCreateDTO {
    private Integer parentId;
    private String cotent;
    private Integer type;
}

