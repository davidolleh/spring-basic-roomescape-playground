package roomescape.reservation;

import roomescape.theme.Theme;
import roomescape.time.Time;

public record ReservationRequest(
        String name,
        String date,
        Long theme,
        Long time
) {

    public Reservation toEntity() {
        return new Reservation(
                this.name,
                this.date,
                new Time(time),
                new Theme(theme)
        );
    }
}
