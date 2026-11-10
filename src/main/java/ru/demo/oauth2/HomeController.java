package ru.demo.oauth2;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(@AuthenticationPrincipal OAuth2User principal, Model model) {
        if (principal != null) {
            model.addAttribute("login", principal.getAttribute("login"));
            model.addAttribute("name", principal.getAttribute("name"));
            model.addAttribute("avatarUrl", principal.getAttribute("avatar_url"));
            model.addAttribute("profileUrl", principal.getAttribute("html_url"));
        }
        return "index";
    }
}
