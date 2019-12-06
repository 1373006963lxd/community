package life.lxd.community.controller;

import life.lxd.community.dto.PaginationDTO;
import life.lxd.community.dto.QuestionDTO;
import life.lxd.community.mapper.QuestionMapper;
import life.lxd.community.mapper.UserMapper;
import life.lxd.community.model.Question;
import life.lxd.community.model.User;
import life.lxd.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/*
* reponse 可以addcookie返回到浏览器
* request 可以getcookie获取到cookie
*
* */
@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;

    /*
    * 这一步做的操作就是为了服务器每次重新启动的时候，从本地浏览器中判断token的用户是否存在
    * 如果存在直接显示我，如果没有在授权登录*/
    @GetMapping("/")
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(name="page",defaultValue = "1") Integer page,
                        @RequestParam(name="size",defaultValue = "2") Integer size){
        Cookie[] cookies = request.getCookies();
        if(cookies!=null&&cookies.length!=0){
        for (Cookie cookie:cookies) {
            if (cookie.getName().equals("token")) {
                String tooken = cookie.getValue();
                User user = userMapper.findByToken(tooken);
                if (user != null) {
                    request.getSession().setAttribute("user", user);
                }
                break;
            }
        } }
        //实现首页展示发表问题列表，主要问题出现头像，需要和question关联，
        //因为question里面没有头像，需要与用户表关联，但是数据库中都是字段，没有引入对象
        //所以引入了DTO,又因为之前用questionMapper返回的是question对象，所以引入了service层来做
        PaginationDTO pagination = questionService.list(page,size);
        model.addAttribute("pagination", pagination);
        return "index";
    }
}
