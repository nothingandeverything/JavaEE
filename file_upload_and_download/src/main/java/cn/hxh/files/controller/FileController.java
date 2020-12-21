package cn.hxh.files.controller;

import cn.hxh.files.pojo.Files;
import cn.hxh.files.pojo.User;
import cn.hxh.files.service.FilesService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/file")
public class FileController {


    @Autowired
    private FilesService filesService;


    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile[] file, HttpSession session) throws Exception {
        for (MultipartFile multipartFile : file) {
            //文件大小
            long size = multipartFile.getSize();
            //老文件名字
            String originalFilename = multipartFile.getOriginalFilename();
            //文件类型
            String contentType = multipartFile.getContentType();
            //获取文件后缀名
            String[] split = contentType.split("/");
            String ext = "." + split[1];
            //获取下载路径url
            String s = ResourceUtils.getURL("classpath:").getPath() + "static/upload";
            String url = URLDecoder.decode(s);
            Files files = new Files();
            files.setExt(ext);
            //创建新文件名字
            String newFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + UUID.randomUUID().toString().replace("-", "") + ext;
            files.setNewfilename(newFileName);
            files.setOldfilename(originalFilename);
            files.setSize(size);
            files.setUploadTime(new Date());
            String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String catalogName = url + "/" + format;
            files.setPath("/upload/" + format);
            files.setType(contentType);
            User user_info = (User) session.getAttribute("USER_INFO");
            files.setUserId(user_info.getId());
            files.setDowncounts(0);
            if (multipartFile.getContentType().indexOf("image") != -1) {
                files.setIsimg("是");
            } else {
                files.setIsimg("否");
            }

            File fileDir = new File(catalogName);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }

            //下载文件
            multipartFile.transferTo(new File(catalogName, newFileName));
            //保存到数据库
            filesService.insert(files);
        }


        return "redirect:/file/showALL";

    }


    @RequestMapping("/showALL")
    public String showALL(HttpSession session,
                          Model model,
                          HttpServletResponse response) {
        //存放session
        User user_info = (User) session.getAttribute("USER_INFO");

        //返回files集合
        List<Files> filesList = filesService.queryAllByLimit(0, 100000, user_info.getId());
        //前端传递数据
        model.addAttribute("fileList", filesList);
        model.addAttribute("user", user_info);

        return "file";
    }

    @RequestMapping("/dowload")
    public void dowload(HttpServletResponse response, int id) throws IOException {
        Files files = filesService.queryById(id);
        String Path = ResourceUtils.getURL("classpath:").getPath() + "/static" + files.getPath();
        FileInputStream in = new FileInputStream(new File(URLDecoder.decode(Path), files.getNewfilename()));
        response.setHeader("Content-Disposition", "attachment;filename=" + URLDecoder.decode(files.getOldfilename(), "UTF-8"));
        ServletOutputStream os = response.getOutputStream();
        IOUtils.copy(in, os);
        IOUtils.closeQuietly(in);
        IOUtils.closeQuietly(os);
        files.setDowncounts(files.getDowncounts() + 1);
        filesService.update(files);
    }


    //删除功能
    @RequestMapping("/delete")
    public String delete(@RequestParam("id") int id,
                         Model model) throws Exception {
        Files files = filesService.queryById(id);
        String path = ResourceUtils.getURL("classpath:").getPath() + "static" + files.getPath() + "/" + files.getNewfilename();
        String decode = URLDecoder.decode(path);
        File file = new File(decode);
        boolean delete = file.delete();
        if (delete) {
            boolean b = filesService.deleteById(id);
            if (b){
                model.addAttribute("msg", "删除成功");
                return "forward:/file/showALL";
            }
        }
        model.addAttribute("msg", "删除失败");
        return "forward:/file/showALL";
    }


}
