package com.sunnao.aibox.module.biz.dal.redis;

public interface RedisKeyConstants {

    /**
     * 模板类型关联模板列表
     */
    String RECOMMEND_TEMPLATE_TYPE = "biz:recommend:template_type:{}";

    /**
     * 用户最近使用的模板
     */
    String RECOMMEND_USER_LAST_TEMPLATE = "biz:recommend:user_last_template:{}";

    /**
     * 用户标签使用量的缓存
     */
    String RECOMMEND_USER_TAG_COUNT = "biz:recommend:user_tag_count:{}";

    /**
     * 标签模板关联Set
     */
    String RECOMMEND_TAG_TEMPLATE = "biz:recommend:tag_template:{}";

}
