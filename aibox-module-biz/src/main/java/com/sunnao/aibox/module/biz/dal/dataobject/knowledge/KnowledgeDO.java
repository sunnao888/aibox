package com.sunnao.aibox.module.biz.dal.dataobject.knowledge;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.sunnao.aibox.framework.mybatis.core.dataobject.BaseDO;

/**
 * 知识库 DO
 *
 * @author sunnao
 */
@TableName("biz_knowledge")
@KeySequence("biz_knowledge_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KnowledgeDO extends BaseDO {

    /**
     * 用户ID
     */
    @TableId
    private Long id;
    /**
     * 标题
     */
    private String title;
    /**
     * 状态
     *
     * 枚举 {@link TODO common_status 对应的类}
     */
    private Integer status;
    /**
     * 内容
     */
    private String context;


}