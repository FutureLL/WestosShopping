package cn.e3mall.sso.service;

import cn.e3mall.common.utils.E3Result;

/**
 * @author Mr.Li
 * @version 1.0
 * @Description: 根据token查询用户信息
 * @Modified By:
 * @date 2018/12/23 22:36
 */
public interface TokenService {

    E3Result getUserByToken(String token);
}
