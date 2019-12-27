package life.lxd.community.interceptor;


import life.lxd.community.enums.AdPosEnum;
import life.lxd.community.mapper.UserMapper;
import life.lxd.community.model.User;
import life.lxd.community.model.UserExample;
import life.lxd.community.service.AdService;
import life.lxd.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by codedrinker on 2019/12/16.
 */
@Service
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private AdService adService;
    @Value("${github.redirect.uri}")
    private String redirectUrl;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //设置context级别属性
        request.getServletContext().setAttribute("redirectUrl", redirectUrl);
        //没有登录的时候可以查看导航
        for(AdPosEnum adPos:AdPosEnum.values()) {
            request.getServletContext().setAttribute(adPos.name(), adService.list(adPos.name()));
        }
       /* HttpSession session = request.getSession();
        session.setAttribute("ads", adService.list());*/

//       将token写入数据库后，通过前端的cookie去数据库中查询是否有这个用户数据，
//        如果有则将user写入session中，前端就可以通过session展示用户名还是登录
        Cookie[] cookies = request.getCookies();
        if(cookies!=null&&cookies.length!=0){
            for (Cookie cookie:cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    UserExample userExample = new UserExample();
                    userExample.createCriteria()
                            .andTokenEqualTo(token);
                    List<User> users = userMapper.selectByExample(userExample);
                    if (users.size() != 0) {
                        HttpSession session = request.getSession();
                        //注册用户信息---如果没有指定cookie，会自动生成一个JSESSIONID，然后在再次请求的时候会带着这个去session中查找是否有用户
                        session.setAttribute("user", users.get(0));
                        Long unreadCount = notificationService.unreadCount(users.get(0).getId());
                        session.setAttribute("unreadCount", unreadCount);
                    }
                    break;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

    }
}
