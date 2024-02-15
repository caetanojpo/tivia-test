package br.com.tiviatest.infrastructure.http.controller;

import br.com.tiviatest.domain.model.User;
import br.com.tiviatest.infrastructure.database.schema.UserSchema;
import br.com.tiviatest.infrastructure.http.dto.request.UserRequest;
import br.com.tiviatest.infrastructure.http.dto.response.JWTResponse;
import br.com.tiviatest.infrastructure.http.dto.response.UserResponse;
import br.com.tiviatest.infrastructure.mapper.UserMapper;
import br.com.tiviatest.infrastructure.security.TokenService;
import br.com.tiviatest.usecase.user.CreateUser;
import br.com.tiviatest.usecase.user.FindUser;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController()
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final CreateUser create;
    private final FindUser find;
    private final AuthenticationManager manager;
    private final TokenService tokenService;

    private static final UserMapper mapper = UserMapper.INSTANCE;

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody @Valid UserRequest userData, UriComponentsBuilder uriBuilder) {

        var user = mapper.toUser(userData);
        var createdUser = create.execute(user);

        var uri = uriBuilder.path("/api/v1/users/{id}").buildAndExpand(createdUser.getId()).toUri();

        return ResponseEntity.created(uri).body(mapper.toUserResponse(createdUser));
    }

    @PostMapping("/login")
    public ResponseEntity<JWTResponse> login(@RequestBody @Valid UserRequest userData) {
        var token = new UsernamePasswordAuthenticationToken(userData.email(), userData.password());
        var authentication = manager.authenticate(token);

        var jwt = tokenService.generateToken((UserSchema) authentication.getPrincipal());

        return ResponseEntity.ok(new JWTResponse(jwt));
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserResponse> detail(@PathVariable String email) {
        var user = find.byEmail(email);
        var userResponse = mapper.toUserResponse(user);
        return ResponseEntity.ok(userResponse);
    }
}
