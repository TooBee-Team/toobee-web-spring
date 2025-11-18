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
        return this == o
                || (o instanceof Group g
                        && projectId.equals(g.projectId)
                        && playerId.equals(g.playerId));
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
}
