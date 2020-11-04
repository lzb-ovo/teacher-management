package com.lzb.cloudlike.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * projectName: cloudlike
 *
 * @author: 罗智博
 * time: 2020/11/3 14:27
 * description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MqMessageDto {
    private long id;
    private int type;
    private Object data;
}
