package com.sunnao.aibox.module.system.api.dept;

import com.sunnao.aibox.framework.common.util.object.BeanUtils;
import com.sunnao.aibox.module.system.api.dept.dto.PostRespDTO;
import com.sunnao.aibox.module.system.dal.dataobject.dept.PostDO;
import com.sunnao.aibox.module.system.service.dept.PostService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * 岗位 API 实现类
 *
 * @author sunnao
 */
@Service
public class PostApiImpl implements PostApi {

    @Resource
    private PostService postService;

    @Override
    public void validPostList(Collection<Long> ids) {
        postService.validatePostList(ids);
    }

    @Override
    public List<PostRespDTO> getPostList(Collection<Long> ids) {
        List<PostDO> list = postService.getPostList(ids);
        return BeanUtils.toBean(list, PostRespDTO.class);
    }

}
