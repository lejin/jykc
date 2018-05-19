package org.jesusyouth.jykc.jykcadmin.controller.api;

import org.jesusyouth.jykc.jykcadmin.model.GroupInfo;
import org.jesusyouth.jykc.jykcadmin.repository.GroupInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GroupInfoController {
    @Autowired
    private GroupInfoRepo groupInfoRepo;

    @GetMapping("/api/group/info")
    public GroupInfo getGroupInfo(@RequestParam Integer groupId){
        return groupInfoRepo.findFirstByGidEquals(groupId);
    }

    @PostMapping("/api/group/finalsubmit")
    public GroupInfo finalSubmit(@RequestParam Integer groupId){
        GroupInfo groupInfo=groupInfoRepo.findFirstByGidEquals(groupId);
        if(null!=groupInfo){
            groupInfo.setStatus("submitted");
            groupInfo.setEditable(false);
            groupInfoRepo.save(groupInfo);
            groupInfo.setMessage("success");
        }else {
            groupInfo=new GroupInfo();
            groupInfo.setMessage("no group found");
        }
        return groupInfo;
    }

}
