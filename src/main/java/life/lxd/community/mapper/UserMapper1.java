package life.lxd.community.mapper;

import life.lxd.community.model.User;
import org.apache.ibatis.annotations.*;

/*
* 如果参数类型是类的时候自动放进去
* 如果不是的话，就需要加@param注解*/
@Mapper
public interface UserMapper1 {
    @Insert("insert into user(name,account_id,token,gmt_create,gmt_modified,avatar_url)" +
            "values(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);

    @Select("select * from user where token=#{token}")
    User findByToken(@Param("token") String tooken);
    @Select("select * from user where id=#{id}")
    User findById(@Param("id")Integer id);
    @Select("select * from user where account_id=#{accountId}")
    User findByAccountId(@Param("accountId")String accountId);
    @Update("update user set name=#{name},token=#{token},gmt_modified=#{gmtModified},avatar_url=#{avatarUrl} where id=#{id}")
    void update(User user);
}

