package be.vdab.movies.genres;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GenreController {
    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }
    @GetMapping ("genres")
    List<Genre> toonAlleGenres(){
        return genreService.toonAlleGenres();
    }
}
