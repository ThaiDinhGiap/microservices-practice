package mss301.giaptd.userservice.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenController {

    @PostMapping("/register")
    public String register(@RequestBody String userInfo) {
        return "Public information available to all.";
    }

    @GetMapping("/userInfo")
    public String getUser() {
        return "Public information available to all.";
    }
}