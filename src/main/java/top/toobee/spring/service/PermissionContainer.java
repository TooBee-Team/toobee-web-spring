package top.toobee.spring.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import top.toobee.spring.entity.PermissionEntity;
import top.toobee.spring.repository.PermissionRepository;

public class PermissionContainer {
    private final Map<Integer, PermissionEntity> permissionById = new ConcurrentHashMap<>();
    private final Map<String, PermissionEntity> permissionByName = new ConcurrentHashMap<>();
    private final Set<PermissionEntity> allPermissions = ConcurrentHashMap.newKeySet();
    private final Set<String> permissionNames = ConcurrentHashMap.newKeySet();

    public PermissionContainer(PermissionRepository permissionRepository) {
        loadPermissions(permissionRepository);
    }

    private void loadPermissions(PermissionRepository permissionRepository) {
        List<PermissionEntity> permissions = permissionRepository.findAll();
        permissions.forEach(
                permission -> {
                    permissionById.put(permission.id, permission);
                    permissionByName.put(permission.getName(), permission);
                    allPermissions.add(permission);
                    permissionNames.add(permission.getName());
                });
        System.out.println("已加载 " + permissions.size() + " 个权限");
    }
}
