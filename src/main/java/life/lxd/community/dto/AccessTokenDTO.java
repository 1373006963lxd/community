package life.lxd.community.dto;

import lombok.Data;

/**
 * Created by codedrinker on 2019/12/4.
 * 当传递的参数超过两个以上时，用dto的形式封装成对象
 * 2. Users are redirected back to your site by GitHub
 */
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;

}

