package com.sunnao.aibox.module.system.api.social;

import com.sunnao.aibox.module.system.api.social.dto.SocialUserBindReqDTO;
import com.sunnao.aibox.module.system.api.social.dto.SocialUserRespDTO;
import com.sunnao.aibox.module.system.api.social.dto.SocialUserUnbindReqDTO;
import com.sunnao.aibox.module.system.service.social.SocialUserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * 社交用户的 API 实现类
 *
 * @author sunnao
 */
@Service
@Validated
public class SocialUserApiImpl implements SocialUserApi {

    @Resource
    private SocialUserService socialUserService;

    @Override
    public String bindSocialUser(SocialUserBindReqDTO reqDTO) {
        return socialUserService.bindSocialUser(reqDTO);
    }

    @Override
    public void unbindSocialUser(SocialUserUnbindReqDTO reqDTO) {
        socialUserService.unbindSocialUser(reqDTO.getUserId(), reqDTO.getUserType(),
                reqDTO.getSocialType(), reqDTO.getOpenid());
    }

    @Override
    public SocialUserRespDTO getSocialUserByUserId(Integer userType, Long userId, Integer socialType) {
        return socialUserService.getSocialUserByUserId(userType, userId, socialType);
    }

    @Override
    public SocialUserRespDTO getSocialUserByCode(Integer userType, Integer socialType, String code, String state) {
       return socialUserService.getSocialUserByCode(userType, socialType, code, state);
    }

}
