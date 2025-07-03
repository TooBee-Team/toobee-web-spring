package top.toobee.spring.service.impl;

import ch.qos.logback.core.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import top.toobee.spring.dto.ResultInfo;
import top.toobee.spring.dto.TBUser;
import top.toobee.spring.mapper.UserMapper;
import top.toobee.spring.service.UserService;

import java.util.UUID;
@Service
public class UserServiceImpl implements UserDetailsService , UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TBUser TBUser = userMapper.findByUsername(username);
        if (TBUser == null) {
            throw new UsernameNotFoundException("该用户未注册");
        }
        return User.withUsername(TBUser.getUsername())
                .password(TBUser.getPassword())
                .roles("USER")
                .build();
    }

    @Override
    public ResultInfo register(TBUser user) {
        TBUser tbUser = new TBUser();
        String str = "^[a-zA-Z0-9_.]{2,20}";
        if (!user.getUsername().matches(str)) {
            return new ResultInfo(false,"用户名格式错误");
        }
        if (userMapper.findByUsername(user.getUsername()) != null) {
            return new ResultInfo(false,"该用户名已被注册");
        }
        tbUser.setUsername(user.getUsername());
        tbUser.setUuid(UUID.randomUUID());
        tbUser.setEmail(user.getEmail());
        tbUser.setQq(user.getQq());
        tbUser.setPassword(user.getPassword());
        tbUser.setWechat(user.getWechat());
        tbUser.setTelegram(user.getTelegram());
        userMapper.insert(tbUser);
        return new ResultInfo(true,"注册成功");
    }

    @Override
    public ResultInfo login(String username, String password) {
        TBUser user = userMapper.findByUsername(username);
        if (user == null) {
            return new ResultInfo(false,"该用户未注册");
        }
        if (!(password.equals(user.getPassword()))) {
            return new ResultInfo(false,"用户名或密码错误");
        }
        return new ResultInfo(true,"用户："+username+"欢迎登录");
    }

    @Override
    public ResultInfo updateQQ(TBUser user) {
        TBUser tbUser = userMapper.findByUsername(user.getUsername());
        if (tbUser == null) {
            return new ResultInfo(false,"该用户未注册");
        }
        String qqRegex = "^[1-9][0-9]{4,14}$";
        if (!user.getQq().toString().matches(qqRegex)) {
            return new ResultInfo(false, "QQ号格式错误");
        }
        userMapper.updateQQ(user.getUsername(), user.getQq());
        return new ResultInfo(true,"QQ号更新成功");
    }

    @Override
    public ResultInfo updateWechat(TBUser user) {
        TBUser tbUser = userMapper.findByUsername(user.getUsername());
        if (tbUser == null) {
            return new ResultInfo(false,"该用户未注册");
        }
        userMapper.updateWechat(user.getUsername(), user.getWechat());
        return new ResultInfo(true,"微信号更新成功");
    }

    @Override
    public ResultInfo updateTelegram(TBUser user) {
        TBUser tbUser = userMapper.findByUsername(user.getUsername());
        if (tbUser == null) {
            return new ResultInfo(false,"该用户未注册");
        }
        userMapper.updateTelegram(user.getUsername(), user.getTelegram());
        return new ResultInfo(true,"Telegram号更新成功");
    }

    @Override
    public ResultInfo updateEmail(TBUser user) {
        TBUser tbUser = userMapper.findByUsername(user.getUsername());
        if (tbUser == null) {
            return new ResultInfo(false,"该用户未注册");
        }
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (!user.getEmail().matches(emailRegex)) {
            return new ResultInfo(false, "邮箱格式错误");
        }
        userMapper.updateEmail(user.getUsername(), user.getEmail());
        return new ResultInfo(true,"邮箱更新成功");
    }

    @Override
    public ResultInfo updatePassword(TBUser user) {
        TBUser tbUser = userMapper.findByUsername(user.getUsername());
        if (tbUser == null) {
            return new ResultInfo(false,"该用户未注册");
        }
        userMapper.updatePassword(user.getUsername(), user.getPassword());
        return new ResultInfo(true,"密码更新成功");
    }

    @Override
    public ResultInfo getUserInfo(String username) {
        TBUser user = userMapper.findByUsername(username);
        if(user == null) {
            return new ResultInfo(false,"该用户不存在");
        }

        return new ResultInfo(true,"查询成功",user);
    }

    @Override
    public ResultInfo updateUserInfo(TBUser tbUser) {
        TBUser user = userMapper.findByUsername(tbUser.getUsername());
        if (user==null) {
            return new ResultInfo(false,"该用户不存在");
        }
        userMapper.updateUserInfo(tbUser);
        return new ResultInfo(true,"更新成功");
    }
}
