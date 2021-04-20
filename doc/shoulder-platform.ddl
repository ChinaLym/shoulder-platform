/*
SQLyog Professional v12.09 (64 bit)
MySQL - 8.0.17 : Database - shoulder_demo
*********************************************************************
*/
/**
   todo 业务类型表（用于导入等）？
注意时区问题

标准字段
    `creator`     VARCHAR(64) NOT NULL COMMENT '创建人编号',
    `create_time` DATETIME             DEFAULT NOW() COMMENT '创建时间',
    `modifier`    VARCHAR(64) NOT NULL COMMENT '最近修改人编码',
    `modify_time` DATETIME             DEFAULT NOW() COMMENT '最后修改时间',

 */

/*!40101 SET nameS utf8mb4 */;

/*!40101 SET SQL_MODE = ''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

/*Table structure for table `crypt_info` 加密部件表，可以由每个应用自身维护，也可统一管理 */

CREATE TABLE `crypt_info`
(
    `app_id`        VARCHAR(32) NOT NULL COMMENT '应用标识',
    `header`        VARCHAR(32) NOT NULL DEFAULT '' COMMENT '密文前缀/算法标识/版本标志',
    `data_key`      VARCHAR(64) NOT NULL COMMENT '数据密钥（密文）',
    `root_key_part` VARCHAR(64)          DEFAULT NULL COMMENT '根密钥部件',
    `vector`        VARCHAR(64)          DEFAULT NULL COMMENT '初始偏移向量',
    `create_time`   DATETIME             DEFAULT NOW() COMMENT '创建时间',
    PRIMARY KEY (`app_id`, `header`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='加密元信息';

/*Data for the table `crypt_info` */

/* 属于特定应用的配置管理，由应用自身后台管理 */
create table shoulder_ext_config_data
(
    `id`             BIGINT UNSIGNED           NOT NULL AUTO_INCREMENT COMMENT '主键',
    `biz_id`         VARCHAR(32)               NOT NULL COMMENT '业务唯一标识(不可修改；业务键拼接并哈希)',
    `delete_version` BIGINT unsigned DEFAULT 0 NOT NULL comment '删除标记：0-未删除；否则为删除时间',
    `version`        INT             DEFAULT 0 NOT NULL COMMENT '数据版本号：用于幂等防并发',
    `tenant`         VARCHAR(32)               NOT NULL COMMENT '租户',
    `type`           VARCHAR(64)               NOT NULL COMMENT '配置类型，通常可据此分库表',
    `note`           VARCHAR(255)              NULL COMMENT '备注:介绍为啥添加这一条',
    `creator`        VARCHAR(64)               NOT NULL COMMENT '创建人编号',
    `create_time`    DATETIME        DEFAULT NOW() COMMENT '创建时间',
    `modifier`       VARCHAR(64)               NOT NULL COMMENT '最近修改人编码',
    `modify_time`    DATETIME        DEFAULT NOW() COMMENT '最后修改时间',
    `business_value` TEXT                      NOT NULL COMMENT '业务数据，json 类型',

    CONSTRAINT config_data_pk
        PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT '配置数据表';

create unique index config_data_uni_biz_index
    on shoulder_ext_config_data (biz_id, delete_version, version);

/*Table structure for table `batch_record` 批处理记录 */

CREATE TABLE `batch_record`
(
    `id`          VARCHAR(48) NOT NULL COMMENT '主键',
    `data_type`   VARCHAR(64) NOT NULL COMMENT '导入数据类型，建议可翻译。对应 导入数据库表名 / 领域对象名称，如用户、人员、订单',
    `operation`   VARCHAR(64) COMMENT '业务操作类型，如校验、同步、导入、更新，可空',
    `total_num`   INT         NOT NULL COMMENT '总数据数量',
    `success_num` INT         NOT NULL COMMENT '执行成功条数',
    `fail_num`    INT         NOT NULL COMMENT '执行失败条数',
    `creator`     VARCHAR(64) NOT NULL COMMENT '创建人编号',
    `create_time` DATETIME DEFAULT NOW() COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='批量任务执行记录';

/*Data for the table `batch_record` */

/*Table structure for table `batch_record_detail` 批处理详情（包含数据） */

CREATE TABLE `batch_record_detail`
(
    `id`          INT         NOT NULL AUTO_INCREMENT COMMENT '主键',
    `record_id`   VARCHAR(48) NOT NULL COMMENT '批量任务执行表id',
    `index`       INT         NOT NULL COMMENT '该任务中，本数据行对应的行号 / 下标值',
    `operation`   VARCHAR(64) NOT NULL COMMENT '业务操作类型，如校验、同步、导入、更新',
    `status`      INT         NOT NULL COMMENT '结果 0 执行成功 1 执行失败、2 跳过',
    `fail_reason` VARCHAR(1024) DEFAULT NULL COMMENT '失败原因，推荐支持多语言',
    `source`      TEXT COMMENT '导入的原数据',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='批量任务执行详情';

/*Table structure for table `batch_record` */
/*Data for the table `batch_record_detail` */

/*Table structure for table `log_operation` 操作日志 */

CREATE TABLE `log_operation`
(
    `id`               BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `app_id`           VARCHAR(32)  NOT NULL COMMENT '组件id',
    `version`          VARCHAR(64)       DEFAULT NULL COMMENT '组件版本',
    `instance_id`      VARCHAR(64)       DEFAULT NULL COMMENT '操作服务器节点标识（支持集群时用于定位具体哪台服务器执行）',
    `user_id`          BIGINT       NOT NULL COMMENT '用户标识',
    `user_name`        VARCHAR(64)       DEFAULT NULL COMMENT '用户名',
    `user_real_name`   VARCHAR(128)      DEFAULT NULL COMMENT '用户真实姓名',
    `user_org_id`      BIGINT            DEFAULT NULL COMMENT '用户组标识',
    `user_org_name`    VARCHAR(64)       DEFAULT NULL COMMENT '用户组名',
    `terminal_type`    INT          NOT NULL COMMENT '终端类型。0:服务内部定时任务等触发；1:浏览器；2:客户端；3:移动App；4:小程序。推荐前端支持多语言',
    `terminal_address` VARCHAR(64)       DEFAULT NULL COMMENT '操作者所在终端地址，如 IPv4(15) IPv6(46)',
    `terminal_id`      VARCHAR(64)       DEFAULT NULL COMMENT '操作者所在终端标识，如PC的 MAC；手机的 IMSI、IMEI、ESN、MEID；甚至持久化的 UUID',
    `terminal_info`    VARCHAR(255)      DEFAULT NULL COMMENT '操作者所在终端信息，如操作系统类型、浏览器、版本号等',
    `object_type`      VARCHAR(128)      DEFAULT NULL COMMENT '操作对象类型；建议支持多语言',
    `object_id`        VARCHAR(128)      DEFAULT NULL COMMENT '操作对象id',
    `object_name`      VARCHAR(255)      DEFAULT NULL COMMENT '操作对象名称',
    `operation_param`  TEXT COMMENT '触发该操作的参数',
    `operation`        VARCHAR(255) NOT NULL COMMENT '操作动作；建议支持多语言',
    `detail`           TEXT              DEFAULT NULL COMMENT '操作详情。详细的描述用户的操作内容、json对象等',
    `detail_key`       VARCHAR(128)      DEFAULT NULL COMMENT '操作详情对应的多语言key',
    `detail_item`      VARCHAR(255)      DEFAULT NULL COMMENT '填充 detail_i18n_key 对应的多语言翻译。数组类型',
    `result`           INT          NOT NULL COMMENT '操作结果,0成功；1失败；2部分成功；建议支持多语言',
    `error_code`       VARCHAR(32)       DEFAULT NULL COMMENT '错误码',
    `operation_time`   timestamp    NOT NULL COMMENT '操作触发时间，注意采集完成后替换为日志服务所在服务器时间',
    `end_time`         timestamp    NULL DEFAULT NULL COMMENT '操作结束时间',
    `duration`         BIGINT            DEFAULT NULL COMMENT '操作持续时间，冗余字段，单位 ms',
    `trace_id`         VARCHAR(64)       DEFAULT NULL COMMENT '调用链id',
    `relation_id`      VARCHAR(64)       DEFAULT NULL COMMENT '关联的调用链id/业务id',
    `tenant_code`      VARCHAR(20)       DEFAULT '' COMMENT '租户编码',
    `insert_time`      timestamp    NULL DEFAULT CURRENT_TIMESTAMP COMMENT '数据入库时间',
    `extended_field0`  VARCHAR(1024)     DEFAULT NULL,
    `extended_field1`  VARCHAR(1024)     DEFAULT NULL,
    `extended_field2`  VARCHAR(1024)     DEFAULT NULL,
    `extended_field3`  VARCHAR(1024)     DEFAULT NULL,
    `extended_field4`  VARCHAR(1024)     DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `idx_trace_id` (`trace_id`),
    KEY `idx_operation_time` (`operation_time`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_terminal_address` (`terminal_address`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='业务日志';

/*Data for the table `log_operation` */

/*Table structure for table `log_server` 服务器日志 */

CREATE TABLE `log_server`
(
    `id`                  BIGINT        NOT NULL AUTO_INCREMENT COMMENT '主键',
    `command_id`          BIGINT        NOT NULL COMMENT '服务器远程日志ID，linux为历史命令分配的递增序号',
    `user_name`           VARCHAR(128)  NOT NULL COMMENT '执行命令使用用户的名称（可分组）',
    `login_ip`            VARCHAR(48)   NOT NULL COMMENT '登录终端IP（可分组）',
    `command`             VARCHAR(1024) NOT NULL COMMENT '执行的命令',
    `login_time`          timestamp     NOT NULL COMMENT '用户登录时间（可分组）',
    `operation_time`      timestamp     NOT NULL COMMENT '操作时间',
    `operation_localtime` timestamp     NOT NULL COMMENT '执行命令时，服务器本地时间，是否记录采集时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='服务器shell/bash命令日志';

/*Data for the table `log_server` */

/*Table structure for table `log_server_login` 服务器登录日志 */

CREATE TABLE `log_server_login`
(
    `id`         BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user`       VARCHAR(128) NOT NULL COMMENT '登录使用用户的名称',
    `ip`         VARCHAR(48)  NOT NULL COMMENT '登录终端IP',
    `login_time` timestamp    NOT NULL COMMENT '登录时间',
    `success`    INT          NOT NULL COMMENT '是否登录成功',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='服务器shell/bash登录日志';

/*Data for the table `log_server_login` */

/*Table structure for table `mail_provider` 邮件服务器 */

CREATE TABLE `mail_provider`
(
    `id`          BIGINT      NOT NULL DEFAULT '0' COMMENT 'ID',
    `tenant_code` VARCHAR(20)          DEFAULT '' COMMENT '租户编码',
    `mail_type`   VARCHAR(16)          DEFAULT 'QQ' COMMENT '邮箱类型\n#MailType{SINA:新浪;QQ:腾讯;WY163:网易}',
    `username`    VARCHAR(255)         DEFAULT NULL COMMENT '邮箱账号',
    `password`    VARCHAR(255)         DEFAULT NULL COMMENT '邮箱授权码【推荐加密存储】',
    `host`        VARCHAR(64)          DEFAULT NULL COMMENT '主机',
    `port`        VARCHAR(8)           DEFAULT NULL COMMENT '端口',
    `protocol`    VARCHAR(16)          DEFAULT NULL COMMENT '协议',
    `auth`        VARCHAR(64)          DEFAULT NULL COMMENT '是否进行用户名密码校验',
    `name`        VARCHAR(64)          DEFAULT NULL COMMENT '名称',
    `description` VARCHAR(255)         DEFAULT NULL COMMENT '描述',
    `properties`  VARCHAR(500)         DEFAULT NULL COMMENT '属性',
    `creator`     VARCHAR(64) NOT NULL COMMENT '创建人编号',
    `create_time` DATETIME             DEFAULT NOW() COMMENT '创建时间',
    `modifier`    VARCHAR(64) NOT NULL COMMENT '最近修改人编码',
    `modify_time` DATETIME             DEFAULT NOW() COMMENT '最后修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='邮件供应商';

/*Data for the table `mail_provider` */

/*Table structure for table `mail_send_record` 邮件发送记录 */

CREATE TABLE `mail_send_record`
(
    `id`          BIGINT       NOT NULL DEFAULT '0' COMMENT 'ID',
    `tenant_code` VARCHAR(20)           DEFAULT '' COMMENT '租户编码',
    `task_id`     BIGINT       NOT NULL COMMENT '任务id mail_task',
    `email`       VARCHAR(64)  NOT NULL COMMENT '收件邮箱',
    `mail_status` VARCHAR(255) NOT NULL DEFAULT 'UNREAD' COMMENT '邮件状态\r\n#MailStatus{UNREAD:未读;READ:已读;DELETED:已删除;ABNORMAL:异常;VIRUSES:病毒;TRASH:垃圾}',
    `creator`     VARCHAR(64)  NOT NULL COMMENT '创建人编号',
    `create_time` DATETIME              DEFAULT NOW() COMMENT '创建时间',
    `modifier`    VARCHAR(64)  NOT NULL COMMENT '最近修改人编码',
    `modify_time` DATETIME              DEFAULT NOW() COMMENT '最后修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='邮件发送记录';

/*Data for the table `mail_send_record` */

/*Table structure for table `mail_task` */

CREATE TABLE `mail_task`
(
    `id`          BIGINT      NOT NULL DEFAULT '0' COMMENT 'ID',
    `tenant_code` VARCHAR(20)          DEFAULT '' COMMENT '租户编码',
    `status`      VARCHAR(10)          DEFAULT 'WAITING' COMMENT '执行状态：TaskStatus{WAITING:等待执行;SUCCESS:执行成功;FAIL:执行失败}',
    `provider_id` BIGINT               DEFAULT NULL COMMENT '发件人id\n#mail_provider',
    `to`          VARCHAR(1024)        DEFAULT '' COMMENT '收件人。多个,号分割',
    `cc`          VARCHAR(255)         DEFAULT '' COMMENT '抄送人。多个,分割',
    `bcc`         VARCHAR(255)         DEFAULT '' COMMENT '密送人。多个,分割',
    `subject`     VARCHAR(255)         DEFAULT '' COMMENT '主题',
    `body`        TEXT CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '正文',
    `fail_reason` VARCHAR(255)         DEFAULT '' COMMENT '发送失败原因，错误码',
    `sender_code` VARCHAR(64)          DEFAULT '' COMMENT '发送商编码',
    `plan_time`   DATETIME             DEFAULT CURRENT_TIMESTAMP COMMENT '计划发送时间\n(默认当前时间，可定时发送)',
    `creator`     VARCHAR(64) NOT NULL COMMENT '创建人编号',
    `create_time` DATETIME             DEFAULT NOW() COMMENT '创建时间',
    `modifier`    VARCHAR(64) NOT NULL COMMENT '最近修改人编码',
    `modify_time` DATETIME             DEFAULT NOW() COMMENT '最后修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='邮件发送任务';

/*Data for the table `mail_task` */

/*Table structure for table `principal_role` */

CREATE TABLE `principal_role`
(
    `id`             BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `principal_id`   BIGINT       DEFAULT NULL COMMENT '凭证标识',
    `principal_type` VARCHAR(255) DEFAULT NULL COMMENT '凭证类型 0：用户，1：部门',
    `role_id`        BIGINT       DEFAULT NULL COMMENT '角色',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='凭证-角色关联表';

/*Data for the table `principal_role` */

/*Table structure for table `resource_operation` */

CREATE TABLE `resource_operation`
(
    `code`               VARCHAR(255) DEFAULT NULL COMMENT '操作编码',
    `name`               VARCHAR(128) DEFAULT NULL COMMENT '操作名称',
    `resource_type`      VARCHAR(255) DEFAULT NULL COMMENT '资源类型编码',
    `resource_type_name` VARCHAR(128) DEFAULT NULL COMMENT '资源类型名称',
    `parent_code`        VARCHAR(255) DEFAULT NULL COMMENT '父编码'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='资源操作项，所有操作';

/*Data for the table `resource_operation` */

/*Table structure for table `resource_type` */

CREATE TABLE `resource_type`
(
    `code` VARCHAR(255) DEFAULT NULL COMMENT '编码',
    `name` VARCHAR(128) DEFAULT NULL COMMENT '名称'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='资源类型';

/*Data for the table `resource_type` */

/*Table structure for table `role` */

CREATE TABLE `role`
(
    `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`        VARCHAR(64)     NOT NULL COMMENT '名称',
    `initials`    VARCHAR(64)              DEFAULT NULL COMMENT '名称-首字母缩写',
    `pinyin`      VARCHAR(255)             DEFAULT NULL COMMENT '名称-全拼音',
    `type`        INT             NOT NULL DEFAULT '0' COMMENT '角色类型，1管理员，2普通角色',
    `sub_type`    INT                      DEFAULT NULL COMMENT '创建的角色类型，用于继承 0操作员 1管理员 2超级管理员',
    `enable`      INT                      DEFAULT '1' COMMENT '0:禁用，1：启用',
    `description` VARCHAR(255)             DEFAULT NULL COMMENT '描述',
    `creator`     VARCHAR(64)     NOT NULL COMMENT '创建人编号',
    `create_time` DATETIME                 DEFAULT NOW() COMMENT '创建时间',
    `modifier`    VARCHAR(64)     NOT NULL COMMENT '最近修改人编码',
    `modify_time` DATETIME                 DEFAULT NOW() COMMENT '最后修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='角色表';

/*Data for the table `role` */

/*Table structure for table `role_rel_menu` */

CREATE TABLE `role_rel_menu`
(
    `id`                   BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `role_id`              BIGINT DEFAULT NULL COMMENT '角色id',
    `menu_id`              INT             NOT NULL COMMENT '菜单id',
    `menu_type`            VARCHAR(16)     NOT NULL COMMENT '菜单类型',
    `menu_permission_code` VARCHAR(64)     NOT NULL COMMENT '菜单的code',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='角色-菜单的权限关系';

/*Data for the table `role_rel_menu` */
/*Table structure for table `role_rel_resource` */

CREATE TABLE `role_rel_resource`
(
    `id`            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `role_id`       BIGINT DEFAULT NULL COMMENT '角色id',
    `resource_id`   BIGINT          NOT NULL COMMENT '资源id',
    `resource_type` VARCHAR(64)     NOT NULL COMMENT '资源类型编码',
    `auth_value`    BIGINT DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='角色-资源的权限关系（角色上配了哪些资源）';

/*Data for the table `role_rel_resource` */

/*Table structure for table `role_rel_resource_type` */

CREATE TABLE `role_rel_resource_type`
(
    `id`            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `role_id`       BIGINT                   DEFAULT NULL COMMENT '角色id',
    `contain_sub`   INT             NOT NULL DEFAULT '1' COMMENT '是否包含下级：0 - 不包含； 1 - 包含',
    `resource_type` VARCHAR(64)              DEFAULT NULL COMMENT '资源类型编码',
    `auth_value`    BIGINT                   DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='角色-资源类型的权限关系';

/*Data for the table `role_rel_resource_type` */

/*Table structure for table `role_rel_role` */

CREATE TABLE `role_rel_role`
(
    `id`              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `role_id`         BIGINT          NOT NULL COMMENT '角色id',
    `control_role_id` BIGINT          NOT NULL COMMENT '目标角色id',
    `auth_value`      BIGINT DEFAULT NULL COMMENT '权限值，为资源操作项值之和',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='角色-角色的权限关系';

/*Data for the table `role_rel_role` */

/*Table structure for table `role_rel_user_group` */

CREATE TABLE `role_rel_user_group`
(
    `id`            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `role_id`       BIGINT DEFAULT NULL COMMENT '角色id',
    `user_group_id` INT    DEFAULT NULL COMMENT '目标用户组id',
    `auth_value`    BIGINT DEFAULT NULL COMMENT '权限值，为资源操作项值之和，0 - 没有权限，1 2 4',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='角色-用户组的权限关系';

/*Data for the table `role_rel_user_group` */

/*Table structure for table `sms_send_record` */

CREATE TABLE `sms_send_record`
(
    `id`           BIGINT      NOT NULL COMMENT 'ID',
    `tenant_code`  VARCHAR(20)          DEFAULT '' COMMENT '租户编码',
    `task_id`      BIGINT      NOT NULL COMMENT '任务ID\n#sms_task',
    `send_status`  VARCHAR(10) NOT NULL DEFAULT 'WAITING' COMMENT '发送状态\n#SendStatus{WAITING:等待发送;SUCCESS:发送成功;FAIL:发送失败}',
    `receiver`     VARCHAR(20) NOT NULL COMMENT '接收者手机号\n单个手机号',
    `biz_id`       VARCHAR(255)         DEFAULT '' COMMENT '发送回执ID\n阿里：发送回执ID,可根据该ID查询具体的发送状态  腾讯：sid 标识本次发送id，标识一次短信下发记录  百度：requestId 短信发送请求唯一流水ID',
    `ext`          VARCHAR(255)         DEFAULT '' COMMENT '发送返回\n阿里：RequestId 请求ID  腾讯：ext：用户的session内容，腾讯server回包中会原样返回   百度：无',
    `code`         VARCHAR(255)         DEFAULT '' COMMENT '状态码\n阿里：返回OK代表请求成功,其他错误码详见错误码列表  腾讯：0表示成功(计费依据)，非0表示失败  百度：1000 表示成功',
    `message`      VARCHAR(500)         DEFAULT '' COMMENT '状态码的描述',
    `fee`          INT                  DEFAULT '0' COMMENT '短信计费的条数\n腾讯专用',
    `create_month` VARCHAR(7)           DEFAULT '' COMMENT '创建时年月\n格式：yyyy-MM 用于统计',
    `create_week`  VARCHAR(10)          DEFAULT '' COMMENT '创建时年周\n创建时处于当年的第几周 yyyy-ww 用于统计',
    `create_date`  VARCHAR(10)          DEFAULT '' COMMENT '创建时年月日\n格式： yyyy-MM-dd 用于统计',
    `creator`      VARCHAR(64) NOT NULL COMMENT '创建人编号',
    `create_time`  DATETIME             DEFAULT NOW() COMMENT '创建时间',
    `modifier`     VARCHAR(64) NOT NULL COMMENT '最近修改人编码',
    `modify_time`  DATETIME             DEFAULT NOW() COMMENT '最后修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='短信发送记录';

/*Data for the table `sms_send_record` */

/*Table structure for table `sms_task` */

CREATE TABLE `sms_task`
(
    `id`              BIGINT      NOT NULL COMMENT '短信记录ID',
    `tenant_code`     VARCHAR(20)  DEFAULT '' COMMENT '租户编码',
    `template_id`     BIGINT      NOT NULL COMMENT '模板ID\n#sms_template',
    `status`          VARCHAR(10)  DEFAULT 'WAITING' COMMENT '执行状态\n(手机号具体发送状态看sms_send_status表) \n#TaskStatus{WAITING:等待执行;SUCCESS:执行成功;FAIL:执行失败}',
    `source_type`     VARCHAR(10)  DEFAULT 'APP' COMMENT '来源类型\n#SourceType{APP:应用;SERVICE:服务}\n',
    `receiver`        TEXT COMMENT '接收者手机号\n群发用英文逗号分割.\n支持2种格式:\n1: 手机号,手机号 \n2: 姓名<手机号>,姓名<手机号>',
    `topic`           VARCHAR(255) DEFAULT '' COMMENT '主题',
    `template_params` VARCHAR(500) DEFAULT '' COMMENT '参数 \n需要封装为{‘key’:’value’, ...}格式\n且key必须有序\n\n',
    `send_time`       DATETIME     DEFAULT NULL COMMENT '发送时间',
    `content`         VARCHAR(500) DEFAULT '' COMMENT '发送内容\n需要封装正确格式化: 您好，张三，您有一个新的快递。',
    `draft`           bit(1)       DEFAULT b'0' COMMENT '是否草稿',
    `creator`         VARCHAR(64) NOT NULL COMMENT '创建人编号',
    `create_time`     DATETIME     DEFAULT NOW() COMMENT '创建时间',
    `modifier`        VARCHAR(64) NOT NULL COMMENT '最近修改人编码',
    `modify_time`     DATETIME     DEFAULT NOW() COMMENT '最后修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='发送任务\n所有的短息发送调用，都视为是一次短信任务，任务表只保存数据和执行状态等信息，\n具体的发送状态查看发送状态（#sms_send_status）表';

/*Data for the table `sms_task` */

/*Table structure for table `sms_template` */

CREATE TABLE `sms_template`
(
    `id`                BIGINT       NOT NULL COMMENT '模板ID',
    `tenant_code`       VARCHAR(20)           DEFAULT '' COMMENT '租户编码',
    `provider_type`     VARCHAR(10)  NOT NULL COMMENT '供应商类型\n#ProviderType{ALI:OK,阿里云短信;TENCENT:0,腾讯云短信;BAIDU:1000,百度云短信}',
    `app_id`            VARCHAR(255) NOT NULL COMMENT '应用ID，每个租户可能不一样，所以需要在这里保存',
    `app_secret`        VARCHAR(255) NOT NULL COMMENT '应用密码',
    `url`               VARCHAR(255)          DEFAULT '' COMMENT 'SMS服务域名\n百度、其他厂商会用',
    `custom_code`       VARCHAR(20)  NOT NULL DEFAULT '' COMMENT '模板编码\n用于api发送',
    `name`              VARCHAR(255)          DEFAULT '' COMMENT '模板名称',
    `content`           VARCHAR(255) NOT NULL DEFAULT '' COMMENT '模板内容',
    `template_params`   VARCHAR(255) NOT NULL DEFAULT '' COMMENT '模板参数',
    `template_code`     VARCHAR(50)  NOT NULL DEFAULT '' COMMENT '模板code',
    `sign_name`         VARCHAR(100)          DEFAULT '' COMMENT '签名',
    `template_describe` VARCHAR(255)          DEFAULT '' COMMENT '备注',
    `creator`           VARCHAR(64)  NOT NULL COMMENT '创建人编号',
    `create_time`       DATETIME              DEFAULT NOW() COMMENT '创建时间',
    `modifier`          VARCHAR(64)  NOT NULL COMMENT '最近修改人编码',
    `modify_time`       DATETIME              DEFAULT NOW() COMMENT '最后修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `UN_CODE` (`custom_code`, `tenant_code`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='短信模板';

/*Data for the table `sms_template` */

/*Table structure for table `system_config_item` 提供统一的后台管理界面，管理多个应用的配置项 */

CREATE TABLE `system_config_item`
(
    `id`            VARCHAR(64)  NOT NULL COMMENT '数据标识',
    `app_id`        VARCHAR(32)   DEFAULT NULL COMMENT '组件标识',
    `item_key`      VARCHAR(128) NOT NULL COMMENT '配置项键',
    `item_value`    VARCHAR(1024) DEFAULT NULL COMMENT '配置项值',
    `default_value` VARCHAR(1024) DEFAULT NULL COMMENT '默认值',
    `value_type`    INT          NOT NULL COMMENT '值类型，0：字符串, 1：数字；2：浮点数；3：布尔；4：日期格式（年月日）；5：日期时间格式（年月日时分秒），默认0',
    `multi_value`   INT          NOT NULL COMMENT '是否多值。决定是否以数组返回值，0:单值，1:多值',
    `regex`         VARCHAR(512)  DEFAULT NULL COMMENT '正则表达式',
    `flag`          INT          NOT NULL COMMENT '配置项类型，0: 默认存在的；1：组件导入的；2：通过接口加入，不在页面体现',
    `source_type`   INT          NOT NULL COMMENT '数据来源类型，0：同步入库的配置项（不可删除）；1：接口保存入库的配置项',
    `notifiable`    INT          NOT NULL COMMENT '是否发送变更通知，0 -不发送，1 - 发送',
    `is_sensitive`  INT          NOT NULL COMMENT '是否敏感：需要加密传输：0 - 正常传输；1 - 需要加密传输',
    `enable`        INT          NOT NULL COMMENT '启用状态，0:禁用；1：启用',
    `remark`        VARCHAR(512)  DEFAULT NULL COMMENT '辅助说明',
    `create_time`   DATETIME      DEFAULT NOW() COMMENT '创建时间',
    `modify_time`   DATETIME      DEFAULT NOW() COMMENT '最后修改时间',
    `isolate_flag`  INT           DEFAULT NULL COMMENT '租户隔离标记，用于判断配置项是否做租户隔离，0:隔离，1:不隔离，默认为0',
    `domain_id`     VARCHAR(64)   DEFAULT NULL COMMENT '域id，用于租户隔离判断不同租户',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_system_config_item_key` (`item_key`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='系统全局配置项表';

/*Data for the table `system_config_item` */

/*Table structure for table `system_dictionary_item` */

CREATE TABLE `system_dictionary_item`
(
    `id`           INT          NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `name`         VARCHAR(64)  NOT NULL COMMENT '字典项名称，最好支持翻译，翻译由对应组件的前端做',
    `data_key`     VARCHAR(255) NOT NULL COMMENT '字典项键，通常数字/缩写/全路径',
    `data_value`   VARCHAR(255) NOT NULL COMMENT '字典项值，与key相同/更完善/最后一段路径，由业务方定义',
    `type_code`    VARCHAR(255) NOT NULL COMMENT '字典项所属的字典类型，字典类型表 code 字段，用来检索某一类型所有的字典项 key/value',
    `app_id`       VARCHAR(32)  NOT NULL COMMENT '组件id，标识这个配置是哪个组件配置进来的。如果是common，则表示是通用配置',
    `tenant_id`    VARCHAR(64)           DEFAULT NULL COMMENT '租户标识',
    `dis_order`    INT                   DEFAULT NULL COMMENT '字典项界面展示顺序',
    `data_level`   INT          NOT NULL COMMENT '字典项层级',
    `remark`       VARCHAR(512)          DEFAULT NULL COMMENT '辅助说明',
    `expand`       VARCHAR(1024)         DEFAULT NULL COMMENT '字典项扩展属性',
    `source_type`  INT                   DEFAULT NULL COMMENT '数据来源类型。0:系统默认自带 1:页面/接口添加',
    `status`       INT          NOT NULL DEFAULT '0' COMMENT '字典项状态：0:正常；1:禁用；-1:删除',
    `creator`      VARCHAR(64)  NOT NULL COMMENT '创建人编号',
    `create_time`  DATETIME              DEFAULT NOW() COMMENT '创建时间',
    `modifier`     VARCHAR(64)  NOT NULL COMMENT '最近修改人编码',
    `modify_time`  DATETIME              DEFAULT NOW() COMMENT '最后修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_system_dictionary_item_code_key` (`type_code`, `data_key`),
    KEY `idx_system_dictionary_item_dis_order` (`dis_order`),
    KEY `idx_system_dictionary_item_data_key` (`data_key`),
    KEY `idx_system_dictionary_item_data_value` (`data_value`),
    KEY `idx_system_dictionary_item_data_level` (`data_level`),
    KEY `idx_system_dictionary_item_type_code` (`type_code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='数据字典项，常用于与下拉框结合';

/*Data for the table `system_dictionary_item` */


/*Table structure for table `system_dictionary_type` */

CREATE TABLE `system_dictionary_type`
(
    `id`            INT          NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `name`          VARCHAR(64)  NOT NULL COMMENT '字典类型名称',
    `code`          VARCHAR(255) NOT NULL COMMENT '字典类型code',
    `parent_code`   VARCHAR(255) NOT NULL COMMENT '父字典类型code',
    `default_value` VARCHAR(255) DEFAULT NULL COMMENT '数据字典类型默认的数据字典项',
    `app_id`        VARCHAR(32)  NOT NULL COMMENT '配置所属应用标识。common表示通用配置',
    `tenant_code`   VARCHAR(32)  DEFAULT 'shoulder' COMMENT '租户标识',
    `creator`       VARCHAR(64)  NOT NULL COMMENT '创建人编号',
    `modifier`      VARCHAR(64)  NOT NULL COMMENT '最近修改人编码',
    `create_time`   DATETIME     DEFAULT NOW() COMMENT '创建时间',
    `modify_time`   DATETIME     DEFAULT NOW() COMMENT '最后修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_system_dictionary_type_code` (`code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='数据字典类型';


/*Table structure for table `system_faq` */

CREATE TABLE `system_faq`
(
    `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `title`       VARCHAR(255) DEFAULT NULL COMMENT '标题，以空格分隔',
    `tags`        VARCHAR(255) DEFAULT NULL COMMENT '标签，以空格分隔',
    `content`     TEXT COMMENT 'html全文',
    `user_id`     BIGINT       DEFAULT NULL COMMENT '用户id',
    `username`    VARCHAR(64)  DEFAULT NULL COMMENT '用户昵称',
    `file_name`   VARCHAR(255) DEFAULT NULL COMMENT '文件名',
    `create_time` DATETIME     DEFAULT NOW() COMMENT '创建时间',
    `modify_time` DATETIME     DEFAULT NOW() COMMENT '最后修改时间',
    `whole_text`  TEXT COMMENT '从HTML中解析出来的具体文本内容，供搜索使用',
    `language_id` VARCHAR(20)  DEFAULT NULL COMMENT '语言标识，用于区分内不同语言置文档，查询时该字段为 null 或特定语言标识',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='常见问答，轻量级设计，可扩展：如tag设计、评论设计';

/*Data for the table `system_faq` */

/*Table structure for table `system_faq_reply` */

CREATE TABLE `system_faq_reply`
(
    `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `faq_id`      BIGINT          NOT NULL COMMENT 'faq主键',
    `context`     TEXT            NOT NULL COMMENT '补充内容',
    `username`    VARCHAR(255)    NOT NULL COMMENT '用户名',
    `create_time` DATETIME DEFAULT NOW() COMMENT '创建时间',
    `modify_time` DATETIME DEFAULT NOW() COMMENT '最后修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='常见问题补充/回复/评论，简单实现，无层级';

/*Data for the table `system_faq_reply` */

/*Table structure for table `system_i18n` */

CREATE TABLE `system_i18n`
(
    `app_id`       VARCHAR(32)  NOT NULL COMMENT '应用标识',
    `i18n_key`     VARCHAR(255) NOT NULL COMMENT '多语言key',
    `locale`       VARCHAR(64)  NOT NULL COMMENT '语言标识',
    `value`        VARCHAR(255) DEFAULT NULL COMMENT '翻译值，可能有占位符',
    `modify_time`  DATETIME     DEFAULT NOW() COMMENT '最后修改时间',
    PRIMARY KEY (`app_id`, `i18n_key`, `locale`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='系统内置的翻译项';

/*Data for the table `system_i18n` */

/*Table structure for table `system_language_dictionary` */

CREATE TABLE `system_language_dictionary`
(
    `id`            INT          NOT NULL AUTO_INCREMENT COMMENT '主键',
    `locale`        VARCHAR(64)  NOT NULL COMMENT '语言/地区类型',
    `english_name`  VARCHAR(255) NOT NULL COMMENT '语种对应英文名',
    `display_name`  VARCHAR(255) NOT NULL COMMENT '语种本地显示名称',
    `display_order` INT          NOT NULL COMMENT '排序字段',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 41
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='多语言字典表';

/*Table structure for table `system_lock` */

CREATE TABLE `system_menu`
(
    `id`              INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `code`            VARCHAR(255)          DEFAULT NULL COMMENT '菜单码',
    `type`            VARCHAR(16)           DEFAULT NULL COMMENT '菜单类型，1：web, 2:客户端，3：移动端',
    `category`        VARCHAR(16)           DEFAULT NULL COMMENT '菜单目录/分组：APP:应用菜单 MANAGER:管理菜单 FUNCTION 功能项',
    `name`            VARCHAR(255)          DEFAULT NULL COMMENT '名称，国际化key',
    `parent_id`       INT                   DEFAULT NULL COMMENT '父节点菜单编号,根为-1',
    `display_order`   INT                   DEFAULT NULL COMMENT '排序',
    `url`             VARCHAR(255)          DEFAULT NULL COMMENT '链接跳转地址url/路径，可空',
    `icon`            VARCHAR(255)          DEFAULT '' COMMENT '菜单图标url，可空',
    `auth_control`    tinyint(1)            DEFAULT NULL COMMENT '0:不控权限，1：控权限',
    `permission_code` VARCHAR(255)          DEFAULT NULL COMMENT '权限码，服务标识_菜单编号',
    `app_id`          VARCHAR(32)           DEFAULT NULL COMMENT '服务标识',
    `remark`          VARCHAR(255)          DEFAULT NULL COMMENT '备注',
    `state`           INT                   DEFAULT NULL COMMENT '状态,0：正常；-1 删除；1：隐藏；3：已过期；定时拿出即将过期的',
    `expire_date`     VARCHAR(255)          DEFAULT NULL COMMENT '过期时间',
    `modify_time`     DATETIME              DEFAULT NOW() COMMENT '最后修改时间',
    `tree_path`       VARCHAR(2048)         DEFAULT NULL,
    `tree_level`      INT                   DEFAULT NULL,
    `onclick`         INT          NOT NULL DEFAULT '0' COMMENT '打开模式；1: 内嵌式（embed）, 1:弹出式（pop）',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_system_menu_name` (`name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='菜单表';

/*Data for the table `system_lock` */

/*Table structure for table `system_menu` */

CREATE TABLE `system_version`
(
    `app_id`          VARCHAR(32) NOT NULL COMMENT '应用标识',
    `install_flag`    INT         DEFAULT '0' COMMENT '版本类型 0:安装，1:升级，2:卸载',
    `current_version` VARCHAR(64) DEFAULT NULL COMMENT '当前版本',
    `modify_time`     DATETIME    DEFAULT NOW() COMMENT '最后修改时间',
    PRIMARY KEY (`app_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='版本信息表';

/*Data for the table `system_menu` */

/*Table structure for table `system_version` */

CREATE TABLE `system_lock`
(
    `resource`     VARCHAR(64)  NOT NULL COMMENT '锁定的资源，组件标识:模块标识:资源/操作标识',
    `owner`        VARCHAR(64)  NOT NULL COMMENT '持有者，可通过该值解析持有应用 / 机器 / 线程 等',
    `token`        VARCHAR(64)  NOT NULL COMMENT '令牌，用于操作锁（获取、解锁、修改）在达到 ttl 之前，必须通过该令牌，才能对锁进行操作',
    `version`      INT          NOT NULL DEFAULT '0' COMMENT '版本号',
    `lock_time`    DATETIME     NOT NULL COMMENT '上锁时间',
    `release_time` DATETIME     NOT NULL COMMENT '超时自动释放时间',
    `description`  VARCHAR(128) NOT NULL DEFAULT '' COMMENT '备注：描述这个锁的目的',
    PRIMARY KEY (`resource`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='全局锁';

/*Data for the table `system_version` */

/*Table structure for table `tenant` */

CREATE TABLE `tenant`
(
    `id`          VARCHAR(64)    NOT NULL COMMENT '租户id',
    `tenant_code` VARCHAR(128)   NOT NULL COMMENT '租户编码',
    `name`        VARCHAR(64)    NOT NULL COMMENT '租户名称',
    `desc`        VARCHAR(1024)  NOT NULL COMMENT '租户描述',
    `logo_url`    VARCHAR(255)   NOT NULL COMMENT '租户logo地址',
    `status`      smallint       NOT NULL COMMENT '状态0有效；-1删除；1冻结',
    `creator`     VARCHAR(64)    NOT NULL COMMENT '创建人编号',
    `create_time` DATETIME     DEFAULT NOW() COMMENT '创建时间',
    `modifier`    VARCHAR(64)    NOT NULL COMMENT '最近修改人编码',
    `modify_time` DATETIME     DEFAULT NOW() COMMENT '最后修改时间',
    `province`    VARCHAR(32)  DEFAULT NULL COMMENT '一级行政单位,如广东省,上海市等',
    `city`        VARCHAR(32)  DEFAULT NULL COMMENT '城市, 如广州市,佛山市等',
    `district`    VARCHAR(32)  DEFAULT NULL COMMENT '行政区,如番禺区,天河区等',
    `address`     VARCHAR(255) DEFAULT NULL COMMENT '街道楼号地址',
    `link_man`    VARCHAR(64)    NOT NULL COMMENT '联系人',
    `link_phone`  VARCHAR(64)    NOT NULL COMMENT '联系电话',
    `longitude`   decimal(10, 6) NOT NULL COMMENT '经度',
    `latitude`    decimal(10, 6) NOT NULL COMMENT '纬度',
    `adcode`      VARCHAR(16)    NOT NULL COMMENT '区域编码,用于通过区域id快速匹配后展示',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='租户信息';

/*Data for the table `tenant` */

/*Table structure for table `schedule_template` */

CREATE TABLE `schedule_template`
(
    `id`            BIGINT      NOT NULL AUTO_INCREMENT COMMENT '主键',
    `template_name` VARCHAR(64) NOT NULL COMMENT '模板名称',
    `monday_sch`    VARCHAR(512) DEFAULT NULL COMMENT '周一模板，格式为多个秒数段，0-86400，段之间使用|分割，注意超长，如可限制最大20段',
    `tuesday_sch`   VARCHAR(512) DEFAULT NULL COMMENT '周二模板',
    `wednesday_sch` VARCHAR(512) DEFAULT NULL COMMENT '周三模板',
    `thursday_sch`  VARCHAR(512) DEFAULT NULL COMMENT '周四模板',
    `friday_sch`    VARCHAR(512) DEFAULT NULL COMMENT '周五模板',
    `saturday_sch`  VARCHAR(512) DEFAULT NULL COMMENT '周六模板',
    `sunday_sch`    VARCHAR(512) DEFAULT NULL COMMENT '周日模板',
    `edit`          INT          DEFAULT '1' COMMENT '可否编辑，0不可编辑，1可编辑',
    `is_delete`     INT          DEFAULT '0' COMMENT '是否删除，0未删除，1已删除',
    `creator`       VARCHAR(64) NOT NULL COMMENT '创建人编号',
    `create_time`   DATETIME     DEFAULT NOW() COMMENT '创建时间',
    `modifier`      VARCHAR(64) NOT NULL COMMENT '最近修改人编码',
    `modify_time`   DATETIME     DEFAULT NOW() COMMENT '最后修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='时间模板';

/*Table structure for table `schedule_template_plan` */

CREATE TABLE `schedule_template_plan`
(
    `id`          BIGINT      NOT NULL AUTO_INCREMENT COMMENT '主键',
    `resource_id` VARCHAR(64) NOT NULL COMMENT '关联资源id',
    `xx_code`     INT         NOT NULL COMMENT '资源索引',
    `template_id` BIGINT      NOT NULL COMMENT '关联的计划模板',
    `create_time` DATETIME DEFAULT NOW() COMMENT '创建时间',
    `modify_time` DATETIME DEFAULT NOW() COMMENT '最后修改时间',
    `state`       INT      DEFAULT '0' COMMENT '计划状态',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_schedule_template_plan` (`resource_id`, `xx_code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='xxx计划表';

/*Data for the table `schedule_template_plan` */

/*Table structure for table `user_group` */

CREATE TABLE `user_group`
(
    `id`            INT           NOT NULL COMMENT '主键，uuid，常常迁移',
    `name`          VARCHAR(64)   NOT NULL COMMENT '用户组名',
    `initials`      VARCHAR(64)   DEFAULT NULL COMMENT '名称-首字母缩写',
    `spellings`     VARCHAR(255)  DEFAULT NULL COMMENT '名称-全拼音',
    `parent_id`     BIGINT        DEFAULT NULL COMMENT '上级用户组id',
    `level`         INT           DEFAULT NULL COMMENT '层级',
    `path`          VARCHAR(1024) NOT NULL COMMENT '组、部门路径',
    `description`   VARCHAR(1024) DEFAULT NULL COMMENT '用户组描述',
    `weight`        INT           DEFAULT NULL COMMENT '权重',
    `display_order` INT           NOT NULL COMMENT '排序字段',
    `creator`       VARCHAR(64)   NOT NULL COMMENT '创建人编号',
    `create_time`   DATETIME      DEFAULT NOW() COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='用户组、部门表';

/*Data for the table `user_group` */

/*Table structure for table `user_info` */

CREATE TABLE `user_info`
(
    `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`        VARCHAR(64)     NOT NULL COMMENT '昵称',
    `sex`         INT             NOT NULL COMMENT '性别: 0:未知；1：男性；2：女性',
    `age`         INT             NOT NULL COMMENT '年龄',
    `birth`       date            NOT NULL COMMENT '出生日期',
    `level`       INT             NOT NULL COMMENT '用户等级/权重 1-低，2-中低，3-中，4-中高，5-高',
    `id_card`     VARCHAR(64)              DEFAULT NULL,
    `real_name`   VARCHAR(128)             DEFAULT NULL COMMENT '真实姓名',
    `initials`    VARCHAR(128)             DEFAULT NULL COMMENT '真实姓名-首字母',
    `spellings`   VARCHAR(255)             DEFAULT NULL COMMENT '真实姓名-汉语拼音全拼',
    `phone_num`   VARCHAR(32)              DEFAULT NULL COMMENT '手机号',
    `email`       VARCHAR(255)             DEFAULT NULL COMMENT '邮箱',
    `status`      INT             NOT NULL DEFAULT '0' COMMENT '用户是否启用：0-正常；1-禁用 2- 删除',
    `group_auth`  INT                      DEFAULT NULL COMMENT '是否校验部门/组权限',
    `group_id`    INT                      DEFAULT NULL COMMENT '用户所属组id',
    `group_name`  VARCHAR(64)     NOT NULL COMMENT '用户组名称',
    `group_path`  VARCHAR(255)             DEFAULT NULL COMMENT '用户所属组路径',
    `creator`     VARCHAR(64)     NOT NULL COMMENT '创建人编号',
    `create_time` DATETIME                 DEFAULT NOW() COMMENT '创建时间',
    `modify_time` DATETIME                 DEFAULT NOW() COMMENT '最后修改时间',
    `description` VARCHAR(255)             DEFAULT NULL COMMENT '用户描述',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='用户信息表';

/*Data for the table `user_info` */

/*Table structure for table `user_login_info` */

CREATE TABLE `user_login_info`
(
    `id`                     VARCHAR(255) NOT NULL COMMENT '主键，用userId则每个用户只有一种登录方式',
    `user_id`                BIGINT       NOT NULL COMMENT '用户信息表id',
    `identifier`             VARCHAR(255) NOT NULL COMMENT '认证唯一标识（如：手机号 邮箱 用户名、第三方应用的唯一标识）',
    `identity_type`          INT          NOT NULL COMMENT '认证类型（枚举：手机号 邮箱 用户名）或第三方应用名称（wechat weibo qq）',
    `credential`             VARCHAR(255) NOT NULL COMMENT '认证凭证，如密码，注意密码时，需要自行体现其加密方式、盐值等',
    `last_pwd_modified_time` timestamp    NULL DEFAULT NULL COMMENT '上次密码修改时间',
    `pwd_level`              INT               DEFAULT NULL COMMENT '-1 管理员重置等强制改密码 0风险 1低 2中 3高',
    `pwd_expire_strategy`    INT          NOT NULL COMMENT '密码失效策略：是否退出所有已登录的用户',
    `login_strategy_config`  INT          NOT NULL COMMENT '登录策略，踢出上一个用户，已经登陆拒绝登录、登录数目限制、不在同一个终端、验证ip、mac',
    `online_number`          INT               DEFAULT NULL COMMENT '在线终端数',
    `ip`                     VARCHAR(1024)     DEFAULT NULL COMMENT '限制登录的ip地址-白名单',
    `ip_segment`             VARCHAR(1024)     DEFAULT NULL COMMENT '限制登录的ip段-白名单',
    `mac`                    VARCHAR(1024)     DEFAULT NULL COMMENT '限制登录的MAC地址-白名单',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_login_info` (`identifier`, `identity_type`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='用户登录信息表';

/*Data for the table `user_login_info` */

/*Table structure for table `user_login_record_fail` */

CREATE TABLE `user_login_record_fail`
(
    `id`            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `ip`            VARCHAR(64)   DEFAULT NULL COMMENT '登录的IP地址',
    `mac`           VARCHAR(1024) DEFAULT NULL COMMENT '登录的MAC地址',
    `address`       VARCHAR(64)   DEFAULT NULL COMMENT '地理位置信息',
    `identifier`    VARCHAR(64)   DEFAULT NULL COMMENT '登录的用户名',
    `identity_type` INT             NOT NULL COMMENT '认证类型（枚举：手机号 邮箱 用户名）或第三方应用名称（wechat weibo qq）',
    `fail_times`    INT           DEFAULT '0' COMMENT '登录失败次数',
    `unlock_time`   DATETIME        NOT NULL COMMENT '可登录时间',
    `login_time`    DATETIME        NOT NULL COMMENT '登录时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_user_login_info` (`identifier`, `identity_type`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='登录失败记录表';

/*Data for the table `user_login_record_fail` */

/*Table structure for table `user_login_record_success` */

CREATE TABLE `user_login_record_success`
(
    `id`            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `ip`            VARCHAR(64) DEFAULT NULL,
    `identity_type` INT         DEFAULT NULL COMMENT '登录类型（手机号 邮箱 用户名）或第三方应用名称（微信 微博等）',
    `identifier`    VARCHAR(64) DEFAULT NULL COMMENT '登录的用户名',
    `login_time`    DATETIME        NOT NULL COMMENT '登录时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='登录成功记录表';

/*Data for the table `user_login_record_success` */

/*Table structure for table `user_rel_org` */

CREATE TABLE `user_rel_org`
(
    `id`       BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`  BIGINT DEFAULT NULL COMMENT '用户id',
    `group_id` INT    DEFAULT NULL COMMENT '组id',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_rel_org` (`user_id`, `group_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='用户-用户组关联关系';

/*Data for the table `user_rel_org` */

/*Table structure for table `user_rel_role` */

CREATE TABLE `user_rel_role`
(
    `id`        BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`   BIGINT          NOT NULL COMMENT '用户id',
    `user_name` VARCHAR(64)     NOT NULL COMMENT '用户名称',
    `role_id`   BIGINT          NOT NULL COMMENT '角色id',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='用户-角色关系表';

/*Data for the table `user_rel_role` */

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;
