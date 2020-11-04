package com.lzb.cloudlike.provider.listener;

import com.lzb.cloudlike.common.config.RebbitMQTypeConfig;
import com.lzb.cloudlike.common.dto.LikeAddDto;
import com.lzb.cloudlike.common.dto.MqMessageDto;
import com.lzb.cloudlike.provider.dao.LikeDao;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * projectName: cloudlike
 *
 * @author: 罗智博
 * time: 2020/11/3 15:46
 * description:
 */
@Component
@RabbitListener
public class LikeQueueListener {
    @Autowired
    private LikeDao likeDao;
    @RabbitHandler
    public void handler(MqMessageDto dto){
        //验证消息的类型
        if (dto.getType()== RebbitMQTypeConfig.MY_LIKE_ADD||dto.getType()==RebbitMQTypeConfig.MY_LIKE_DEL){
            //获取操作
            switch (dto.getType()){
                case RebbitMQTypeConfig.MY_LIKE_ADD:
                    //新增点赞
                    likeDao.insert((LikeAddDto) dto.getData());
                    break;
                 case RebbitMQTypeConfig.MY_LIKE_DEL:
                     //删除点赞
                    likeDao.del((LikeAddDto) dto.getData());
                    break;
            }
        }
    }
}
