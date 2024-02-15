package br.com.tiviatest.infrastructure.mapper;

import br.com.tiviatest.domain.model.User;
import br.com.tiviatest.infrastructure.database.schema.UserSchema;
import br.com.tiviatest.infrastructure.http.dto.request.UserRequest;
import br.com.tiviatest.infrastructure.http.dto.response.JWTResponse;
import br.com.tiviatest.infrastructure.http.dto.response.UserResponse;
import com.auth0.jwt.JWT;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toUser(UserSchema userSchema);

    User toUser(UserDetails userDetails);

    @Mapping(target = "password", qualifiedByName = "encryptPassword")
    User toUser(UserRequest userRequest);

    UserSchema toUseSchema(User user);

    UserResponse toUserResponse(User user);

    @Named("encryptPassword")
    default String encryptPassword(String rawPassword) {
        return new BCryptPasswordEncoder().encode(rawPassword);
    }

}
