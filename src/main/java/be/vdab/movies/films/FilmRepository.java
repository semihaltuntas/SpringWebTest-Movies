package be.vdab.movies.films;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class FilmRepository {
    private final JdbcClient jdbcClient;

    public FilmRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Film> findByGenreId(long id) {
        var sql = """
                select id,genreId,titel,voorraad,gereserveerd,prijs
                From films
                where genreId = ?
                order by titel
                """;
        return jdbcClient.sql(sql)
                .param(id)
                .query(Film.class)
                .list();
    }

    public Optional<Film> findByIdWithBeschikbaar(long id) {
        var sql = """
                select id,genreId,titel, voorraad, gereserveerd, prijs
                from films
                where id = ?
                """;
        return jdbcClient.sql(sql)
                .param(id)
                .query(Film.class)
                .optional();
    }
}
//