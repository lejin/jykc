package org.jesusyouth.jykc.jykcadmin.controller.web;

import org.apache.commons.collections.CollectionUtils;
import org.jesusyouth.jykc.jykcadmin.Constants.ZoneNames;
import org.jesusyouth.jykc.jykcadmin.common.FamilyUtil;
import org.jesusyouth.jykc.jykcadmin.common.GroupFee;
import org.jesusyouth.jykc.jykcadmin.common.GroupMemberUtil;
import org.jesusyouth.jykc.jykcadmin.dto.MembersWithTeensDto;
import org.jesusyouth.jykc.jykcadmin.dto.PaymentDTO;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @Autowired
    private GroupMembersRepo groupMembersRepo;

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

    @PostMapping("/admin/member/payment")
    public String paymentRemark(@RequestParam Integer group,@RequestParam Integer uid,@RequestParam String remark){
        Optional<GroupMembers> groupMembers=groupMembersRepo.findById(uid);
        if(groupMembers.isPresent()){
            GroupMembers groupMembers1=groupMembers.get();
            groupMembers1.setPaid(true);
            groupMembers1.setPaymentRemark(remark);
            groupMembersRepo.save(groupMembers1);
        }
        return "redirect:/admin/group_members/".concat(String.valueOf(group));
    }

    @GetMapping("/admin/payment")
    public String getDetailsOfAGroup(Model model) {
        Iterable<Payment> paymentList=paymentRepo.findAll();
        Iterable<GroupInfo> groupInfoList=groupInfoRepo.findAll();
        Map<Integer,GroupInfo> groupInfoMap=new HashMap<>();
        groupInfoList.forEach(e->{
            groupInfoMap.put(e.getGid(), e);
        });
        int total=0;
        for (Payment payment : paymentList) {
            total=total+payment.getAmount();
            payment.setGroupCode(groupInfoMap.get(payment.getGroup()).getGroupID());
        }
        model.addAttribute("total", total);
        model.addAttribute("payment", paymentList);
        return "admin_payment";
    }

    @GetMapping("/admin/payment_status")
    public String getPaymentStatus(Model model) {
        List<PaymentDTO> paid=paymentRepo.getPaidDetails();
        List<PaymentDTO> notPaid=paymentRepo.getNotPaidDetails();

        model.addAttribute("paid_total", paid.stream().map(e->e.getAmount()).reduce(Integer::sum).get());
        model.addAttribute("paid_group_total", paid.stream().map(e->e.getGroups()).reduce(Integer::sum).get());

        model.addAttribute("not_paid_total", notPaid.stream().map(e->e.getAmount()).reduce(Integer::sum).get());
        model.addAttribute("not_paid_group_total", notPaid.stream().map(e->e.getGroups()).reduce(Integer::sum).get());


        model.addAttribute("paid", paid);
        model.addAttribute("not_paid", notPaid);

        return "admin_paid_status";
    }

    @DeleteMapping("/admin/group_members")
    public String deleteMember(@RequestParam Integer groupID,@RequestParam(required = false) Integer memberID,
                               @RequestParam(required = false) Integer teenID) {
        if(null!=teenID){
            groupMemberUtil.removeGroupMember(groupID,null,teenID.toString());
            return "redirect:/admin/group_members/".concat(String.valueOf(groupID));
        }
       groupMemberUtil.removeGroupMember(groupID,memberID.toString(),null);
        return "redirect:/admin/group_members/".concat(String.valueOf(groupID));
    }

}
