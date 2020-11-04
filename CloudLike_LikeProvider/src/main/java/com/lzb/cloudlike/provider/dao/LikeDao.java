package com.lzb.cloudlike.provider.dao;

import com.lzb.cloudlike.common.dto.ContentLikeDto;
import com.lzb.cloudlike.common.dto.LikeAddDto;
import com.lzb.cloudlike.entity.Like;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * projectName: cloudlike
 *
 * @author: 罗智博
 * time: 2020/10/30 16:36
 * description:
 */
@Repository
public interface LikeDao {
    int insert(LikeAddDto likeAddDto);

    int del(LikeAddDto likeAddDto);

    List<Like> queryByCid(int cid);

    Like querySingle(LikeAddDto dto);

    List<ContentLikeDto> queryCount();

    int[] insertBatch(List<LikeAddDto> likeAddDtos);
}
