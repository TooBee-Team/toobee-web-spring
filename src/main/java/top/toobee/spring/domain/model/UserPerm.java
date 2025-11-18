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
        return this == o
                || (o instanceof UserPerm p && userId.equals(p.userId) && permId.equals(p.permId));
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
