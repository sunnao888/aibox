package com.sunnao.aibox.module.biz.dal.dataobject.templatetaglink;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.sunnao.aibox.framework.mybatis.core.dataobject.BaseDO;

/**
 * 模板标签关联 DO
 *
 * @author sunnao
 */
@TableName("biz_template_tag_link")
@KeySequence("biz_template_tag_link_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateTagLinkDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 模板id
     */
    private Long templateId;
    /**
     * 标签id
     */
    private Long tagId;


}