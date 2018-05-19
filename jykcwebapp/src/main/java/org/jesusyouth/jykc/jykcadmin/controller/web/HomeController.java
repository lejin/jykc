package org.jesusyouth.jykc.jykcadmin.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping(value = "/zonaladmin/home" )
    public String home(Model modelMap){
        modelMap.addAttribute("message","hggmlfgsdfffgai");
        return "home";
    }
}
