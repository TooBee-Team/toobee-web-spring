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
     * 从游戏中验证密码
     * @param name 用户名
     * @param password 密码
     * @return 是否密码正确，空值表示该玩家未注册
     */
    @Nullable Boolean verifyPasswordFromGame(String name, String password);

    /**
     * 检查用户登录的预期行为，并返回用户信息。
     * 先校验用户名合法性，寻找当前数据库有无该用户。如果无用户，调用 {@link #verifyPasswordFromGame} 决定是否要通过 {@link #register} 注册用户。
     * 然后返回可能包含用户实体的登录结果。
     * @param ip 登录IP
     * @param name 用户名
     * @param password 密码
     * @return 预期的登录结果
     */
    LoginPair getLoginInfo(InetAddress ip, String name, String password);

    /**
     * 尝试修改密码，要求新密码与旧密码不同，且新密码符合密码规则（长度4～30）
     * @param token 登录时获取的JWT令牌
     * @param oldOriginalPassword 旧原始密码
     * @param newOriginalPassword 新原始密码
     * @return 密码修改结果
     */
    ChangePasswordResult changePassword(String token, String oldOriginalPassword, String newOriginalPassword);

    /**
     * 在数据库中注册用户，注册前校验用户名
     * @param name 用户名
     * @param originalPassword 原始密码
     * @return 注册后的用户实体
     * @throws IllegalArgumentException 用户名非法
     * @throws DuplicateKeyException 用户名已存在
     */
    UserEntity register(String name, String originalPassword) throws IllegalArgumentException, DuplicateKeyException;

    /**
     * 登录后，记录登录日志，录入缓存等
     * @param ip 登录IP
     * @param name 用户名
     * @return 随机生成的 Token 校验码
     */
    byte afterLogin(InetAddress ip, String name);

    /**
     * 生成JWT令牌
     * @param name 用户名
     * @param ip 登录IP
     * @param mark Token 校验码
     * @return JWT令牌
     */
    String signAndIssueToken(String name, InetAddress ip, byte mark);

    /**
     * 从JWT令牌中获取用户实体
     */
    UserEntity getUserFromToken(String token);

    /**
     * 尝试登录并获取JWT令牌全过程
     */
    ResponseEntity<?> tryLoginAndGetToken(InetAddress ip, String username, String password);
}
