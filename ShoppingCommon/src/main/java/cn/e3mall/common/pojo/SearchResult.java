package cn.e3mall.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @author Mr.Li
 * @version 1.0
 * @Description: Solr搜索接收的pojo
 * @Modified By:
 * @date 2018/12/5 0:06
 */
public class SearchResult implements Serializable {

    private long recordCount;
    private int totalPages;
    private List<SearchItem> itemList;

    public long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(long recordCount) {
        this.recordCount = recordCount;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<SearchItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<SearchItem> itemList) {
        this.itemList = itemList;
    }
}
