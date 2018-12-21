package cn.e3mall.sso.service;

import cn.e3mall.common.utils.E3Result;

/**
 * @author Mr.Li
 * @version 1.0
 * @Description:
 * @Modified By:
 * @date 2018/12/20 23:43
 */
public interface RegisterServcice {

    E3Result checkData(String param,int type);
}
