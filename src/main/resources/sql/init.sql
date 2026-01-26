CREATE SCHEMA game_project;

-- 项目贡献等级：所有者（核心发起人）、主要贡献者、参与贡献者
CREATE TYPE game_project.user_role AS ENUM ('OWNER', 'CONTRIBUTOR', 'PARTICIPANT');
-- 维度：主世界、下界、末地、大厅
CREATE TYPE game_project.world AS ENUM ('OVERWORLD', 'THE_NETHER', 'THE_END', 'LOBBY');
-- 联系方式类型：QQ、微信、Telegram、邮箱
CREATE TYPE account.contact_type AS ENUM ('QQ', 'WECHAT', 'TELEGRAM', 'EMAIL');
-- 问卷状态：待审核、通过、拒绝、完成、被覆盖
CREATE TYPE public.request_status AS ENUM ('PENDING', 'PASSED', 'REJECTED', 'COMPLETED', 'OVERRIDDEN');
-- 登录状态：成功、密码错误、账号被锁定、登出
CREATE TYPE account.login_status AS ENUM ('SUCCESS', 'PASSWORD_WRONG', 'ACCOUNT_LOCKED', 'LOGGED_OUT');

CREATE TABLE account.perm_node (
    id          serial PRIMARY KEY,
    name        varchar(64) NOT NULL UNIQUE,
    description text
); COMMENT ON TABLE account.perm_node IS '面向资源的权限节点';

CREATE TABLE account.perm_role (
    id          serial PRIMARY KEY,
    name        varchar(64) NOT NULL UNIQUE,
    parent_id   integer REFERENCES account.perm_role,
    description text
); COMMENT ON TABLE account.perm_role IS '用户角色';

CREATE TABLE account.perm_role_node ( -- non-updatable
    role_id integer NOT NULL REFERENCES account.perm_role,
    node_id integer NOT NULL REFERENCES account.perm_node,
    ctime   timestamp NOT NULL DEFAULT now(),
    UNIQUE (role_id, node_id)
); COMMENT ON TABLE account.perm_role_node IS '角色拥有的权限节点';

CREATE TABLE public.users (
    id              serial PRIMARY KEY,
    uuid            uuid NOT NULL UNIQUE DEFAULT gen_random_uuid(), -- non-updatable
    name            varchar(20) NOT NULL UNIQUE,
    password        varchar(512) NOT NULL,
    role_id         integer NOT NULL REFERENCES account.perm_role,
    locked          boolean NOT NULL DEFAULT false,
); COMMENT ON TABLE public.users IS '网站用户核心信息';

CREATE TABLE public.player (
    id              serial PRIMARY KEY,
    uuid            uuid NOT NULL UNIQUE,
    name            varchar(20) NOT NULL UNIQUE,
    fake            boolean NOT NULL DEFAULT false,
    user_id         integer REFERENCES public.users,
    ctime           timestamp NOT NULL DEFAULT now() -- non-updatable
); COMMENT ON TABLE public.player IS '游戏中的部分玩家';

CREATE TABLE account.register_request (
    id              serial PRIMARY KEY,
    ip              inet NOT NULL DEFAULT '0.0.0.0',
    username        varchar(20) NOT NULL,
    password        varchar(512) NOT NULL,
    contact_type    account.contact_type NOT NULL,
    contact_info    varchar(127) NOT NULL,
    reason          text,
    status          public.request_status NOT NULL DEFAULT 'PENDING',
    ctime           timestamp NOT NULL DEFAULT now()
); COMMENT ON TABLE account.register_request IS '用户注册申请';

CREATE TABLE account.contact_check (
    id              serial PRIMARY KEY,
    user_id         integer NOT NULL REFERENCES public.users,
    contact_type    account.contact_type NOT NULL,
    contact_info    varchar(127) NOT NULL,
    status          public.request_status NOT NULL DEFAULT 'PENDING',
    ctime           timestamp NOT NULL DEFAULT now(),
    checker_id      integer REFERENCES public.users,
    utime           timestamp
); COMMENT ON TABLE account.contact_check IS '用户联系方式审核';

CREATE TABLE public.user_profile (
    user_id         integer PRIMARY KEY REFERENCES public.users,
    ctime           timestamp NOT NULL DEFAULT now(), -- non-updatable
    main_player     integer UNIQUE REFERENCES public.player,
    email           varchar(127),
    qq              varchar(12),
    wechat          varchar(64),
    telegram        varchar(64),
    nickname        varchar(30),
    introduction    text
); COMMENT ON TABLE public.user_profile IS '网站用户资料';
COMMENT ON COLUMN public.user_profile.ctime IS '用户创建时间';
COMMENT ON COLUMN public.user_profile.nickname IS '昵称';
COMMENT ON COLUMN public.user_profile.introduction IS '自我介绍';
CREATE INDEX user_qq_index ON public.user_profile (qq);

