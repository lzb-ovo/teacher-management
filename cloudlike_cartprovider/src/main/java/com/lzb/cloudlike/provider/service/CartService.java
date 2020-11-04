package com.lzb.cloudlike.provider.service;

import com.lzb.cloudlike.common.dto.CartAddDto;
import com.lzb.cloudlike.common.dto.CartItemDto;
import com.lzb.cloudlike.common.vo.R;

/**
 * projectName: cloudlike
 *
 * @author: 罗智博
 * time: 2020/11/4 16:54
 * description:
 */

public interface CartService {
    //在商品主页加入购物车
   R addCartV1(CartAddDto cartAddDto);
   //在购物车中加减商品数量
   R plusCountV1(CartItemDto cartItemDto);
   R minusCountV1(CartItemDto cartItemDto);
   //删除购物车中的商品
    R deleteV1(int id);
}
