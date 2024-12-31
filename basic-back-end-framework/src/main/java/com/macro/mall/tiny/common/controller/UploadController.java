package com.macro.mall.tiny.common.controller;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.common.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 上传图片控制器
 *
 * @author ztx
 * @version 1
 */
@RestController
@RequestMapping("/upload")
@CrossOrigin
public class UploadController {
    @Value("${file.root.path}")
    private String fileRootPath;

    @PostMapping("/file/upload")
    public CommonResult<String> fileUpload(@RequestParam("file") MultipartFile[] files) {
        String filePath = "";
        List<String> fileNames = new ArrayList<>();
        String folder = "temp";
        if (AuthUtils.isUser()) {
            folder = LocalDateTimeUtil.formatNormal(LocalDate.now()) + StringPool.SLASH+AuthUtils.getWxUser().getWxUser().getId();
        }
        if (AuthUtils.isAdmin()) {
            folder = LocalDateTimeUtil.formatNormal(LocalDate.now()) + StringPool.SLASH+AuthUtils.getAdminUser().getUser().getId().toString();
        }
        // 多文件上传
        for (MultipartFile file : files) {
            // 上传简单文件名
            String originalFilename = System.currentTimeMillis() + file.getOriginalFilename().replace(StringPool.SPACE, StringPool.EMPTY);
            fileNames.add(folder + StringPool.SLASH + originalFilename);
            String folderName = fileRootPath + folder + StringPool.SLASH;
            // 存储路径
            filePath = new StringBuilder(folderName).append(originalFilename).toString();
            File newFile = new File(filePath);
            if (!newFile.getParentFile().exists()) {
                newFile.getParentFile().mkdirs();
            }
            try {
                // 保存文件
                file.transferTo(newFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return CommonResult.success(fileNames.stream().collect(Collectors.joining(StringPool.COMMA)));
    }
}
