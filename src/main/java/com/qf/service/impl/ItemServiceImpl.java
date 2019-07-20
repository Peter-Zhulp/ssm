package com.qf.service.impl;

import com.qf.mapper.ItemMapper;
import com.qf.pojo.Item;
import com.qf.pojo.PageInfo;
import com.qf.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemMapper itemMapper;
    @Override
    public PageInfo<Item> findItemAndLinmit(String name, Integer page, Integer size) {
        //将pageInfo创建出来,并且封装list集合
        //1.查询数据总条数
        Long total = itemMapper.findCountByName(name);
        //2.创建PageInfo对象
        PageInfo<Item> pageInfo = new PageInfo<>(page, size, total);
        //3查询商品信息List
        List<Item> list = itemMapper.findItemByNameLikeAndLimit(name, pageInfo.getOffset(), pageInfo.getSize());
        //将list集合set到pageinfo中
        pageInfo.setList(list);
        return pageInfo ;
    }

    @Override
    public Integer del(Long id) {
        return itemMapper.delById(id);
    }

    @Override
    @Transactional
    public Integer save(Item item) {

        return itemMapper.save(item);
    }
}
