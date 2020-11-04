package com.lzb.cloudlike.provider.service.impl;

import com.lzb.cloudlike.common.config.RebbitMQTypeConfig;
import com.lzb.cloudlike.common.dto.LikeAddDto;
import com.lzb.cloudlike.common.dto.MqMessageDto;
import com.lzb.cloudlike.common.util.IdGeneratorSinglon;
import com.lzb.cloudlike.common.vo.R;
import com.lzb.cloudlike.entity.Like;
import com.lzb.cloudlike.provider.config.RabbitMQConfig;
import com.lzb.cloudlike.provider.config.RedisKeyConfig;
import com.lzb.cloudlike.provider.dao.LikeDao;
import com.lzb.cloudlike.provider.service.LikeService;
import com.lzb.cloudlike.provider.third.RedissonUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * projectName: cloudlike
 *
 * @author: 罗智博
 * time: 2020/10/30 17:43
 * description:
 */
@Service
public class LikeServiceImpl implements LikeService {
    @Autowired
    private LikeDao likeDao;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Override
    public R likev1(LikeAddDto dto) {
        if (dto!=null && dto.getCid()>0 && dto.getUid()>0){
            Like like = likeDao.querySingle(dto);
            if (like==null){
                if (likeDao.insert(dto)>0){
                    return R.ok();
                }else {
                    return R.fail("傻逼点赞失败了");
                }
            }else {
                if (likeDao.del(dto)>0){
                    return R.ok();
                }else {
                    return R.fail("傻逼谁让你取消了");
                }
            }
        }else {
            return  R.fail("傻逼你传过来的东西都不对");
        }
    }

    //更改redis----直接操作mysql
    public R likev2Old(LikeAddDto dto) {
        if (dto!=null && dto.getCid()>0 && dto.getUid()>0){
            String key = RedisKeyConfig.LIKE_CID+dto.getCid();
            //验证之前是否点过赞
            if (RedissonUtils.checkKey(key)){
                //点过赞所有会有key但是不一定你自己点过赞
                //首先判断你自己是否点过赞
                if(RedissonUtils.checkZSet(key,dto.getUid())){
                    //点过赞
                    //取消点赞
                    if(RedissonUtils.delZSet(key,dto.getUid()+"")){
                        likeDao.del(dto);
                        return R.ok();
                    }else {
                        return R.fail("傻逼服务器蹦了");
                    }
                }else{
                    //点赞
                    RedissonUtils.setZset(key, System.currentTimeMillis(),dto.getUid()+"");
                    //mysql
                    likeDao.insert(dto);
                    return R.ok();
                }
            }else {
                //文章的首次点赞
                RedissonUtils.setZset(key, System.currentTimeMillis(),dto.getUid()+"");
                likeDao.insert(dto);
                return R.ok();
            }
        }
        return R.fail("请输入合法的参数");
    }
    @Override
    public R likev2(LikeAddDto dto) {
        if (dto!=null && dto.getCid()>0 && dto.getUid()>0){
            String key = RedisKeyConfig.LIKE_CID+dto.getCid();
            //验证之前是否点过赞
            if (RedissonUtils.checkKey(key)){
                //点过赞所有会有key但是不一定你自己点过赞
                //首先判断你自己是否点过赞
                if(RedissonUtils.checkZSet(key,dto.getUid())){
                    //点过赞
                    //取消点赞
                    if(RedissonUtils.delZSet(key,dto.getUid()+"")){
                        return R.ok();
                    }else {
                        return R.fail("傻逼服务器蹦了");
                    }
                }else{
                    //点赞
                    RedissonUtils.setZset(key, System.currentTimeMillis(),dto.getUid()+"");
                    //mysql
                    return R.ok();
                }
            }else {
                //文章的首次点赞
                RedissonUtils.setZset(key, System.currentTimeMillis(),dto.getUid()+"");
                return R.ok();
            }
        }
        return R.fail("请输入合法的参数");
    }

