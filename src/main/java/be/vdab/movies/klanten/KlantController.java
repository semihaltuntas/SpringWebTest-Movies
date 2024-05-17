package be.vdab.movies.klanten;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("klanten")
public class KlantController {
    private final KlantService klantService;

    public KlantController(KlantService klantService) {
        this.klantService = klantService;
    }
    @GetMapping (params = "naamBevat")
    List<Klant> findByNaamBevat(String naamBevat){
        return klantService.findByNaamBevat(naamBevat);
    }
}
