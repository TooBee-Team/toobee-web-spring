package top.toobee.spring.domain.model;

import java.util.Collection;
import java.util.Set;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public final class UserDetailsImpl implements UserDetails {
    private final String username;
    private final String password;
    private final Set<GrantedAuthority> authorities;
    private final boolean nonLocked;

    public UserDetailsImpl(
            String username,
            String password,
            Set<GrantedAuthority> authorities,
            boolean nonLocked) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.nonLocked = nonLocked;
    }

    @Override
    public @NonNull Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public @Nullable String getPassword() {
        return password;
    }

    @Override
    public @NonNull String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonLocked() {
        return nonLocked;
    }
}
