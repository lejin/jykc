package org.jesusyouth.jykc.jykcadmin.controller.api;

import org.jesusyouth.jykc.jykcadmin.model.AuthUser;
import org.jesusyouth.jykc.jykcadmin.model.CommittedMember;
import org.jesusyouth.jykc.jykcadmin.model.GroupInfo;
import org.jesusyouth.jykc.jykcadmin.model.User;
import org.jesusyouth.jykc.jykcadmin.repository.CommittedMembersRepo;
import org.jesusyouth.jykc.jykcadmin.repository.GroupInfoRepo;
import org.jesusyouth.jykc.jykcadmin.repository.UsersRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class Authenticate {

    private static final String DEFAULT = "default";
    private static final Logger logger = LoggerFactory.getLogger(Authenticate.class);

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private CommittedMembersRepo committedMembersRepo;

    @Autowired
    private GroupInfoRepo groupInfoRepo;

    @GetMapping("/api/authenticate")
    private AuthUser getUserDetails(@RequestParam String email,
                                    @RequestParam String googleid,
                                    @RequestParam(required = false) String image,
                                    @RequestParam String name,
                                    @RequestParam(required = false,defaultValue = DEFAULT) String phone) {
        logger.info("inside /api/authenticate");
        logger.info("email : "+email + " google id  " + googleid+" image "+image+" name "+name +" phone "+phone);
        if(null==phone
                || "".equals(phone)
                || (null!=phone && phone.trim().toLowerCase().equals("null"))
                || " ".equals(phone)){
            phone=DEFAULT;
        }
        boolean vip = false;
        User specialUser = usersRepo.findFirstByEmailLike(email);

        if (null != specialUser && specialUser.getRole().contains("web_admin")) {
            vip = true;
        }

        User user;
        if (vip) {
            user = usersRepo.findFirstByPhoneLike(phone);
            user.setVip(true);
        } else {
            user = usersRepo.findFirstByEmailLikeOrPhoneLike(email, phone);
        }
        if (null == user) {
            CommittedMember committedMember = committedMembersRepo.findFirstCommittedMemberByEmailLikeOrPhoneLike(email, phone);
            if (null != committedMember) {
//                committed member login first time
                User newUser = new User();
                if(!DEFAULT.equals(phone)){
                    newUser.setPhone(phone);
                }
                newUser.setUserId(committedMember.getId());
                newUser.setZone(committedMember.getZoneId());
                newUser.setRole("member");
                newUser.setEmail(email);
                newUser.setImage(image);
                newUser.setName(committedMember.getName());
                newUser.setUid(googleid);
                newUser.setVip(committedMember.isVip());
                usersRepo.save(newUser);
                return convertFromUser(newUser);
            }
        } else {
            //retuning user
            return convertFromUser(user);
        }

        //        normal user login first time

        User newUser = new User();
        if(!DEFAULT.equals(phone)){
            newUser.setPhone(phone);
        }
        newUser.setRole("user");
        newUser.setEmail(email);
        newUser.setImage(image);
        newUser.setName(name);
        newUser.setUid(googleid);
        usersRepo.save(newUser);
        return convertFromUser(newUser);
    }

    private AuthUser convertFromUser(User user) {
        AuthUser authUser = new AuthUser();
        authUser.setAuthid(user.getAuthId());
        authUser.setApproved(user.isApproved());
        authUser.setRole(user.getRole());
        authUser.setZone(user.getZone());
        authUser.setUserid(user.getUserId());
        authUser.setVip(user.isVip());
        if(null!=user.getRole() && user.getRole().contains("group_leader")){
            GroupInfo groupInfo=groupInfoRepo.findByGroupLeaderEquals(user.getUserId());
            if (null!=groupInfo){
                authUser.setGroupId(groupInfo.getGid());
                authUser.setGroupInfo(groupInfo);
            }
        }
        return authUser;
    }

}
