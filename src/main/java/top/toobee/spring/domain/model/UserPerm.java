package top.toobee.spring.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserPerm implements Serializable {
    @Column(name = "user_id")
    public Integer userId;

    @Column(name = "perm_id")
    public Integer permId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPerm userPerm = (UserPerm) o;
        return userId.equals(userPerm.userId) && permId.equals(userPerm.permId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, permId);
    }

    public UserPerm() {}

    public UserPerm(Integer userId, Integer permId) {
        this.userId = userId;
        this.permId = permId;
    }
}
