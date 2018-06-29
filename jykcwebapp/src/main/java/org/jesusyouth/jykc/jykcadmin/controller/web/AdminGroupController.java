package org.jesusyouth.jykc.jykcadmin.controller.web;

import org.apache.commons.collections.CollectionUtils;
import org.jesusyouth.jykc.jykcadmin.Constants.ZoneNames;
import org.jesusyouth.jykc.jykcadmin.common.FamilyUtil;
import org.jesusyouth.jykc.jykcadmin.common.GroupFee;
import org.jesusyouth.jykc.jykcadmin.common.GroupMemberUtil;
import org.jesusyouth.jykc.jykcadmin.dto.MembersWithTeensDto;
import org.jesusyouth.jykc.jykcadmin.model.GroupInfo;
import org.jesusyouth.jykc.jykcadmin.model.GroupMembers;
import org.jesusyouth.jykc.jykcadmin.model.Payment;
import org.jesusyouth.jykc.jykcadmin.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class AdminGroupController {

    @Autowired
    private GroupInfoRepo groupInfoRepo;

    @Autowired
    private PaymentRepo paymentRepo;

    @Autowired
    private GroupMemberUtil groupMemberUtil;

    @Autowired
    private GroupFee groupFee;

    @Autowired
    private FamilyUtil familyUtil;

    @GetMapping("/admin/group_info")
    public String getGroupInfo(Model model, HttpSession httpSession) {
        model.addAttribute("group_info", groupInfoRepo.findGroupInfoWithMembersCount());
        return "admin_group_info";

    }



    @GetMapping("/admin/group_members/{groupID}")
    public String getDetailsOfAGroup(Model model, @PathVariable Integer groupID, HttpSession httpSession) {
        List<MembersWithTeensDto> membersWithTeensDtos=groupMemberUtil.getMembersWithTeensDtos(groupID);
        model.addAttribute("group_members", membersWithTeensDtos);
        model.addAttribute("group_id", groupID);
        return "admin_group_members";
    }

    @PostMapping ("/admin/group/payment")
    public String markPayment(@RequestParam Integer zone,@RequestParam Integer amount,@RequestParam Integer group,@RequestParam String remark, HttpSession httpSession) {
        String email= (String) httpSession.getAttribute("email");
        Payment payment=new Payment();
        payment.setZone(zone);
        payment.setGroup(group);
        payment.setAmount(amount);
        payment.setRemark(remark);
        payment.setAddedBy(email);
        paymentRepo.save(payment);

        GroupInfo groupInfo=groupInfoRepo.findFirstByGidEquals(group);
        groupInfo.setStatus("Payment received");
        groupInfoRepo.save(groupInfo);
        return "redirect:/admin/group_info";
    }

}
