package be.vdab.movies.klanten;

public class Klant {
    private final long id;
    private final String familienaam;
    private final String voornaam;
    private final String straatNummer;
    private final int postcode;
    private final String gemeente;

    public Klant(long id, String familienaam, String voornaam, String straatNummer, int postcode, String gemeente) {
        this.id = id;
        this.familienaam = familienaam;
        this.voornaam = voornaam;
        this.straatNummer = straatNummer;
        this.postcode = postcode;
        this.gemeente = gemeente;
    }

    public long getId() {
        return id;
    }

    public String getFamilienaam() {
        return familienaam;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public String getStraatNummer() {
        return straatNummer;
    }

    public int getPostcode() {
        return postcode;
    }

    public String getGemeente() {
        return gemeente;
    }
}
