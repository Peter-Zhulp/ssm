package com.qf.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:applicationContext-dao.xml",
        "classpath:applicationContext-service.xml",
})
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;
    @Test
    public void findCountByUsername() {
        Integer count = userMapper.findCountByUsername("admin");
        System.out.println(count);
    }


}