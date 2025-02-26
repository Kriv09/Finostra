package com.example.finostra.Controllers.UserDocuments;

import com.example.finostra.Services.UserDocs.ImgBBService;
import com.example.finostra.Utils.UserDocuments.UserDocsChecker;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/userdocs")
public class UserDocumentsController {

    @Autowired
    private ImgBBService imgBBService;

    @PostMapping("/upload")
    @Transactional
    public String uploadUserDocument(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "File is empty!";
        }
        // TODO: Check the image according to docs format

        return imgBBService.uploadImage(file);
        }
}
