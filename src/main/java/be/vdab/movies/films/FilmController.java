package be.vdab.movies.films;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("films")
public class FilmController {
    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    private record IdGenreIdTitel(long id, int genreId, String titel) {
        IdGenreIdTitel(Film film) {
            this(film.getId(), film.getGenreId(), film.getTitel());
        }
    }

    private record IdTitelVoorraadGereserveerdMetBeschikbaar(long id, String titel, int voorraad, int gereserveerd,
                                                             BigDecimal prijs, int beschikbaar) {
        IdTitelVoorraadGereserveerdMetBeschikbaar(Film film) {
            this(film.getId(), film.getTitel(), film.getVoorraad(), film.getGereserveerd(),
                    film.getPrijs(),film.getVoorraad() - film.getGereserveerd());
        }
    }

    @GetMapping(params = "genreId")
    List<IdGenreIdTitel> findByGenreId(long genreId) {
        return filmService.findByGenreId(genreId)
                .stream()
                .map(film -> new IdGenreIdTitel(film))
                .toList();
    }

    @GetMapping("{id}")
    IdTitelVoorraadGereserveerdMetBeschikbaar findByIdWithBeschikbaar(@PathVariable long id) {
        return filmService.findByIdWithBeschikbaar(id)
                .map(film -> new IdTitelVoorraadGereserveerdMetBeschikbaar(film))
                .orElseThrow(() -> new FilmNietGevondenException(id));
    }
    @PostMapping("{filmId}/reservaties")
    void reserveer(@PathVariable long filmId, @RequestBody @Valid NieuweKlantMetKlantId klantMetKlantId){
        filmService.reserveer(filmId,klantMetKlantId);
    }
}
