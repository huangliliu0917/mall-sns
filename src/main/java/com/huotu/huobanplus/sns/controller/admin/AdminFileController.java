package com.huotu.huobanplus.sns.controller.admin;

import com.huotu.huobanplus.sns.model.common.UploadImageModel;
import com.huotu.huobanplus.sns.service.resource.StaticResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.UUID;

/**
 * Created by Administrator on 2016/10/17.
 */
@Controller
@RequestMapping("/top/file")
public class AdminFileController {

    private static final String[] PIC_EXT = {"BMP", "JPG", "JPEG", "PNG", "GIF"};

    @Autowired
    private StaticResourceService staticResourceService;

    /**
     * 富文本图片上传
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/uploadUeImage", method = RequestMethod.POST)
    @ResponseBody
    public UploadImageModel fileUploadUeImage(MultipartHttpServletRequest request) throws Exception {
        UploadImageModel result = new UploadImageModel();
        MultipartFile file = request.getFile("imgFile");

        String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase();
        String fileName = StaticResourceService.article + "/" + UUID.randomUUID().toString().replace("-", "") + "." + fileExt;

        URI uri = staticResourceService.uploadResource(fileName, file.getInputStream());
        result.setCode(1);
        result.setError(0);
        result.setMessage("上传成功!");
        result.setUrl(uri.toString());
        return result;
    }


    @RequestMapping(value = "/uploadAticleImage")
    @ResponseBody
    public UploadImageModel uploadAticleImage(@RequestParam(value = "fileImage") MultipartFile shareImage
            , HttpServletResponse response) throws Exception {
        UploadImageModel resultModel = new UploadImageModel();

        //文件格式判断
        if (ImageIO.read(shareImage.getInputStream()) == null) {
            resultModel.setCode(0);
            resultModel.setMessage("请上传图片文件！");
            return resultModel;
        }

        if (shareImage.getSize() == 0) {
            resultModel.setCode(0);
            resultModel.setMessage("请上传图片！");
            return resultModel;
        }

        //获取图片后缀名
        String fileName = shareImage.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1).toUpperCase();
        Boolean flag = false;

        for (String s : PIC_EXT) {
            if (ext.equals(s)) {
                flag = true;
                break;
            }
        }

        if (!flag) {
            resultModel.setCode(0);
            resultModel.setMessage("文件后缀名非图片后缀名");
            return resultModel;
        }

        //保存图片
        fileName = StaticResourceService.article + "/" + UUID.randomUUID().toString().replace("-", "") + "." + ext.toLowerCase();
        URI uri = staticResourceService.uploadResource(fileName, shareImage.getInputStream());
        response.setHeader("X-frame-Options", "SAMEORIGIN");
        resultModel.setCode(1);
        resultModel.setMessage(uri.toString());
        resultModel.setUrl(uri.toString());
        return resultModel;
    }

}
