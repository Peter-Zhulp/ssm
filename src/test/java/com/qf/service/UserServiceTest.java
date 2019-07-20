package com.qf.service;

import com.qf.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:applicationContext-dao.xml",
        "classpath:applicationContext-service.xml",
})
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    @Transactional
    public void register() {
        User user = new User();
        user.setUsername("xxx");
        user.setPassword("xxxxx");
        user.setPhone("11111111111");
        Integer count = userService.register(user);
        assertEquals(new Integer(1),count);
    }
}