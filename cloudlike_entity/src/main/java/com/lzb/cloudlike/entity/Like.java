package com.lzb.cloudlike.entity;

import lombok.Data;

import java.util.Date;

/**
 * projectName: cloudlike
 *
 * @author: 罗智博
 * time: 2020/10/30 16:33
 * description:
 */
@Data
public class Like {
    private Integer id;
    private Integer cid;
    private Integer uid;
    private Date ctime;

}
