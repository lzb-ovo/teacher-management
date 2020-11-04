package com.lzb.cloudlike.provider.service.impl;

import com.lzb.cloudlike.common.dto.CartAddDto;
import com.lzb.cloudlike.common.dto.CartItemDto;
import com.lzb.cloudlike.common.vo.R;
import com.lzb.cloudlike.entity.Cart;
import com.lzb.cloudlike.provider.dao.CartDao;
import com.lzb.cloudlike.provider.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * projectName: cloudlike
 *
 * @author: 罗智博
 * time: 2020/11/4 16:59
 * description:
 */
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartDao cartDao;
    @Override
    public R addCartV1(CartAddDto cartAddDto) {
        if (cartAddDto != null) {
            if (cartAddDto.getUid()>0&&cartAddDto.getSkuid()>0&&cartAddDto.getJprice()>0&&cartAddDto.getCount()>0){
                Cart cart = cartDao.queryBySkuid(cartAddDto);
                if (cart == null) {
                    if (cartDao.insert(cartAddDto)>0){
                        return R.ok();
                    }
                }else {
                    if(cartDao.update(cartAddDto)>0){
                     return R.ok();
                    }
                }
            }
        }
        return R.fail();
    }

    @Override
    public R plusCountV1(CartItemDto cartItemDto) {
        if (cartItemDto != null) {
            if (cartItemDto.getCount()>0&&cartItemDto.getId()>0){
                if (cartDao.updateByid(cartItemDto)>0){
                    return R.ok();
                }else{
                    return R.fail();
                }
            }
        }
        return R.fail();
    }

    @Override
    public R minusCountV1(CartItemDto cartItemDto) {
        if (cartItemDto != null) {
            if (cartItemDto.getCount()>0&&cartItemDto.getId()>0){
                cartItemDto.setCount(-cartItemDto.getCount());
                if (cartDao.updateByid(cartItemDto)>0){
                    return R.ok();
                }else{
                    return R.fail();
                }
            }
        }
        return R.fail();
    }

    @Override
    public R deleteV1(int id){
        if (id>0) {
            if(cartDao.delById(id)>0){
             return R.ok();
            }else {
                return R.fail();
            }
        }
        return R.fail();
    }
}
