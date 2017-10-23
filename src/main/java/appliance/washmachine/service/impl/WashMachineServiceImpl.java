package appliance.washmachine.service.impl;

import appliance.washmachine.domain.Appliance;
import appliance.washmachine.domain.WashMachine;
import appliance.washmachine.domain.WashMachineRepository;
import appliance.washmachine.service.WashMachineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class WashMachineServiceImpl implements WashMachineService {

    private final WashMachineRepository washMachineRepository;

    public WashMachineServiceImpl(WashMachineRepository washMachineRepository){
        this.washMachineRepository = washMachineRepository;
    }

    @Override
    public WashMachine createWashMachine() {
        WashMachine wm = new WashMachine();
        washMachineRepository.save(wm);
        return wm;
    }

    @Override
    public void save(WashMachine wm) {
        washMachineRepository.save(wm);
    }

    @Override
    public WashMachine getWashMachineById(Long id) {
        return washMachineRepository.findOne(id);
    }

    @Override
    public WashMachine pushSwitch(Long id) {
        WashMachine wm = washMachineRepository.findOne(id);
        if (wm.getState() == Appliance.State.ON) {
            wm.switchOff();
        } else {
            wm.switchOn();
        }
        washMachineRepository.save(wm);
        return wm;
    }

    @Override
    public ResponseEntity<Object> customCurrentWash(Long id, Integer waterTemperature, Integer spin) {
        WashMachine wm = washMachineRepository.findOne(id);
        if (wm.isOn()){
            if (wm.getWashCycle()!= null) {
                if(waterTemperature!= null) {
                    if(waterTemperature >= WashMachine.MIN_TEMP && waterTemperature <= WashMachine.MAX_TEMP)
                        wm.setWaterTemperature(waterTemperature);
                    else
                        return new ResponseEntity<Object>("Err: temperature is out of range!", HttpStatus.OK);
                }
                if(spin != null) {
                    if(spin >= WashMachine.MIN_SPIN && spin <= WashMachine.MAX_SPIN)
                        wm.setSpin(spin);
                    else
                        return new ResponseEntity<Object>("Err: spin is out of range!", HttpStatus.OK);
                }
                washMachineRepository.save(wm);
                return new ResponseEntity<Object>(wm, HttpStatus.OK);
            } else {
                return new ResponseEntity<Object>("You cant set temperature or spin without wash cycle :(",HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<Object>("Err: washing machine if off :(",HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<Object> setCottonsWash(Long id) {
        WashMachine wm = washMachineRepository.findOne(id);
        if(wm.isOn()){
            if(wm.isStopped()){
                wm.setCottonsWash();
                washMachineRepository.save(wm);
                return new ResponseEntity<Object>(wm, HttpStatus.OK);
            } else{
                return new ResponseEntity<Object>("Err: washing machine is not stopped",HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<Object>("Err: washing machine if off :(",HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<Object> setQuickWash(Long id) {
        WashMachine wm = washMachineRepository.findOne(id);
        if(wm.isOn()){
            if(wm.isStopped()){
            wm.setQuickWash();
            washMachineRepository.save(wm);
                return new ResponseEntity<Object>(wm, HttpStatus.OK);
            } else{
                return new ResponseEntity<Object>("Err: washing machine is not stopped",HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<Object>("Err: washing machine if off :(",HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<Object> setMixedWash(Long id) {
        WashMachine wm = washMachineRepository.findOne(id);
        if(wm.isOn()){
            if(wm.isStopped()){
                wm.setMixedWash();
                washMachineRepository.save(wm);
                return new ResponseEntity<Object>(wm, HttpStatus.OK);
            } else  {
                return new ResponseEntity<Object>("Err: washing machine is not stopped",HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<Object>("Err: washing machine if off :(",HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<Object> setDelicateWash(Long id) {
        WashMachine wm = washMachineRepository.findOne(id);
        if(wm.isOn()){
            if(wm.isStopped()){
                wm.setDelicateWash();
                washMachineRepository.save(wm);
                return new ResponseEntity<Object>(wm, HttpStatus.OK);
            } else  {
                return new ResponseEntity<Object>("Err: washing machine is not stopped",HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<Object>("Err: washing machine if off :(",HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<Object> setWoolWash(Long id) {
        WashMachine wm = washMachineRepository.findOne(id);
        if(wm.isOn()){
            if(wm.isStopped()){
                wm.setWoolWash();
                washMachineRepository.save(wm);
                return new ResponseEntity<Object>(wm, HttpStatus.OK);
            } else {
                return new ResponseEntity<Object>("Err: washing machine is not stopped",HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<Object>("Err: washing machine if off :(",HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<Object> pushStartAndPauseButton(Long id) {
        WashMachine wm = washMachineRepository.findOne(id);
        if (wm.isOn()){
            if (wm.isStopped()) {
                if(wm.isStartable()) {
                    wm.startWashing();
                    washMachineRepository.save(wm);
                    return new ResponseEntity<Object>(wm, HttpStatus.OK);
                } else {
                    return new ResponseEntity<Object>("Err: not enough params for start washing!",HttpStatus.OK);
                }
            } else if(wm.isPaused()){
                wm.continueWashing();
                washMachineRepository.save(wm);
                return new ResponseEntity<Object>(wm, HttpStatus.OK);
            } else {
                wm.pauseWashing();
                washMachineRepository.save(wm);
                return new ResponseEntity<Object>(wm, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<Object>("Err: washing machine is off :(", HttpStatus.OK);
        }
    }
}
