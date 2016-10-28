package com.huotu.huobanplus.sns.controller.admin;

import com.huotu.huobanplus.sns.entity.Login;
import com.huotu.huobanplus.sns.model.admin.PermissionSearchModel;
import com.huotu.huobanplus.sns.repository.LoginRepository;
import com.huotu.huobanplus.sns.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 权限配置(多商家版弃用)
 * Created by Administrator on 2016/10/11.
 */
@Controller
@RequestMapping("/top/permission")
public class AdminManagerController {
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private LoginRepository loginRepository;

    /**
     * 返回权限用户列表
     * @param permissionSearchModel 查询model
     * @return  返回
     * @throws Exception
     */
    @RequestMapping(value = "/getPermissionList", method = RequestMethod.POST)
    @ResponseBody
    public ModelMap getPermissionList(@RequestBody PermissionSearchModel permissionSearchModel) throws Exception {

        Page<Login> managers=permissionService.getManagerList(permissionSearchModel);

        ModelMap modelMap = new ModelMap();
        modelMap.put("data", managers.getContent());
        modelMap.put("total", managers.getTotalElements());
        modelMap.put("totalPage", managers.getTotalPages());
        return modelMap;

    }

    /**
     * 根据权限用户ID，获取权限用户具体信息
     *
     * @param id            用户ID
     * @param model         返回的model
     * @return              视图模板字符串
     * @throws Exception
     */
    @RequestMapping(value = "/editPermission",method = RequestMethod.GET)
    public String editPermission(Long id, Model model) throws Exception{
        Login login=null;
        String view= "/admin/permission/modifyPermission";
        if(id!=null){
            login=loginRepository.findOne(id);
        }
        if(login==null){
            login=new Login();
        }
        String[] pers=login.getAuthors().split("\\|");
        for (String per : pers) {
            model.addAttribute(per,true);
        }
        model.addAttribute("login",login);

        return view;
    }

    /**
     * 保存后台用户
     * @param login     后台用户实体
     * @return          只要正常返回则表示保存成功
     * @throws Exception
     */
    @RequestMapping(value = "savePermission",method = RequestMethod.POST)
    @ResponseBody
    public ModelMap savePermission(@RequestBody Login login) throws Exception{
        ModelMap modelMap=new ModelMap();
        if(login.getId()==null){
            permissionService.addPermission(login);
        }else {
            permissionService.updatePermission(login);
        }
        return modelMap;
    }
}
