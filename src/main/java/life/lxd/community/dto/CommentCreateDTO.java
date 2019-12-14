package life.lxd.community.dto;

import lombok.Data;

@Data
public class CommentCreateDTO {
    private Integer parentId;
    private String cotent;
    private Integer type;
}

