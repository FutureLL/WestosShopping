package cn.e3mall.controller;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.search.serivce.SearchItemServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Mr.Li
 * @version 1.0
 * @Description: 导入商品数据到索引库Solr
 * @Modified By:
 * @date 2018/12/3 23:00
 */

@Controller
public class SearchItemController {

    @Autowired
    private SearchItemServer searchItemServer;

    @RequestMapping("/index/item/import")
    @ResponseBody
    public E3Result importItemList(){
        E3Result e3Result = searchItemServer.importAllItem();
        return e3Result;
    }
}
