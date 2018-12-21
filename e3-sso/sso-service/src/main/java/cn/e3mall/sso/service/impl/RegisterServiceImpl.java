package cn.e3mall.sso.service.impl;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.mapper.TbUserMapper;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.pojo.TbUserExample;
import cn.e3mall.pojo.TbUserExample.Criteria;
import cn.e3mall.sso.service.RegisterServcice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Mr.Li
 * @version 1.0
 * @Description: 用户注册处理Service
 * @Modified By:
 * @date 2018/12/20 23:45
 */

@Service
public class RegisterServiceImpl implements RegisterServcice {

    @Autowired
    private TbUserMapper userMapper;

    @Override
    public E3Result checkData(String param, int type) {
        //根据不同的type生成不同的查询条件
        TbUserExample example=new TbUserExample();
        Criteria criteria = example.createCriteria();
        //1：用户名 2：手机号 3：邮箱
        if (type==1){
            criteria.andUsernameEqualTo(param);
        } else if (type==2){
            criteria.andPhoneEqualTo(param);
        } else if (type==3){
            criteria.andEmailEqualTo(param);
        } else {
            return E3Result.build(400,"数据类型错误");
        }
        //执行查询
        List<TbUser> list = userMapper.selectByExample(example);
        //判断结果中是否包含数据
        if (list!=null && list.size()>0){
            //如果有数据返回false
            return E3Result.ok(false);
        }
        //如果没有数据返回true
        return E3Result.ok(true);
    }
}
