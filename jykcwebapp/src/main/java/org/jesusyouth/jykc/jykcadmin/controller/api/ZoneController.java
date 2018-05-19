package org.jesusyouth.jykc.jykcadmin.controller.api;

import org.jesusyouth.jykc.jykcadmin.dto.ZoneDTO;
import org.jesusyouth.jykc.jykcadmin.model.Zone;
import org.jesusyouth.jykc.jykcadmin.repository.ZoneRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ZoneController {

    @Autowired
    private ZoneRepo zoneRepo;

    @GetMapping("/api/zones")
    public Iterable<Zone> getZones(){
        return zoneRepo.findAll();
    }

    @GetMapping("/api/zones/details")
    public Iterable<ZoneDTO> getZonesDetails(){
        return zoneRepo.getZoneDetails();
    }

}
