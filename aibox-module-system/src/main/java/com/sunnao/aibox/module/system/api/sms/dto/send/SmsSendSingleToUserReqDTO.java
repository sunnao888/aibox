package com.sunnao.aibox.module.system.api.sms.dto.send;

import com.sunnao.aibox.framework.common.validation.Mobile;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Map;

/**
 * 短信发送给 Admin 或者 Member 用户
 *
 * @author sunnao
 */
@Data
public class SmsSendSingleToUserReqDTO {

    /**
     * 用户编号
     */
    private Long userId;
    /**
     * 手机号
     */
    @Mobile
    private String mobile;
    /**
     * 短信模板编号
     */
    @NotEmpty(message = "短信模板编号不能为空")
    private String templateCode;
    /**
     * 短信模板参数
     */
    private Map<String, Object> templateParams;

}
