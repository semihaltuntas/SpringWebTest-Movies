package be.vdab.movies.genres;

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
@Sql("/genres.sql")
@AutoConfigureMockMvc
class GenreControllerTest {
    private final static String GENRES_TABLE = "genres";
    private final MockMvc mockMvc;
    private final JdbcClient jdbcClient;

    public GenreControllerTest(MockMvc mockMvc, JdbcClient jdbcClient) {
        this.mockMvc = mockMvc;
        this.jdbcClient = jdbcClient;
    }

    @Test
    void findtAllVindtAlleGenres() throws Exception {
        var aantalGenres = JdbcTestUtils.countRowsInTable(jdbcClient, GENRES_TABLE);
        System.out.println(aantalGenres);
        mockMvc.perform(get("/genres"))
                .andExpectAll(status().isOk(),
                        jsonPath("length()")
                                .value(aantalGenres));
    }
}