package br.com.tiviatest.infrastructure.http.controller;

import br.com.tiviatest.domain.util.MessageUtil;
import br.com.tiviatest.infrastructure.database.schema.UserSchema;
import br.com.tiviatest.infrastructure.http.dto.request.UserRequest;
import br.com.tiviatest.infrastructure.http.dto.response.JWTResponse;
import br.com.tiviatest.infrastructure.http.dto.response.UserResponse;
import br.com.tiviatest.infrastructure.mapper.UserMapper;
import br.com.tiviatest.infrastructure.security.TokenService;
import br.com.tiviatest.usecase.user.CreateUser;
import br.com.tiviatest.usecase.user.FindUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController()
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "users")
@Slf4j
public class UserController {

    private final CreateUser create;
    private final FindUser find;
    private final AuthenticationManager manager;
    private final TokenService tokenService;

    private static final UserMapper mapper = UserMapper.INSTANCE;

    @PostMapping
    @Operation(summary = "Criar um novo usuário", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Documento encontrado com sucesso"),
    })
    public ResponseEntity<UserResponse> create(@RequestBody @Valid UserRequest userData, UriComponentsBuilder uriBuilder) {
        log.info(MessageUtil.MAP_ENT);
        var user = mapper.toUser(userData);

        log.info(MessageUtil.INSERINDO_OBJETO_BD, MessageUtil.USER_ENTIDADE_NOME);
        var createdUser = create.execute(user);

        var uri = uriBuilder.path("/api/users/{id}").buildAndExpand(createdUser.getId()).toUri();

        log.info(MessageUtil.RETORNO_HTTP);
        return ResponseEntity.created(uri).body(mapper.toUserResponse(createdUser));
    }

    @PostMapping("/login")
    @Operation(summary = "Autentica o usuário no sistema", method = "POST", description = "Devolve um Bearer Token para autenticação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário logado. Token no corpo da resposta"),
    })
    public ResponseEntity<JWTResponse> login(@RequestBody @Valid UserRequest userData) {
        log.info(MessageUtil.BUSCANDO_OBJETO_BD, MessageUtil.USER_ENTIDADE_NOME);
        find.byEmail(userData.email());
        log.info(MessageUtil.LOGANDO_USER_SISTEMA);
        var token = new UsernamePasswordAuthenticationToken(userData.email(), userData.password());
        var authentication = manager.authenticate(token);

        var jwt = tokenService.generateToken((UserSchema) authentication.getPrincipal());

        log.info(MessageUtil.RETORNO_HTTP);
        return ResponseEntity.ok(new JWTResponse(jwt));
    }

    @GetMapping("/{email}")
    @Operation(summary = "Obter um usuário por email", method = "GET", description = "Informe o email do usuario na rota para ver os seus dados detalhadamente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<UserResponse> detail(@PathVariable String email) {
        log.info(MessageUtil.BUSCANDO_OBJETO_BD, MessageUtil.USER_ENTIDADE_NOME);
        var user = find.byEmail(email);

        log.info(MessageUtil.MAP_ENT);
        var userResponse = mapper.toUserResponse(user);

        log.info(MessageUtil.RETORNO_HTTP);
        return ResponseEntity.ok(userResponse);
    }
}
