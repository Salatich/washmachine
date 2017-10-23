package appliance.washmachine.controllers;

import appliance.washmachine.domain.Appliance;
import appliance.washmachine.domain.WashMachine;
import appliance.washmachine.service.WashMachineService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WashMachineService washMachineService;

    @Test
    public void createWashMachine() throws Exception {
        this.mockMvc.perform(get("/createWashMachine").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.washCycle").value(nullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.spin").value("0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.state").value("OFF"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.waterTemperature").value("0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.washingState").value("STOPPED"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startWashingDate").value(nullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.time.seconds").value("0"))
                .andExpect(jsonPath("$.*",hasSize(8)));
    }

    @Test
    public void getState() throws Exception {
        WashMachine wm = washMachineService.createWashMachine();
        Long id = wm.getId();


        this.mockMvc.perform(get("/getState/{id}", id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*",hasSize(8)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.state").value(wm.getState().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.washingState").value(wm.getWashingState().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.waterTemperature").value(wm.getWaterTemperature()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.washCycle").value(wm.getWashCycle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.spin").value(wm.getSpin()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startWashingDate").value(wm.getStartWashingDate()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.time.seconds").value(wm.getTime().getSeconds()));
    }

    @Test
    public void pushSwitch() throws Exception {
        WashMachine wm = washMachineService.createWashMachine();
        Long id = wm.getId();

        Appliance.State newState = wm.getState() == Appliance.State.ON ? Appliance.State.OFF : Appliance.State.ON;

        this.mockMvc.perform(get("/switch/{id}", id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*",hasSize(8)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.state").value(newState.toString()));
    }

    @Test
    public void setCottonsWash() throws Exception {
        WashMachine wm = washMachineService.createWashMachine();
        Long id = wm.getId();
        wm.setState(Appliance.State.ON);
        washMachineService.save(wm);

        this.mockMvc.perform(get("/{id}/setCottonsWash", id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*",hasSize(8)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.washCycle").value(WashMachine.WashCycle.COTTONS.toString()));
    }

    @Test
    public void setQuickWash() throws Exception {
        WashMachine wm = washMachineService.createWashMachine();
        Long id = wm.getId();
        wm.setState(Appliance.State.ON);
        washMachineService.save(wm);

        this.mockMvc.perform(get("/{id}/setQuickWash", id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*",hasSize(8)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.washCycle").value(WashMachine.WashCycle.SUPERQUICK.toString()));
    }

    @Test
    public void setMixedWash() throws Exception {
        WashMachine wm = washMachineService.createWashMachine();
        Long id = wm.getId();
        wm.setState(Appliance.State.ON);
        washMachineService.save(wm);

        this.mockMvc.perform(get("/{id}/setMixedWash", id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*",hasSize(8)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.washCycle").value(WashMachine.WashCycle.MIXED.toString()));
    }

    @Test
    public void setDelicateWash() throws Exception {
        WashMachine wm = washMachineService.createWashMachine();
        Long id = wm.getId();
        wm.setState(Appliance.State.ON);
        washMachineService.save(wm);

        this.mockMvc.perform(get("/{id}/setDelicateWash", id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*",hasSize(8)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.washCycle").value(WashMachine.WashCycle.DELICATE.toString()));
    }

    @Test
    public void setWoolWash() throws Exception {
        WashMachine wm = washMachineService.createWashMachine();
        Long id = wm.getId();
        wm.setState(Appliance.State.ON);
        washMachineService.save(wm);

        this.mockMvc.perform(get("/{id}/setWoolWash", id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*",hasSize(8)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.washCycle").value(WashMachine.WashCycle.WOOL.toString()));
    }

    @Test
    public void customCurrentWash() throws Exception {
        WashMachine wm = washMachineService.createWashMachine();
        Integer waterTemperature = 50;
        Integer spin = 1000;
        Long id = wm.getId();
        wm.setState(Appliance.State.ON);
        wm.setCottonsWash();
        washMachineService.save(wm);

        this.mockMvc.perform(get("/{id}/customCurrentWash?temperature={waterTemperature}&spin={spin}", id , waterTemperature, spin).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*",hasSize(8)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.waterTemperature").value(waterTemperature))
                .andExpect(MockMvcResultMatchers.jsonPath("$.spin").value(spin));
    }
}