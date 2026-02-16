<div align="center">

# 🏦 Legacy Sync Cloud - Integração Bancária Automática
</div>

>Desenvolvido por **DavidABx**
*Estudante de GTI (Gestão da Tecnologia da Informação) e Desenvolvedor Back-end.*

---

## 📝 Sobre o Projeto
O **Legacy Sync Cloud** é um microsserviço de alta performance focado na modernização de fluxos bancários legados. O sistema atua como uma ponte entre servidores antigos (**FTP**) e infraestruturas modernas em nuvem (**AWS**). 

Este projeto foi o desafio técnico para a **SIS Innov & Tech**, simulando um ambiente real de processamento batch e mensageria assíncrona.

## 🛠️ Stack Tecnológica
* **Linguagem:** Java 17 (LTS)
* **Framework:** Spring Boot 3.2.3
* **BD (banco de dados):** PostgreSQL 15 (via Docker)
* **Cloud & Mensageria:** AWS SQS (Serviço de Fila Simples) via LocalStack
* **Integração:** FTP (File Transfer Protocol) com Apache Commons Net
* **Persistência:** Spring Data JPA (Java Persistence API) com Hibernate
* **Migração:** Flyway DB
* **Interface:** JSP (JavaServer Pages) com JSTL (JavaServer Pages Standard Tag Library)

## 🏗️ Arquitetura do Sistema
O sistema segue os princípios da **Clean Architecture (Arquitetura Limpa)** e o padrão **MVC (Model-View-Controller)**:

1. **Worker (LegacySyncWorker):** Robô agendado que monitora o servidor FTP.
2. **Integration Service:** Faz o download e sanitização dos arquivos CSV.
3. **Processor Service:** Valida a lógica de negócio e persiste as transações no **BD (banco de dados)**.
4. **Cloud Service:** Notifica a AWS SQS sobre o sucesso da operação.
5. **UI Layer:** Dashboard visual para auditoria das 188+ transações processadas.

---

## 🚀 Como Executar o Projeto
Siga este guia passo a passo para subir o ecossistema completo de integração:

1. **Clonar o Repositório:**
   ```bash
   git clone [https://github.com/davidabx-dev/legacy-sync-cloud.git](https://github.com/davidabx-dev/legacy-sync-cloud.git)
   cd legacy-sync-cloud
   ```

---

2. **Subir a Infraestrutura (Docker):**
   >Inicie o **BD (banco de dados)** PostgreSQL e o LocalStack (AWS SQS):
   ```bash
   docker-compose up -d
   ```

---

3. **Limpar e Empacotar (Maven):**
   >Gere o **JAR (Java Archive)** executável com o manifesto correto:
   ```bash
   .\.maven\apache-maven-3.9.6\bin\mvn clean package
   ```

---

4. **Executar a Aplicação:**
   >Inicie o motor de sincronização de forma profissional:
   ```bash
   java -jar target/legacy-sync-0.0.1-SNAPSHOT.jar
   ```

---

5. **Acessar o Relatório (Dashboard):**
   >Visualize as transações processadas em tempo real no seu navegador:
   >[http://localhost:8080/relatorio](http://localhost:8080/relatorio)

---

6. **Auditoria de Dados (CLI):**
   >Verifique os registros diretamente no container do **BD (banco de dados)**:
   ```bash
   docker exec -it legacy-sync-db psql -U root -d tqi_challenge_db -c "SELECT status, count(*) FROM tb_transaction GROUP BY status;"
   ```

---

7. **Executar Testes de Qualidade:**
   >Valide a lógica de negócio e as integrações:
   ```bash
   .\.maven\apache-maven-3.9.6\bin\mvn test
   ```

---

8. **Monitoramento em Background (Opcional):**
   >Para rodar o processo sem travar o terminal (Windows PowerShell):
   ```bash
   $env:JAVA_HOME = "C:\Program Files\Microsoft\jdk-17.0.18.8-hotspot"
   $env:Path += ";$env:JAVA_HOME\bin"
   java -jar target/legacy-sync-0.0.1-SNAPSHOT.jar
   ```

---

Desenvolvido com ☕ e persistência por DavidABx
