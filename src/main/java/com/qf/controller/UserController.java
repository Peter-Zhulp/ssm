package com.qf.controller;

import com.qf.pojo.User;
import com.qf.service.UserService;
import com.qf.utils.SendSMSUtils;
import com.qf.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.qf.constant.SsmConstant.*;

/*
* 用户模块的controller
* */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private SendSMSUtils sendSMSUtils;
    //跳转到注册页面
    //jsonlib 比较古老的json工具 第三方依赖过多 当pojo类过于复杂时
    //jackson spring默认工具 三个依赖
    //gson 一个依赖

    @GetMapping("/register-ui")
    public String registerUI(){
        //转发到注册页面
        return "user/register";
    }
    @PostMapping("/check-username")
    @ResponseBody //不走视图解析器,直接响应 如果返回结果为map或pojo,自动序列化为json
    //@RequestBody 接收json参数需要的注解
    public ResultVo checkUsername(@RequestBody User user){
        //1.json数据需反序列化成pojo对象
        //页面发送json数据时,接受的数据类型,map,pojo类
        //1.调用service,判断
        Integer count = userService.checkUsername(user.getUsername());

        //2封装响应数据的map
        return new ResultVo(0, "成功", count);
        //xhr XmlHttpRequest

    }
    @PostMapping(value = "/send-sms",produces = "text/html;charset=utf-8")
    @ResponseBody//ajax请求
    public String sendSMS(@RequestParam String phone,HttpSession session){
        //1.调用工具发短信
        String result = sendSMSUtils.sendSMS(session, phone);
        //2.将result响应给ajax引擎
        return result;

    }
    @PostMapping("/register")
    public String register(@Valid User user, BindingResult bindingResult, String registerCode, HttpSession session, RedirectAttributes redirectAttributes){
        //1校验验证码
        if(!StringUtils.isEmpty(registerCode)){
            //验证码不为空
            String code = (int) session.getAttribute(CODE)+"";
            if(!registerCode.equals(code)){
                //验证码不正确
                redirectAttributes.addAttribute("registerInfo", "验证码不正确");
                return REDIRECT+REGISTER_UI;
            }
        }
        //2校验参数是否合法
        if(bindingResult.hasErrors()){
            //参数不合法
            String msg = bindingResult.getFieldError().getDefaultMessage();
            if(StringUtils.isEmpty(msg)){
                msg = "验证码为必填项";
            }
            redirectAttributes.addAttribute("registerInfo", msg);
            return REDIRECT+REGISTER_UI;
        }
        Integer count = userService.register(user);
        //3.调用service执行注册功能
        if(count==1){
            return REDIRECT+LOGIN_UI;
        }else {
            redirectAttributes.addAttribute("registerInfo", "服务器爆炸了");
            return REDIRECT+REGISTER_UI;
        }
        //4.根据service返回的结果跳转到指定的页面

    }
    //跳转到登录页面
    //GET http:localhost/user/login-ui
    @GetMapping("/login-ui")
    public String loginUI(){
//        int i = 1/0 ;
        return "user/login";
    }
    @PostMapping("/login")
    @ResponseBody
    public ResultVo login(String username,String password,HttpSession session){
        //1.检验参数是否合法,
        if(StringUtils.isEmpty(username)||StringUtils.isEmpty(password)){
            return new ResultVo(1, "用户名和密码为必填项", null);
        }
        //2.调用service执行登陆
        User user = userService.login(username,password);
        //3.根据service返回结果判断登录是否成功
        if(user!=null){
            //4若成功,将用户信息放到session域中
            session.setAttribute("user", user);
            return new ResultVo(0, "成功", null);
        }else{
            //5.失败,响应错误信息给ajax引擎
            return new ResultVo(2, "用户名或密码错误", null);

        }

    }

}
