package com.lzb.cloudlike.provider.task;

import com.lzb.cloudlike.common.dto.LikeAddDto;
import com.lzb.cloudlike.provider.config.RedisKeyConfig;
import com.lzb.cloudlike.provider.dao.LikeDao;
import com.lzb.cloudlike.provider.third.RedissonUtils;
import org.redisson.client.protocol.ScoredEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

/**
 * projectName: cloudlike
 *
 * @author: 罗智博
 * time: 2020/11/2 22:17
 * description:
 */
@Component
public class HelloTask {
    @Autowired
    private LikeDao dao;
    //每隔三秒执行一次
    @Scheduled(cron = "0/3 * * * * ?")
    public void t1(){
        System.out.println("醒醒，不能睡觉："+System.currentTimeMillis()/1000);
    }
    //实现点赞数据同步到mysql中  每天凌晨4点
    @Scheduled(cron = "0 0 4 ? * *")
    public void syncMysqlLike(){
        //当前时间的24小时前 创建日历
        Calendar calendar = Calendar.getInstance();
        //给日历添加时间 前一天
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        //获得时间戳
        long ctime = calendar.getTimeInMillis();
        ArrayList<LikeAddDto> likeAddDtos = new ArrayList<>();
        //实现点赞同步
        //用模糊查询查询出所有的key
        Iterable<String> keys = RedissonUtils.getKeys(RedisKeyConfig.LIKE_CID+"*");
        while (keys.iterator().hasNext()){
            String k = keys.iterator().next();
            Collection<ScoredEntry<Object>> uids = RedissonUtils.getZSet(k, ctime);
            for (ScoredEntry<Object> uid : uids) {
                likeAddDtos.add(new LikeAddDto(Integer.parseInt(uid.toString()),Integer.parseInt(k.substring(k.lastIndexOf(":")+1))));
            }
        }
        dao.insertBatch(likeAddDtos);
    }
}
