package com.sunnao.aibox.module.biz.service.manus;

import com.sunnao.aibox.module.biz.ai.agent.manus.model.ResultMessage;
import com.sunnao.aibox.module.biz.controller.admin.manus.vo.ManusReqVO;

import java.util.List;

public interface ManusService {

    List<ResultMessage> jManus(ManusReqVO reqVO);

}
