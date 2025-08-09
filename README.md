# API de Gestion d'Employés

Une solution complète de gestion d'employés avec authentification JWT et opérations CRUD.

## Technologies utilisées

- Spring Boot 3.5.4
- Spring Security
- JWT (JSON Web Token)
- JPA/Hibernate
- MySQL
- Lombok
- Maven

## Configuration

### Base de données
Configurez votre base de données MySQL dans `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/employer_db?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=
```

### JWT
Les paramètres JWT sont configurés dans `application.properties`:
```properties
jwt.secret=mySecretKey123456789012345678901234567890
jwt.expiration=86400000
```

## Endpoints de l'API

### Authentification

#### Inscription
```
POST /api/auth/register
Content-Type: application/json

{
    "username": "john_doe",
    "password": "password123",
    "email": "john@example.com"
}
```

#### Connexion
```
POST /api/auth/login
Content-Type: application/json

{
    "username": "john_doe",
    "password": "password123"
}
```

Réponse:
```json
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "type": "Bearer",
    "username": "john_doe",
    "email": "john@example.com"
}
```

### Gestion des Employés

**Note**: Tous les endpoints d'employés nécessitent un token JWT dans l'en-tête:
```
Authorization: Bearer <votre_token_jwt>
```

#### Lister tous les employés
```
GET /api/employees
```

#### Obtenir un employé par ID
```
GET /api/employees/{id}
```

#### Créer un nouvel employé
```
POST /api/employees
Content-Type: application/json

{
    "nom": "Dupont",
    "prenom": "Jean",
    "poste": "Développeur",
    "email": "jean.dupont@example.com",
    "dateEmbauche": "2024-01-15"
}
```

#### Mettre à jour un employé
```
PUT /api/employees/{id}
Content-Type: application/json

{
    "nom": "Dupont",
    "prenom": "Jean",
    "poste": "Senior Développeur",
    "email": "jean.dupont@example.com",
    "dateEmbauche": "2024-01-15"
}
```

#### Supprimer un employé
```
DELETE /api/employees/{id}
```

#### Rechercher par prénom
```
GET /api/employees/search/prenom/{prenom}
```

#### Rechercher par nom
```
GET /api/employees/search/nom/{nom}
```

#### Lister par poste
```
GET /api/employees/poste/{poste}
```

## Démarrage de l'application

1. Assurez-vous que MySQL est installé et en cours d'exécution
2. Créez une base de données nommée `employer_db` (ou laissez l'application la créer automatiquement)
3. Exécutez l'application:
```bash
./mvnw spring-boot:run
```

L'application sera disponible sur `http://localhost:8080`

## Exemples d'utilisation

### 1. Inscription d'un utilisateur
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123",
    "email": "admin@example.com"
  }'
```

### 2. Connexion
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123"
  }'
```

### 3. Créer un employé (avec token)
```bash
curl -X POST http://localhost:8080/api/employees \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "nom": "Martin",
    "prenom": "Marie",
    "poste": "Chef de projet",
    "email": "marie.martin@example.com",
    "dateEmbauche": "2024-02-01"
  }'
```

## Structure du projet

```
src/main/java/com/icprecrutement/employer/
├── config/
│   ├── GlobalExceptionHandler.java
│   ├── JwtAuthenticationFilter.java
│   └── SecurityConfig.java
├── controller/
│   ├── AuthController.java
│   └── EmployeeController.java
├── dto/
│   ├── JwtResponse.java
│   ├── LoginRequest.java
│   └── RegisterRequest.java
├── entity/
│   ├── Employee.java
│   ├── Role.java
│   └── User.java
├── repository/
│   ├── EmployeeRepository.java
│   └── UserRepository.java
├── service/
│   ├── EmployeeService.java
│   ├── JwtService.java
│   └── UserDetailsServiceImpl.java
└── EmployerApplication.java
```

## Fonctionnalités

- ✅ Authentification JWT
- ✅ Inscription et connexion des utilisateurs
- ✅ CRUD complet pour les employés
- ✅ Recherche par nom/prénom
- ✅ Filtrage par département
- ✅ Validation des données
- ✅ Gestion des erreurs
- ✅ Sécurité des endpoints