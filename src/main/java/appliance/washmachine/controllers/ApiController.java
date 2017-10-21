package appliance.washmachine.controllers;

import appliance.washmachine.domain.*;
import appliance.washmachine.domain.Appliance.State;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ApiController {

    private final ApplianceRepository applianceRepository;


    public ApiController(ApplianceRepository applianceRepository) {
        this.applianceRepository = applianceRepository;
    }


    @RequestMapping(path = "/createWashMachine",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String createWashMachine(){
            WashMachine wm = new WashMachine();
            applianceRepository.save(wm);
            return "new washing machine has been created";
    }

    @RequestMapping(path = "/createOven",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String createOven(){
        Oven oven = new Oven();
        applianceRepository.save(oven);
        return "new oven has been created";
    }


    @RequestMapping(path = "getState/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getState(@PathVariable("id") long id) {
            Appliance appliance = applianceRepository.findOne(id);
            return appliance.getState().toString();
    }


    @RequestMapping(path = "switch/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String pushSwitch (@PathVariable("id") long id) {
        Appliance appliance = applianceRepository.findOne(id);
        State newState = appliance.getState() == State.ON ? State.OFF : State.ON;
        appliance.setState(newState);
        applianceRepository.save(appliance);
        return appliance.getState().toString();
    }


}
