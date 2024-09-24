# Email Service

Este projeto é uma aplicação Spring Boot que oferece um serviço RESTful para envio de e-mails. Utiliza o **JavaMailSender** para funcionalidades de e-mail e é projetado para ser simples e eficiente.

## Estrutura do Projeto

```bash
com.backend.srv.email
├── controller
│   └── EmailController.java    # Controlador para os endpoints relacionados ao envio de emails
├── dto
│   └── EmailRequest.java       # Objeto de transferência de dados para as requisições de envio de email
├── service
│   └── EmailService.java       # Lógica principal do serviço de email
└── EmailApplication.java       # Classe principal para inicializar a aplicação
```

## Funcionalidades

- **Envio de e-mails via API RESTful**: Suporta o envio de e-mails utilizando SMTP.
- **Integração com JavaMail API**: Implementa o envio de e-mails utilizando o `JavaMailSender`.
- **Documentação com Swagger**: Disponível para facilitar o uso e teste dos endpoints.
- **Análise de Qualidade de Código**: Integração com SonarQube para monitorar a qualidade e segurança do código.

## Começando

### Pré-requisitos

Certifique-se de ter instalado:

- **Java 11** ou superior
- **Maven** (para gerenciamento de dependências e construção do projeto)

### Executando a Aplicação

1. Clone o repositório:
   ```bash
   git clone https://github.com/DanielFullStack/srv-email.git
   ```
2. Navegue até o diretório do projeto:
   ```bash
   cd seu-repositorio
   ```
3. Execute a aplicação usando Maven:
   ```bash
   mvn spring-boot:run
   ```

A aplicação será iniciada e estará disponível em: `http://localhost:8080`

## Endpoints da API

### Envio de E-mail

- **POST** `/api/email/send`

Corpo da requisição (JSON):

```json
{
  "to": "recipient@example.com",
  "subject": "Email Subject",
  "text": "Email Body"
}
```

### Respostas:

- **200 OK**: E-mail enviado com sucesso.
- **400 Bad Request**: Erro de validação nos campos de entrada.
- **500 Internal Server Error**: Erro ao enviar o e-mail.

## Configuração

Configure as credenciais de SMTP e outros detalhes no arquivo `application.properties` ou `application.yml`:

```properties
spring.mail.host=smtp.example.com
spring.mail.port=587
spring.mail.username=your-username
spring.mail.password=your-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

> **Nota**: Substitua `smtp.example.com` e as credenciais de e-mail pelas suas configurações.

## Testes

Execute os testes unitários com Maven:

```bash
mvn test
```

Os testes cobrem as funcionalidades principais do serviço de e-mail e garantem que a lógica de negócio está correta.

## Construído Com

- **Spring Boot** - Framework principal para a aplicação
- **JavaMail API** - Para envio de e-mails
- **Swagger** - Documentação interativa da API
- **Maven** - Ferramenta de build e gerenciamento de dependências

## Docker

### Construção e Execução com Docker

#### 1. Configurar Rede Docker (Opcional, se necessário)
Crie uma rede Docker para conectar este serviço a outros containers, como o SonarQube:

```bash
docker network create images-network
```

#### 2. Imagens Docker
Este projeto depende de outras imagens Docker que estão disponíveis no repositório [images](https://github.com/DanielFullStack/images).

#### 3. Build e Execução do Serviço

Para construir e executar o serviço em background usando Docker Compose:

```bash
docker compose up --build -d
```

O serviço será executado no modo detach (em segundo plano). Você pode verificar os logs usando:

```bash
docker logs <container_id>
```

## Documentação Swagger

A documentação Swagger para os endpoints da API está disponível em:

```
http://localhost:8080/swagger-ui/index.html
```

Você pode usar essa interface para testar os endpoints diretamente a partir do navegador.

## Análise de Código com SonarQube

Este projeto integra-se com o SonarQube para análise de qualidade e segurança do código.

### Acesso ao SonarQube

- **URL**: `http://localhost:9001`
- **Usuário**: `admin`
- **Senha**: `imagesAdmin`

Certifique-se de ter o SonarQube configurado e em execução na mesma rede Docker ou acessível para o serviço.

### SonarQube Analyze

Analyze "srv-email": sqp_31eb928d3776eb1dfb5d70e0f52fcfb5f1b25c89

Para executar a análise de qualidade do código:
```bash
mvn clean install

mvn dependency-check:aggregate -PsonarReports

mvn clean verify sonar:sonar \
  -Dsonar.projectKey=srv-email \
  -Dsonar.host.url=http://localhost:9001 \
  -Dsonar.login=sqp_31eb928d3776eb1dfb5d70e0f52fcfb5f1b25c89
```
ou

```
mvn clean verify sonar:sonar \
  -Dsonar.projectKey=srv-email \
  -Dsonar.host.url=http://localhost:9001 \
  -Dsonar.login=admin \
  -Dsonar.password=imagesAdmin
```