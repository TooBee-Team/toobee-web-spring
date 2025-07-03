package top.toobee.spring.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
@Data
@NoArgsConstructor
public class LoginUser implements UserDetails {

    private TBUser user;
    public LoginUser(TBUser user) {
        this.user = user;
    }
    @Override
    //权限列表
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    //账户是否未过期
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    //账户是否未锁定
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    //密码是否未过期
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    //账户是否可用
    public boolean isEnabled() {
        return true;
    }
}
