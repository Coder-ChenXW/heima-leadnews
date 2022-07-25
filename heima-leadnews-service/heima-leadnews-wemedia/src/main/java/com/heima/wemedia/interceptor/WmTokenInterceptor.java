package com.heima.wemedia.interceptor;

import com.heima.model.wemedia.pojos.WmUser;
import com.heima.utils.thread.WmThreadLocalUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class WmTokenInterceptor implements HandlerInterceptor {

    /**
     * @Function: 功能描述 的都header的用户信息并且存入当前线程中
     * @Author: ChenXW
     * @Date: 19:19 2022/7/24
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userId = request.getHeader("userId");
        if (userId != null) {
            //存入到当前线程中
            WmUser wmUser = new WmUser();
            wmUser.setId(Integer.valueOf(userId));
            WmThreadLocalUtil.setUser(wmUser);

        }
        return true;
    }


    /**
     * @Function: 功能描述 清理线程中的数据
     * @Author: ChenXW
     * @Date: 19:20 2022/7/24
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        WmThreadLocalUtil.clear();
    }
}
