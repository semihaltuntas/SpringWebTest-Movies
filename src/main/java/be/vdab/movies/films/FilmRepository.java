package be.vdab.movies.films;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class FilmRepository {
    private final JdbcClient jdbcClient;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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

    Optional<Film> findAndLockById(long id) {
        var sql = """
                select id,genreId,titel, voorraad, gereserveerd, prijs
                from films
                where id = ?
                for update
                """;
        return jdbcClient.sql(sql)
                .param(id)
                .query(Film.class)
                .optional();
    }

    void updateGereserveerd(long id) {
        var sql = """
                update films
                set gereserveerd = gereserveerd + 1
                where id = ? and (voorraad - films.gereserveerd) >= 1
                """;
        if (jdbcClient.sql(sql).params(id).update() == 0) {
            logger.info("update poging van onbestaande film {}", id);
            throw new FilmNietGevondenException(id);
        }
    }
}
