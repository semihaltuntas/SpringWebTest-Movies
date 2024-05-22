package be.vdab.movies.films;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class FilmTest {
    @Test
    void reserveerMetVolgoendeVoorraad() {
        var film = new Film(1, 1, "test1", 4, 3, BigDecimal.TEN);
        film.reserveer();
        assertThat(film.getGereserveerd()).isEqualTo(2);
        film.reserveer();
        assertThat(film.getGereserveerd()).isEqualTo(1);
    }

    @Test
    void reserveerVerminderdtDeGereserveerd() {
        var film = new Film(1, 1, "test1", 2, 2, BigDecimal.TEN);
        assertThatExceptionOfType(OnvoldoendeVoorraadException.class).isThrownBy(
                () -> film.reserveer());
    }
}