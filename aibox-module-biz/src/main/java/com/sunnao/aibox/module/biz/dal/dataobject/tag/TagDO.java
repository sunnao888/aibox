package com.sunnao.aibox.module.biz.dal.dataobject.tag;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.sunnao.aibox.framework.mybatis.core.dataobject.BaseDO;

/**
 * 标签 DO
 *
 * @author sunnao
 */
@TableName("biz_tag")
@KeySequence("biz_tag_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagDO extends BaseDO {

    /**
     * 用户ID
     */
    @TableId
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 状态(0 禁用 1启用)
     */
    private Boolean status;


}