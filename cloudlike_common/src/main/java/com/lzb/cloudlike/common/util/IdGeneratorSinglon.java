package com.lzb.cloudlike.common.util;

/**
 * projectName: cloudlike
 *
 * @author: 罗智博
 * time: 2020/11/03 14:43
 * description: 雪花算法单例模式 保证唯一实例
 */
public class IdGeneratorSinglon {
    private IdGeneratorSinglon(){
        generator=new IdGenerator();
    }
    private static IdGeneratorSinglon singlon=new IdGeneratorSinglon();
    public IdGenerator generator;
    public static IdGeneratorSinglon getInstance(){
        return singlon;
    }
}
