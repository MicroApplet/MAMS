-- ============================================================
-- MicroApplet — MySQL DDL (3.0.0)
-- 引擎: InnoDB | 字符集: utf8mb4 | 排序: utf8mb4_unicode_ci
-- 适用 MySQL 8.0+
-- ============================================================

-- ==================== 1. 用户体系 ====================

-- 1.1 主用户表
CREATE TABLE IF NOT EXISTS `user` (
  `id`          VARCHAR(64)   NOT NULL COMMENT '用户编号（雪花ID）',
  `appid`       VARCHAR(64)   NOT NULL COMMENT '所属应用编号',
  `org_id`      VARCHAR(64)   DEFAULT NULL COMMENT '所属机构编号',
  `nickname`    VARCHAR(128)  DEFAULT NULL COMMENT '用户昵称',
  `username`    VARCHAR(128)  DEFAULT NULL COMMENT '登录用户名',
  `password`    VARCHAR(256)  DEFAULT NULL COMMENT '登录密码（哈希）',
  `role_bit`    BIGINT        NOT NULL DEFAULT 0 COMMENT '角色位图',
  `deleted`     TINYINT(1)    NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `create_time` DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_appid` (`appid`),
  KEY `idx_org` (`org_id`),
  KEY `idx_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='主用户表';

-- 1.2 渠道用户表
CREATE TABLE IF NOT EXISTS `chl_user` (
  `id`           VARCHAR(64)  NOT NULL COMMENT '编号（雪花ID）',
  `appid`        VARCHAR(64)  NOT NULL COMMENT '所属应用编号',
  `userid`       VARCHAR(64)  NOT NULL COMMENT '主用户编号',
  `chl_type`     VARCHAR(32)  NOT NULL COMMENT '渠道类型（wechat/h5/phone）',
  `chl_appid`    VARCHAR(64)  NOT NULL COMMENT '渠道应用编号',
  `chl_app_type` VARCHAR(32)  DEFAULT NULL COMMENT '渠道应用类型',
  `chl_userid`   VARCHAR(128) DEFAULT NULL COMMENT '渠道用户标识（OpenId/手机号）',
  `chl_unionid`  VARCHAR(128) DEFAULT NULL COMMENT '渠道联合标识（UnionId）',
  `role_bit`     BIGINT       NOT NULL DEFAULT 0 COMMENT '角色位图',
  `chl_user_code` VARCHAR(512) DEFAULT NULL COMMENT '用户授权码',
  `chl_user_token` VARCHAR(512) DEFAULT NULL COMMENT '渠道用户令牌',
  `deleted`      TINYINT(1)   NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `create_time`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_userid` (`userid`),
  KEY `idx_chl` (`chl_type`, `chl_appid`, `chl_userid`),
  KEY `idx_appid` (`appid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='渠道用户表';

-- 1.3 用户证件表
CREATE TABLE IF NOT EXISTS `id_card_user` (
  `id`           VARCHAR(64)  NOT NULL COMMENT '编号（雪花ID）',
  `appid`        VARCHAR(64)  NOT NULL COMMENT '所属应用编号',
  `userid`       VARCHAR(64)  NOT NULL COMMENT '主用户编号',
  `id_type`      VARCHAR(32)  NOT NULL COMMENT '证件类型（身份证/护照等）',
  `id_no`        VARCHAR(64)  NOT NULL COMMENT '证件号码',
  `name`         VARCHAR(64)  NOT NULL COMMENT '证件姓名',
  `gender`       VARCHAR(8)   DEFAULT NULL COMMENT '性别',
  `nationality`  VARCHAR(32)  DEFAULT NULL COMMENT '国籍',
  `birthday`     DATE         DEFAULT NULL COMMENT '出生日期',
  `address`      VARCHAR(256) DEFAULT NULL COMMENT '地址',
  `issue`        VARCHAR(128) DEFAULT NULL COMMENT '签发机关',
  `issue_date`   DATE         DEFAULT NULL COMMENT '签发日期',
  `issue_expires` DATE        DEFAULT NULL COMMENT '有效期至',
  `front_file_id` VARCHAR(128) DEFAULT NULL COMMENT '证件正面文件ID',
  `back_file_id`  VARCHAR(128) DEFAULT NULL COMMENT '证件反面文件ID',
  `deleted`      TINYINT(1)   NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `create_time`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_userid` (`userid`),
  KEY `idx_appid` (`appid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户证件表';

-- 1.4 用户认证记录表
CREATE TABLE IF NOT EXISTS `user_self_verify_record` (
  `id`           VARCHAR(64)  NOT NULL COMMENT '编号（雪花ID）',
  `appid`        VARCHAR(64)  NOT NULL COMMENT '所属应用编号',
  `userid`       VARCHAR(64)  NOT NULL COMMENT '主用户编号',
  `id_type`      VARCHAR(32)  NOT NULL COMMENT '证件类型',
  `name`         VARCHAR(64)  NOT NULL COMMENT '姓名',
  `id_no`        VARCHAR(64)  NOT NULL COMMENT '证件号码',
  `verify_chl`   VARCHAR(32)  DEFAULT NULL COMMENT '认证渠道',
  `verify_param` VARCHAR(256) DEFAULT NULL COMMENT '认证参数',
  `status`       VARCHAR(16)  NOT NULL DEFAULT 'PENDING' COMMENT '状态（PENDING/SUCCESS/FAIL）',
  `deleted`      TINYINT(1)   NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `create_time`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_userid` (`userid`),
  KEY `idx_appid` (`appid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户实名认证记录表';

-- ==================== 2. 应用与组织 ====================

-- 2.1 应用表
CREATE TABLE IF NOT EXISTS `app` (
  `id`          VARCHAR(64)  NOT NULL COMMENT '应用编号（雪花ID）',
  `name`        VARCHAR(128) NOT NULL COMMENT '应用名称',
  `org_id`      VARCHAR(64)  DEFAULT NULL COMMENT '所属机构编号',
  `status`      VARCHAR(16)  NOT NULL DEFAULT 'ACTIVE' COMMENT '状态（ACTIVE/DISABLED/DELETED）',
  `deleted`     TINYINT(1)   NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_org` (`org_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='应用表';

-- 2.2 渠道应用表（微信/抖音/支付宝等第三方平台配置）
CREATE TABLE IF NOT EXISTS `chl_app` (
  `id`            VARCHAR(64)  NOT NULL COMMENT '编号（雪花ID）',
  `appid`         VARCHAR(64)  NOT NULL COMMENT '所属应用编号',
  `org_id`        VARCHAR(64)  DEFAULT NULL COMMENT '所属机构编号',
  `chl_type`      VARCHAR(32)  NOT NULL COMMENT '渠道类型（wechat/douyin/alipay）',
  `chl_appid`     VARCHAR(64)  NOT NULL COMMENT '第三方平台 AppId',
  `chl_app_type`  VARCHAR(32)  DEFAULT NULL COMMENT '渠道应用类型（applet/official/pay）',
  `chl_app_secret` VARCHAR(256) DEFAULT NULL COMMENT '第三方平台 Secret',
  `chl_app_name`  VARCHAR(128) DEFAULT NULL COMMENT '第三方应用名称',
  `chl_subject_id` VARCHAR(64) DEFAULT NULL COMMENT '商户号/主体ID',
  `chl_agent_id`  VARCHAR(64)  DEFAULT NULL COMMENT '代理ID',
  `chl_token`     VARCHAR(256) DEFAULT NULL COMMENT '消息校验 Token',
  `chl_enc_key`   VARCHAR(256) DEFAULT NULL COMMENT '消息加解密 Key',
  `chl_enc_type`  VARCHAR(16)  DEFAULT NULL COMMENT '加密类型',
  `url`           VARCHAR(256) DEFAULT NULL COMMENT '回调 URL',
  `manager`       VARCHAR(64)  DEFAULT NULL COMMENT '管理员',
  `description`   VARCHAR(512) DEFAULT NULL COMMENT '备注说明',
  `deleted`       TINYINT(1)   NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `create_time`   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time`   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_app` (`appid`),
  KEY `idx_chl` (`chl_type`, `chl_appid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='渠道应用配置表';

-- 2.3 机构表
CREATE TABLE IF NOT EXISTS `org` (
  `id`          VARCHAR(64)  NOT NULL COMMENT '机构编号（雪花ID）',
  `name`        VARCHAR(128) NOT NULL COMMENT '机构名称',
  `id_no`       VARCHAR(64)  DEFAULT NULL COMMENT '机构证件号码',
  `type`        VARCHAR(32)  DEFAULT NULL COMMENT '机构类型',
  `deleted`     TINYINT(1)   NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='组织机构表';

-- ==================== 3. Hermes 事件体系 ====================

-- 3.1 订阅者表
CREATE TABLE IF NOT EXISTS `hermes_subscriber` (
  `id`          VARCHAR(36)  NOT NULL COMMENT '主键UUID',
  `type`        VARCHAR(255) NOT NULL COMMENT '事件类型',
  `application` VARCHAR(255) NOT NULL COMMENT '订阅者名称',
  `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_type_app` (`type`, `application`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Hermes 事件订阅者表';

-- 3.2 事件表
CREATE TABLE IF NOT EXISTS `hermes_event` (
  `id`          VARCHAR(36)  NOT NULL COMMENT '事件编号UUID',
  `type`        VARCHAR(255) NOT NULL COMMENT '事件类型',
  `status`      VARCHAR(32)  NOT NULL DEFAULT 'PENDING' COMMENT '状态（PENDING/PUBLISHED/CONSUMED/FAILED）',
  `body`        JSON         DEFAULT NULL COMMENT '事件体',
  `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_type` (`type`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Hermes 事件表';

-- ==================== 4. AIMAMS 扩展表 ====================

-- 4.1 AI 会话持久化（Redis 不可用时的降级方案）
CREATE TABLE IF NOT EXISTS `aimams_session` (
  `id`          VARCHAR(64)  NOT NULL COMMENT '会话编号',
  `user_id`     VARCHAR(64)  NOT NULL COMMENT '用户编号',
  `platform`    VARCHAR(32)  DEFAULT NULL COMMENT '来源平台',
  `role_bit`    VARCHAR(128) DEFAULT NULL COMMENT '角色位图',
  `context`     JSON         DEFAULT NULL COMMENT '会话上下文',
  `expire_at`   DATETIME     NOT NULL COMMENT '过期时间',
  `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user` (`user_id`),
  KEY `idx_expire` (`expire_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='AI 会话持久化表';

-- 4.2 AI 审计日志
CREATE TABLE IF NOT EXISTS `aimams_audit_log` (
  `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `session_id`  VARCHAR(64)  NOT NULL COMMENT '会话编号',
  `user_id`     VARCHAR(64)  NOT NULL COMMENT '用户编号',
  `action`      VARCHAR(32)  NOT NULL COMMENT '操作类型（CREATE/ACCESS/REMOVE）',
  `intent`      VARCHAR(64)  DEFAULT NULL COMMENT '识别到的意图',
  `channel`     VARCHAR(32)  DEFAULT NULL COMMENT '执行通道',
  `detail`      JSON         DEFAULT NULL COMMENT '详情',
  `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_session` (`session_id`),
  KEY `idx_user` (`user_id`),
  KEY `idx_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='AI 审计日志表';
