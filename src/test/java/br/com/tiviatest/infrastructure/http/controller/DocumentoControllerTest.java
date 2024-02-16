package br.com.tiviatest.infrastructure.http.controller;

import br.com.tiviatest.domain.enums.TipoDocumento;
import br.com.tiviatest.domain.model.Documento;
import br.com.tiviatest.infrastructure.http.dto.request.DocumentoCreateRequest;
import br.com.tiviatest.infrastructure.http.dto.request.DocumentoUpdatedRequest;
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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
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
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void byId() throws Exception {
        Documento mockedDocumento = mockDocumento();

        when(find.byId(anyLong())).thenReturn(mockedDocumento);

        mvc.perform(get(ROUTE + "/{id}", id)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mockedDocumento.getId()))
                .andExpect(jsonPath("$.tipoDocumento").value(mockedDocumento.getTipoDocumento()))
                .andExpect(jsonPath("$.descricao").value(mockedDocumento.getDescricao()))
                .andExpect(jsonPath("$.dataInclusao").value(mockedDocumento.getDataInclusao()))
                .andExpect(jsonPath("$.dataAtualizacao").value(mockedDocumento.getDataAtualizacao()))
                .andReturn();


        verify(find, times(1)).byId(any());
        verifyNoMoreInteractions(find);
        verifyNoInteractions(create, remove, update);
    }

    @Test
    @DisplayName("Find All by Documento ID: Should return HTTP 200")
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
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

        verify(find, times(1)).allByBeneficiarioId(any());
        verifyNoMoreInteractions(find);
        verifyNoInteractions(create, remove, update);
    }

    @Test
    @DisplayName("Find All: Should return HTTP 200")
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
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

        verify(find, times(1)).all();
        verifyNoMoreInteractions(find);
        verifyNoInteractions(create, remove, update);
    }

    @Test
    @DisplayName("Create: Should return HTTP 201")
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void save() throws Exception {
        DocumentoCreateRequest request = new DocumentoCreateRequest(TipoDocumento.CPF, "551111111");

        Documento mockedDocumento = mockDocumento();

        when(create.execute(anyLong(), any())).thenReturn(mockedDocumento);

        mvc.perform(post(ROUTE + "/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(mockedDocumento.getId()))
                .andExpect(jsonPath("$.tipoDocumento").value(mockedDocumento.getTipoDocumento()))
                .andExpect(jsonPath("$.descricao").value(mockedDocumento.getDescricao()))
                .andExpect(jsonPath("$.dataInclusao").value(mockedDocumento.getDataInclusao()))
                .andExpect(jsonPath("$.dataAtualizacao").value(mockedDocumento.getDataAtualizacao()))
                .andReturn();

        verify(create, times(1)).execute(anyLong(), any());
        verifyNoMoreInteractions(create);
        verifyNoInteractions(remove, find, update);
    }

    @Test
    @DisplayName("Update: Should return HTTP 200")
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void update() throws Exception {
        DocumentoUpdatedRequest request = new DocumentoUpdatedRequest(TipoDocumento.CPF, "551111111");

        Documento mockedDocumento = mockDocumento();

        when(update.execute(anyLong(), any())).thenReturn(mockedDocumento);

        mvc.perform(put(ROUTE + "/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mockedDocumento.getId()))
                .andExpect(jsonPath("$.tipoDocumento").value(mockedDocumento.getTipoDocumento()))
                .andExpect(jsonPath("$.descricao").value(mockedDocumento.getDescricao()))
                .andExpect(jsonPath("$.dataInclusao").value(mockedDocumento.getDataInclusao()))
                .andExpect(jsonPath("$.dataAtualizacao").value(mockedDocumento.getDataAtualizacao()))
                .andReturn();


        verify(update, times(1)).execute(anyLong(), any());
        verifyNoMoreInteractions(update);
        verifyNoInteractions(create, find, remove);
    }

    @Test
    @DisplayName("Delete: Should return HTTP 204")
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void remove() throws Exception {
        doNothing().when(remove).execute(id);

        mvc.perform(delete(ROUTE + "/{id}", id))
                .andExpect(status().isNoContent())
                .andReturn();

        verify(remove, times(1)).execute(anyLong());
        verifyNoMoreInteractions(remove);
        verifyNoInteractions(create, find, update);
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
                post(ROUTE + "/{id}", id),
                put(ROUTE + "/{id}", id)
        );
    }

    private static Documento mockDocumento() {
        return Mockito.mock(Documento.class);
    }

}