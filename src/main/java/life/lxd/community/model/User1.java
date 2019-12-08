package life.lxd.community.model;

import lombok.Data;

@Data
public class User1 {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    //数据库中是bigint类型
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;
}
