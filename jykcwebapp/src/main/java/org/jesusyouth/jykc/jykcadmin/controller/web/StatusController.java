package org.jesusyouth.jykc.jykcadmin.controller.web;

import org.jesusyouth.jykc.jykcadmin.dto.RegistrationStatus;
import org.jesusyouth.jykc.jykcadmin.dto.RegistrationStatusConcrete;
import org.jesusyouth.jykc.jykcadmin.repository.GroupInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class StatusController {

    @Autowired
    private GroupInfoRepo groupInfoRepo;

    @GetMapping("/registration_status")
    public String getGroupSignup(Model model, HttpSession httpSession) {
        model.addAttribute("status", groupInfoRepo.getRegistrationStatus());
        return "registration_status";

    }

    @GetMapping("/group_members_status")
    public String getGroupMembersStatus(Model model, HttpSession httpSession) {
        List<RegistrationStatus> notApprovedGL=groupInfoRepo.getNotApprovedGroupLeadersCount();
        List<RegistrationStatus> totalList=groupInfoRepo.getGroupMembersCount();
        List<RegistrationStatusConcrete> concreteList=new ArrayList<>();
        Map<String,Integer> reduceMap=new HashMap<>();
        notApprovedGL.forEach(e->{
            reduceMap.put(e.getZone(),e.getCount() );
        });
        Integer totalReduction=notApprovedGL.stream().map(e->e.getCount()).reduce(Integer::sum).get();
       totalList.forEach(e->{
           RegistrationStatusConcrete concrete = new RegistrationStatusConcrete();
           concrete.setZone(e.getZone());
           if(reduceMap.containsKey(e.getZone())){
               concrete.setCount(e.getCount()-reduceMap.get(e.getZone()));
           }
           else {
               concrete.setCount(e.getCount());
           }
           concreteList.add(concrete);
       });

        model.addAttribute("status", concreteList);
        Integer members=groupInfoRepo.getGroupMembersCount().stream().map(e->e.getCount()).reduce(Integer::sum).get();
        Integer family=groupInfoRepo.getFamilyCount();
        model.addAttribute("members", members-totalReduction);
        model.addAttribute("family", family);
        model.addAttribute("total", family+members-totalReduction);
        return "registration_status_2";

    }
}
