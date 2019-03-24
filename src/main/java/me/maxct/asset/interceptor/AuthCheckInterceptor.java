package me.maxct.asset.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import me.maxct.asset.constant.AppConst;
import me.maxct.asset.domain.Role;
import me.maxct.asset.domain.User;
import me.maxct.asset.dto.Msg;
import me.maxct.asset.mapper.RoleDao;
import me.maxct.asset.mapper.UserDao;
import me.maxct.asset.security.JwtUtil;

/**
 * @author imaxct
 * 2019-03-22 21:48
 */
@Aspect
@Component
@Order(9)
public class AuthCheckInterceptor {

    private final JwtUtil jwtUtil;

    private final UserDao userDao;

    private final RoleDao roleDao;

    @Around("execution(public me.maxct.asset.dto.Msg me.maxct.asset.controller.*.*(..)) && args(..,request)"
            + "&& @annotation(AuthCheck)")
    public Object beforeAuthCheck(ProceedingJoinPoint joinPoint,
                                  HttpServletRequest request) throws Throwable {
        if (StringUtils.isEmpty(request.getHeader(AppConst.TOKEN_NAME))) {
            return Msg.err("请先登录");
        }
        String rawToken = request.getHeader(AppConst.TOKEN_NAME);
        User user = jwtUtil.check(rawToken);
        if (user == null || StringUtils.isEmpty(user.getUsername())) {
            return Msg.err("登陆过期");
        }
        user = userDao.getUserByUsername(user.getUsername());

        List<Role> roles = roleDao.getUserRoles(user.getId());

        String requestMapping = request.getRequestURI();
        for (Role role : roles) {
            String[] arr;
            if (!StringUtils.isEmpty(role.getAuthorizedMapping())) {
                arr = role.getAuthorizedMapping().split(",");
                for (String uri : arr) {
                    if (requestMapping.startsWith(uri)) {
                        return joinPoint.proceed();
                    }
                }
            }
        }
        return Msg.err("没有权限进行此操作");
    }

    @Autowired
    public AuthCheckInterceptor(JwtUtil jwtUtil, UserDao userDao, RoleDao roleDao) {
        this.jwtUtil = jwtUtil;
        this.userDao = userDao;
        this.roleDao = roleDao;
    }
}
