package com.cicoding.fastdfsdemo.controller;

import com.crecgec.bootbase.fastdfs.util.FastdfsUtil;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author zhaokejin
 * @description
 * @date 2020/7/1
 */
@RestController
@RequestMapping("/upload")
public class UploadController {

    @Resource
    private FastdfsUtil fastdfsUtil;

    /**
     * 上传
     * @param file 主文件
     * @return
     */
    @PostMapping("/addmainfile")
    public String addMainFile(@RequestPart(name = "file") MultipartFile file){
        System.out.println("ClientGlobal.configInfo(): " + ClientGlobal.configInfo());

        try {
            String fileName = file.getOriginalFilename();
            String filePath = fastdfsUtil.uploadFile(file.getBytes(),fileName);
            return filePath;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return null;
    }

    // fastdfsUtil 里面的方法省略 测试  ····

    /**
     * 普通文件上传到磁盘
     * @param file 主文件
     * @return
     */
    @PostMapping("/upload")
    public String upload(@RequestPart(name = "file") MultipartFile file){

        if (file != null) {
            System.out.println(file.getContentType());//在控制台打印文件的类型
            System.out.println(file.getName());//返回文件的名称
            System.out.println(file.getOriginalFilename());//返回文件的原文件名
            try {
                file.transferTo(new File("/data/"+ UUID.randomUUID()+file.getOriginalFilename()));
            } catch (IllegalStateException e) {
                e.printStackTrace();
                return "error";
            } catch (IOException e) {
                e.printStackTrace();
                return "error";
            }
        }
        return "success";
    }
}
