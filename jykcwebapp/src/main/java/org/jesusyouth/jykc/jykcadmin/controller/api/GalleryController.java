package org.jesusyouth.jykc.jykcadmin.controller.api;

import org.jesusyouth.jykc.jykcadmin.model.Gallery;
import org.jesusyouth.jykc.jykcadmin.model.Promos;
import org.jesusyouth.jykc.jykcadmin.repository.GalleryRepo;
import org.jesusyouth.jykc.jykcadmin.repository.PromoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GalleryController {

    @Autowired
    private GalleryRepo galleryRepo;

    @Autowired
    private PromoRepo promoRepo;

   @GetMapping("/api/gallery")
    public Iterable<Gallery> getGalleryImages(){
        return galleryRepo.findAll();
    }

    @GetMapping("/api/promos")
    public Iterable<Promos> getPromos(){
        return promoRepo.findAll();
    }

}
