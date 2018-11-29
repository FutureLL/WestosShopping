package cn.e3mall.fast;

import cn.e3mall.common.utils.FastDFSClient;
import org.csource.fastdfs.*;
import org.junit.Test;

/**
 * 上传图片
 */

public class FastDfsTest {

    @Test
    public void testUpload() throws Exception{

        //前提是连接好的虚拟机配置好FastDFS

        //创建一个配置文件。文件名任意。内容就是tracker服务器的地址。
        //使用全局对象加载配置文件。
        ClientGlobal.init("E:/Javaee轻量级开发/(A088)java ee互联网轻量级框架整合开发 SSM框架Spring MVC+Spring+MyBatis和Redis实现/spring/springmvc视频教程 mybatis电商项目 java商城源码 SSM框架maven/赠送及跟新文件/宜立方商城       2017.02 免费更新赠送/e3商城_day04/项目二_day04/source/e3-manager-web/src/main/resources/conf/client.conf");
        //创建一个TrackerClient对象
        TrackerClient trackerClient = new TrackerClient();
        //通过TrackClient获得一个TrackerServer对象
        TrackerServer trackerServer = trackerClient.getConnection();
        //创建一个StrorageServer的引用，可以是null
        StorageServer storageServer = null;
        //创建一个StorageClient，参数需要TrackerServer和StrorageServer
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        //使用StorageClient上传文件。
        String[] strings = storageClient.upload_file("C:/Users/hasee/Desktop/李雷.jpg", "jpg", null);
        for (String string : strings) {
            System.out.println(string);
        }

    }

    @Test
    public void testFastDfsClient() throws Exception {

        //通过common模块中的一个工具类FastDFSClient.java可以实现
        FastDFSClient fastDFSClient = new FastDFSClient("E:/Javaee轻量级开发/(A088)java ee互联网轻量级框架整合开发 SSM框架Spring MVC+Spring+MyBatis和Redis实现/spring/springmvc视频教程 mybatis电商项目 java商城源码 SSM框架maven/赠送及跟新文件/宜立方商城       2017.02 免费更新赠送/e3商城_day04/项目二_day04/source/e3-manager-web/src/main/resources/conf/client.conf");
        String string = fastDFSClient.uploadFile("D:/Documents/Pictures/images/200811281555127886.jpg");
        System.out.println(string);
    }
}
