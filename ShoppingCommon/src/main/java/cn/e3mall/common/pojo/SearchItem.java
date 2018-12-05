package cn.e3mall.common.pojo;

import java.io.Serializable;

/**
 * @author Mr.Li
 * @version 1.0
 * @Description:
 * @Modified By:
 * @date 2018/12/3 12:07
 */
public class SearchItem implements Serializable {

    public String id;
    public String title;
    public String sell_point;
    public long price;
    public String image;
    public String category_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSell_point() {
        return sell_point;
    }

    public void setSell_point(String sell_point) {
        this.sell_point = sell_point;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    //因为所有的图片都写在了一个url中，造成了非法的格式，所以这里需要将这个非法的url使用","分隔开，返回一个字符串数组
    public String[] getImages(){
        if(image!=null &&!"".equals(image)){
            String[] strings=image.split(",");
            return strings;
        }
        return null;
    }
}
