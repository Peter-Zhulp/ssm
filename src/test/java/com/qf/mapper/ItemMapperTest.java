package com.qf.mapper;

import com.qf.pojo.Item;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:applicationContext-dao.xml",
        "classpath:applicationContext-service.xml",
})
public class ItemMapperTest {
    @Autowired
    private ItemMapper itemMapper;

    @Test
    @Transactional
    public void save() {
        Item item = new Item();
        item.setName("xxx");
        item.setPrice(new BigDecimal(1));
        item.setProductionDate(new Date());
        item.setDescription("xxxxxx");
        item.setPic("zzzzzzzzzz");
        Integer count = itemMapper.save(item);
        assertEquals(new Integer(1),count);
    }
}