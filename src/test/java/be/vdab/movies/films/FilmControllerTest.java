package be.vdab.movies.films;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Sql("/films.sql")
@AutoConfigureMockMvc
class FilmControllerTest {
    private final static String FILMS_TABLE = "films";
    private final MockMvc mockMvc;
    private final JdbcClient jdbcClient;

    public FilmControllerTest(MockMvc mockMvc, JdbcClient jdbcClient) {
        this.mockMvc = mockMvc;
        this.jdbcClient = jdbcClient;
    }

    @Test
    void findByGenreIdVindtDeJuisteFilms() throws Exception {
        var aantalFilmsMetGenreId = JdbcTestUtils.countRowsInTableWhere(
                jdbcClient, FILMS_TABLE, "genreId = '6'");
        System.out.println(aantalFilmsMetGenreId);
        mockMvc.perform(get("/films").param("genreId", "6"))
                .andExpectAll(status().isOk(),
                        jsonPath("length()")
                                .value(aantalFilmsMetGenreId));
    }
}