package com.kincurrently.services;

import com.kincurrently.models.User;
import com.kincurrently.models.UserWithRoles;
import com.kincurrently.repositories.Roles;
import com.kincurrently.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@Service
public class UserDetailsLoader implements UserDetailsService {
    private final UserRepository userRepo;
    private final Roles roles;
    @Value("${file-upload-path}")
    private String uploadPath;

    public UserDetailsLoader(UserRepository userRepo, Roles roles) {
        this.userRepo = userRepo;
        this.roles = roles;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user found for " + username);
        }

        return new UserWithRoles(user, roles.ofUserWith(username));
    }

    public Errors checkPassword(User user, Errors errors) {
        if (!user.getPassword().matches(".{6,}")) {
            errors.rejectValue(
                    "password",
                    "user.password",
                    "Password must be at least 6 characters in length."
            );
        }
        if (!user.getPassword().matches("\\S+$")) {
            errors.rejectValue(
                    "password",
                    "user.password",
                    "Password must not contain any whitespace."
            );
        }
        if (!user.getPassword().matches("(.*[a-z].*)")) {
            errors.rejectValue(
                    "password",
                    "user.password",
                    "Password must include a lower case letter."
            );
        }
        if (!user.getPassword().matches("(.*[A-Z].*)")) {
            errors.rejectValue(
                    "password",
                    "user.password",
                    "Password must include an upper case letter."
            );
        }
        if (!user.getPassword().matches("(.*[0-9].*)")) {
            errors.rejectValue(
                    "password",
                    "user.password",
                    "Password must include a number."
            );
        }
        if (!user.getPassword().matches("(.*[!@#$%^&+=].*)")) {
            errors.rejectValue(
                    "password",
                    "user.password",
                    "Password must include a special character."
            );
        }
        return errors;
    }
    public Errors checkRegistration(User user, Errors errors) {
        if (userRepo.findByUsername(user.getUsername()) != null) {
            errors.rejectValue(
                    "username",
                    "user.username",
                    "Username already exists."
            );
        }
        if (userRepo.findByEmail(user.getEmail()) != null) {
            errors.rejectValue(
                    "email",
                    "user.email",
                    "There is already an account with this email."
            );
        }
        if(!(user.getEmail().contains("@") && user.getEmail().contains("."))) {
            errors.rejectValue(
                    "email",
                    "user.email",
                    "Please enter a valid email."
            );
        }
        return errors;
    }

    public Errors checkUpdate(User user, Errors errors, User dbUser) {
        System.out.println(dbUser);
        if (userRepo.findByUsername(user.getUsername()) != null && !user.getUsername().equals(dbUser.getUsername())) {
            errors.rejectValue(
                    "username",
                    "user.username",
                    "Username already exists."
            );
        }
        if (userRepo.findByEmail(user.getEmail()) != null && !user.getEmail().equals(dbUser.getEmail())) {
            errors.rejectValue(
                    "email",
                    "user.email",
                    "There is already an account with this email."
            );
        }
        if(!(user.getEmail().contains("@") && user.getEmail().contains("."))) {
            errors.rejectValue(
                    "email",
                    "user.email",
                    "Please enter a valid email."
            );
        }
        return errors;
    }

    public String saveFile(MultipartFile uploadedFile, Model model) {
        String filename = uploadedFile.getOriginalFilename();
        if(filename.trim().equals("")) {
            return null;
        }
        String htmlfilepath = Paths.get("/img/", filename).toString();
        String filepath = Paths.get(uploadPath, filename).toString();
        File destinationFile = new File(filepath);
        try {
            uploadedFile.transferTo(destinationFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return htmlfilepath;
    }
}