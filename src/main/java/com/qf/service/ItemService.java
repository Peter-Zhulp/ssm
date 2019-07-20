package com.qf.service;

import com.qf.pojo.Item;
import com.qf.pojo.PageInfo;

public interface ItemService {
    PageInfo<Item> findItemAndLinmit(String name, Integer page, Integer size);

    Integer del(Long id);

    Integer save(Item item);
}
