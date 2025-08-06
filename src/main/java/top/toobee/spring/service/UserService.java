package top.toobee.spring.service;

import top.toobee.spring.dto.ResultInfo;
import top.toobee.spring.dto.TBUser;

import java.util.UUID;

public interface UserService {
    ResultInfo register(TBUser user);

    ResultInfo login(String username, String password );

    ResultInfo updateQQ(TBUser user);

    ResultInfo updateWechat(TBUser user);

    ResultInfo updateTelegram(TBUser user);

    ResultInfo updateEmail(TBUser user);

    ResultInfo updatePassword(TBUser user);

<<<<<<< HEAD
    ResultInfo getUserInfo(int id);

    ResultInfo updateUserInfo(TBUser tbUser);

    ResultInfo findByEmail(String email);

    ResultInfo findById(int id);

    ResultInfo findByQq(String qq);

    ResultInfo findByWechat(String wechat);

    ResultInfo findByTelegram(String telegram);
=======
    ResultInfo getUserInfo(String username);

    ResultInfo updateUserInfo(TBUser tbUser);
>>>>>>> 12c385e32a27627a2527439f29ef196b8dd842f5
}
