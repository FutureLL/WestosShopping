package cn.e3mall.sso.controller;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.sso.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
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

    /**
     * 指定Content-Type的响应结果为application/json
     *      produces = MediaType.APPLICATION_JSON_VALUE
     *      produces = "application/json;charset=utf-8"
     * 两种方式都可以
     */
    /**
     * 方法一：
     *     @RequestMapping(value = "/user/token/{token}",produces = "application/json;charset=utf-8")
     *     @ResponseBody
     *     //这里发送一个请求带一个callback参数，参数名=mycall，这里的mycall的值是在JS定义好的方法mycall，
     *     //有callback这里要拼装成js语句，在响应
     *     public String getUserByToken(@PathVariable String token, String callback) {
     *         E3Result result = tokenService.getUserByToken(token);
     *         //响应结果之前，判断是否为jsonp请求
     *         if (StringUtils.isNotBlank(callback)) {
     *             //把结果封装成一个js语句响应
     *             return callback + "(" + JsonUtils.objectToJson(result) + ");";
     *         }
     *         return JsonUtils.objectToJson(result);
     *     }
     */

    /**
     * 方法二：Spring 4.2以上才能用
     */
    @RequestMapping(value = "/user/token/{token}")
    @ResponseBody
    //这里发送一个请求带一个callback参数，参数名=mycall，这里的mycall的值是在JS定义好的方法mycall，
    //有callback这里要拼装成js语句，在响应
    public Object getUserByToken(@PathVariable String token, String callback) {
        E3Result result = tokenService.getUserByToken(token);
        //响应结果之前，判断是否为jsonp请求
        if (StringUtils.isNotBlank(callback)) {
            //把结果封装成一个js语句响应
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue;
        }
        return result;
    }
}
