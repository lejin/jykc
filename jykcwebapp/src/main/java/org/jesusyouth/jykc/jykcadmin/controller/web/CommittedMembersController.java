package org.jesusyouth.jykc.jykcadmin.controller.web;

import com.google.gson.Gson;
import org.jesusyouth.jykc.jykcadmin.model.CommittedMember;
import org.jesusyouth.jykc.jykcadmin.model.CommittedMembers;
import org.jesusyouth.jykc.jykcadmin.model.User;
import org.jesusyouth.jykc.jykcadmin.repository.CommittedMembersRepo;
import org.jesusyouth.jykc.jykcadmin.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CommittedMembersController {

    @Autowired
    private CommittedMembersRepo committedMembersRepo;

    @Autowired
    private UsersRepo usersRepo;

    @GetMapping("/zonaladmin/committed_members")
    public String getCommittedMembers(Model model,HttpSession httpSession){
        Integer zone = (Integer) httpSession.getAttribute("zone");
        model.addAttribute("committed_members",committedMembersRepo.findAllByZoneIdEquals(zone));
        return "committed_members";
    }

    @GetMapping("/zonaladmin/registered")
    public String getRegisteredMembers(Model model,HttpSession httpSession){
        Integer zone = (Integer) httpSession.getAttribute("zone");
        model.addAttribute("committed_members",committedMembersRepo.registeredMembers(zone));
        return "registered";
    }

    @GetMapping("/zonaladmin/not_registered")
    public String getNotRegisteredMembers(Model model,HttpSession httpSession){
        Integer zone = (Integer) httpSession.getAttribute("zone");
        model.addAttribute("committed_members",committedMembersRepo.NotRegisteredMembers(zone));
        return "not_registered";
    }

    @PostMapping("/zonaladmin/committed_members/updateinfo")
    public String putCommittedMembers(@RequestParam Integer member, @RequestParam String mail, @RequestParam String phone,
                                      @RequestParam String oldMail, @RequestParam String oldPhone) {
        User newUser = usersRepo.findFirstByEmailLikeOrPhoneLike(mail, phone);
        User oldUser = usersRepo.findFirstByEmailLikeOrPhoneLike(oldMail, oldPhone);
        if (isUserEditable(newUser) && isUserEditable(oldUser)) {
            CommittedMember committedMember = committedMembersRepo.findFirstCommittedMemberByEmailLikeOrPhoneLike(mail, phone);
            if (committedMember == null || committedMember.getId().equals(member)) {
                committedMembersRepo.updateMemberInfo(mail, phone, member);
                deleUser(newUser);
                deleUser(oldUser);
                return "redirect:/zonaladmin/committed_members?success=true";
            }
            return "redirect:/zonaladmin/committed_members?message=exist";
        }
        return "redirect:/zonaladmin/committed_members?success=false";
    }

    @PostMapping("/zonaladmin/committed_members/updatepersonal")
    public String putCommittedMembers(@RequestParam Integer member, @RequestParam String name, @RequestParam Integer age,
                                      @RequestParam String gender) {
        CommittedMember committedMember = committedMembersRepo.findById(member).get();
        if (committedMember != null) {
            committedMember.setName(name);
            committedMember.setAge(age);
            committedMember.setGender(gender);
            usersRepo.updateName(member,name );
            return "redirect:/zonaladmin/committed_members?success=true";
        }
        return "redirect:/zonaladmin/committed_members?success=false";
    }

    private void deleUser(User newUser) {
        if (null != newUser) {
            usersRepo.delete(newUser);
        }
    }

    private boolean isUserEditable(User user) {
        return user==null || user.getRole().trim().equals("user");
    }
}
