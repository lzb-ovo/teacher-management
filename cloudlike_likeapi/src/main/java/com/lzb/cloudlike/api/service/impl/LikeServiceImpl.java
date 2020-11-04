package com.lzb.cloudlike.api.service.impl;

import com.lzb.cloudlike.common.dto.LikeAddDto;
import com.lzb.cloudlike.common.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * projectName: cloudlike
 *
 * @author: 罗智博
 * time: 2020/10/30 19:09
 * description:
 */
@Service
public class LikeServiceImpl {
    @Autowired
    private RestTemplate restTemplate;
    public R dz(LikeAddDto dto){
        System.out.println("dto ==================== " + dto);
        return restTemplate.postForObject("http://LikeProvider/provider/like/dz.do",dto,R.class);
    }
    public R all(){
        return restTemplate.getForObject("http://LikeProvider/provider/like/count.do",R.class);
    }
}
