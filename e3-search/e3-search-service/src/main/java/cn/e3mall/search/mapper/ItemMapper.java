package cn.e3mall.search.mapper;

import cn.e3mall.common.pojo.SearchItem;

import java.util.List;

/**
 * @author Mr.Li
 * @version 1.0
 * @Description: 获取商品表：用来让搜索服务查询【Solr】
 * @Modified By:
 * @date 2018/12/3 12:03
 */
public interface ItemMapper {

    List<SearchItem> getItemList();
    SearchItem getItemById(long itemId);
}
