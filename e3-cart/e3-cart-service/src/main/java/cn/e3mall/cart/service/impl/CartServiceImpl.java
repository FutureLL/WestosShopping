package cn.e3mall.cart.service.impl;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr.Li
 * @version 1.0
 * @Description: 购物车处理服务
 * @Modified By:
 * @date 2019/1/17 13:31
 */

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private JedisClient jedisClient;
    @Value("${REDIS_CART_PRE}")
    private String REDIS_CART_PRE;
    @Autowired
    private TbItemMapper itemMapper;

    @Override
    public E3Result addCart(long userId, long itemId, int num) {
        //向Redis添加购物车
        //数据类型hash key:用户id field:商品id value:商品信息
        //判断商品是否存在  hexists作用：根据key来判断value是否存在
        Boolean hexists = jedisClient.hexists(REDIS_CART_PRE + ":" + userId, itemId + "");
        //如果存在，数量相加
        if (hexists){
            String json = jedisClient.hget(REDIS_CART_PRE + ":" + userId, itemId + "");
            //把json转换成TBItem
            TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
            //如果Redis和cookie中有相同的商品，那么数量相加
            item.setNum(item.getNum()+num);
            //写回Redis
            jedisClient.hset(REDIS_CART_PRE + ":" + userId, itemId + "",JsonUtils.objectToJson(item));
            return E3Result.ok();
        }
        //如果不存在，根据商品id取商品信息
        TbItem item = itemMapper.selectByPrimaryKey(itemId);
        //设置购物车数量
        item.setNum(num);
        //取一张图片
        String image = item.getImage();
        if (StringUtils.isNotBlank(image)){
            item.setImage(image.split(",")[0]);
        }
        //添加到购物车列表
        jedisClient.hset(REDIS_CART_PRE + ":" + userId, itemId + "",JsonUtils.objectToJson(item));
        //返回成功
        return E3Result.ok();
    }

    @Override
    public E3Result mergeCart(long userId, List<TbItem> itemList) {
        //遍历商品列表
        //把列表添加到购物车
        //判断购物车中是否有此商品
        //如果有，数量相加
        //如果没有，添加一个新的商品
        for (TbItem tbItem:itemList){
            //这里调用了上边的addCart()方法
            addCart(userId,tbItem.getId(),tbItem.getNum());
        }
        //返回成功
        return E3Result.ok();
    }

    @Override
    public List<TbItem> getCartList(long userId) {
        //根据用户ID查询购物车列表
        List<String> jsonList = jedisClient.hvals(REDIS_CART_PRE + ":" + userId);
        List<TbItem> itemList = new ArrayList<>();
        for (String string:jsonList){
            //创建一个TbItem对象
            TbItem item = JsonUtils.jsonToPojo(string, TbItem.class);
            //添加到列表
            itemList.add(item);
        }
        return itemList;
    }

    @Override
    public E3Result updateCartNum(long userId, long itemId, int num) {
        //从Redis中取到商品信息
        String json = jedisClient.hget(REDIS_CART_PRE + ":" + userId, itemId + "");
        //更新商品数量
        TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
        //更新数量
        tbItem.setNum(num);
        //写入Redis
        jedisClient.hset(REDIS_CART_PRE + ":" + userId, itemId + "",JsonUtils.objectToJson(tbItem));
        return E3Result.ok();
    }

    @Override
    public E3Result deleteCartItem(long userId, long itemId) {
        //删除购物车商品
        jedisClient.hdel(REDIS_CART_PRE + ":" + userId, itemId + "");
        return E3Result.ok();
    }
}
