package cn.e3mall.sso.controller;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.sso.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Mr.Li
 * @version 1.0
 * @Description: 根据token查询用户信息Controller
 * @Modified By:
 * @date 2018/12/23 22:51
 */

@Controller
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @RequestMapping("/user/token/{token}")
    @ResponseBody
    public E3Result getUserByToken(@PathVariable String token){
        E3Result result = tokenService.getUserByToken(token);
        return result;
    }
}
