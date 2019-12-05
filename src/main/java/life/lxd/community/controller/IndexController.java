package life.lxd.community.controller;

import life.lxd.community.mapper.UserMapper;
import life.lxd.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/*
* reponse 可以addcookie返回到浏览器
* request 可以getcookie获取到cookie
*
* */
@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;

    /*
    * 这一步做的操作就是为了服务器每次重新启动的时候，从本地浏览器中判断token的用户是否存在
    * 如果存在直接显示我，如果没有在授权登录*/
    @GetMapping("/")
    public String index(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie:cookies) {
            if(cookie.getName().equals("token")){
                String tooken = cookie.getValue();
                User user = userMapper.findByToken(tooken);
                if(user!=null) {
                    request.getSession().setAttribute("user", user);
                }
                break;
            }
        }
        return "index";
    }
}
