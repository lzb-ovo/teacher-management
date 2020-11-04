package com.lzb.cloudlike.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * projectName: cloudlike
 *
 * @author: 罗智博
 * time: 2020/11/4 16:20
 * description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart implements Serializable {
    private Integer id;
    private Integer skuid;
    private Integer scount;
    private Integer uid;
    private Integer jprice;
    private Date ctime;

    public Cart(Integer skuid, Integer scount, Integer uid, Integer jprice) {
        this.skuid = skuid;
        this.scount = scount;
        this.uid = uid;
        this.jprice = jprice;
    }
}
