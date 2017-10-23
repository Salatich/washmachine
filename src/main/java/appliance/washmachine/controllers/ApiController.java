package appliance.washmachine.controllers;

import appliance.washmachine.service.WashMachineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ApiController {

    private final WashMachineService washMachineService;

    public ApiController(WashMachineService washMachineService) {
        this.washMachineService = washMachineService;
    }

    @RequestMapping(path = "/createWashMachine",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> createWashMachine() {
        return new ResponseEntity<Object>(washMachineService.createWashMachine(), HttpStatus.OK);
    }

    @RequestMapping(path = "/getState/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> getState(@PathVariable("id") long id) {
        return new ResponseEntity<Object>(washMachineService.getWashMachineById(id), HttpStatus.OK);
    }

    @RequestMapping(path = "switch/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> pushSwitch(@PathVariable("id") long id) {
        return new ResponseEntity<Object>(washMachineService.pushSwitch(id), HttpStatus.OK);
    }

    @RequestMapping(path = "{id}/setCottonsWash",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> setCottonsWash(@PathVariable("id") long id) {
        return washMachineService.setCottonsWash(id);
    }

    @RequestMapping(path = "{id}/setQuickWash",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> setQuickWash(@PathVariable("id") long id) {
        return washMachineService.setQuickWash(id);
    }

    @RequestMapping(path = "{id}/setMixedWash",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> setMixedWash(@PathVariable("id") long id) {
        return washMachineService.setMixedWash(id);
    }

    @RequestMapping(path = "{id}/setDelicateWash",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> setDelicateWash(@PathVariable("id") long id) {
        return washMachineService.setDelicateWash(id);
    }

    @RequestMapping(path = "{id}/setWoolWash",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> setWoolWash(@PathVariable("id") long id) {
        return washMachineService.setWoolWash(id);
    }

    @RequestMapping(path = "{id}/customCurrentWash",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> customCurrentWash(@PathVariable ("id") long id, @RequestParam(value="temperature", required = false) Integer waterTemperature, @RequestParam (value ="spin", required = false) Integer spin) {
            return washMachineService.customCurrentWash(id, waterTemperature, spin);
    }


    @RequestMapping(path = "{id}/startAndPauseButton",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> startWashing(@PathVariable("id") long id) {
        return washMachineService.pushStartAndPauseButton(id);
    }

}
