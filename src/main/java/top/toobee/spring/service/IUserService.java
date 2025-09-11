package top.toobee.spring.service;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UserDetailsService;
import top.toobee.spring.domain.response.ChangePasswordResult;
import top.toobee.spring.domain.response.LoginResult;
import top.toobee.spring.entity.UserEntity;

import java.net.InetAddress;

public interface IUserService extends UserDetailsService {
    @Nullable UserEntity findUserById(int id);

    @Nullable UserEntity findUserByName(String name);

    record LoginPair(LoginResult result, @Nullable UserEntity entity) {}

    /**
     * 检查用户登录的预期行为，并返回用户信息
     * @param ip 登录IP
     * @param name 用户名
     * @param password 密码
     * @return 预期的登录结果
     */
    LoginPair getLoginInfo(InetAddress ip, String name, String password);

    ChangePasswordResult changePassword(String token, String oldPassword, String newPassword);

    /**
     * 在数据库中注册用户，注册前校验用户名
     * @param name 用户名
     * @param encodedPassword 处理后的密码
     * @return 注册后的用户实体
     * @throws IllegalArgumentException 用户名非法
     * @throws DuplicateKeyException 用户名已存在
     */
    UserEntity register(String name, String encodedPassword) throws IllegalArgumentException, DuplicateKeyException;

    /**
     * 登录后，记录登录信息，执行缓存等
     * @param ip 登录IP
     * @param name 用户名
     */
    void afterLogin(InetAddress ip, String name);

    /**
     * 生成JWT令牌
     */
    String signAndIssueToken(String name, InetAddress ip);

    /**
     * 从JWT令牌中获取用户实体
     */
    UserEntity getUserFromToken(String token);

    /**
     * 尝试登录并获取JWT令牌全过程
     */
    ResponseEntity<?> tryLoginAndGetToken(InetAddress ip, String username, String password);
}
