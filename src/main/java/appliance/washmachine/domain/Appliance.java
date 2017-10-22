package appliance.washmachine.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Appliance {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Getter @Setter
    private long id;


    @Getter
    @Setter
    @Column
    @Enumerated(EnumType.STRING)
    private State state = State.OFF;

    public void switchOn(){
        this.state = State.ON;
    }

    public void switchOff(){
        this.state = State.OFF;
    }

    public enum State {
        ON,
        OFF
    }
}
