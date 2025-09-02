package top.toobee.spring.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class Group implements Serializable {
    @Column(name = "project_id")
    public Integer projectId;

    @Column(name = "player_id")
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

    public Group() {}
}
