package net.lab1024.sa.base.common.locale;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 语言环境拦截器
 *
 * @author FangCheng
 * @since 2025/01/02 11:11
 **/
public class LocaleInterceptor implements HandlerInterceptor {
    /**
     * 前置拦截方法
     *
     * @param request  request
     * @param response response
     * @param handler  handler
     * @return boolean
     * @author FangCheng
     * @since 2025/01/02 11:12
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        LocaleContextHolder.setLocale(request.getLocale());
        return true;
    }

    /**
     * 后置拦截方法
     *
     * @param request  request
     * @param response response
     * @param handler  handler
     * @param ex       ex
     * @author FangCheng
     * @since 2025/01/02 11:12
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        LocaleContextHolder.clearLocale();
    }
}
