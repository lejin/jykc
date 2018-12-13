package org.jesusyouth.jykc.jykcadmin.common;

import org.apache.commons.beanutils.BeanUtils;
import org.jesusyouth.jykc.jykcadmin.dto.GroupMemberDTO;
import org.jesusyouth.jykc.jykcadmin.dto.MembersWithTeensDto;
import org.jesusyouth.jykc.jykcadmin.model.GroupInfo;
import org.jesusyouth.jykc.jykcadmin.model.GroupMembers;
import org.jesusyouth.jykc.jykcadmin.model.Teen;
import org.jesusyouth.jykc.jykcadmin.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class GroupMemberUtil {

    private static final Logger logger = LoggerFactory.getLogger(GroupMemberUtil.class);

    @Autowired
    private GroupMembersRepo groupMembersRepo;

    @Autowired
    private TeensRepo teensRepo;

    @Autowired
    private GroupFee groupFeeComponent;

    @Autowired
    private CommittedMembersRepo committedMembersRepo;

    @Autowired
    private FamilyUtil familyUtil;

    @Autowired
    private GroupInfoRepo groupInfoRepo;

    public List<MembersWithTeensDto> getMembersWithTeensDtos(Integer groupId) {
        List<GroupMemberDTO> groupMemberDTOS = groupMembersRepo.getAllMembers(groupId);
        List<GroupMembers> groupMembers=groupMembersRepo.getAllByGroupIdEquals(groupId);
        Map<Integer,GroupMembers> paidmap=groupMembers.stream().filter(e->null!=e.getTeenId()).collect(Collectors.toMap(m->m.getTeenId(), Function.identity()));
        List<Teen> teenList = teensRepo.findAllByTeenGroupIdEquals(groupId);
        List<MembersWithTeensDto> membersWithTeensDtos = new ArrayList<>();
        groupMemberDTOS.forEach(e -> {
            MembersWithTeensDto membersWithTeensDto = new MembersWithTeensDto();
            try {
                BeanUtils.copyProperties(membersWithTeensDto, e);
                membersWithTeensDto.setIs_group_leader(e.getIs_group_leader());
                membersWithTeensDto.setIs_group_member(e.getIs_group_member());
                membersWithTeensDto.setGid(groupId);
                membersWithTeensDto.setPaid(e.getPaid());
                membersWithTeensDto.setUid(e.getUid());
                membersWithTeensDto.setPaymentRemark(e.getPayment_remark());
                membersWithTeensDtos.add(membersWithTeensDto);
            } catch (Exception ex) {
                logger.error(ex.getMessage());
            }
        });
        if (!CollectionUtils.isEmpty(teenList)) {
            teenList.forEach(e -> {
                MembersWithTeensDto membersWithTeensDto = new MembersWithTeensDto();
                try {
                    membersWithTeensDto.setAge(e.getTeenAge());
                    membersWithTeensDto.setEmail(e.getTeenEmail());
                    membersWithTeensDto.setGender(e.getTeenGender());
                    membersWithTeensDto.setPhone_number(e.getTeenPhone());
                    membersWithTeensDto.setName(e.getTeenName());
                    membersWithTeensDto.setCategory("student");
                    membersWithTeensDto.setTeenId(e.getTeenId());
                    membersWithTeensDto.setGid(groupId);
                    GroupMembers groupMembers1=paidmap.get(e.getTeenId());
                    membersWithTeensDto.setPaid(groupMembers1.isPaid());
                    membersWithTeensDto.setPaymentRemark(groupMembers1.getPaymentRemark());
                    membersWithTeensDto.setUid(groupMembers1.getUid());
                    membersWithTeensDtos.add(membersWithTeensDto);
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                }
            });
        }
        return membersWithTeensDtos;
    }

    public GroupInfo removeGroupMember(@RequestParam Integer groupId, @RequestParam(required = false) String userId, @RequestParam(required = false) String teenId) {
        GroupInfo groupInfo=null;

        if(null!=teenId && !"null".equals(teenId) && !StringUtils.isEmpty(teenId)){
            Integer teen=Integer.valueOf(StringUtils.trimAllWhitespace(teenId));
            teensRepo.deleteTeenByTeenIdEquals(teen);
            groupMembersRepo.deleteGroupMembersByTeenIdEquals(teen);
            groupInfo=groupFeeComponent.reduceGroupFee(groupId, "student");
            groupInfo.setMessage("success");
            return groupInfo;
        }
        Integer userIdInt=Integer.valueOf(StringUtils.trimAllWhitespace(userId));
        GroupMembers groupMembers = groupMembersRepo.findFirstByMemberEquals(userIdInt);
        if (null != groupMembers) {

            groupInfo = groupInfoRepo.findFirstByGidEquals(groupId);
            if(null!=groupInfo && groupInfo.getGroupLeader().equals(userIdInt)){
                groupInfo.setMessage("can't delete group leader");
                logger.error("can't delete group leader");
                return groupInfo;
            }

            String category = groupMembers.getCategory();
            groupMembersRepo.deleteByMemberEquals(userIdInt,groupId );
            committedMembersRepo.updateIsGroupMember(0, userIdInt);

            groupInfo=groupFeeComponent.reduceGroupFee(groupInfo, category);

            if("family".equals(category)){
                familyUtil.deleteFamily(userIdInt);
            }
            groupInfo.setMessage("success");
        }
        return groupInfo;
    }
}
