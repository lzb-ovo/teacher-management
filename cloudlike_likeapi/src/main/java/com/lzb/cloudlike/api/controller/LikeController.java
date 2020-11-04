package com.lzb.cloudlike.api.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.lzb.cloudlike.api.service.impl.LikeServiceImpl;
import com.lzb.cloudlike.common.dto.LikeAddDto;
import com.lzb.cloudlike.common.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * projectName: cloudlike
 *
 * @author: 罗智博
 * time: 2020/10/30 19:56
 * description:
 */
@RestController
@RequestMapping("/api/like/")
public class LikeController {
    @Autowired
    private LikeServiceImpl service;
    @PostMapping("/dz.do")
    public R dz(@RequestBody LikeAddDto likeAddDto){
        return service.dz(likeAddDto);
    }
    //查询  如果出现错误  服务降级
    @SentinelResource(value = "count.do",fallback = "error1")
    @GetMapping("/count.do")
    public R all(){
        return service.all();
    }
    //服务的降级
    public R error1(){
        return R.fail("傻逼，服务器出毛病了");
    }
}
