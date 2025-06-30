package com.sunnao.aibox.module.biz.enums;

import com.sunnao.aibox.framework.common.exception.ErrorCode;

public interface ErrorCodeConstants {
    ErrorCode TEMPLATE_NOT_EXISTS = new ErrorCode(1_000_001, "模板不存在");
    ErrorCode TAG_NOT_EXISTS = new ErrorCode(1_000_002, "标签不存在");
    ErrorCode TEMPLATE_TAG_LINK_NOT_EXISTS = new ErrorCode(1_000_003, "模板标签关联不存在");
    ErrorCode KNOWLEDGE_NOT_EXISTS = new ErrorCode(1_000_004, "知识库不存在");
}
