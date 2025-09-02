CREATE SCHEMA game_project;

-- 项目贡献等级：所有者（核心发起人）、主要贡献者、参与贡献者
CREATE TYPE game_project.user_role AS ENUM ('owner', 'contributor', 'participant');
-- 维度：主世界、下界、末地
CREATE TYPE public.world AS ENUM ('overworld', 'the_nether', 'the_end');

CREATE TABLE public.users (
    id              serial PRIMARY KEY,
    uuid            uuid NOT NULL UNIQUE, -- non-updatable
    name            varchar(20) NOT NULL UNIQUE,
    password        varchar(512) NOT NULL
); COMMENT ON TABLE public.users IS '网站用户核心信息';

CREATE TABLE public.user_profile (
    user_id         integer PRIMARY KEY REFERENCES public.users,
    created_time    timestamp NOT NULL DEFAULT now(), -- non-updatable
    last_login_time timestamp,
    email           varchar(127),
    qq              varchar(12),
    wechat          varchar(64),
    telegram        varchar(64),
    nickname        varchar(30),
    introduction    text
); COMMENT ON TABLE public.user_profile IS '网站用户资料';
COMMENT ON COLUMN public.user_profile.created_time IS '用户创建时间';
COMMENT ON COLUMN public.user_profile.last_login_time IS '用户上一次登录时间';
COMMENT ON COLUMN public.user_profile.nickname IS '昵称';
COMMENT ON COLUMN public.user_profile.introduction IS '自我介绍';
CREATE INDEX user_qq_index ON public.user_profile (qq);

CREATE TABLE public.permission (
    id          serial PRIMARY KEY,
    name        varchar(64) NOT NULL UNIQUE,
    description text
); COMMENT ON TABLE public.permission IS '网站权限';

CREATE TABLE public.user_perm (
    user_id         integer NOT NULL REFERENCES public.users,
    perm_id         integer NOT NULL REFERENCES public.permission,
    created_time    timestamp NOT NULL DEFAULT now(),
    UNIQUE (user_id, perm_id)
); COMMENT ON TABLE public.user_perm IS '用户拥有的权限';

CREATE TABLE public.player_role (
    id          serial PRIMARY KEY,
    name        varchar(64) NOT NULL UNIQUE,
    description text
); COMMENT ON TABLE public.player_role IS '玩家在游戏中的角色';

CREATE TABLE public.player (
    id      serial PRIMARY KEY,
    uuid    uuid NOT NULL UNIQUE,
    name    varchar(20) NOT NULL UNIQUE,
    white   boolean NOT NULL DEFAULT false,
    role_id integer REFERENCES player_role,
    user_id integer REFERENCES users
); COMMENT ON TABLE public.player IS '游戏中的玩家信息';
COMMENT ON COLUMN public.player.white IS '是否为白名单';

CREATE TABLE game_project.type (
    id          serial PRIMARY KEY,
    name        varchar(64),
    description text
); COMMENT ON TABLE game_project.type IS '项目工程类型';

CREATE TABLE game_project.item (
    id                      serial PRIMARY KEY,
    identifier              varchar(100) NOT NULL UNIQUE,
    name                    varchar(100) NOT NULL,
    type_id                 integer REFERENCES game_project.type,
    creator_id              integer NOT NULL REFERENCES users,
    webpage_created_time    timestamp NOT NULL DEFAULT now(), -- non-updatable
    webpage_updated_time    timestamp NOT NULL DEFAULT now(),
    project_created_time    date,
    project_updated_time    date,
    introduction            text,
    thumbnail               bytea,
    world                   public.world NOT NULL DEFAULT 'overworld',
    x                       integer NOT NULL,
    y                       integer NOT NULL,
    z                       integer NOT NULL
); COMMENT ON TABLE game_project.item IS '用以项目工程页面展示';
COMMENT ON COLUMN game_project.item.identifier IS '唯一标识符，仅允许小写英文、数字、下划线和横线';
COMMENT ON COLUMN game_project.item.name IS '项目名称，允许中文，原则上可重复';
COMMENT ON COLUMN game_project.item.type_id IS '项目类型';
COMMENT ON COLUMN game_project.item.creator_id IS '页面创建者';
COMMENT ON COLUMN game_project.item.webpage_created_time IS '项目页面的创建时间';
COMMENT ON COLUMN game_project.item.webpage_updated_time IS '项目在页面中的最后更新时间';
COMMENT ON COLUMN game_project.item.project_created_time IS '项目在游戏中的创建时间';
COMMENT ON COLUMN game_project.item.project_updated_time IS '项目在游戏中的最后更新时间';
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
    id              serial PRIMARY KEY,
    user_id         integer NOT NULL REFERENCES public.users,
    project_id      integer NOT NULL REFERENCES game_project.item,
    created_time    timestamp NOT NULL DEFAULT now(),
    UNIQUE (user_id, project_id)
); COMMENT ON TABLE game_project.like IS '项目点赞记录';
