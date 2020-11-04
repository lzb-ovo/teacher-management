package com.lzb.cloudlike.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * projectName: cloudlike
 *
 * @author: 罗智博
 * time: 2020/11/4 16:48
 * description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto implements Serializable {
    private Integer id;
    private Integer count; //加是正数  减是负数
}
