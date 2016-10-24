package com.huotu.huobanplus.sns.boot;

import com.huotu.huobanplus.sns.entity.User;
import com.huotu.huobanplus.sns.model.AppPublicModel;
import com.huotu.huobanplus.sns.repository.UserRepository;
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
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {

        //todo 登录方式暂定
        Long userId = userService.getUserId(request);
        String paramUserId = request.getParameter("mainUserId");
        if (env.acceptsProfiles("production")) {
//            String customerIdStr = request.getParameter("customerId");
//            if (customerIdStr == null) {
//                customerIdStr = request.getParameter("customerid");
//            }
//            Long customerId = Long.parseLong(customerIdStr);
//            Boolean toSSO = false;
//            //强制刷新用户
//            String forceRefresh = "0";
//            //强制授权
//            if (userId == null || userId == 0) {
//                toSSO = true;
//            } else if (!StringUtils.isEmpty(paramUserId) && !userId.toString().equals(paramUserId)) {
//                //用户切换 强制刷新
//                toSSO = true;
//                forceRefresh = "1";
//            } else {
//                //商家切换 强制刷新
//                User user = userRepository.findOne(userId);
//                if (!user.getMerchant().getId().equals(customerId)) {
//                    toSSO = true;
//                    forceRefresh = "1";
//                }
//            }
//
//            //进行单点登录
//            if (toSSO) {
//                //todo customerId为空
//                String redirectUrl = commonConfigService.getWebUrl() + "/sisweb/auth?redirectUrl=" + URLEncoder.encode(request.getRequestURL()
//                        + (StringUtils.isEmpty(request.getQueryString()) ? "" : "?" + request.getQueryString()), "utf-8");
//
//                //生成sign
//                Map<String, String> map = new HashMap<>();
//                map.put("customerId", customerId.toString());
//                map.put("redirectUrl", redirectUrl);
//                map.put("forceRefresh", forceRefresh);
//                String sign = securityService.getMapSign(map);
//
//                //生成toUrl
//                String toUrl = "";
//                for (String key : map.keySet()) {
//                    toUrl += "&" + key + "=" + URLEncoder.encode(map.get(key), "utf-8");
//                }
//                toUrl = commonConfigService.getAuthWebUrl() + "/api/login?" + (toUrl.length() > 0 ? toUrl.substring(1) : "");
//
//                response.sendRedirect(toUrl + "&sign=" + sign);
//                return false;
//            }
        } else {
            AppPublicModel appPublicModel = new AppPublicModel();
            //在测试环境下，获取所有用户中的第一个用户，如果数据库中没有用户则请求失败
            List<User> userList = userRepository.findAll();
            if (userList == null || userList.size() < 1) {
                return false;
            }
            //默认取第一个用户
            User user = userList.get(0);
            appPublicModel.setIp(StringHelper.getIp(request));
//            appPublicModel.setOpenId(user.getWeixinOpenId());
//            appPublicModel.setSign(userService.getSign(user));
            appPublicModel.setCurrentUser(user);
            appPublicModel.setCustomerId(user.getCustomerId());
            PublicParameterHolder.putParameters(appPublicModel);
        }

        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

}
