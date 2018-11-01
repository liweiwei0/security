package fun.lww.securitydemo.web;

import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
@RequestMapping("/file")
public class FileController {

    private String filder = "E:/projects/security/security-demo/src/main/resources/static/filder/";

    @PostMapping
    public void upload(MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        int index = filename.lastIndexOf(".");
        File file1 = new File(filder + System.currentTimeMillis() + filename.substring(index));
        file.transferTo(file1);
    }

    @GetMapping("/{filename}")
    public void download(@PathVariable("filename") String filename, HttpServletRequest request, HttpServletResponse response) {
        File file = new File(filder + filename);
        try (
                OutputStream outputStream = response.getOutputStream();
        ) {
            response.setContentType("application/x-download");
            response.setHeader("Content-Disposition", "attachment;filename=test.txt");
            FileUtils.copyFile(file, outputStream);
            outputStream.flush();
        } catch (Exception e) {
        }
    }
}
