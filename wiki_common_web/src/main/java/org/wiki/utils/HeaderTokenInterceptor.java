package org.wiki.utils;

import com.alibaba.fastjson.JSON;
import org.wiki.entity.ResponseData;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HeaderTokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ResponseData responseData = null;
        //获取请求头中的token
        String headerToken = request.getHeader("token");
        //检测当前请求是否需要拦截
        if (!request.getRequestURI().contains("login") && !request.getRequestURI().contains("/doc.html")) {
            if (headerToken == null) {
                //token不存在  则返回错误信息
                responseData = ResponseData.cusstomerError("用户认证不通过");
            }
            //如果token存在 则进行验证和更新  对token进行续期
            try {
                headerToken = JWTUtils.updateToken(headerToken);
            } catch (Exception e) {
                //token验证错误
                responseData = ResponseData.serverInternalError();
            }
        }
        if (responseData != null) {// 如果由错误信息
            response.getWriter().write(JSON.toJSONString(responseData));
            return false;
        } else {
            //认证通过  将token加入页面你的header 返回
            response.setHeader("token", headerToken);
            return true;
        }
    }
}
