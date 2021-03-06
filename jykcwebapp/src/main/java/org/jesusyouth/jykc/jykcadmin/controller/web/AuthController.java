package org.jesusyouth.jykc.jykcadmin.controller.web;

import org.jesusyouth.jykc.jykcadmin.model.User;
import org.jesusyouth.jykc.jykcadmin.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class AuthController {

    @Autowired
    private UsersRepo usersRepo;

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String signin(@RequestParam String uid, @RequestParam String name, @RequestParam String email, @RequestParam String image, HttpSession httpSession) {
        User user=usersRepo.findFirstByEmail(email);
        if(null==user && !user.getRole().contains("zonal_admin") && !user.getRole().contains("web_admin")){
            return "redirect:/403";
        }
        if(StringUtils.isEmpty(user.getUid())){
            user.setUid(uid);
            user.setImage(image);
            usersRepo.save(user);
        }
        httpSession.setAttribute("uid",uid);
        httpSession.setAttribute("role",user.getRole());
        httpSession.setAttribute("zone",user.getZone());
        httpSession.setAttribute("image",image);
        httpSession.setAttribute("email",email);
        if(user.getRole().contains("web_admin")){
            return "redirect:/admin/home";
        }
        return "redirect:/zonaladmin/home";
    }

    @GetMapping("/checkuser/{email}")
    @ResponseBody
    public User CheckUser(@PathVariable String email) {
        return usersRepo.findFirstByEmail(email);
    }
}
