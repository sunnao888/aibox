package com.sunnao.aibox.module.biz.dal.dataobject.template;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sunnao.aibox.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

/**
 * 模板 DO
 *
 * @author sunnao
 */
@TableName("biz_template")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateDO extends BaseDO {

    /**
     * 用户ID
     */
    @TableId
    private Long id;
    /**
     * 模板名称
     */
    private String name;
    /**
     * 模板类型
     */
    @TableField("`type`")
    private Integer type;
    /**
     * 输入用例
     */
    @TableField("`input`")
    private String input;
    /**
     * 输出用例
     */
    @TableField("`output`")
    private String output;


}