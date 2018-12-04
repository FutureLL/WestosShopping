package cn.e3mall.search.controller;

import cn.e3mall.common.pojo.SearchResult;
import cn.e3mall.search.serivce.SearchServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Mr.Li
 * @version 1.0
 * @Description: 商品搜索Controller
 * @Modified By:
 * @date 2018/12/5 1:26
 */

@Controller
public class SearchController {

    @Autowired
    private SearchServer searchServer;

    @Value("${SEARCH_RESULT_ROWS}")
    Integer SEARCH_RESULT_ROWS;

    @RequestMapping("/search")
    @ResponseBody
    public String searchItemList(String keyword,
                                 @RequestParam(defaultValue = "1") Integer page, Model model) throws Exception{
        //设定编码格式
        keyword=new String(keyword.getBytes("iso-8859-1"),"utf-8");
        //查询商品列表
        SearchResult searchResult = searchServer.search(keyword, page, SEARCH_RESULT_ROWS);
        //把结果传递给页面
        model.addAttribute("query",keyword);
        model.addAttribute("totalPages",searchResult.getTotalPages());
        model.addAttribute("page",page);
        model.addAttribute("recourdCount",searchResult.getRecordCount());
        model.addAttribute("itemList",searchResult.getItemList());

        //返回逻辑视图
        return "search";
    }
}
