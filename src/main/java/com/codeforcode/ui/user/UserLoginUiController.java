package com.codeforcode.ui.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/login")
public class UserLoginUiController {

    @RequestMapping
    public String login() {
        return "non-auth/user/login";
    }
}
