package com.sunnao.aibox.module.biz.service.recreation;

import com.sunnao.aibox.module.biz.ai.agent.recreation.WritePoemAgent;
import com.sunnao.aibox.module.biz.controller.admin.recreation.vo.WritePoemReqVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 生活娱乐 Service 实现类
 *
 * @author sunnao
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RecreationServiceImpl implements RecreationService {

    private final WritePoemAgent writePoemAgent;

    @Override
    public String writePoem(WritePoemReqVO reqVO) {
        return writePoemAgent.chat(reqVO.getUserMessage());
    }
}
