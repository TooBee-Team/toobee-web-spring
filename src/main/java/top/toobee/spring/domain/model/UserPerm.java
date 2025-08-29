package top.toobee.spring.domain.model;

import java.io.Serializable;
import java.util.Objects;

public class UserPerm implements Serializable {
    public Integer userId;
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
}
