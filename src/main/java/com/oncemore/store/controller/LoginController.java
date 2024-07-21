package com.oncemore.store.controller;

import com.oncemore.store.model.UserModel;
import com.oncemore.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityExistsException;
import java.util.Objects;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {

        return "login";
    }

    @GetMapping("/register")
    public String register(Model model, @ModelAttribute("toastMessage") String toastMessage) {
        model.addAttribute("userModel", new UserModel());

        return "register";
    }

    @PostMapping("/register-user")
    public String createUser(UserModel user, RedirectAttributes redirectAttributes) {
        try {
            if (!Objects.equals(user.getPassword(), user.getConfirmPwd())) {
                redirectAttributes.addFlashAttribute("toastMessage", "Không khớp mật khẩu!");
            }
            userService.save(user);
            redirectAttributes.addFlashAttribute("toastMessage", "Tài khoản đã được tạo thành công.");

        } catch (EntityExistsException e) {
            redirectAttributes.addFlashAttribute("toastMessage", e.getMessage());
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("toastMessage", "Đã xảy ra lỗi khi tạo tài khoản.");
        }
        return "redirect:/register";
    }



}
