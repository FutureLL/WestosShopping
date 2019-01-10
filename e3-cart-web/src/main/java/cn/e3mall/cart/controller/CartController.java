package cn.e3mall.cart.controller;

import cn.e3mall.common.utils.CookieUtils;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr.Li
 * @version 1.0
 * @Description: 购物车处理Controller
 * @Modified By:
 * @date 2019/1/10 18:26
 */

@Controller
public class CartController {

    @Autowired
    private ItemService itemService;
    @Value("${COOKIE_CART_EXPIRE}")
    private Integer COOKIE_CART_EXPIRE;

    @RequestMapping("/cart/add/{itemId}")
    /**
     * 这里设置了购物车的某件商品的数量默认值为1
     */
    public String addCart(@PathVariable Long itemId, @RequestParam(defaultValue = "1") Integer num,
                          HttpServletRequest request, HttpServletResponse response){
        //从cookie中取购物车列表
        List<TbItem> cartList = getCartListFromCookie(request);
        //判断商品在商品列表中是否存在
        boolean flag=false;
        for (TbItem tbItem : cartList){
            //如果存在数量相加
            if (tbItem.getId() == itemId.longValue()){
                flag=true;
                //找到商品，数量相加
                tbItem.setNum(tbItem.getNum()+num);
                //跳出循环
                break;
            }
        }
        //如果不存在
        //如果flag为false
        if (!flag){
            //根据商品id查询商品信息，得到一个TbItem对象
            TbItem tbItem = itemService.getItemById(itemId);
            //设置商品数量
            tbItem.setNum(num);
            //取一张图片
            String image=tbItem.getImage();
            if (StringUtils.isNotBlank(image)){
                tbItem.setImage(image.split(",")[0]);
            }
            //把商品添加到商品列表
            cartList.add(tbItem);
        }
        //写入cookie
        CookieUtils.setCookie(request,response,"cart",JsonUtils.objectToJson(cartList),COOKIE_CART_EXPIRE,true);
        //返回成功添加页面
        return "cartSuccess";
    }

    /**
     * 从cookie中取购物车列表的处理
     * @param request
     * @return
     */
    private List<TbItem> getCartListFromCookie(HttpServletRequest request){
        String json = CookieUtils.getCookieValue(request, "cart", true);
        //判断json是否为空
        if (StringUtils.isBlank(json)){
            return new ArrayList<>();
        }
        //吧json转换成一个商品列表
        List<TbItem> list = JsonUtils.jsonToList(json, TbItem.class);
        return list;
    }

    @RequestMapping("/cart/cart")
    public String showCartList(HttpServletRequest request){
        //从cookie中取购物车列表
        List<TbItem> cartList = getCartListFromCookie(request);
        //把列表传递给页面
        request.setAttribute("cartList",cartList);
        //返回逻辑视图
        return "cart";
    }
}
