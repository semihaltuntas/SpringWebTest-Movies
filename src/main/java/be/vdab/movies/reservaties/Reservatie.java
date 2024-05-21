package be.vdab.movies.reservaties;

import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public class Reservatie {
    private final long klantId;
    private final long filmId;
    private final LocalDateTime reservatieDatum;

    public Reservatie(long klantId, long filmId, LocalDateTime reservatieDatum) {
        this.klantId = klantId;
        this.filmId = filmId;
        this.reservatieDatum = reservatieDatum;
    }

    public long getKlantId() {
        return klantId;
    }

    public long getFilmId() {
        return filmId;
    }

    public LocalDateTime getReservatieDatum() {
        return reservatieDatum;
    }
}