    @Override
    public R likev3(LikeAddDto dto) {
        if (dto!=null && dto.getCid()>0 && dto.getUid()>0){
            String key = RedisKeyConfig.LIKE_CID+dto.getCid();
            //验证之前是否点过赞
            if (RedissonUtils.checkKey(key)){
                //点过赞所有会有key但是不一定你自己点过赞
                //首先判断你自己是否点过赞
                if(RedissonUtils.checkZSet(key,dto.getUid())){
                    //点过赞
                    //取消点赞
                    if(RedissonUtils.delZSet(key,dto.getUid()+"")){
                        rabbitTemplate.convertAndSend("", RabbitMQConfig.qname_like,new MqMessageDto(IdGeneratorSinglon.getInstance().generator.nextId(),RebbitMQTypeConfig.MY_LIKE_DEL,dto));
                        return R.ok();
                    }else {
                        return R.fail("傻逼服务器蹦了");
                    }
                }else{
                    //点赞
                    RedissonUtils.setZset(key, System.currentTimeMillis(),dto.getUid()+"");
                    rabbitTemplate.convertAndSend("", RabbitMQConfig.qname_like,new MqMessageDto(IdGeneratorSinglon.getInstance().generator.nextId(),RebbitMQTypeConfig.MY_LIKE_ADD,dto));
                    //mysql
                    return R.ok();
                }
            }else {
                //mysql
                List<Like> list = likeDao.queryByCid(dto.getCid());
                if (list==null){
                    //文章的首次点赞
                    RedissonUtils.setZset(key, System.currentTimeMillis(),dto.getUid()+"");
                    RedissonUtils.setTime(key,RedisKeyConfig.LIKE_TIME, TimeUnit.DAYS);
                    rabbitTemplate.convertAndSend("", RabbitMQConfig.qname_like,new MqMessageDto(IdGeneratorSinglon.getInstance().generator.nextId(),RebbitMQTypeConfig.MY_LIKE_ADD,dto));
                    return R.ok();
                }else {
                    boolean r = false;
                    //点过赞redis过期 从数据库同步
                    Map<Object, Double> map = new LinkedHashMap<>();
                    for (Like like : list) {
                        if (like.getUid()==dto.getUid()){
                            r = true;
                        }
                        map.put(like.getUid().toString(), Double.parseDouble(like.getCtime().getTime() + ""));
                    }
                    RedissonUtils.setZset(key, map);
                    if (r){
                        if(RedissonUtils.delZSet(key,dto.getUid()+"")){
                            rabbitTemplate.convertAndSend("", RabbitMQConfig.qname_like,new MqMessageDto(IdGeneratorSinglon.getInstance().generator.nextId(),RebbitMQTypeConfig.MY_LIKE_DEL,dto));
                            return R.ok();
                        }else {
                            return R.fail("傻逼服务器蹦了");
                        }
                    }else {
                        //文章的首次点赞
                        RedissonUtils.setZset(key, System.currentTimeMillis(),dto.getUid()+"");
                        RedissonUtils.setTime(key,RedisKeyConfig.LIKE_TIME, TimeUnit.DAYS);
                        rabbitTemplate.convertAndSend("", RabbitMQConfig.qname_like,new MqMessageDto(IdGeneratorSinglon.getInstance().generator.nextId(),RebbitMQTypeConfig.MY_LIKE_ADD,dto));
                        return R.ok();
                    }
                }
            }
        }
        return R.fail("请输入合法的参数");
    }

    @Override
    public R queryCount() {
        return R.ok(likeDao.queryCount());
    }

    @Override
    public R queryByCid(int cid) {
        //rediskey
        String key = RedisKeyConfig.LIKE_CID + cid;
        //校验如果redis里面有这个key,则直接获得这个key的所有值
        if (RedissonUtils.checkKey(key)) {
            return R.ok(RedissonUtils.getZSet(key));
        } else {
            //如果redis里面没有这个key的值则去数据库中取，取过之后先放到redis中
            List<Like> list = likeDao.queryByCid(cid);
            Map<Object, Double> map = new LinkedHashMap<>();
            for (Like like : list) {
                map.put(like.getUid().toString(), Double.parseDouble(like.getCtime().getTime() + ""));
            }
            RedissonUtils.setZset(key, map);
            RedissonUtils.setTime(key,RedisKeyConfig.LIKE_TIME, TimeUnit.DAYS);
            return R.ok(map.keySet());
        }
    }
}
