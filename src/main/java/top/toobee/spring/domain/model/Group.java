package top.toobee.spring.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import top.toobee.spring.entity.PlayerEntity;
import top.toobee.spring.entity.game_project.ItemEntity;

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

    public Group(Integer projectId, Integer playerId) {
        this.projectId = projectId;
        this.playerId = playerId;
    }

    public Group(Integer projectId, Integer playerId) {
        this.projectId = projectId;
        this.playerId = playerId;
    }
}
