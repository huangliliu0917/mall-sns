package com.huotu.huobanplus.sns.controller.admin;

import com.huotu.huobanplus.sns.entity.Circle;
import com.huotu.huobanplus.sns.entity.Login;
import com.huotu.huobanplus.sns.entity.User;
import com.huotu.huobanplus.sns.repository.CircleRepository;
import com.huotu.huobanplus.sns.repository.UserRepository;
import com.huotu.huobanplus.sns.service.impl.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.Random;

/**
 * Created by Administrator on 2016/10/17.
 */
@Controller
public class CommonController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CircleRepository circleRepository;

    @Autowired
    private LoginService loginService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping("/addManagerTest")
    @ResponseBody
    public ModelMap addManagerTest() throws Exception{
        ModelMap modelMap=new ModelMap();

        Login manager=new Login();
        manager.setAuthors("ROLE_ALL");
        loginService.newLogin(manager,"123456");

        return modelMap;
    }

    @RequestMapping("/searchDataList")
    @ResponseBody
    public ModelMap searchDataList() throws Exception{
        ModelMap modelMap=new ModelMap();


        return modelMap;
    }

    @RequestMapping("/addUserTest")
    @ResponseBody
    public ModelMap addUserTest() throws Exception{
        Random random=new Random();
        for(int i=0;i<55;i++){
            User user=new User();
            user.setId(100L+i);
            user.setNickName("测试用户"+i);
            user.setArticleAmount((long)random.nextInt(100000));
            user.setExperience((long)random.nextInt(100000));
            user.setFansAmount((long)random.nextInt(100000));
            user.setUserAmount((long)random.nextInt(100000));
            user.setCreateDate(new Date());
            userRepository.save(user);

        }
        return null;
    }

    @RequestMapping("/addCircleTest")
    @ResponseBody
    public ModelMap addCircleTest() throws Exception{
        Random random=new Random();

        for(int i=0;i<55;i++){
            Circle circle=new Circle();
            circle.setName(i+"");
            circle.setArticleAmount((long)random.nextInt(100000));
            circle.setUserAmount((long)random.nextInt(100000));
            circle.setSuggested(random.nextBoolean());
            circle.setDate(new Date());
            circle.setEnabled(random.nextBoolean());
            circleRepository.save(circle);
        }
        return null;
    }

}
