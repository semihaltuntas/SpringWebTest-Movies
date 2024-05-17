package be.vdab.movies.klanten;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class KlantRepository {
    private final JdbcClient jdbcClient;

    public KlantRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }
    List<Klant> findByNaamBevat(String woord) {
        var sql = """
                select id,familienaam,voornaam,straatNummer,postcode,gemeente
                from klanten
                where familienaam like ?
                order by familienaam
                """;
        return jdbcClient.sql(sql)
                .param("%" + woord + "%")
                .query(Klant.class)
                .list();
    }
}
