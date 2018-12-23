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

    /**
     * 网页进行测试：localhost:1028/user/token/08fa2c76-6f41-4e32-8298-4dc19c6626b5
     * 得到：{"status":200,"msg":"OK","data":{"id":9,"username":"lilei","password":null,"phone":"13186195292","email":null,"created":1545480349000,"updated":1545480349000}}
     *
     * 网页进行测试：localhost:1028/user/token/08fa2c76-6f41-4e32-8298-4dc19c6626b0
     * 得到：{"status":201,"msg":"用户登录已经过期","data":null}
     */

    @RequestMapping("/user/token/{token}")
    @ResponseBody
    public E3Result getUserByToken(@PathVariable String token){
        E3Result result = tokenService.getUserByToken(token);
        return result;
    }
}
