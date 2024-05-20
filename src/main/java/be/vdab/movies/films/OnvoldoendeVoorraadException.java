package be.vdab.movies.films;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class OnvoldoendeVoorraadException extends RuntimeException{
    public OnvoldoendeVoorraadException() {
        super("Onvoldoende voorraad!!!");
    }
}
