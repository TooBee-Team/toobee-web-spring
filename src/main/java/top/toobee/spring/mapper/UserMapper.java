package top.toobee.spring.mapper;

import org.apache.ibatis.annotations.*;
import top.toobee.spring.dto.TBUser;
@Mapper
public interface UserMapper {
    @Select("select * from users where username = #{username}")
    TBUser findByUsername(String username);

    @Insert("insert into users (uuid, username, email, qq, password, wechat, telegram) values (#{uuid}, #{username}, #{email}, #{qq}, #{password}, #{wechat}, #{telegram})")
    void insert(TBUser TBUser);

    @Delete("delete from users where username = #{username}")
    void deleteByUsername(String username);

    //@Update("update users set username = #{username}, email = #{email}, qq = #{qq}, wechat = #{wechat}, telegram = #{telegram} where username = #{username}")
    void updateUserInfo(TBUser tbUser);

    @Update("update users set password = #{password} where username = #{username}")
    void updatePassword(String username, String password);

    @Update("update users set qq = #{qq} where username = #{username}")
<<<<<<< HEAD
    void updateQQ(String username, String qq);
=======
    void updateQQ(String username, Long qq);
>>>>>>> 12c385e32a27627a2527439f29ef196b8dd842f5

    @Update("update users set wechat = #{wechat} where username = #{username}")
    void updateWechat(String username, String wechat);

    @Update("update users set telegram = #{telegram} where username = #{username}")
    void updateTelegram(String username, String telegram);

    @Update("update users set email = #{email} where username = #{username}")
    void updateEmail(String username, String email);

<<<<<<< HEAD
    @Select("select * from users where telegram = #{telegram}")
    TBUser findByTelegram(String telegram);

    @Select("select * from users where qq = #{qq}")
    TBUser findByQq(String qq);

    @Select("select * from users where wechat = #{wechat}")
    TBUser findByWechat(String wechat);

    @Select("select * from users where email = #{email}")
    TBUser findByEmail(String email);

    @Select("select * from users where id = #{id}")
    TBUser findById(int id);

=======
>>>>>>> 12c385e32a27627a2527439f29ef196b8dd842f5
}
