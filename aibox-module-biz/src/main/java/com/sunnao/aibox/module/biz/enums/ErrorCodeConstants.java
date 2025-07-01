package com.sunnao.aibox.module.biz.enums;

import com.sunnao.aibox.framework.common.exception.ErrorCode;

public interface ErrorCodeConstants {
    ErrorCode TEMPLATE_NOT_EXISTS = new ErrorCode(1_000_001, "模板不存在");
    ErrorCode TAG_NOT_EXISTS = new ErrorCode(1_000_002, "标签不存在");
    ErrorCode TEMPLATE_TAG_LINK_NOT_EXISTS = new ErrorCode(1_000_003, "模板标签关联不存在");
    ErrorCode KNOWLEDGE_NOT_EXISTS = new ErrorCode(1_000_004, "知识库不存在");

    // manus相关错误码
    ErrorCode USER_PROMPT_NOT_EXISTS = new ErrorCode(1_000_101, "用户提示词为空");
    ErrorCode AGENT_IS_RUNNING = new ErrorCode(1_000_102, "智能体正在运行中，请等待任务执行完成");
}
