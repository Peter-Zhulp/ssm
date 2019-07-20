package com.qf.mapper;

import com.qf.pojo.Item;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemMapper {
    //1.查询商品总条数
    Long findCountByName(@Param("name")String name);
    //查询商品具体信息
    List<Item> findItemByNameLikeAndLimit(@Param("name")String name, @Param("offset")Integer offset,
                                          @Param("size")Integer size);
    Integer delById(@Param("id")Long id);

    Integer save(Item item);
}
