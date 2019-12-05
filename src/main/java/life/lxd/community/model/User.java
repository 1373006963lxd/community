package life.lxd.community.model;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    //数据库中是bigint类型
    private Long gmtCreate;
    private Long gmtModified;
}
