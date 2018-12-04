package cn.e3mall.search.serivce;

import cn.e3mall.common.pojo.SearchResult;

/**
 * @author Mr.Li
 * @version 1.0
 * @Description:
 * @Modified By:
 * @date 2018/12/5 0:54
 */
public interface SearchServer {

    SearchResult search(String keyword,int page,int rows)  throws Exception;
}
