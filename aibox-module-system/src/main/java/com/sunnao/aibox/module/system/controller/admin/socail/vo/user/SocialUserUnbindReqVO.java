package com.sunnao.aibox.module.system.controller.admin.socail.vo.user;

import com.sunnao.aibox.framework.common.validation.InEnum;
import com.sunnao.aibox.module.system.enums.social.SocialTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "管理后台 - 取消社交绑定 Request VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocialUserUnbindReqVO {

    @Schema(description = "社交平台的类型，参见 UserSocialTypeEnum 枚举值", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    @InEnum(SocialTypeEnum.class)
    @NotNull(message = "社交平台的类型不能为空")
    private Integer type;

    @Schema(description = "社交用户的 openid", requiredMode = Schema.RequiredMode.REQUIRED, example = "IPRmJ0wvBptiPIlGEZiPewGwiEiE")
    @NotEmpty(message = "社交用户的 openid 不能为空")
    private String openid;

}
