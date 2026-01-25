package top.toobee.spring.utils;

import it.unimi.dsi.fastutil.ints.*;
import it.unimi.dsi.fastutil.objects.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.atomic.AtomicReference;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public final class RolePermissionData implements ApplicationRunner {
    private record PermData(
            LocalDateTime lastUpdateTime,
            Int2ReferenceMap<GrantedAuthority> perms,
            Int2ObjectMap<@NonNull String> roleName,
            Int2IntMap roleParent,
            Int2ReferenceMap<ReferenceSet<GrantedAuthority>> rolePerms) {}

    private final JdbcTemplate jdbc;
    private final AtomicReference<PermData> data = new AtomicReference<>();

    @Autowired
    public RolePermissionData(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void run(@NonNull ApplicationArguments args) throws DataAccessException {
        refresh();
    }

    private static @NonNull ReferenceSet<GrantedAuthority> dfs(
            Int2ReferenceMap<ReferenceSet<GrantedAuthority>> oldMap,
            Int2IntMap roleParent,
            Int2ReferenceMap<ReferenceSet<GrantedAuthority>> newMap,
            int i) {
        if (newMap.containsKey(i)) return ReferenceSet.of();
        final var set = oldMap.getOrDefault(i, new ReferenceOpenHashSet<>());
        newMap.put(i, set);
        if (roleParent.containsKey(i))
            set.addAll(dfs(oldMap, roleParent, newMap, roleParent.get(i)));
        return set;
    }

    public void refresh() throws DataAccessException {
        final Int2ReferenceMap<GrantedAuthority> perms = new Int2ReferenceOpenHashMap<>();
        jdbc.query(
                "SELECT id, name FROM permission.node",
                rs -> {
                    perms.put(rs.getInt(1), new SimpleGrantedAuthority(rs.getString(1)));
                    return null;
                });
        final Int2ObjectMap<@NonNull String> roleName = new Int2ObjectOpenHashMap<>();
        final Int2IntMap roleParent = new Int2IntOpenHashMap();
        jdbc.query(
                "SELECT id, name, parent_id FROM permission.role",
                rs -> {
                    int id = rs.getInt(1);
                    roleName.put(id, rs.getString(2));
                    int p = rs.getInt(3);
                    if (!rs.wasNull()) roleParent.put(id, p);
                    return null;
                });
        final Int2ReferenceMap<ReferenceSet<GrantedAuthority>> oldMap =
                new Int2ReferenceOpenHashMap<>();
        jdbc.query(
                "SELECT role_id, node_id FROM permission.role_node",
                rs -> {
                    oldMap.computeIfAbsent(rs.getInt(1), _ -> new ReferenceOpenHashSet<>())
                            .add(perms.get(rs.getInt(2)));
                    return null;
                });
        final Int2ReferenceMap<ReferenceSet<GrantedAuthority>> newMap =
                new Int2ReferenceOpenHashMap<>(roleName.size());
        roleName.keySet().forEach(id -> dfs(oldMap, roleParent, newMap, id));
        data.set(
                new PermData(
                        LocalDateTime.now(ZoneId.systemDefault()),
                        perms,
                        roleName,
                        roleParent,
                        newMap));
    }

    public @NonNull ReferenceSet<GrantedAuthority> getPerms(int roleId) {
        final var d = data.get();
        if (d == null) return ReferenceSet.of();
        return d.rolePerms.getOrDefault(roleId, ReferenceSet.of());
    }
}
