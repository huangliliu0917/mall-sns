package com.huotu.huobanplus.sns.boot;

import com.huotu.huobanplus.sns.entity.User;
import com.huotu.huobanplus.sns.exception.NeedLoginException;
import com.huotu.huobanplus.sns.model.AppPublicModel;
import com.huotu.huobanplus.sns.repository.UserRepository;
import com.huotu.huobanplus.sns.service.AppSecurityService;
import com.huotu.huobanplus.sns.service.CommonConfigService;
import com.huotu.huobanplus.sns.service.SecurityService;
import com.huotu.huobanplus.sns.service.UserService;
import com.huotu.huobanplus.sns.utils.StringHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;


/**
 * todo
 * Created by Administrator on 2016/10/8.
 */
public class AppCommonInterceptor implements HandlerInterceptor {
    private static Log log = LogFactory.getLog(AppCommonInterceptor.class);
    @Autowired
    protected SecurityService securityService;
    @Autowired
    private CommonConfigService commonConfigService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Environment env;

    @Autowired
    private AppSecurityService appSecurityService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String userId = appSecurityService.getUserId(request);
        if (!StringUtils.isEmpty(userId)) {
            AppPublicModel appPublicModel = new AppPublicModel();
            appPublicModel.setIp(StringHelper.getIp(request));
            appPublicModel.setCurrentUser(userRepository.findOne(Long.parseLong(userId)));
            PublicParameterHolder.putParameters(appPublicModel);

            return true;

        } else {
            String requestURI = request.getRequestURI().substring(request.getContextPath().length());
            if (requestURI.startsWith("/app/user/")) {
                //用户模块需要检测用户是否登录,没有登录则跳转到登录页面
                throw new NeedLoginException("需要登录");
            }
        }


//        if (!env.acceptsProfiles("production")) {
//            AppPublicModel appPublicModel = new AppPublicModel();
//            //在测试环境下，获取所有用户中的第一个用户，如果数据库中没有用户则请求失败
//            List<User> userList = userRepository.findAll();
//            if (userList == null || userList.size() < 1) {
//                return false;
//            }
//            //默认取第一个用户
//            User user = userList.get(0);
//            appPublicModel.setIp(StringHelper.getIp(request));
//            appPublicModel.setCurrentUser(user);
//            appPublicModel.setCustomerId(user.getCustomerId());
//            PublicParameterHolder.putParameters(appPublicModel);
//        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

}
