package be.vdab.movies.genres;

public class Genre {
    private final int id;
    private final String naam;

    public int getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public Genre(int id, String naam) {
        this.id = id;
        this.naam = naam;
    }
}
