# Sistema de Gerenciamento de Usuários

Este projeto é uma aplicação Spring Boot desenvolvida como trabalho de conclusão das 
disciplinas de Engenharia de Software e Gestão da Qualidade na UCPel.

## 📋 Funcionalidades

- Cadastro, listagem, atualização e exclusão de usuários.
- Validação de campos com feedback visual usando Thymeleaf.
- Perfis de acesso.

## 🚀 Executando com Docker

### Pré-requisitos:
- Docker instalado na máquina.

### Instruções:

1. Clone o repositório:
   ```bash
   git clone https://github.com/LuisEdDias/projeto-v-b.git
   cd projeto-v-b
   ```
2. Construa a imagem Docker:
   ```bash
   docker build -t user-app .
   ```
3. Execute o container:
   ```bash
   docker run -p 8080:8080 user-app
   ```
   🔁 O comando -p 8080:8080 mapeia a porta interna da aplicação para a porta do seu host.


4. Acesse a aplicação através do navegador:
``
   http://localhost:8080
``


## 🛠️ Tecnologias
- Java 21
- Spring Boot
- Thymeleaf
- Bootstrap 5
- Docker

## 📚 Autor
Luís Eduardo Dias — UCPel 2025


[![LinkedIn](https://img.shields.io/badge/LinkedIn-Perfil-blue?logo=linkedin)](https://www.linkedin.com/in/luisvdias94)