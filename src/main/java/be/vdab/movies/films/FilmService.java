package be.vdab.movies.films;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class FilmService {
    private final FilmRepository filmRepository;

    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public List<Film> findByGenreId(long genreId) {
        return filmRepository.findByGenreId(genreId);
    }

    public Optional<Film> findByIdWithBeschikbaar(long id) {
        return filmRepository.findByIdWithBeschikbaar(id);
    }
}
