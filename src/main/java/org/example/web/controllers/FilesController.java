package org.example.web.controllers;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.example.app.exceptions.FileUploadException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@Controller
public class FilesController {

    private Logger logger = Logger.getLogger(FilesController.class);
    private List<File> fileList;

    @PostMapping("/books/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) throws Exception {
        String name = file.getOriginalFilename();
        byte[] bytes = file.getBytes();

        //create dir
        String rootPath = System.getProperty("catalina.home");
        File dir = new File(rootPath + File.separator + "external_uploads");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        //create file
        File serverFile = new File(dir.getAbsoluteFile() + File.separator + name);

        if (!file.isEmpty()) {
            try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile))) {
                stream.write(bytes);
                fileList.add(serverFile);
                model.addAttribute("file_name", fileList);
                logger.info("new file saved at: " + serverFile.getAbsolutePath());
            }
        } else {
            logger.info("file upload fail");
            throw new FileUploadException("file not found");
        }
        return "redirect:/books/shelf";
    }

    @GetMapping("/books/files/{file_name}")
    public void DownloadFile(@RequestParam(value = "file_name") String fileName, HttpServletResponse response) {
        try (BufferedInputStream stream = new BufferedInputStream(new FileInputStream(new File(fileName)))) {
            IOUtils.copy(stream, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException exception) {
            logger.info("file download fail");
            throw new RuntimeException("IOError writing file to output stream");
        }
    }

    @ExceptionHandler(FileUploadException.class)
    public String handlerError(Model model, FileUploadException exception) {
        model.addAttribute("errorMessage", exception.getMessage());
        return "errors/500";
    }
}
