package appliance.washmachine.domain;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.time.Duration;
import java.util.Date;

@Entity
@Table
public class WashMachine extends Appliance {

    public static final int MAX_SPIN = 1200;

    public static final int MIN_SPIN = 600;

    public static final int MAX_TEMP = 80;

    public static final int MIN_TEMP = 30;

    @Column
    @Getter @Setter
    private WashingState washingState = WashingState.STOPPED;

    @Column
    @Getter @Setter
    private int waterTemperature = 0;

    @Column
    @Getter @Setter
    private WashCycle washCycle = null;

    @Column
    @Getter @Setter
    private int spin = 0;

    @Column
    @Getter @Setter
    private Date startWashingDate = null;

    @Column
    @Getter @Setter
    private Duration time = Duration.ZERO;

    @JsonIgnore
    public boolean isStopped(){
        return this.washingState == WashingState.STOPPED;
    }
    @JsonIgnore
    public boolean isPaused(){
        return this.washingState == WashingState.PAUSE;
    }
    @JsonIgnore
    public boolean isOn(){
        return this.getState() == State.ON;
    }
    @JsonIgnore
    public boolean isStartable(){
        return this.waterTemperature != 0 && this.spin != 0 && !this.time.isZero() && this.washingState != WashingState.PROCESS;
    }

    public void startWashing(){
        this.startWashingDate = new Date();
        this.washingState = WashingState.PROCESS;
    }

    public void continueWashing(){
        this.washingState = WashingState.PROCESS;
    }

    public void pauseWashing(){
        this.washingState = WashingState.PAUSE;
    }

    public void switchOff(){
        super.switchOff();
        this.washingState = WashingState.STOPPED;
        this.waterTemperature = 0;
        this.washCycle = null;
        this.spin = 0;
        this.startWashingDate = null;
        this.time = Duration.ZERO;
    }

    public void setCottonsWash(){
        this.time = Duration.ofMinutes(127);
        this.waterTemperature = 60;
        this.spin = 1200;
        this.washCycle = WashCycle.COTTONS;
    }

    public void setQuickWash(){
        this.time = Duration.ofMinutes(30);
        this.waterTemperature = 30;
        this.spin = 800;
        this.washCycle = WashCycle.SUPERQUICK;
    }

    public void setMixedWash(){
        this.time = Duration.ofMinutes(63);
        this.waterTemperature = 40;
        this.spin = 1200;
        this.washCycle = WashCycle.MIXED;
    }

    public void setDelicateWash(){
        this.time = Duration.ofMinutes(43);
        this.waterTemperature = 30;
        this.spin = 600;
        this.washCycle = WashCycle.DELICATE;
    }

    public void setWoolWash(){
        this.time = Duration.ofMinutes(40);
        this.waterTemperature = 30;
        this.spin = 800;
        this.washCycle = WashCycle.WOOL;
    }

    public enum WashCycle {
        COTTONS,
        SUPERQUICK,
        MIXED,
        DELICATE,
        WOOL
    }

    public enum WashingState {
        PROCESS,
        PAUSE,
        STOPPED
    }

    @Override
    public String toString() {
        return "{ \n" +
                "id: "+this.getId()+"\n"+
                "state: "+this.getState()+"\n"+
                "washingState: "+this.washingState+"\n"+
                "waterTemperature: "+this.waterTemperature+" C \n"+
                "washCycle: "+this.washCycle+"\n"+
                "spin: "+this.spin+"\n"+
                "time: "+this.time.toMinutes()+" min \n"+
                "startWashingDate: "+this.startWashingDate+"\n"+
                "}";
    }
}
