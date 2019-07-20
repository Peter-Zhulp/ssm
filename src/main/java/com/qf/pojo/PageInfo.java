package com.qf.pojo;

import lombok.Data;

import java.util.List;
@Data
public class PageInfo<T> {
//     <!--当前页-->
    private Integer page;
//    <!--每页显示条数
    private Integer size;
//    <!--查询总条数-->
    private Long total;
//    <!--计算总页数-->
    private Integer pages;//total%size==0?(total/size)?(total/size+1)
//    <!--计算起始索引-->
    private Integer offset; //(page-1)*size
    private List<T> list;  //查询得到

    public PageInfo(Integer page, Integer size, Long total) {
        this.page = page<=0?1:page;
        this.size = size<=0?5:size;
        this.total = total<0?0:total;
        this.pages = (int)(this.total%this.size==0?this.total/this.size:this.total/this.size+1);
        this.offset=(this.page-1)*this.size;
    }
}
