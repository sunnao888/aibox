package com.sunnao.aibox.module.biz.dal.redis.recommend;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.sunnao.aibox.module.biz.dal.redis.RedisKeyConstants;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 模板智能推荐 的 RedisDAO
 *
 * @author sunnao
 */
@Repository
public class RecommendTemplateRedisDAO {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 用户最近使用的模板
     */
    public void addUserLastTemplate(@NotNull Long userId, @NotNull Long templateId) {
        String formatKey = StrUtil.format(RedisKeyConstants.RECOMMEND_USER_LAST_TEMPLATE, userId);
        stringRedisTemplate.opsForValue().set(formatKey, templateId.toString());
    }

    /**
     * 模板类型关联模板列表
     */
    public void addTemplateTypeLink(@NotNull Integer type, @NotNull Long templateId) {
        String formatKey = StrUtil.format(RedisKeyConstants.RECOMMEND_TEMPLATE_TYPE, type);
        stringRedisTemplate.opsForSet().add(formatKey, templateId.toString());
    }

    /**
     * 用户标签关联zSet
     */
    public void incrementTagScores(@NotNull Long userId, List<Long> tagIds) {
        if (CollUtil.isEmpty(tagIds)) {
            return;
        }
        String formatKey = StrUtil.format(RedisKeyConstants.RECOMMEND_USER_TAG_COUNT, userId);

        // 使用 executePipelined 来执行批量操作
        stringRedisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            // 在这个回调中，所有命令都不会立即发送，而是被缓存起来
            for (Long tag : tagIds) {
                // 为每个标签的分数加 1
                connection.zSetCommands().zIncrBy(formatKey.getBytes(), 1, tag.toString().getBytes());
            }
            return null;
        });
    }

    /**
     * 获取用户最近使用的标签
     */
    public List<Long> getUserLastTags(@NotNull Long userId, int count) {
        String formatKey = StrUtil.format(RedisKeyConstants.RECOMMEND_USER_TAG_COUNT, userId);
        Set<String> topTags = stringRedisTemplate.opsForZSet().reverseRange(formatKey, 0, count - 1);
        if (topTags == null) {
            return Collections.emptyList();
        }
        return topTags.stream().map(Long::valueOf).collect(Collectors.toList());
    }

    /**
     * 标签模板关联Set
     */
    public void addTagTemplateLink(@NotNull Long tagId, @NotNull Long templateId) {
        String formatKey = StrUtil.format(RedisKeyConstants.RECOMMEND_TAG_TEMPLATE, tagId);
        stringRedisTemplate.opsForSet().add(formatKey, templateId.toString());
    }

    /**
     * 根据标签ID列表和模板类型，推荐指定数量的模板
     *
     * @param tagIds 标签ID列表 (可以为 null 或空)
     * @param type   模板类型 (可以为 null)
     * @param count  需要推荐的数量
     * @return 随机的模板ID列表
     */
    public List<String> recommendTemplates(List<Long> tagIds, Integer type, int count) {
        // --- 1. 参数校验和初始条件判断 ---
        // 必须至少有一个过滤条件（标签或类型）
        if ((CollUtil.isEmpty(tagIds)) && type == null) {
            return Collections.emptyList();
        }
        if (count <= 0) {
            return Collections.emptyList();
        }
        // --- 2. 构建所有需要参与计算的 Redis keys ---
        // 这个列表将包含所有需要求交集的 key
        List<String> keysToProcess = new ArrayList<>();
        String tempUnionKey = null; // 用于存储标签并集的临时 key
        // a. 处理标签 (OR 逻辑)
        if (CollUtil.isNotEmpty(tagIds)) {
            List<String> tagKeys = tagIds.stream()
                    .map(id -> StrUtil.format(RedisKeyConstants.RECOMMEND_TAG_TEMPLATE, id))
                    .collect(Collectors.toList());
            if (tagKeys.size() > 1) {
                // 如果有多个标签，先求并集 (SUNION)，并将结果存入一个临时 key
                tempUnionKey = "temp:union:" + IdUtil.fastSimpleUUID(); // 使用更快的UUID生成
                stringRedisTemplate.opsForSet().unionAndStore(tagKeys.getFirst(), tagKeys.subList(1, tagKeys.size()), tempUnionKey);
                // *重要*：给临时 key 设置一个短暂的过期时间，作为安全保障
                stringRedisTemplate.expire(tempUnionKey, 60, TimeUnit.SECONDS);
                keysToProcess.add(tempUnionKey);
            } else {
                // 只有一个标签，直接使用该标签的 key
                keysToProcess.add(tagKeys.getFirst());
            }
        }
        // b. 处理类型 (AND 逻辑)
        if (type != null) {
            String typeKey = StrUtil.format(RedisKeyConstants.RECOMMEND_TEMPLATE_TYPE, type);
            keysToProcess.add(typeKey);
        }
        // 如果最终没有可处理的 key，直接返回（理论上不会发生，因为我们有入口检查）
        if (keysToProcess.isEmpty()){
            return Collections.emptyList();
        }
        // --- 3. 执行核心操作：求交集并随机抽样 ---
        String finalSetKey; // 将存放最终结果集的 key
        String tempIntersectKey = null; // 用于存储交集的临时 key
        if (keysToProcess.size() > 1) {
            // 有多个 key（例如：一个标签并集 + 一个类型），需要求交集 (SINTER)
            tempIntersectKey = "temp:intersect:" + IdUtil.fastSimpleUUID();
            stringRedisTemplate.opsForSet().intersectAndStore(keysToProcess.getFirst(), keysToProcess.subList(1, keysToProcess.size()), tempIntersectKey);
            stringRedisTemplate.expire(tempIntersectKey, 60, TimeUnit.SECONDS);
            finalSetKey = tempIntersectKey;
        } else {
            // 只有一个 key（例如：只有一个标签，或只按类型过滤）
            finalSetKey = keysToProcess.getFirst();
        }

        // c. 从最终的结果集中随机抽取 'count' 个成员
        Set<String> randomMembers = stringRedisTemplate.opsForSet().distinctRandomMembers(finalSetKey, count);
        // --- 4. 清理所有临时 key ---
        // 这是一个良好的实践，确保不留垃圾数据
        if (tempUnionKey != null) {
            stringRedisTemplate.delete(tempUnionKey);
        }
        if (tempIntersectKey != null) {
            stringRedisTemplate.delete(tempIntersectKey);
        }
        return randomMembers != null ? List.copyOf(randomMembers) : Collections.emptyList();
    }
}
