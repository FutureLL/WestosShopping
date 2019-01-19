package cn.e3mall.cart.controller;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.common.utils.CookieUtils;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @Autowired
    private CartService cartService;

    @RequestMapping("/cart/add/{itemId}")
    /**
     * 这里设置了购物车的某件商品的数量默认值为1
     */
    public String addCart(@PathVariable Long itemId, @RequestParam(defaultValue = "1") Integer num,
                          HttpServletRequest request, HttpServletResponse response){
        /**
         * 后加的内容，添加了用户登录与不登录状态下的购物车
         */
        //判断用户是否为登录状态
        TbUser user= (TbUser) request.getAttribute("user");
        //如果是登录状态，把购物车写入Redis
        if (user!=null){
            //保存到服务端
            cartService.addCart(user.getId(),itemId,num);
            //返回逻辑视图
            return "cartSuccess";
        }

        //如果是未登录状态，使用cookie
        //从cookie中取购物车列表
        List<TbItem> cartList = getCartListFromCookie(request);
        //判断商品在商品列表中是否存在
        boolean flag=false;
        for (TbItem tbItem : cartList){
            //如果存在，数量相加
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
        /**
         * 这里的cookieName是自己定义的，isDecoder表示开启默认的编码格式【默认为utf-8】
         */
        String json = CookieUtils.getCookieValue(request, "cart", true);
        //判断json是否为空
        if (StringUtils.isBlank(json)){
            return new ArrayList<>();
        }
        //把json转换成一个商品列表
        List<TbItem> list = JsonUtils.jsonToList(json, TbItem.class);
        return list;
    }

    /**
     * 展示商品购物车列表
     * @param request
     * @return
     */
    @RequestMapping("/cart/cart")
    public String showCartList(HttpServletRequest request,HttpServletResponse response){
        //从cookie中取购物车列表
        List<TbItem> cartList = getCartListFromCookie(request);

        //判断用户是否为登录状态
        TbUser user= (TbUser) request.getAttribute("user");
        //如果为登录状态
        if (user!=null){
            //从cookie中取购物车列表,上边完成了
            //如果不为空，把cookie中的购物车商品和Redis【服务端】的购物车商品合并
            cartService.mergeCart(user.getId(),cartList);
            //把cookie中的购物车删除
            CookieUtils.deleteCookie(request,response,"cart");
            //从服务端取购物车列表
            cartList = cartService.getCartList(user.getId());
        }

        //如果用户是未登录状态
        //从cookie中取购物车列表,上边完成了
        //把列表传递给页面
        request.setAttribute("cartList",cartList);
        //返回逻辑视图
        return "cart";
    }

    /**
     * 更新购物车商品数量
     *      需要对cookie进行读写，加上HttpServletResponse 和 HttpServletRequest
     */
    @RequestMapping("/cart/update/num/{itemId}/{num}")
    @ResponseBody
    public E3Result updateCartNum(@PathVariable Long itemId,@PathVariable Integer num,
                                  HttpServletRequest request,HttpServletResponse response){
        //判断用户是否为登录状态
        TbUser user=(TbUser) request.getAttribute("user");
        if (user!=null){
            //更新
            cartService.updateCartNum(user.getId(),itemId,num);
            //直接返回
            return E3Result.ok();
        }
        //从cookie取购物车列表
        List<TbItem> cartList = getCartListFromCookie(request);
        //遍历商品列表，找到对应的商品
        for (TbItem tbItem:cartList){
            if (tbItem.getId().longValue()==itemId){
                //更新数量
                tbItem.setNum(num);
                break;
            }
        }
        //把购物车列表写回cookie
        CookieUtils.setCookie(request,response,"cart",JsonUtils.objectToJson(cartList),COOKIE_CART_EXPIRE,true);
        //返回成功
        return E3Result.ok();
    }

    /**
     * 删除购物车商品
     */
    @RequestMapping("/cart/delete/{itemId}")
    public String deleteCartItem(@PathVariable Long itemId,HttpServletResponse response,HttpServletRequest request){
        //判断用户是否为登录状态
        TbUser user=(TbUser) request.getAttribute("user");
        if (user!=null){
            //更新
            cartService.deleteCartItem(user.getId(),itemId);
            //直接返回
            return "redirect:/cart/cart.html";
        }
        //从cookie中取购物车列表
        List<TbItem> cartList = getCartListFromCookie(request);
        //遍历列表，找到要删除的商品
        for (TbItem tbItem:cartList){
            if (tbItem.getId().longValue()==itemId){
                //删除商品
                cartList.remove(tbItem);
                //跳出循环
                break;
            }
        }
        //把购物车列表写入cookie
        CookieUtils.setCookie(request,response,"cart",JsonUtils.objectToJson(cartList),COOKIE_CART_EXPIRE,true);
        //返回逻辑视图，并实现页面的跳转【redirect:跳转的路径名】
        return "redirect:/cart/cart.html";
    }
}
