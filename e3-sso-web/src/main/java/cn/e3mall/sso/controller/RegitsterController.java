package cn.e3mall.sso.controller;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.sso.service.RegisterServcice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 注册功能Controller
 * <p>Title: RegitsterController</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0
 */
@Controller
public class RegitsterController {

	@Autowired
	private RegisterServcice registerServcice;

	@RequestMapping("/page/register")
	public String showRegister() {
		return "register";
	}

	@RequestMapping("/user/check/{param}/{type}")
	@ResponseBody
	public E3Result checkData(@PathVariable String param,@PathVariable Integer type){
		E3Result e3Result = registerServcice.checkData(param, type);
		return e3Result;
	}
}
