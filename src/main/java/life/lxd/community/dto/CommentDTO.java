package life.lxd.community.dto;

import lombok.Data;

@Data
public class CommentDTO {
    private Integer parentId;
    private String cotent;
    private Integer type;
}

