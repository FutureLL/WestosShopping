package cn.e3mall.order.service;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.order.pojo.OrderInfo;

/**
 * @author Mr.Li
 * @version 1.0
 * @Description:
 * @Modified By:
 * @date 2019/1/19 15:50
 */
public interface OrderService {

    E3Result createOrder(OrderInfo orderInfo);
}
