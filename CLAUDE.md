# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

### Build Commands
```bash
# Clean and package the application (skip tests) - most common
mvn clean package -Dmaven.test.skip=true

# Build with tests
mvn clean package

# Build specific module
mvn clean package -pl aibox-server -am
```

### Run Commands
```bash
# Run using Spring Boot plugin (development)
mvn spring-boot:run -pl aibox-server -Dspring.profiles.active=local

# Run packaged JAR
java -jar aibox-server/target/aibox-server.jar --spring.profiles.active=local
```

### Test Commands
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=ClassName
```

### Docker Infrastructure
```bash
# Start MySQL and Redis for local development
cd script/docker && docker-compose up -d
```

## Architecture Overview

### Project Structure
AIBox is a multi-module Maven project based on Spring Boot 3.4.5 and Java 21:

- **aibox-dependencies**: Dependency version management (BOM)
- **aibox-framework**: Core framework with Spring Boot starters
  - Common utilities, security, Redis, MyBatis, web, WebSocket starters
  - Data permissions, protection, monitoring capabilities
- **aibox-module-system**: System management (users, roles, permissions, tenants)
- **aibox-module-infra**: Infrastructure (file upload, code generation, API logs)
- **aibox-module-biz**: Business modules (AI, workflow, payments, CRM, ERP)
- **aibox-server**: Main application entry point

### Key Architectural Patterns

1. **Multi-Tenant SaaS Architecture**
   - Tenant isolation at data level via `tenant_id` field
   - Configurable tenant management through system module

2. **Security Architecture**
   - Spring Security with token-based authentication (stored in Redis)
   - Method-level authorization with `@PreAuthorize`
   - Support for OAuth2, WeChat, DingTalk authentication

3. **Database Architecture**
   - MyBatis Plus for ORM with code generation support
   - Multi-datasource support (master-slave configuration)
   - Compatible with MySQL, PostgreSQL, Oracle, DM, TiDB, etc.
   - Base entity fields: id, creator, create_time, updater, update_time, deleted

4. **API Design**
   - RESTful APIs with unified response structure: `CommonResult<T>`
   - SpringDoc OpenAPI 3.0 for documentation
   - Request validation using Bean Validation

5. **Code Generation**
   - Template-based generation using Velocity
   - Generates complete CRUD: Controller, Service, Mapper, Frontend
   - Smart field type detection and component mapping

6. **Message Queue & Real-time**
   - WebSocket support with Spring WebSocket
   - MQ support: RocketMQ, RabbitMQ, Kafka, Redis

7. **Workflow Engine**
   - Flowable 7.0.0 for BPM capabilities
   - Dynamic form support
   - Visual workflow designer

### Configuration Profiles
- `local`: Local development (default)
- `dev`: Development environment
- `prod`: Production environment

Configuration files location: `aibox-server/src/main/resources/application-{profile}.yaml`

### Development Tips
- Always run with `local` profile for development
- Use code generation for CRUD operations to maintain consistency
- Follow existing package structure when adding new features
- Check `aibox-framework` for reusable components before implementing new ones
- Tenant-aware entities should extend appropriate base classes