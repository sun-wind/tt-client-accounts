package testtask.accounts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import testtask.accounts.model.Client;
import testtask.accounts.service.ClientService;

import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author Strannica
 */
@RunWith(MockitoJUnitRunner.class)
public class ClientControllerMockTests {

    private MockMvc mockMvc;

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController controller;

    private JacksonTester<Client> jacksonTester;

    @Before
    public void init() {
        // initialize jacksonTester
        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testNotFound() throws Exception {
        mockMvc.perform(get("/client/46456435"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void testFindById() throws Exception {

        // given
        final Client client = new Client();
        client.setFirstName("Stephen");
        client.setLastName("Hawking");
        client.setId(1L);
        BDDMockito.given(clientService.findOne(1L)).willReturn(client);

        // when
        MockHttpServletResponse response = mockMvc.perform(get("/client/1"))
                .andDo(print())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8_VALUE);
        assertThat(response.getContentAsString()).isEqualTo(jacksonTester.write(client).getJson());

    }

    @Test
    public void canCreateNewClient() throws Exception {

        // given
        final Client client = new Client("Christopfer", "Nolan");
        BDDMockito.given(clientService.create(BDDMockito.any())).willAnswer((invocation) -> {
            client.setId(2L);
            return client;
        });

        // when
        MockHttpServletResponse responce = mockMvc.perform(post("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonTester.write(client).getJson()))
                .andDo(print())
                .andReturn().getResponse();

        // then
        assertThat(responce.getStatus()).isEqualTo(HttpStatus.CREATED.value());

        Client savedClient = jacksonTester.parseObject(responce.getContentAsString());
        assertThat(savedClient.getId()).isEqualTo(2L);
        assertThat(savedClient.getFirstName()).isEqualTo(client.getFirstName());
        assertThat(savedClient.getLastName()).isEqualTo(client.getLastName());
    }

    @Test
    public void canUpdateClient() throws IOException, Exception {

        // given
        Client client = new Client("Tim", "Burton");
        client.setId(3L);
        BDDMockito.given(clientService.update(client)).willReturn(client);

        // when
        MockHttpServletResponse responce = mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonTester.write(client).getJson()))
                .andDo(print())
                .andReturn().getResponse();

        // then
        assertThat(responce.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void canDeleteClient() throws Exception {
        // given
        //BDDMockito.given(clientService.delete(1L));

        // when
        MockHttpServletResponse responce = mockMvc.perform(delete("/client/1"))
                .andDo(print())
                .andReturn().getResponse();

        // then
        assertThat(responce.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
}
