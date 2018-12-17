package cn.e3mall.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.*;

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
//        Template template = configuration.getTemplate("hello.ftl");
        Template template = configuration.getTemplate("student.ftl");
        //创建一个数据集，可以使Pojo也可以是Map，推荐使用Map
        Map data=new HashMap<>();

        /**
         * FreeMarker的模板语法：
         */
        //1、文本形式【.txt】
        data.put("hello","hello FreeMarker ! ! !");

        //2、访问pojo对象中的属性
        Student student=new Student(1,"小明",18,"回龙观");
        data.put("student",student);

        //3、添加一个List
        List<Student> stuList=new ArrayList<Student>();
        stuList.add(new Student(1,"小明1",18,"回龙观"));
        stuList.add(new Student(2,"小明2",19,"回龙观"));
        stuList.add(new Student(3,"小明3",20,"回龙观"));
        stuList.add(new Student(4,"小明4",21,"回龙观"));
        stuList.add(new Student(5,"小明5",22,"回龙观"));
        stuList.add(new Student(6,"小明6",23,"回龙观"));
        stuList.add(new Student(7,"小明7",24,"回龙观"));
        stuList.add(new Student(8,"小明8",25,"回龙观"));
        stuList.add(new Student(9,"小明9",26,"回龙观"));
        data.put("stuList",stuList);

        //4、添加日期类型
        data.put("date",new Date());

        //5、null值的测试
        data.put("val",null);

        // 创建一个Writer对象，指定输出文件的路径以及文件名
//        Writer out=new FileWriter(new File("F:\\JavaEE32\\freemarker\\hello.txt"));
        Writer out=new FileWriter(new File("F:\\JavaEE32\\freemarker\\student.html"));
        //生成静态页面
        template.process(data,out);
        //关闭流
        out.close();
    }
}
