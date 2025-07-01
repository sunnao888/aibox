package com.sunnao.aibox.module.biz.service.manus;

import com.sunnao.aibox.module.biz.ai.agent.manus.JManus;
import com.sunnao.aibox.module.biz.ai.agent.manus.model.ResultMessage;
import com.sunnao.aibox.module.biz.controller.admin.manus.vo.ManusReqVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ManusServiceImpl implements ManusService {

    @Resource
    private JManus jManus;

    @Override
    public List<ResultMessage> jManus(ManusReqVO reqVO) {
        List<ResultMessage> result = jManus.run(reqVO.getUserMessage());
        log.info("JManus 执行结果 {}", result);
        return result;
    }
}
