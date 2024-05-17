package be.vdab.movies.klanten;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class KlantService {
    private final KlantRepository klantRepository;

    public KlantService(KlantRepository klantRepository) {
        this.klantRepository = klantRepository;
    }
    public List<Klant> findByNaamBevat(String woord){
        return klantRepository.findByNaamBevat(woord);
    }
}
