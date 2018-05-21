package org.jesusyouth.jykc.jykcadmin.common;

import org.jesusyouth.jykc.jykcadmin.repository.GroupMembersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GroupValidations {

    @Autowired
    private GroupMembersRepo groupMembersRepo;

    public void validateGroupMembersCount(Integer groupId) throws GroupMemberValidationException {
        Integer groupMemberCount = groupMembersRepo.countAllByGroupIdEquals(groupId);
        if (groupMemberCount >= 10) {
            throw new GroupMemberValidationException("Group can have only 6 to 10 members !");
        }
    }

    public void validateGroupMemberAge(String category, Integer age) throws GroupMemberValidationException {
        switch (category) {
            case "teens":
                if (age <= 13 || age > 18) {
                    throw new GroupMemberValidationException("Teen should be between 13 and 18");
                }
                break;
            case "youth":
                if (age <= 18) {
                    throw new GroupMemberValidationException("Youth should be 18+");
                }
                break;

            case "pre teens":
                if (age < 8 || age > 12) {
                    throw new GroupMemberValidationException("pre teens shoud be between 8 and 12");
                }
                break;

            case "kids":
                if (age < 8 || age > 12) {
                    throw new GroupMemberValidationException("kids shoud be between 4 and 7");
                }
                break;
        }
    }

    public void validateFamilyCount(Integer groupId) throws GroupMemberValidationException {
        Integer familyCount=groupMembersRepo.countAllByGroupIdEqualsAndCategoryEquals(groupId,"family");
        Integer groupMemberCount = groupMembersRepo.countAllByGroupIdEquals(groupId);

        if(groupMemberCount==0 && familyCount>=7){
            throw new GroupMemberValidationException("A family only group can have only 7 families");
        }

        if(groupMemberCount!=0 && familyCount>=4){
            throw new GroupMemberValidationException("A group can have only 4 families");
        }
    }


}
