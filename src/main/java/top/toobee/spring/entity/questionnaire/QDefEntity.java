package top.toobee.spring.entity.questionnaire;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import tools.jackson.databind.JsonNode;

@Entity
@Table(schema = "questionnaire", name = "def")
public class QDefEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @Column(updatable = false, nullable = false)
    public String name;

    @Column(updatable = false, nullable = false)
    public Integer version;

    @Column(name = "is_current", nullable = false)
    public Boolean isCurrent;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb", nullable = false)
    public JsonNode structure;

    @Column(name = "created_at", nullable = false)
    public LocalDateTime createdAt;
}
