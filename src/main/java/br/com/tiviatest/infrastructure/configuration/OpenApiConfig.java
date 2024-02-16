package br.com.tiviatest.infrastructure.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("api")
                .pathsToMatch("/api/**")
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("TIVIA TEST API")
                        .version("1")
                        .description("""
                                A API é construída em REST, centrada nas entidades Beneficiário e Documento, com o uso de boas práticas da programação. 
                                A API oferece endpoints para manipular e consultar dados dessas entidades, seguindo os princípios REST.
                                  
                                A relação 1 para muitos indica que um beneficiário pode ter vários documentos associados. O cadastro de um novo Beneficiário pode incluir ou não uma lista de Documentos, 
                                e foi dedicado um endpoint específico para o vínculo de Documentos a um Beneficiário já cadastrado. 
                                Além disso, a implementação de autenticação e autorização foi modelada através de uma entidade User, oferecendo uma camada adicional de segurança.
           
                                O design atende aos desafios específicos, proporcionando uma solução eficiente para o gerenciamento de informações, de modo que ao utilizar arquitetura Clean Architecture, 
                                o core da aplicação foi isolado, permitindo a implementação de funcionalidades de forma modular e facilitando a manutenção.
                                """)
                        .contact(new Contact()
                                .name("João Pedro Caetano")
                                .email("caetanojpo@gmail.com")))
                .components(new Components()
                        .addSecuritySchemes("bearer-key", new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));

    }
}
