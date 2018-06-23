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

    public void validateGroupCountForSubmission(Integer groupId) throws GroupMemberValidationException {
        Integer groupMemberCount = groupMembersRepo.countAllByGroupIdEquals(groupId);
        if (groupMemberCount < 6) {
            throw new GroupMemberValidationException("Group should have 6 to 10 members !");
        }
        //for final submission so value can be 10
        if (groupMemberCount >10) {
            throw new GroupMemberValidationException("Group can have only 6 to 10 members !");
        }
    }

    public void validateGroupMemberAge(String category, Integer age) throws GroupMemberValidationException {
        switch (category) {
            case "teen":
                if (age < 13 || age > 18) {
                    throw new GroupMemberValidationException("Teen should be between 13 and 18");
                }
                break;

            case "kid":
                if (age > 13) {
                    throw new GroupMemberValidationException("kids shoud be below 13");
                }
                break;
        }
    }

    public void validateFamilyCount(Integer groupId,String category) throws GroupMemberValidationException {
        Integer familyCount=groupMembersRepo.countAllByGroupIdEqualsAndCategoryEquals(groupId,"family");
        Integer groupMemberCount = groupMembersRepo.countAllByGroupIdEquals(groupId);

        if(groupMemberCount==familyCount && familyCount>=7){
            throw new GroupMemberValidationException("A family only group can have only 7 families");
        }

        if(groupMemberCount!=familyCount && familyCount>=4 && "family".equals(category)){
            throw new GroupMemberValidationException("A mixed group can have only 4 families");
        }
    }


}
