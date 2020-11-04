package com.lzb.cloudlike.provider.service;

import com.lzb.cloudlike.common.dto.LikeAddDto;
import com.lzb.cloudlike.common.vo.R;

/**
 * projectName: cloudlike
 *
 * @author: 罗智博
 * time: 2020/10/30 17:39
 * description:
 */

public interface LikeService {
    R likev1(LikeAddDto dto);
    R likev2(LikeAddDto dto);
    R likev3(LikeAddDto dto);
    R queryCount();
    R queryByCid(int cid);
}
