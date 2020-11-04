package com.lzb.cloudlike.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * projectName: cloudlike
 *
 * @author: 罗智博
 * time: 2020/11/4 16:30
 * description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartAddDto implements Serializable {
    private Integer skuid;
    private Integer uid;
    private Integer count;
    private Integer jprice;
}
