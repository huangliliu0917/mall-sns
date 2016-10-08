package com.huotu.huobanplus.sns.boot;

import com.huotu.huobanplus.sns.model.common.AppCode;
import com.huotu.huobanplus.sns.model.common.PhysicalApiResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lgh on 2016/1/12.
 */
public class AppHandlerExceptionResolver implements HandlerExceptionResolver {
    private static Log log = LogFactory.getLog(AppHandlerExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        String requestURI = request.getRequestURI().substring(request.getContextPath().length());
        if (requestURI.startsWith("/app/")) {
            ModelAndView mv = new ModelAndView();
            PhysicalApiResult result = new PhysicalApiResult();
            result.setSystemResultDescription(ex.getLocalizedMessage());
            try {
                throw ex;
            } catch (MissingServletRequestParameterException e) {
                result.setSystemResultCode(AppCode.PARAMETER_ERROR.getValue());
                result.setSystemResultDescription(AppCode.PARAMETER_ERROR.getName());
            } catch (Exception e) {
                log.error("unExcepted app error ", e);
                result.setSystemResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            }

            mv.addObject("__realResult", result);
            MappingJackson2JsonView jsonView = new MappingJackson2JsonView();

            jsonView.setContentType("application/json;charset=UTF-8");
            jsonView.setModelKey("__realResult");
            jsonView.setExtractValueFromSingleKeyModel(true);
            mv.setView(jsonView);
            return mv;
        }
        if (requestURI.startsWith("/admin/")) {
                try {
                    throw ex;
                } catch (Exception e) {
                    log.error("admin upExcepted error", e);
                }
                return new ModelAndView("redirect:/admin/error.html");
        }
        return null;
    }
}
