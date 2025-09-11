package top.toobee.spring.service;

import org.graalvm.collections.Pair;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UserDetailsService;
import top.toobee.spring.domain.response.LoginTokenResponse;
import top.toobee.spring.entity.UserEntity;

import java.net.InetAddress;

public interface IUserService extends UserDetailsService {
    @Nullable UserEntity findById(int id);

    @Nullable UserEntity findByName(String name);

    /**
     * 注册用户，注册前校验用户名
     * @param name 用户名
     * @param encodedPassword 处理后的密码
     * @return 注册后的用户实体
     * @throws IllegalArgumentException 用户名非法
     * @throws DuplicateKeyException 用户名已存在
     */
    @NonNull UserEntity register(@NonNull String name, @NonNull String encodedPassword) throws IllegalArgumentException, DuplicateKeyException;

    /**
     * 登录用户，登录前校验用户名和密码
     * @param ip 登录IP
     * @param name 用户名
     * @param originalPassword 原始密码
     * @return 登录成功后的JWT
     */
    Pair<LoginTokenResponse, String> login(@Nullable InetAddress ip, @NonNull String name, @NonNull String originalPassword);
}
