package com.lzb.cloudlike.provider.dao;

import com.lzb.cloudlike.common.dto.CartAddDto;
import com.lzb.cloudlike.common.dto.CartItemDto;
import com.lzb.cloudlike.entity.Cart;
import org.springframework.stereotype.Repository;

/**
 * projectName: cloudlike
 *
 * @author: 罗智博
 * time: 2020/11/4 16:27
 * description:
 */
@Repository
public interface CartDao {
    //查询这个商品以前是否加入过购物车 根据skuid
    Cart queryBySkuid(CartAddDto cartAddDto);
    //加入购物车
    int insert(CartAddDto cartAddDto);
    //修改
    int update(CartAddDto cartAddDto);
    //这种情况是在购物车里面直接修改数量 这里只需要id即可
    int updateByid(CartItemDto cartItemDto);
    //删除
    int delById(int id);
}
