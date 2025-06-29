package com.sunnao.aibox.module.biz.service.recreation;

import com.sunnao.aibox.module.biz.controller.admin.recreation.vo.WritePoemReqVO;
import jakarta.validation.Valid;

public interface RecreationService {

    /**
     * 写诗
     *
     * @param reqVO 写诗请求参数
     * @return 生成的诗歌内容
     */
    String writePoem(@Valid WritePoemReqVO reqVO);
}
