package cn.e3mall.sso.service;

import cn.e3mall.common.utils.E3Result;

/**
 * @author Mr.Li
 * @version 1.0
 * @Description:
 * @Modified By:
 * @date 2018/12/22 17:35
 */
public interface LoginService {

    //参数是：用户名和密码
    /**
     * 业务逻辑:
     *      1、判断用户名和密码是否正确
     *      2、如果不正确返回登录失败
     *      3、如果正确生成Token
     *      4、把用户信息写入Redis
     *          key:token   value:用户信息
     *      5、设置Session的过期时间
     *      6、把Token返回
     */
    //返回值：E3Result，其中包含Token信息
    E3Result userLogin(String username,String password);
}
