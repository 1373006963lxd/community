package life.lxd.community.controller;

import life.lxd.community.dto.AccessTokenDTO;
import life.lxd.community.dto.GithubUser;
import life.lxd.community.mapper.UserMapper;
import life.lxd.community.model.User;
import life.lxd.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Autowired
    private UserMapper userMapper;
    //读取配置文件中的值赋值给参数
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                            HttpServletRequest request,HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        //申请的
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        //通过github回调给用户端的github用户的code
        accessTokenDTO.setCode(code);
        //从github中跳转到社区
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        //通过这个参数封装好的对象，获得access_token
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        //通过access_token获得搜权用户
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if(githubUser!=null&&githubUser.getId()!=null){
            //登录成功。写cookie和session
            User user = new User();
            user.setName(githubUser.getName());
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setAvatarUrl(githubUser.getAvatarUrl());
            //插入数据库的过程就相当于写入了session
            userMapper.insert(user);
            response.addCookie(new Cookie("token", token));
//            request.getSession().setAttribute("user",githubUser);
            return "redirect:/";
        }else{
            //登录失败
            return "redirect:/";
        }
//        System.out.println(user.getName());
//        return "index";
    }
}
