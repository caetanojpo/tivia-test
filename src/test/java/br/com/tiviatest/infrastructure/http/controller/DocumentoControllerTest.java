package br.com.tiviatest.infrastructure.http.controller;

import br.com.tiviatest.domain.model.Documento;
import br.com.tiviatest.infrastructure.http.dto.request.DocumentoCreateRequest;
import br.com.tiviatest.infrastructure.http.dto.request.DocumentoUpdatedRequest;
import br.com.tiviatest.infrastructure.http.dto.response.DocumentoResponse;
import br.com.tiviatest.usecase.documento.CreateDocumento;
import br.com.tiviatest.usecase.documento.FindDocumento;
import br.com.tiviatest.usecase.documento.RemoveDocumento;
import br.com.tiviatest.usecase.documento.UpdateDocumento;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RequiredArgsConstructor
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class DocumentoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FindDocumento find;

    @MockBean
    private CreateDocumento create;

    @MockBean
    private UpdateDocumento update;

    @MockBean
    private RemoveDocumento remove;

    private static final String ROUTE = "/api/documentos";
    private static final Long id = 1L;

    @Test
    @DisplayName("Find by ID: Should return HTTP 200")
    void byId() throws Exception {
        Documento mockedDocumento = mockDocumento();

        when(find.byId(anyLong())).thenReturn(mockedDocumento);

        MvcResult result = mvc.perform(get(ROUTE + "/{id}", id)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mockedDocumento.getId()))
                .andExpect(jsonPath("$.tipoDocumento").value(mockedDocumento.getTipoDocumento()))
                .andExpect(jsonPath("$.descricao").value(mockedDocumento.getDescricao()))
                .andExpect(jsonPath("$.dataInclusao").value(mockedDocumento.getDataInclusao()))
                .andExpect(jsonPath("$.dataAtualizacao").value(mockedDocumento.getDataAtualizacao()))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();

        DocumentoResponse response = objectMapper.readValue(responseBody, DocumentoResponse.class);

        doAssertions(response, mockedDocumento);
    }

    @Test
    @DisplayName("Find All by Documento ID: Should return HTTP 200")
    void allByDocumentoId() throws Exception {

        Documento mockedDocumento1 = mockDocumento();
        Documento mockedDocumento2 = mockDocumento();
        Documento mockedDocumento3 = mockDocumento();

        List<Documento> mockedList = new ArrayList<>();

        mockedList.add(mockedDocumento1);
        mockedList.add(mockedDocumento2);
        mockedList.add(mockedDocumento3);

        when(find.allByBeneficiarioId(anyLong())).thenReturn(mockedList);

        mvc.perform(get(ROUTE + "/beneficiario/{id}", id)).andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andReturn();
    }

    @Test
    @DisplayName("Find All: Should return HTTP 200")
    void all() throws Exception {

        Documento mockedDocumento1 = mockDocumento();
        Documento mockedDocumento2 = mockDocumento();
        Documento mockedDocumento3 = mockDocumento();

        List<Documento> mockedList = new ArrayList<>();

        mockedList.add(mockedDocumento1);
        mockedList.add(mockedDocumento2);
        mockedList.add(mockedDocumento3);

        when(find.all()).thenReturn(mockedList);

        mvc.perform(get(ROUTE)).andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andReturn();
    }

    @Test
    @DisplayName("Create: Should return HTTP 201")
    void save() throws Exception {
        DocumentoCreateRequest request = new DocumentoCreateRequest("Teste", "551111111");

        Documento mockedDocumento = mockDocumento();

        when(create.execute(anyLong(), any())).thenReturn(mockedDocumento);

        MvcResult result = mvc.perform(post(ROUTE + "/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(mockedDocumento.getId()))
                .andExpect(jsonPath("$.tipoDocumento").value(mockedDocumento.getTipoDocumento()))
                .andExpect(jsonPath("$.descricao").value(mockedDocumento.getDescricao()))
                .andExpect(jsonPath("$.dataInclusao").value(mockedDocumento.getDataInclusao()))
                .andExpect(jsonPath("$.dataAtualizacao").value(mockedDocumento.getDataAtualizacao()))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();

        DocumentoResponse response = objectMapper.readValue(responseBody, DocumentoResponse.class);

        doAssertions(response, mockedDocumento);
    }

    @Test
    @DisplayName("Update: Should return HTTP 200")
    void update() throws Exception {
        DocumentoUpdatedRequest request = new DocumentoUpdatedRequest("Teste", "551111111");

        Documento mockedDocumento = mockDocumento();

        when(update.execute(anyLong(), any())).thenReturn(mockedDocumento);

        MvcResult result = mvc.perform(put(ROUTE + "/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mockedDocumento.getId()))
                .andExpect(jsonPath("$.tipoDocumento").value(mockedDocumento.getTipoDocumento()))
                .andExpect(jsonPath("$.descricao").value(mockedDocumento.getDescricao()))
                .andExpect(jsonPath("$.dataInclusao").value(mockedDocumento.getDataInclusao()))
                .andExpect(jsonPath("$.dataAtualizacao").value(mockedDocumento.getDataAtualizacao()))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();

        DocumentoResponse response = objectMapper.readValue(responseBody, DocumentoResponse.class);

        doAssertions(response, mockedDocumento);
    }

    @Test
    @DisplayName("Delete: Should return HTTP 204")
    void remove() throws Exception {

        doNothing().when(remove).execute(id);

        MvcResult result = mvc.perform(delete(ROUTE + "/{id}", id))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @ParameterizedTest
    @MethodSource("getBadRequests")
    @DisplayName("Should return HTTP 400 when the input data are invalid")
    void badRequest(MockHttpServletRequestBuilder httpMethod) throws Exception {
        var response = mvc
                .perform(httpMethod)
                .andReturn().getResponse();

        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    private static Stream<MockHttpServletRequestBuilder> getBadRequests() {
        return Stream.of(
                post(ROUTE + "/{id}", id),
                put(ROUTE + "/{id}", id)
        );
    }

    private static Documento mockDocumento() {
        return Mockito.mock(Documento.class);
    }

    private static void doAssertions(DocumentoResponse response, Documento mockedDocumento) {
        assertThat(response.id()).isEqualTo(mockedDocumento.getId());
        assertThat(response.tipoDocumento()).isEqualTo(mockedDocumento.getTipoDocumento());
        assertThat(response.descricao()).isEqualTo(mockedDocumento.getDescricao());
        assertThat(response.dataInclusao()).isEqualTo(mockedDocumento.getDataInclusao());
        assertThat(response.dataAtualizacao()).isEqualTo(mockedDocumento.getDataAtualizacao());
    }
}