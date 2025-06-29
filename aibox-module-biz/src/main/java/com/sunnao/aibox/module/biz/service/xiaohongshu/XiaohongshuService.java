package com.sunnao.aibox.module.biz.service.xiaohongshu;

public interface XiaohongshuService {
    /**
     * 小红书选题灵感
     *
     * @param userMessage 用户消息
     * @return AI响应
     */
    String generateTopic(String userMessage);

    /**
     * 小红书笔记助手
     *
     * @param userMessage 用户消息
     * @return AI响应
     */
    String generateNote(String userMessage);

    /**
     * 小红书探店文案
     *
     * @param userMessage 用户消息
     * @return AI响应
     */
    String generateStoreVisit(String userMessage);

    /**
     * 小红书种草文案
     *
     * @param userMessage 用户消息
     * @return AI响应
     */
    String generateProduct(String userMessage);

    /**
     * 小红书爆款标题
     *
     * @param userMessage 用户消息
     * @return AI响应
     */
    String generateTitle(String userMessage);

    /**
     * 小红书账号定位
     *
     * @param userMessage 用户消息
     * @return AI响应
     */
    String generateAccountPosition(String userMessage);

    /**
     * 小红书笔记评分
     *
     * @param userMessage 用户消息
     * @return AI响应
     */
    String scoreNote(String userMessage);
}