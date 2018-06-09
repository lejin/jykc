package org.jesusyouth.jykc.jykcadmin.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Unauthorised {
    @GetMapping("/403")
    public String unauthoried() {
        return "unauthorised";
    }
}
