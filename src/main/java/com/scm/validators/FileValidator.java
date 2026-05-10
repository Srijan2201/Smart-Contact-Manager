package com.scm.validators;

import org.springframework.web.multipart.MultipartFile;
import javax.imageio.*;
import java.awt.image.*;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileValidator implements ConstraintValidator<ValidFile,MultipartFile> {

    private static final long MAX_FILE_SIZE = 2 * 1024 * 1024; // 2MB
    //type

    //height

    //width


    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            // context.disableDefaultConstraintViolation();
            // context.buildConstraintViolationWithTemplate("File must not be empty").addConstraintViolation();
            return true;
        }

        // file size
        if (file.getSize() > MAX_FILE_SIZE) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("File size must be less than 2MB").addConstraintViolation();
            return false;
        }
        // Resolution
//         try{
//         BufferedImage bufferedImage =ImageIO.read(file.getInputStream());

//     } catch (Exception e) {
//         e.printStackTrace();
    

// }

        // Add more validation logic as needed
        return true;
    }

    


    }


