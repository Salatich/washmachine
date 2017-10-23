package appliance.washmachine.service;

import appliance.washmachine.domain.WashMachine;
import org.springframework.http.ResponseEntity;

public interface WashMachineService {

    public WashMachine createWashMachine();

    public WashMachine getWashMachineById(Long id);

    public void save(WashMachine wm);

    public WashMachine pushSwitch(Long id);

    public ResponseEntity<Object> setCottonsWash(Long id);

    public ResponseEntity<Object> setQuickWash(Long id);

    public ResponseEntity<Object> setMixedWash(Long id);

    public ResponseEntity<Object> setDelicateWash(Long id);

    public ResponseEntity<Object> setWoolWash(Long id);

    public ResponseEntity<Object> customCurrentWash(Long id, Integer waterTemperature, Integer spin);

    public ResponseEntity<Object> pushStartAndPauseButton(Long id);

}
