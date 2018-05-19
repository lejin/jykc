package org.jesusyouth.jykc.jykcadmin.controller.api;

import org.jesusyouth.jykc.jykcadmin.model.CommittedMember;
import org.jesusyouth.jykc.jykcadmin.model.CommittedMembers;
import org.jesusyouth.jykc.jykcadmin.repository.CommittedMembersRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CommittedMembersApi {

    @Autowired
    private CommittedMembersRepo committedMembersRepo;

    private static final Logger logger = LoggerFactory.getLogger(CommittedMembersRepo.class);

    @PostMapping("/api/members/{zone_id}")
    List<CommittedMember> getCommittedMembers(@PathVariable Integer zone_id){
        logger.info("inside /api/members/"+zone_id);
        return committedMembersRepo.findCommittedMemberByZoneIdAndGroupLeaderAndGroupMember(zone_id,false,false);
    }

}
