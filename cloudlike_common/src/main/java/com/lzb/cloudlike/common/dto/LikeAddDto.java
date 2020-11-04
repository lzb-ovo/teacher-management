package com.lzb.cloudlike.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * projectName: cloudlike
 *
 * @author: 罗智博
 * time: 2020/10/30 16:38
 * description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeAddDto {
    private int uid;
    private int cid;
}
