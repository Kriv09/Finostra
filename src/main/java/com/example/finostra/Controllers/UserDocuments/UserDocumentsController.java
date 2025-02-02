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
//            File tempFile = File.createTempFile("userdoc_", ".jpg");
//            file.transferTo(tempFile);

//            boolean isValid = UserDocsChecker.isValidDocument(tempFile);
//
//            if (!isValid) {
//                return "Photo is not a document";
//            }

        return imgBBService.uploadImage(file);
        }
}
