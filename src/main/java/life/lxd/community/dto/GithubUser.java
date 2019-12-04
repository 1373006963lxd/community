package life.lxd.community.dto;

import lombok.Data;

/**
 * Created by codedrinker on 2019/12/4.
 * dto一般就是用在向前端传递数据过多时，进行封装
 */
@Data
public class GithubUser {
    private String name;
    private Long id;
    private String bio;
    private String avatarUrl;
}
