package org.jesusyouth.jykc.jykcadmin.controller.web;

import org.jesusyouth.jykc.jykcadmin.model.ReligiousPeople;
import org.jesusyouth.jykc.jykcadmin.repository.ReligiousPeopleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReligiousController {

    @Autowired
    private ReligiousPeopleRepo religiousPeopleRepo;

   @GetMapping("/religious")
    public String religious(){
       return "religious.html";
   }

   @PostMapping("/religious")
   public String addReligious(@RequestParam String name, @RequestParam String email,
                              @RequestParam String phone, @RequestParam Integer zone, @RequestParam Integer age,
                              @RequestParam String address, @RequestParam String gender,
                              @RequestParam(required = false) boolean accomadation, @RequestParam String responsibility,
                              @RequestParam String category) {

       ReligiousPeople religiousPeople=new ReligiousPeople();
       religiousPeople.setAccomadation(accomadation);
       religiousPeople.setName(name);
       religiousPeople.setMail(email);
       religiousPeople.setPhone(phone);
       religiousPeople.setAddress(address);
       religiousPeople.setGender(gender);
       religiousPeople.setAge(age);
       religiousPeople.setZone(zone);
       religiousPeople.setZoneResponsibility(responsibility);
       religiousPeople.setCategory(category);
       religiousPeopleRepo.save(religiousPeople);
       return "redirect:/success";
   }
    @GetMapping("/success")
    public String religiousSuccess(){
        return "success.html";
    }

}
