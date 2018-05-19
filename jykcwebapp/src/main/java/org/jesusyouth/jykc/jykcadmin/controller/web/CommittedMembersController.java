package org.jesusyouth.jykc.jykcadmin.controller.web;

import com.google.gson.Gson;
import org.jesusyouth.jykc.jykcadmin.model.CommittedMembers;
import org.jesusyouth.jykc.jykcadmin.repository.CommittedMembersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommittedMembersController {

    @Autowired
    private CommittedMembersRepo committedMembersRepo;

    @GetMapping("/zonaladmin/committed_members")
    public String getCommittedMembers(Model model){
        model.addAttribute("committed_members",committedMembersRepo.findAll());
        return "committed_members";
    }
}
