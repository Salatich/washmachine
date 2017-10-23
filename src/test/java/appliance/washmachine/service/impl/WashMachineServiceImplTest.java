package appliance.washmachine.service.impl;

import appliance.washmachine.domain.Appliance;
import appliance.washmachine.domain.WashMachine;
import appliance.washmachine.service.WashMachineService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;

import java.util.Random;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
@RunWith(SpringRunner.class)
@SpringBootTest
public class WashMachineServiceImplTest {

    @Autowired
    private WashMachineService washMachineService;

    @Test
    public void createWashMachine() throws Exception {
        WashMachine wm = washMachineService.createWashMachine();
        assertNotNull(washMachineService.getWashMachineById(wm.getId()));
    }

    @Test
    public void save() throws Exception {
        WashMachine wm = new WashMachine();
        wm.setState(Appliance.State.ON);
        wm.setWaterTemperature(50);
        wm.setSpin(1000);
        washMachineService.save(wm);
    }

    @Test
    public void getWashMachineById() throws Exception {
        WashMachine wm = washMachineService.createWashMachine();
        assertEquals(wm.getId(),washMachineService.getWashMachineById(wm.getId()).getId());
    }

    @Test
    public void pushSwitch() throws Exception {
        WashMachine wm = washMachineService.createWashMachine();
        WashMachine wm1 = washMachineService.pushSwitch(wm.getId());
        assertNotEquals(wm.getState(),wm1.getState());
    }

    @Test
    public void customCurrentWash() throws Exception {
        WashMachine wm = washMachineService.createWashMachine();
        Random r = new Random();
        int waterTemperature = r.nextInt(WashMachine.MAX_TEMP - WashMachine.MIN_TEMP) + WashMachine.MIN_TEMP;
        int spin = r.nextInt(WashMachine.MAX_SPIN - WashMachine.MIN_SPIN) + WashMachine.MIN_SPIN;
        wm.setState(Appliance.State.ON);
        wm.setCottonsWash();
        washMachineService.save(wm);
        washMachineService.customCurrentWash(washMachineService.getWashMachineById(wm.getId()).getId(),waterTemperature,spin);
        assertEquals(127, washMachineService.getWashMachineById(wm.getId()).getTime().toMinutes());
        assertEquals(waterTemperature, washMachineService.getWashMachineById(wm.getId()).getWaterTemperature());
        assertEquals(spin, washMachineService.getWashMachineById(wm.getId()).getSpin());
        assertEquals(WashMachine.WashCycle.COTTONS, washMachineService.getWashMachineById(wm.getId()).getWashCycle());
    }

    @Test
    public void setCottonsWash() throws Exception {
        WashMachine wm = washMachineService.createWashMachine();
        wm.setCottonsWash();
        assertEquals(127, wm.getTime().toMinutes());
        assertEquals(60, wm.getWaterTemperature());
        assertEquals(1200, wm.getSpin());
        assertEquals(WashMachine.WashCycle.COTTONS,wm.getWashCycle());
    }

    @Test
    public void setQuickWash() throws Exception {
        WashMachine wm = washMachineService.createWashMachine();
        wm.setQuickWash();
        assertEquals(30, wm.getTime().toMinutes());
        assertEquals(30, wm.getWaterTemperature());
        assertEquals(800, wm.getSpin());
        assertEquals(WashMachine.WashCycle.SUPERQUICK, wm.getWashCycle());
    }

    @Test
    public void setMixedWash() throws Exception {
        WashMachine wm = washMachineService.createWashMachine();
        wm.setMixedWash();
        assertEquals(63, wm.getTime().toMinutes());
        assertEquals(40, wm.getWaterTemperature());
        assertEquals(1200, wm.getSpin());
        assertEquals(WashMachine.WashCycle.MIXED, wm.getWashCycle());
    }

    @Test
    public void setDelicateWash() throws Exception {
        WashMachine wm = washMachineService.createWashMachine();
        wm.setDelicateWash();
        assertEquals(43, wm.getTime().toMinutes());
        assertEquals(30, wm.getWaterTemperature());
        assertEquals(600, wm.getSpin());
        assertEquals(WashMachine.WashCycle.DELICATE, wm.getWashCycle());
    }

    @Test
    public void setWoolWash() throws Exception {
        WashMachine wm = washMachineService.createWashMachine();
        wm.setWoolWash();
        assertEquals(40, wm.getTime().toMinutes());
        assertEquals(30, wm.getWaterTemperature());
        assertEquals(800, wm.getSpin());
        assertEquals(WashMachine.WashCycle.WOOL, wm.getWashCycle());
    }

}