package roomescape.time;

import jakarta.persistence.*;

@Entity
public class Time {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "time_value")
    private String value;

    public Time(Long id, String value) {
        this.id = id;
        this.value = value;
    }

    public Time(Long id) {
        this.id = id;
    }

    public Time(String value) {
        this.value = value;
    }

    public Time() {

    }

    public Long getId() {
        return id;
    }

    public String getTime() { return value; }

    public String getValue() {
        return value;
    }
}
