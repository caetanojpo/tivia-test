package br.com.tiviatest.infrastructure.http.controller;

import br.com.tiviatest.domain.model.User;
import br.com.tiviatest.infrastructure.database.schema.UserSchema;
import br.com.tiviatest.infrastructure.http.dto.request.UserRequest;
import br.com.tiviatest.infrastructure.http.dto.response.UserResponse;
import br.com.tiviatest.infrastructure.security.TokenService;
import br.com.tiviatest.usecase.user.CreateUser;
import br.com.tiviatest.usecase.user.FindUser;
import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RequiredArgsConstructor
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateUser create;

    @MockBean
    private FindUser find;

    @MockBean
    private AuthenticationManager manager;

    @MockBean
    private TokenService tokenService;


    private static final String ROUTE = "/api/users";
    private static final String email = "unitario@teste.com";
    private static final String password = "!@Teste1234";


    @Test
    @DisplayName("Create: Should return HTTP 201")
    void create() throws Exception {
        UserRequest userRequest = new UserRequest(email, password);

        User mockedUser = mockUser();

        when(create.execute(any())).thenReturn(mockedUser);

        MvcResult result = mvc.perform(post(ROUTE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(mockedUser.getId()))
                .andExpect(jsonPath("$.email").value(mockedUser.getEmail()))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();

        UserResponse response = objectMapper.readValue(responseBody, UserResponse.class);

        doAssertions(response, mockedUser);

        verify(create, times(1)).execute(any());
        verifyNoMoreInteractions(create);
        verifyNoInteractions(find, manager, tokenService);
    }

    @Test
    @DisplayName("Login: Should return HTTP 200")
    void login() throws Exception {
        UserRequest userRequest = new UserRequest(email, password);
        Authentication mockAuthentication = Mockito.mock(Authentication.class);
        JWT mockJwt = Mockito.mock(JWT.class);

        User mockedUser = mockUser();

        when(find.byEmail(any())).thenReturn(mockedUser);
        when(manager.authenticate(any())).thenReturn(mockAuthentication);
        doReturn(mockJwt.toString()).when(tokenService).generateToken(any(UserSchema.class));

        mvc.perform(post(ROUTE + "/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andReturn();


        verify(find, times(1)).byEmail(any());
        verify(manager, times(1)).authenticate(any());
        verify(tokenService, times(1)).generateToken((UserSchema) mockAuthentication.getPrincipal());
        verifyNoInteractions(create);
    }

    @Test
    @DisplayName("FIND BY EMAIL: Should return HTTP 200")
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void detail() throws Exception {
        User mockedUser = mockUser();

        when(find.byEmail(any())).thenReturn(mockedUser);

        MvcResult result = mvc.perform(get(ROUTE + "/{email}", email))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mockedUser.getId()))
                .andExpect(jsonPath("$.email").value(mockedUser.getEmail()))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();

        UserResponse response = objectMapper.readValue(responseBody, UserResponse.class);

        doAssertions(response, mockedUser);

        verify(find, times(1)).byEmail(any());
        verifyNoMoreInteractions(find);
        verifyNoInteractions(create, manager, tokenService);
    }

    private static User mockUser() {
        return Mockito.mock(User.class);
    }

    private static void doAssertions(UserResponse response, User mockedUser) {
        assertThat(response.id()).isEqualTo(mockedUser.getId());
        assertThat(response.email()).isEqualTo(mockedUser.getEmail());
    }
}