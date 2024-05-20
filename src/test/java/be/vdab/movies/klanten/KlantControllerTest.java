package be.vdab.movies.klanten;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Sql("/klanten.sql")
@AutoConfigureMockMvc
class KlantControllerTest {
    private final static String KLANTEN_TABLE = "klanten";
    private final MockMvc mockMvc;
    private final JdbcClient jdbcClient;

    public KlantControllerTest(MockMvc mockMvc, JdbcClient jdbcClient) {
        this.mockMvc = mockMvc;
        this.jdbcClient = jdbcClient;
    }

    @Test
    void findByNaamBevatVindtDeJuisteKlanten() throws Exception {
        System.out.println("aantal bevatNaam: " + JdbcTestUtils.countRowsInTableWhere(
                jdbcClient, KLANTEN_TABLE, "familienaam like '%altuntas%'"));
        mockMvc.perform(get("/klanten")
                        .param("naamBevat", "altuntas")) //klanten?naamBevat=altuntas
                .andExpectAll(status().isOk(),
                        jsonPath("length()").value(JdbcTestUtils.countRowsInTableWhere(
                                jdbcClient, KLANTEN_TABLE, "familienaam like '%altuntas%'")));
    }
}