package be.vdab.movies.reservaties;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ReservatieRepository {
    private final JdbcClient jdbcClient;

    public ReservatieRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public void create(Reservatie reservatie) {
        var sql = """
                insert into reservaties(klantId,filmId,reservatie)
                values (?,?,?)
                """;
        var keyHolder = new GeneratedKeyHolder();
        jdbcClient.sql(sql)
                .params(reservatie.getKlantId(),
                        reservatie.getFilmId(),reservatie.getReservatieDatum())
                .update(keyHolder);
    }
}
