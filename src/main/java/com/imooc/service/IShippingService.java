package com.imooc.service;

import com.github.pagehelper.PageInfo;
import com.imooc.common.ServerResponse;
import com.imooc.pojo.Shipping;
import com.imooc.vo.CartVo;

public interface IShippingService {

    ServerResponse add(Integer userId,Shipping shipping);

    ServerResponse<String> del(Integer userId,Integer shippingId);

    ServerResponse update(Integer userId,Shipping shipping);

    ServerResponse<Shipping> select(Integer userId, Integer shippingId);

    ServerResponse<PageInfo> list(Integer userId, int pageNum, int pageSize);

}
