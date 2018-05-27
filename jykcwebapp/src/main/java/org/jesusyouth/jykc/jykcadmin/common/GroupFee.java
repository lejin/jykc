package org.jesusyouth.jykc.jykcadmin.common;

import org.jesusyouth.jykc.jykcadmin.model.GroupInfo;
import org.jesusyouth.jykc.jykcadmin.repository.GroupInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GroupFee {

    @Autowired
    private GroupInfoRepo groupInfoRepo;

    public Integer calculateFee(String category) {
        switch (category) {
            case "family":
                return 1500;
            case "student":
                return 800;
            case "others":
                return 1000;
        }
        return 0;
    }

    public GroupInfo updateGroupFee(Integer groupId,String category){
        GroupInfo groupInfo = groupInfoRepo.findFirstByGidEquals(groupId);
        if (null != groupInfo) {
            Integer groupfee = groupInfo.getGroupFee()==null?0:groupInfo.getGroupFee();
            groupfee = groupfee + calculateFee(category);
            groupInfo.setGroupFee(groupfee);
            groupInfoRepo.save(groupInfo);
        }
        return groupInfo;
    }

    public GroupInfo reduceGroupFee(Integer groupId,String category){
        GroupInfo groupInfo = groupInfoRepo.findFirstByGidEquals(groupId);
        reducefee(groupInfo, category);
        return groupInfo;
    }

    public GroupInfo reduceGroupFee(GroupInfo groupInfo,String category){
        reducefee(groupInfo, category);
        return groupInfo;
    }

    private void reducefee(GroupInfo groupInfo, String category) {
        if (null != groupInfo) {
            Integer groupfee = groupInfo.getGroupFee()==null?0:groupInfo.getGroupFee();
            if(groupfee>0){
                groupfee = groupfee - calculateFee(category);
                groupInfo.setGroupFee(groupfee);
                groupInfoRepo.save(groupInfo);
            }
        }
    }

}
