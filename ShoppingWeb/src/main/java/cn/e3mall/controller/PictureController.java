package cn.e3mall.controller;

import cn.e3mall.common.utils.FastDFSClient;
import cn.e3mall.common.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * 图片上传处理Controller
 *
 *      解决兼容性：富文本编辑器做的时候兼容性没有做好
 *          返回的内容最好是text/plain(它的兼容性是最好的【普通文本信息】)，不需要是application/json
 */

@Controller
public class PictureController {

    @Value("${IMAGE_SERVER_URL}")
    private String IMAGE_SERVER_URL;

    //指定返回值结果类型
    @RequestMapping(value = "/pic/upload" , produces = MediaType.TEXT_PLAIN_VALUE+";charset=utf-8")
    @ResponseBody   //直接响应浏览器，不走逻辑视图
    public String uploadFile(MultipartFile uploadFile){
        try {
            //把图片上传到图片服务器
            FastDFSClient fastDFSClient=new FastDFSClient("classpath:conf/client.conf");
            //取文件的扩展名
            String originalFilename=uploadFile.getOriginalFilename();
            String extName=originalFilename.substring(originalFilename.lastIndexOf(".")+1);
            //得到图片的地址和文件名
            String url = fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
            //补充为完整的url
            url="IMAGE_SERVER_URL"+url;
            //封装到Map中返回
            Map result=new HashMap<>();
            result.put("error",0);
            result.put("url",url);
            //将Java对象变成json串[字符串]
            return JsonUtils.objectToJson(result);

        } catch (Exception e){
            e.printStackTrace();
            Map result=new HashMap<>();
            result.put("error",1);
            result.put("message","图片上传失败");
            return JsonUtils.objectToJson(result);

        }
    }
}
