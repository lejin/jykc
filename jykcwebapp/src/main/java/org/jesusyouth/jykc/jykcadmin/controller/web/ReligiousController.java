package org.jesusyouth.jykc.jykcadmin.controller.web;

import org.jesusyouth.jykc.jykcadmin.Constants.ZoneNames;
import org.jesusyouth.jykc.jykcadmin.model.ReligiousPeople;
import org.jesusyouth.jykc.jykcadmin.repository.ReligiousPeopleRepo;
import org.jesusyouth.jykc.jykcadmin.repository.ZoneRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;


import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ReligiousController {

    @Autowired
    private ReligiousPeopleRepo religiousPeopleRepo;

    @GetMapping("/zonaladmin/religous_list")
    public String getAll(Model model, HttpSession httpSession) {
        Integer zone = (Integer) httpSession.getAttribute("zone");
        List<ReligiousPeople> religiousPeopleList=religiousPeopleRepo.findAllByZoneEquals(zone);
        model.addAttribute("religious_people", religiousPeopleList);
        return "religious_people_list";
    }

    @DeleteMapping("/zonaladmin/religious_people")
    public String getAll(Integer memberID) {
        religiousPeopleRepo.deleteById(memberID);
        return "redirect:/zonaladmin/religous_list";
    }

    @GetMapping("/admin/religious")
    public String getList(Model model, HttpSession httpSession) {
        Iterable<ReligiousPeople> religiousPeopleList=religiousPeopleRepo.findAll();
        religiousPeopleList.forEach(e->{
            e.setZonaName( ZoneNames.ZONE_FULL_NAME_MAP.get(e.getZone()));
        });
        model.addAttribute("religious_people", religiousPeopleList);
        return "admin_religious";
    }

    @DeleteMapping("/admin/religious_people")
    public String delete(Integer memberID) {
        religiousPeopleRepo.deleteById(memberID);
        return "redirect:/admin/religious";
    }

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
       try {
           religiousPeopleRepo.save(religiousPeople);

       }catch (Exception e){
           return "redirect:/religious_error";
       }
       return "redirect:/success";
   }
    @GetMapping("/success")
    public String religiousSuccess(){
        return "success.html";
    }

    @GetMapping("/religious_error")
    public String religiousError(){
        return "religious_error.html";
    }
}
