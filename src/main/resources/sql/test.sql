INSERT INTO account.perm_node (name, description)
VALUES
    ('permission:control', '权限控制'),
    ('user:lock', '用户锁定'),
    ('user:query', '用户查询'),
    ('comment:normal', '普通评论'),
    ('questionnaire:review', '问卷审核'),
    ('vote:cast', '投票'),
    ('vote:create', '发起投票'),
    ('log:create', '日志创建'),
    ('issue:report', '问题上报');

INSERT INTO account.perm_role (name, description)
VALUES
    ('developer', '开发者'),
    ('admin', '管理员'),
    ('member', '星标用户'),
    ('user', '普通用户'),
    ('guest', '访客');

UPDATE account.perm_role 
SET parent_id = (SELECT id FROM account.perm_role WHERE name = 'guest') 
WHERE name = 'user';
UPDATE account.perm_role 
SET parent_id = (SELECT id FROM account.perm_role WHERE name = 'user') 
WHERE name = 'member';
UPDATE account.perm_role 
SET parent_id = (SELECT id FROM account.perm_role WHERE name = 'member') 
WHERE name = 'admin';
UPDATE account.perm_role 
SET parent_id = (SELECT id FROM account.perm_role WHERE name = 'admin') 
WHERE name = 'developer';

INSERT INTO account.perm_role_node(role_id, node_id)
SELECT r.id, n.id
FROM (
  VALUES
    ('developer','permission:control'),
    ('admin','user:lock'),
    ('admin', 'questionnaire:review'),
    ('member','vote:create'),
    ('member','log:create'),
    ('user','vote:cast'),
    ('user','issue:report'),
    ('guest','comment:normal'),
    ('guest', 'user:query')
) AS t(role_name, node_name)
JOIN account.perm_role r ON r.name = t.role_name
JOIN account.perm_node n ON n.name = t.node_name
ON CONFLICT (role_id, node_id) DO NOTHING;
