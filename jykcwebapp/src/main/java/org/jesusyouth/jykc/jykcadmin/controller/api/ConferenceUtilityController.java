package org.jesusyouth.jykc.jykcadmin.controller.api;

import org.jesusyouth.jykc.jykcadmin.model.Forms;
import org.jesusyouth.jykc.jykcadmin.model.Map;
import org.jesusyouth.jykc.jykcadmin.model.Survey;
import org.jesusyouth.jykc.jykcadmin.repository.FormsRepo;
import org.jesusyouth.jykc.jykcadmin.repository.MapRepo;
import org.jesusyouth.jykc.jykcadmin.repository.SurveyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConferenceUtilityController {

    @Autowired
    private FormsRepo formsRepo;

    @Autowired
    private MapRepo mapRepo;

    @Autowired
    private SurveyRepo surveyRepo;

    @GetMapping("/api/forms")
    public Iterable<Forms> getForms(){
        return formsRepo.findAll();
    }

    @GetMapping("/api/maps")
    public Iterable<Map> getMaps(){
        return mapRepo.findAll();
    }

    @GetMapping("/api/surveys")
    public Iterable<Survey> getSurveys(){
        return surveyRepo.findAll();
    }

}
