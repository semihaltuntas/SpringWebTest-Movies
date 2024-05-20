package be.vdab.movies.films;

import be.vdab.movies.reservaties.ReservatieRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class FilmService {
    private final FilmRepository filmRepository;
    private final ReservatieRepository reservatieRepository;

    public FilmService(FilmRepository filmRepository, ReservatieRepository reservatieRepository) {
        this.filmRepository = filmRepository;
        this.reservatieRepository = reservatieRepository;
    }

    public List<Film> findByGenreId(long genreId) {
        return filmRepository.findByGenreId(genreId);
    }

    public Optional<Film> findByIdWithBeschikbaar(long id) {
        return filmRepository.findByIdWithBeschikbaar(id);
    }
}