CREATE TABLE account.user_login_log (
    id      bigserial PRIMARY KEY,
    user_id integer NOT NULL REFERENCES public.users,
    ip      inet NOT NULL DEFAULT '0.0.0.0',
    ctime   timestamp NOT NULL DEFAULT now(),
    status  account.login_status NOT NULL
); COMMENT ON TABLE account.user_login_log IS '用户登录日志';
CREATE INDEX user_login_log_user_id_index ON account.user_login_log (user_id);
CREATE INDEX user_login_log_ctime_index ON account.user_login_log (ctime);

CREATE TABLE game_project.type (
    id          serial PRIMARY KEY,
    name        varchar(64),
    description text
); COMMENT ON TABLE game_project.type IS '项目工程类型';

CREATE TABLE game_project.item (
    id               serial PRIMARY KEY,
    identifier       varchar(100) NOT NULL UNIQUE,
    name             varchar(100) NOT NULL,
    type_id          integer REFERENCES game_project.type,
    creator_id       integer NOT NULL REFERENCES users,
    webpage_ctime    timestamp NOT NULL DEFAULT now(), -- non-updatable
    webpage_utime    timestamp NOT NULL DEFAULT now(),
    project_ctime    date,
    project_utime    date,
    introduction     text,
    thumbnail        bytea,
    world            game_project.world NOT NULL DEFAULT 'overworld',
    x                integer NOT NULL,
    y                integer NOT NULL,
    z                integer NOT NULL
); COMMENT ON TABLE game_project.item IS '用以项目工程页面展示';
COMMENT ON COLUMN game_project.item.identifier IS '唯一标识符，仅允许小写英文、数字、下划线和横线';
COMMENT ON COLUMN game_project.item.name IS '项目名称，允许中文，原则上可重复';
COMMENT ON COLUMN game_project.item.type_id IS '项目类型';
COMMENT ON COLUMN game_project.item.creator_id IS '页面创建者';
COMMENT ON COLUMN game_project.item.webpage_ctime IS '项目页面的创建时间';
COMMENT ON COLUMN game_project.item.webpage_utime IS '项目在页面中的最后更新时间';
COMMENT ON COLUMN game_project.item.project_ctime IS '项目在游戏中的创建时间';
COMMENT ON COLUMN game_project.item.project_utime IS '项目在游戏中的最后更新时间';
COMMENT ON COLUMN game_project.item.introduction IS '文字介绍';
COMMENT ON COLUMN game_project.item.thumbnail IS '缩略图二进制数据';
COMMENT ON COLUMN game_project.item.world IS '项目工程所在维度，再加上三个坐标，标识项目在游戏中的位置';

CREATE TABLE game_project.group (
    project_id  integer NOT NULL REFERENCES game_project.item,
    player_id   integer NOT NULL REFERENCES public.player,
    role        game_project.user_role NOT NULL DEFAULT 'owner',
    UNIQUE (project_id, player_id)
); COMMENT ON TABLE game_project.group IS '每个项目维护组的用户信息，及其所扮演的角色';

CREATE TABLE game_project.like (
    id          serial PRIMARY KEY,
    user_id     integer NOT NULL REFERENCES public.users,
    project_id  integer NOT NULL REFERENCES game_project.item,
    ctime       timestamp NOT NULL DEFAULT now(),
    UNIQUE (user_id, project_id)
); COMMENT ON TABLE game_project.like IS '项目点赞记录';

CREATE TABLE questionnaire.def (
    id          serial PRIMARY KEY,
    name        varchar(40) NOT NULL,
    version     integer NOT NULL DEFAULT 1,
    is_current  boolean NOT NULL DEFAULT false,
    structure   jsonb NOT NULL,
    ctime       timestamp NOT NULL DEFAULT now()
); COMMENT ON TABLE questionnaire.def IS '问卷定义';

CREATE TABLE questionnaire.instance (
    id          serial PRIMARY KEY,
    def_id      integer NOT NULL REFERENCES questionnaire.def,
    player_name varchar(16) NOT NULL,
    ip          inet NOT NULL DEFAULT '0.0.0.0',
    status      public.request_status NOT NULL DEFAULT 'PENDING',
    data        jsonb NOT NULL,
    ctime       timestamp NOT NULL DEFAULT now()
); COMMENT ON TABLE questionnaire.instance IS '问卷实例';

CREATE TABLE questionnaire.review (
    id          serial PRIMARY KEY,
    instance_id integer NOT NULL REFERENCES questionnaire.instance,
    reviewer_id integer NOT NULL REFERENCES public.users,
    comment     text,
    ctime       timestamp NOT NULL DEFAULT now()
); COMMENT ON TABLE questionnaire.review IS '问卷审核';
