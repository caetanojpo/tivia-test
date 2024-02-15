# TIVIA TEST

<p>
Aplicação back-end utilizando Java e Spring Boot, baseada em um teste para vaga de Desenvolvedor Back-end Java pela empresa TIVIA. O presente tem como objetivo o cadastro de Beneficiários e seus respectivos Documentos, a relação entre as duas entidades é de 1 para muitos respectivamente.
</p>

### :pushpin: Features exigidos no teste

- [x]  Cadastrar um beneficiário junto com seus documentos;
- [x]  Listar todos os beneficiários cadastrados;
- [x]  Listar todos os documentos de um beneficiário a partir de seu id;
- [x]  Atualizar os dados cadastrais de um beneficiário;
- [x]  Remover um beneficiário;
- [x]  Implementar autenticação/autorização para acesso aos endpoints;
- [x]  Fornecer a documentação dos endpoints utilizando Swagger;

### :hammer: Pré-requisitos

Criar uma aplicação utilizando Java e Spring Boot que forneça uma API REST para manter o cadastro 
de beneficiários de um plano de saúde.

### 🎲 Iniciando projeto pela primeira vez

```bash
# Clone este repositório
git clone https://github.com/caetanojpo/tivia-test.git

# Inicie a aplicação com uma IDE de sua preferência

#Acesse o seguinte endereço no navegador
http://localhost:8080/swagger-ui/index.html

```

### 🛠 Detalhes Tecnicos

- Java 17
- Arquitetura baseada em Clean Architecture
- Criação de Annotations para validação de dados
- Swagger
- Banco H2
- Testes unitários
- Spring Security
- BCryptPasswordEncoder

## Desafio

Trata-se de desenvolvimento de API Rest, primordialmente baseada em duas Entidades, quais sejam Beneficiário e Documento, com os desafios elencados em tópico anterior. As entidades foram modeladas com os seguintes atributos, em uma relação de 1 para muitos na ordem que segue:

Beneficiario
- id;
- nome;
- telefone;
- dataNascimento;
- dataInclusao;
- dataAtualizacao.

Documento
- id;
- tipoDocumento;
- descricao;
- dataInclusao;
- dataAtualizacao.


## Possibilidades para resolver o desafio

<p>Para o desenvolvimento foi escolhida a utilização de arquitetura baseada em Clean Architecture, com o intuito de fazer o uso de boas práticas de programação e deixar o core da aplicação isolado, deixando o uso de frameworks na camada de infraestrutura.</p>
<p>A criação de um novo Beneficiário pode, ou não, ser realizada em conjunto com a sua lista de Documentos, caso o Beneficiario seja cadastrado sem documentos há um endpoint especifico para o cadastro e vinculação de Documentos a um Beneficiario especifico.</p>
<p>Dada a opção de aplicar o sistema de autorização/autenticação no projeto, tomei a liberdade de modelar uma entidade User para lidar com toda a lógica de autenticação.</p>
<p>A documentação da aplicação está disponível no swagger e, também, disponilizei alguns registros em cada tabela, principalmente na User, para facilitar a obtenção de um Bearer Token.</p>


```
POST localhost:8080/api/users/login

{
    "email":"caetanojpo@gmail.com",
    "password": "1234"
}
```


## SOLID, CLEAN ARCH E BOAS PRATICAS DE PROGRAMAÇÃO.
<p>
Para o desenvolvimento do projeto tentei fazer ao máximo o uso de boas práticas. 
A nível de código podemos ver a utilização de alguns princípios do SOLID. 
O single responsibility principle pode ser observado na implementação de classes
coesas, pequenas, e que possuem um unico objetivo para a sua existência, talvez o 
maior exemplo desse principio seja que para cada validação existe uma classe. O
open closed principle pode ser visto na ValidatorsChainBeans, onde para adicionar
um novo tipo de validação, não precisamos nos preocupar com as demais classes,
nossa unica tarefa é criar um novo bean e adicioná-lo na corrente. O Dependency Inversion Principle
pode ser encontrado por exemplo na implementação do repositório, a interface inicial se
encontra na camada de domain, esta camada não pode enxergar a camada de infra, e nesse caso
para que a camada de app tenha acesso a camada de infra, utilizamos o princípio de inversão
de dependência. Por ultimo podemos observar a utilização do Interface Segregation Principle,
pois temos interfaces coesas, e quando alguma classe a implementa, fazemos uso de todos
os métodos disponíveis.
</p>

<p>
O projeto possui algumas características de arquitetura limpa, ele é dividido
nas seguintes camadas:
</p>

- **DOMAIN**: O coração da aplicação, onde estão contidos modelos, repositorio e exceptions gerais.
- **INFRA**: Camada que possui a responsabilidade de conversar com o mundo externo, aqui temos os
controllers, implementação da database, assim como as configurações do projeto e definições de beans.
- **USECASE**: É a responsável por conter as regras crucias de negocio,
são aquelas regras que sempre existiram na empresa e eram executadas de forma manual.


## PROPOSTAS DE MELHORIA

- Construir a aplicação em microsserviços, o que possibilita a utilização de um Broker, como RabbitMQ, para lidar com as trocas de mensagens entre os serviços,
isso melhoraria a estruturação e manutenção de todo o sistema.
- Migrar a utilização de bancos e outras ferramentas para o Docker.
- A depender da composição e necessidade do projeto, poderia se estudar a utilização de um banco NoSQL, como MongoDB,
a fim de trazer flexibilidade e dinamismo para a estruturação dos documentos.
- Utilização do redis para se trabalhar com cache e evitar que o usuário envie a mesma senha por diversas vezes,
derrubando servidor.

## Tecnologias

<div style="display: inline_block">
  <img align="center" alt="java" src="https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white" />
  <img align="center" alt="spring" src="https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white" />
  <img align="center" alt="swagger" src="https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white" />
</div>

### :sunglasses: Autor

Criado por João Pedro Caetano.

[![Linkedin Badge](https://img.shields.io/badge/-Leonardo-blue?style=flat-square&logo=Linkedin&logoColor=white&link=https://www.linkedin.com/in/leonardo-rodrigues-dantas/)](https://www.linkedin.com/in/caetanojpo/)
[![Gmail Badge](https://img.shields.io/badge/-leonardordnt1317@gmail.com-c14438?style=flat-square&logo=Gmail&logoColor=white&link=mailto:caetanojpo@gmail.com)](mailto:caetanojpo@gmail.com)
