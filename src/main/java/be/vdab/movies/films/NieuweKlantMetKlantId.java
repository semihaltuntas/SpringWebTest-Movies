package be.vdab.movies.films;

import jakarta.validation.constraints.Positive;

public record NieuweKlantMetKlantId(@Positive long klantId) {
}
