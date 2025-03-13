package org.example.seguroform.presentation.logins;

import org.example.seguroform.logic.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@org.springframework.stereotype.Controller("logins")
@RequestMapping("/presentation/logins")
public class controller {
    @Autowired
    private Service service;

    @GetMapping("/login")
    public String login() {
        return "presentation/login/ViewLogin";
    }
}
