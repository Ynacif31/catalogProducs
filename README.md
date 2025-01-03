# ProductsCatalog

![GitHub repo size](https://img.shields.io/github/repo-size/iuricode/README-template?style=for-the-badge)  
![GitHub language count](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)  
![GitHub forks](https://img.shields.io/github/forks/iuricode/README-template?style=for-the-badge)  
![Bitbucket open issues](https://img.shields.io/bitbucket/issues/iuricode/README-template?style=for-the-badge)  
![Bitbucket open pull requests](https://img.shields.io/bitbucket/pr-raw/iuricode/README-template?style=for-the-badge)  

> **ProductsCatalog** é um sistema desenvolvido em **Java** com **Spring Boot** para gerenciar produtos, categorias e usuários. Ele inclui funcionalidades avançadas como autenticação e autorização com **Spring Security**, paginação de resultados e tratamento completo de exceções.

---

## 🛠️ Funcionalidades

- **Gestão de Produtos**: CRUD completo para produtos com suporte a paginação.  
- **Gestão de Categorias**: CRUD completo para categorias.  
- **Gestão de Usuários**: Administração de usuários com permissões e autenticação robusta.  
- **Recuperação de Senha**: Criação de tokens para recuperação e redefinição de senha.  
- **Autenticação e Autorização**: Implementação com **Spring Security** e controle baseado em perfis.  
- **Integração com PostgreSQL**: Banco de dados gerenciado via **Docker**.  
- **Testes Automatizados**: Cobertura de funcionalidades com **JunitTest**.

---

## 🎯 Ajustes e melhorias

Futuras atualizações:  

- [ ] Adicionar relatórios personalizados para administradores.  
- [ ] Implementar filtros avançados para busca de produtos.  
- [ ] Criar integração com serviços externos para envio de notificações.

---

## 💻 Pré-requisitos

Antes de começar, verifique se você atende aos seguintes requisitos:

- **Java 21** e **Maven 3.9.9+** instalados.  
- **Docker** e **Docker Compose** para gerenciamento do PostgreSQL.  
- Dependências principais:  
  - `spring-boot-starter-web`  
  - `spring-boot-starter-data-jpa`  
  - `spring-boot-starter-security`  
  - `spring-boot-starter-validation`  
  - `spring-boot-starter-test`  
  - `lombok`

---

## 🚀 Instalando ProductsCatalog

### Linux/MacOS

git clone https://github.com/seu-usuario/products-catalog.git
cd products-catalog

---

Usando ProductsCatalog

Exemplos de Endpoints

Autenticação
	•	POST /auth/recover-token: Gera um token para recuperação de senha.
	•	PUT /auth/new-password: Atualiza a senha do usuário.

Produtos
	•	GET /products: Lista todos os produtos com suporte a paginação.
	•	GET /products/{id}: Detalha um produto específico.
	•	POST /products: Adiciona um novo produto.
	•	PUT /products/{id}: Atualiza um produto existente.
	•	DELETE /products/{id}: Remove um produto.

Categorias
	•	GET /categories: Lista todas as categorias.
	•	GET /categories/{id}: Detalha uma categoria específica.
	•	POST /categories: Adiciona uma nova categoria.
	•	PUT /categories/{id}: Atualiza uma categoria existente.
	•	DELETE /categories/{id}: Remove uma categoria.

Usuários
	•	GET /users: Lista todos os usuários com suporte a paginação.
	•	GET /users/{id}: Detalha um usuário específico.
	•	POST /users: Adiciona um novo usuário.
	•	PUT /users/{id}: Atualiza os dados de um usuário.
	•	DELETE /users/{id}: Remove um usuário.

---

### Docker
Para subir o banco de dados PostgreSQL com Docker, execute:
- docker-compose up

### Testes
Para executar os testes, execute:
- mvn test
