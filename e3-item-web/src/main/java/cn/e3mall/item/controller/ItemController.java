package cn.e3mall.item.controller;

import cn.e3mall.item.pojo.Item;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Mr.Li
 * @version 1.0
 * @Description: 商品详情页面展示Controller
 * @Modified By:
 * @date 2018/12/15 13:00
 */

@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/item/{itemId}")
    public String showItemInfo(@PathVariable Long itemId, Model model){
        //调用服务来取商品信息
        TbItem tbItem = itemService.getItemById(itemId);
        Item item=new Item(tbItem);
        //取商品描述信息
        TbItemDesc itemDesc = itemService.getItemDescById(itemId);
        //把信息传递给页面
        model.addAttribute("item",item);
        model.addAttribute("itemDesc",itemDesc);
        //返回逻辑视图
        return "item";
    }
}
