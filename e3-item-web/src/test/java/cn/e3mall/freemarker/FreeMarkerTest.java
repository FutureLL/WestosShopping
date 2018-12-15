package cn.e3mall.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mr.Li
 * @version 1.0
 * @Description:
 * @Modified By:
 * @date 2018/12/15 20:08
 */
public class FreeMarkerTest {

    @Test
    public void testFreeMarker() throws Exception{
        //创建一个模板文件
        //创建一个Configuration对象,并设置当前FreeMarker的版本号
        Configuration configuration=new Configuration(Configuration.getVersion());
        //设置模板文件保存的目录
        configuration.setDirectoryForTemplateLoading(new File("F:\\IDEA\\WestosShopping\\e3-item-web\\src\\main\\webapp\\WEB-INF\\ftl"));
        //模板文件的编码格式，一般是UTF-8
        configuration.setDefaultEncoding("utf-8");
        //加载模板文件，创建一个模板对象
        Template template = configuration.getTemplate("hello.ftl");
        //创建一个数据集，可以使Pojo也可以是Map，推荐使用Map
        Map data=new HashMap<>();
        data.put("hello","hello FreeMarker ! ! !");
        //创建一个Writer对象，指定输出文件的路径以及文件名
        Writer out=new FileWriter(new File("F:\\JavaEE32\\freemarker\\hello.txt"));
        //生成静态页面
        template.process(data,out);
        //关闭流
        out.close();
    }
}
