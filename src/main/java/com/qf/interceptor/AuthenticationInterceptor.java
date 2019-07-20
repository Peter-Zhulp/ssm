package com.qf.interceptor;

import com.qf.constant.SsmConstant;
import com.qf.pojo.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthenticationInterceptor implements HandlerInterceptor {

    //可以选择对请求进行拦截和放行 return false 拦截 return true代表放行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.获得session
        HttpSession session = request.getSession();
        //2.通过sesssion获取用户信息
        User user = (User) session.getAttribute(SsmConstant.USER);
        //3判断是否为null
        if(user==null){
            response.sendRedirect(request.getContextPath()+"/user/login-ui");
            return false;
        }else {
            //不为null
            return true;

        }

    }
    //执行完controller,返回ModelAndView之后执行
    //post处理中,修改ModelAndView
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }
    //拦截器后处理器,响应数据之前
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
