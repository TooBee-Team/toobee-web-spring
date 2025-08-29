package top.toobee.spring.domain.model;

import java.io.Serializable;
import java.util.Objects;

public class Group implements Serializable {
    public Integer projectId;
    public Integer playerId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return projectId.equals(group.projectId) && playerId.equals(group.playerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, playerId);
    }

}
