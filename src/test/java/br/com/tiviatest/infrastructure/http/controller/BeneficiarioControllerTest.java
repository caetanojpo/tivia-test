package br.com.tiviatest.infrastructure.http.controller;

import br.com.tiviatest.domain.model.Beneficiario;
import br.com.tiviatest.infrastructure.http.dto.request.BeneficiarioCreateRequest;
import br.com.tiviatest.infrastructure.http.dto.request.BeneficiarioUpdateRequest;
import br.com.tiviatest.infrastructure.http.dto.request.DocumentoCreateRequest;
import br.com.tiviatest.infrastructure.http.dto.response.BeneficiarioResponse;
import br.com.tiviatest.usecase.beneficiario.CreateBeneficiario;
import br.com.tiviatest.usecase.beneficiario.FindBeneficiario;
import br.com.tiviatest.usecase.beneficiario.RemoveBeneficiario;
import br.com.tiviatest.usecase.beneficiario.UpdateBeneficiario;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RequiredArgsConstructor
@AutoConfigureMockMvc
class BeneficiarioControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FindBeneficiario find;

    @MockBean
    private CreateBeneficiario create;

    @MockBean
    private UpdateBeneficiario update;

    @MockBean
    private RemoveBeneficiario remove;

    private static final String ROUTE = "/api/beneficiarios";
    private static final Long id = 1L;

    @Test
    @DisplayName("Find by ID: Should return HTTP 200")
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void byId() throws Exception {
        Beneficiario mockedBeneficiario = mockBeneficiario();

        when(find.byId(anyLong())).thenReturn(mockedBeneficiario);

        MvcResult result = mvc.perform(get(ROUTE + "/{id}", id)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mockedBeneficiario.getId()))
                .andExpect(jsonPath("$.nome").value(mockedBeneficiario.getNome()))
                .andExpect(jsonPath("$.telefone").value(mockedBeneficiario.getTelefone()))
                .andExpect(jsonPath("$.dataNascimento").value(mockedBeneficiario.getDataNascimento()))
                .andExpect(jsonPath("$.documentos").isArray())
                .andExpect(jsonPath("$.dataInclusao").value(mockedBeneficiario.getDataInclusao()))
                .andExpect(jsonPath("$.dataAtualizacao").value(mockedBeneficiario.getDataAtualizacao()))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();

        BeneficiarioResponse response = objectMapper.readValue(responseBody, BeneficiarioResponse.class);

        doAssertions(response, mockedBeneficiario);

        verify(find, times(1)).byId(anyLong());
    }

    @Test
    @DisplayName("Find All: Should return HTTP 200")
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void all() throws Exception {

        Beneficiario mockedBeneficiario1 = mockBeneficiario();
        Beneficiario mockedBeneficiario2 = mockBeneficiario();
        Beneficiario mockedBeneficiario3 = mockBeneficiario();

        List<Beneficiario> mockedList = new ArrayList<>();

        mockedList.add(mockedBeneficiario1);
        mockedList.add(mockedBeneficiario2);
        mockedList.add(mockedBeneficiario3);

        when(find.all()).thenReturn(mockedList);

        mvc.perform(get(ROUTE)).andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andReturn();

        verify(find, times(1)).all();
    }

    @Test
    @DisplayName("Create without Documentos List: Should return HTTP 201")
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void save_first_scenario() throws Exception {

        BeneficiarioCreateRequest request = new BeneficiarioCreateRequest("Teste", "+551111111", Date.valueOf("2000-01-01"), null);

        Beneficiario mockedBeneficiario = mockBeneficiario();

        when(create.execute(any())).thenReturn(mockedBeneficiario);

        MvcResult result = mvc.perform(post(ROUTE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(mockedBeneficiario.getId()))
                .andExpect(jsonPath("$.nome").value(mockedBeneficiario.getNome()))
                .andExpect(jsonPath("$.telefone").value(mockedBeneficiario.getTelefone()))
                .andExpect(jsonPath("$.dataNascimento").value(mockedBeneficiario.getDataNascimento()))
                .andExpect(jsonPath("$.documentos").isArray())
                .andExpect(jsonPath("$.dataInclusao").value(mockedBeneficiario.getDataInclusao()))
                .andExpect(jsonPath("$.dataAtualizacao").value(mockedBeneficiario.getDataAtualizacao()))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();

        BeneficiarioResponse response = objectMapper.readValue(responseBody, BeneficiarioResponse.class);

        doAssertions(response, mockedBeneficiario);

        verify(create, times(1)).execute(any());

    }

    @Test
    @DisplayName("Create with Documentos List: Should return HTTP 201")
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void save_second_scenario() throws Exception {

        DocumentoCreateRequest documentoCreateRequest1 = new DocumentoCreateRequest("CPF", "11111111");
        DocumentoCreateRequest documentoCreateRequest2 = new DocumentoCreateRequest("CNPJ", "2222222");
        List<DocumentoCreateRequest> documentoCreateRequestList = new ArrayList<>();
        documentoCreateRequestList.add(documentoCreateRequest1);
        documentoCreateRequestList.add(documentoCreateRequest2);

        BeneficiarioCreateRequest request = new BeneficiarioCreateRequest("Teste", "+551111111", Date.valueOf("2000-01-01"), documentoCreateRequestList);

        Beneficiario mockedBeneficiario = mockBeneficiario();

        when(create.execute(any())).thenReturn(mockedBeneficiario);

        MvcResult result = mvc.perform(post(ROUTE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(mockedBeneficiario.getId()))
                .andExpect(jsonPath("$.nome").value(mockedBeneficiario.getNome()))
                .andExpect(jsonPath("$.telefone").value(mockedBeneficiario.getTelefone()))
                .andExpect(jsonPath("$.dataNascimento").value(mockedBeneficiario.getDataNascimento()))
                .andExpect(jsonPath("$.documentos").isArray())
                .andExpect(jsonPath("$.dataInclusao").value(mockedBeneficiario.getDataInclusao()))
                .andExpect(jsonPath("$.dataAtualizacao").value(mockedBeneficiario.getDataAtualizacao()))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();

        BeneficiarioResponse response = objectMapper.readValue(responseBody, BeneficiarioResponse.class);

        doAssertions(response, mockedBeneficiario);

        verify(create, times(1)).execute(any());

    }

    @Test
    @DisplayName("Update: Should return HTTP 200")
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void update() throws Exception {

        BeneficiarioUpdateRequest beneficiarioUpdateRequest = new BeneficiarioUpdateRequest("Testado", null, null);
        Beneficiario mockedBeneficiario = mockBeneficiario();

        when(update.execute(anyLong(), any())).thenReturn(mockedBeneficiario);

        MvcResult result = mvc.perform(put(ROUTE + "/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beneficiarioUpdateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mockedBeneficiario.getId()))
                .andExpect(jsonPath("$.nome").value(mockedBeneficiario.getNome()))
                .andExpect(jsonPath("$.telefone").value(mockedBeneficiario.getTelefone()))
                .andExpect(jsonPath("$.dataNascimento").value(mockedBeneficiario.getDataNascimento()))
                .andExpect(jsonPath("$.documentos").isArray())
                .andExpect(jsonPath("$.dataInclusao").value(mockedBeneficiario.getDataInclusao()))
                .andExpect(jsonPath("$.dataAtualizacao").value(mockedBeneficiario.getDataAtualizacao()))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();

        BeneficiarioResponse response = objectMapper.readValue(responseBody, BeneficiarioResponse.class);

        doAssertions(response, mockedBeneficiario);

        verify(update, times(1)).execute(anyLong(), any());
    }

    @Test
    @DisplayName("Delete: Should return HTTP 204")
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void remove() throws Exception {

        doNothing().when(remove).execute(id);

        MvcResult result = mvc.perform(delete(ROUTE + "/{id}", id))
                .andExpect(status().isNoContent())
                .andReturn();

        verify(remove, times(1)).execute(anyLong());
    }

    @ParameterizedTest
    @MethodSource("getBadRequests")
    @DisplayName("Should return HTTP 400 when the input data are invalid")
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void badRequest(MockHttpServletRequestBuilder httpMethod) throws Exception {

        var response = mvc
                .perform(httpMethod)
                .andReturn().getResponse();

        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    private static Stream<MockHttpServletRequestBuilder> getBadRequests() {
        return Stream.of(
                post(ROUTE),
                put(ROUTE + "/{id}", id)
        );
    }

    private static Beneficiario mockBeneficiario() {
        return Mockito.mock(Beneficiario.class);
    }

    private static void doAssertions(BeneficiarioResponse response, Beneficiario mockedBeneficiario) {
        assertThat(response.id()).isEqualTo(mockedBeneficiario.getId());
        assertThat(response.nome()).isEqualTo(mockedBeneficiario.getNome());
        assertThat(response.telefone()).isEqualTo(mockedBeneficiario.getTelefone());
        assertThat(response.dataNascimento()).isEqualTo(mockedBeneficiario.getDataNascimento());
        assertThat(response.documentos()).hasSize(mockedBeneficiario.getDocumentos().size());
        assertThat(response.dataInclusao()).isEqualTo(mockedBeneficiario.getDataInclusao());
        assertThat(response.dataAtualizacao()).isEqualTo(mockedBeneficiario.getDataAtualizacao());
    }
}
