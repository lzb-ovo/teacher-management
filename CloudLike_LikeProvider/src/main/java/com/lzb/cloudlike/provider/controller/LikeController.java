package com.lzb.cloudlike.provider.controller;

import com.lzb.cloudlike.common.dto.LikeAddDto;
import com.lzb.cloudlike.common.vo.R;
import com.lzb.cloudlike.provider.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * projectName: cloudlike
 *
 * @author: 罗智博
 * time: 2020/10/30 17:53
 * description:
 */
@RestController
@RequestMapping("/provider/like/")
@RefreshScope//开启实时刷新 实时获取配置中心的数据 实现动态配置
public class LikeController {
    @Autowired
    private LikeService likeService;
    @Value("${like.score}")//配置得东西需要通过value传过来
    private int score;
    @RequestMapping("dz.do")
    public R dz(@RequestBody LikeAddDto dto){
        System.out.println("dto!!!!!!!!!!!!!!!!!!!!!!!!1 = " + dto);
        System.out.println("真的狗"+score);
        return likeService.likev1(dto);
    }
    @RequestMapping("count.do")
    public R all(){
        return likeService.queryCount();
    }

}
