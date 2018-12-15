package cn.e3mall.item.pojo;

import cn.e3mall.pojo.TbItem;

/**
 * @author Mr.Li
 * @version 1.0
 * @Description:
 * @Modified By:
 * @date 2018/12/15 0:02
 */
public class Item extends TbItem {

    public Item(TbItem tbItem){
        this.setId(tbItem.getId());
        this.setTitle(tbItem.getTitle());
        this.setSellPoint(tbItem.getSellPoint());
        this.setPrice(tbItem.getPrice());
        this.setNum(tbItem.getNum());
        this.setBarcode(tbItem.getBarcode());
        this.setImage(tbItem.getImage());
        this.setCid(tbItem.getCid());
        this.setStatus(tbItem.getStatus());
        this.setCreated(tbItem.getCreated());
        this.setUpdated(tbItem.getUpdated());
    }

    public String[] getImages(){
        String image2 = this.getImage();
        //image2不为null，并且不是空串
        if (image2!=null && !"".equals(image2)){
            String[] strings = image2.split(",");
            return strings;
        }
        return null;
    }
}
