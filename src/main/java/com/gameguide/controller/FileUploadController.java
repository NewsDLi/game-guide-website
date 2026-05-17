package com.gameguide.controller;

import com.gameguide.common.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/api/upload")
public class FileUploadController {

    @Value("${app.upload.dir:./uploads}")
    private String uploadDir;

    private Path getUploadPath() {
        String dir = uploadDir;
        if (dir.startsWith("classpath:")) {
            dir = dir.substring("classpath:".length());
        }
        if (dir.startsWith("/")) {
            dir = dir.substring(1);
        }
        if (dir.isEmpty()) {
            dir = "uploads";
        }
        return Paths.get(dir).toAbsolutePath();
    }

    @PostMapping
    public Result<?> upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Result.fail(400, "文件不能为空");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            return Result.fail(400, "仅支持图片上传");
        }

        if (file.getSize() > 5 * 1024 * 1024) {
            return Result.fail(400, "图片大小不能超过5MB");
        }

        try {
            Path dir = getUploadPath();
            if (!Files.exists(dir)) {
                Files.createDirectories(dir);
            }

            String ext = getExtension(file.getOriginalFilename());
            String filename = UUID.randomUUID().toString().substring(0, 8) + ext;
            Path target = dir.resolve(filename);
            file.transferTo(target.toFile());

            String url = "/uploads/" + filename;
            return Result.ok("上传成功", url);
        } catch (IOException e) {
            return Result.fail(500, "上传失败: " + e.getMessage());
        }
    }

    private String getExtension(String filename) {
        if (filename == null) return ".jpg";
        int i = filename.lastIndexOf('.');
        return i >= 0 ? filename.substring(i) : ".jpg";
    }
}
