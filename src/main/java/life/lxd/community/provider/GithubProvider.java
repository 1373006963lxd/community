package life.lxd.community.provider;
import com.alibaba.fastjson.JSON;
import life.lxd.community.dto.AccessTokenDTO;
import life.lxd.community.dto.GithubUser;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Component;

/**
 * Created by codedrinker on 2019/12/14.
 */
@Component
@Slf4j
public class GithubProvider {
    //查看okHttp网站有获取方式
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        //将对象转化为json---》引入jar包fastjson
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        //调用github获取access_token，也可以通过在github上面直接生成个人access_token，进行测试，调用user api获取用户信息
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            System.out.println(string); //看一下格式
            //access_token=05bf84512692f1d64d42ee0cc558e2bbc0224fef&scope=user&token_type=bearer
            //对其进行拆分获得想要的token 以&拆分获得access_token=05bf84512692f1d64d42ee0cc558e2bbc0224fef
            //再以=拆分获得=后面的token
            String token = string.split("&")[0].split("=")[1];
            return token;
        } catch (Exception e) {
            log.error("getAccessToken error,{}", accessTokenDTO, e);
        }
        return null;
    }


    //通过获得的access_token获取到github用户的一些信息，根据okhttp和OAuth_app(github上)做
    public GithubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        try {
            //json格式可以在github上Personal access tokens中生成access_token进行测试
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            //将json转化为类对象
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (Exception e) {
            log.error("getUser error,{}", accessToken, e);
        }
        return null;
    }

}
