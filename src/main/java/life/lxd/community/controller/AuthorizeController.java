package life.lxd.community.controller;

import life.lxd.community.dto.AccessTokenDTO;
import life.lxd.community.dto.GithubUser;
import life.lxd.community.model.User;
import life.lxd.community.provider.GithubProvider;
import life.lxd.community.service.UserService;
import lombok.extern.slf4j.Slf4j;
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
//做项目的时候打印日志
@Slf4j
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Autowired
    private UserService userService;
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
        //参数操作两个以上一般就要封装成对象去做
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();

        //申请的下面参数的设置可以使用构造函数来实现直接在new 中传参
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        //通过github回调给用户端的github用户的code
        log.info(code);
        accessTokenDTO.setCode(code);
        //从github中跳转到社区
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);

        //通过这个参数封装好的对象，获得access_token
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        //通过access_token获得搜权用户
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if(githubUser!=null&&githubUser.getId()!=null){
            //登录成功。写cookie（银行卡）和session（银行注册你的银行卡信息）
            User user = new User();
            user.setName(githubUser.getName());
            //以token为主来绑定前端和服务端的依据
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setAccountId(String.valueOf(githubUser.getId()));

            user.setAvatarUrl(githubUser.getAvatarUrl());

            //插入数据库的过程就相当于写入了session
            userService.createOrUpdate(user);
            //登录成功以后将token写入cookie中
            response.addCookie(new Cookie("token", token));
            //redirect返回的是路径不能写index
            return "redirect:/";
        }else{
            log.error("callback get github error,{}",githubUser);
            //登录失败
            return "redirect:/";
        }
//        System.out.println(user.getName());
//        return "index";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
