package be.vdab.movies.films;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.InstanceOfAssertFactories.LONG;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Sql({"/films.sql", "/reservaties.sql"})
@AutoConfigureMockMvc
class FilmControllerTest {
    private final static String FILMS_TABLE = "films";
    private final static String RESERVATIES_TABLE = "reservaties";
    private final static Path TEST_RESOURCES = Path.of("src/test/resources");
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

    private long idVanTest1Film() {
        var sql = """
                select id from films where titel = 'test1'
                """;
        return jdbcClient.sql(sql)
                .query(Long.class)
                .single();
    }

    @Test
    void findByIdMetEenBestaandeIdVindtDeFilm() throws Exception {
        var id = idVanTest1Film();
        System.out.println("id: " + id);
        mockMvc.perform(get("/films/{id}", id))
                .andExpectAll(status().isOk(),
                        jsonPath("id").value(id),
                        jsonPath("titel").value("test1"),
                        jsonPath("prijs").value(3.50));
    }

    @Test
    void findByIdMetEenOnbestaandeIdVindtDeFilm() throws Exception {
        mockMvc.perform(get("/films/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    void reserveerMetValidData() throws Exception {
        var jsonData = Files.readString(TEST_RESOURCES.resolve("correcteReserveer.json"));
        var id = idVanTest1Film();
        mockMvc.perform(post("/films/{id}/reservaties", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonData))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertThat(JdbcTestUtils.countRowsInTableWhere(jdbcClient, RESERVATIES_TABLE,
                "klantId=1 and filmId =" + id)).isOne();
        assertThat(JdbcTestUtils.countRowsInTableWhere(jdbcClient, FILMS_TABLE,
                "gereserveerd = 1 and id = " + id)).isOne();
    }

    @Test
    void reserveerMetInvalidFilmId() throws Exception {
        var jsonData = Files.readString(TEST_RESOURCES.resolve("correcteReserveer.json"));
        mockMvc.perform(post("/films/{id}/reservaties", Long.MAX_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonData))
                .andExpect(status().isNotFound());
    }

    @ParameterizedTest
    @ValueSource(strings = {"reserveerMetLegeKlantId.json", "reserveerMetNegatieveKlantId.json"})
    void reserveerMetVerkeerdeDatasMislukt(String bestandsnaam) throws Exception {
        var jsonData = Files.readString(TEST_RESOURCES.resolve(bestandsnaam));
        var id = idVanTest1Film();
        mockMvc.perform(post("/films/{id}/reservaties", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonData))
                .andExpect(status().isBadRequest());
    }

    private long idVanTest3Film() {
        var sql = """
                select id from films where titel = 'test3'
                """;
        return jdbcClient.sql(sql)
                .query(Long.class)
                .single();
    }

    @Test
    void reserveerMetOnvoldoendeVoorraadMislukt() throws Exception {
        var jsonData = Files.readString(TEST_RESOURCES.resolve("correcteReserveer.json"));
        mockMvc.perform(post("/films/{id}/reservaties", idVanTest3Film())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonData))
                .andExpect(status().isConflict());
    }
}