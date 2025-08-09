# 🏢 API de Gestion d'Employés

Une solution complète et moderne de gestion d'employés avec authentification JWT, opérations CRUD et historique des modifications.

## 🚀 Fonctionnalités

- ✅ **Authentification sécurisée** avec JWT
- ✅ **Gestion complète des employés** (CRUD)
- ✅ **Historique des modifications** avec MongoDB
- ✅ **Recherche avancée** par nom, prénom, poste
- ✅ **Validation des données** avec contraintes
- ✅ **Gestion des erreurs** centralisée
- ✅ **Sécurité des endpoints** avec Spring Security
- ✅ **Documentation API** complète

## 🛠️ Technologies utilisées

| Technologie | Version | Usage |
|-------------|---------|-------|
| **Spring Boot** | 3.5.4 | Framework principal |
| **Spring Security** | - | Authentification et autorisation |
| **JWT** | 0.12.3 | Gestion des tokens |
| **JPA/Hibernate** | - | ORM pour MySQL |
| **MySQL** | - | Base de données principale |
| **MongoDB** | - | Historique des modifications |
| **Lombok** | - | Réduction du code boilerplate |
| **Maven** | - | Gestionnaire de dépendances |
| **Java** | 17 | Langage de programmation |

## ⚙️ Configuration

### Prérequis
- Java 17+
- MySQL 8.0+
- MongoDB 4.4+
- Maven 3.6+

### Base de données MySQL
Configurez votre base de données MySQL dans `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:8889/employer_db?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update
```

### Base de données MongoDB
Pour l'historique des modifications :
```properties
spring.data.mongodb.uri=mongodb://localhost:27017/employee_history
```

### Configuration JWT
```properties
jwt.secret=mySecretKey123456789012345678901234567890
jwt.expiration=86400000
```

## 📋 API Endpoints

### 🔐 Authentification

| Méthode | Endpoint | Description | Auth requise |
|---------|----------|-------------|--------------|
| `POST` | `/api/auth/register` | Inscription d'un utilisateur | ❌ |
| `POST` | `/api/auth/login` | Connexion utilisateur | ❌ |

#### Inscription
```http
POST /api/auth/register
Content-Type: application/json

{
    "username": "john_doe",
    "password": "password123",
    "email": "john@example.com"
}
```

#### Connexion
```http
POST /api/auth/login
Content-Type: application/json

{
    "username": "john_doe",
    "password": "password123"
}
```

**Réponse de connexion :**
```json
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "type": "Bearer",
    "username": "john_doe",
    "email": "john@example.com"
}
```

### 👥 Gestion des Employés

> **Important :** Tous les endpoints d'employés nécessitent un token JWT :
> ```
> Authorization: Bearer <votre_token_jwt>
> ```

| Méthode | Endpoint | Description | Auth requise |
|---------|----------|-------------|--------------|
| `GET` | `/api/employees` | Lister tous les employés | ✅ |
| `GET` | `/api/employees/{id}` | Obtenir un employé par ID | ✅ |
| `POST` | `/api/employees` | Créer un nouvel employé | ✅ |
| `PUT` | `/api/employees/{id}` | Mettre à jour un employé | ✅ |
| `DELETE` | `/api/employees/{id}` | Supprimer un employé | ✅ |
| `GET` | `/api/employees/search/prenom/{prenom}` | Rechercher par prénom | ✅ |
| `GET` | `/api/employees/search/nom/{nom}` | Rechercher par nom | ✅ |
| `GET` | `/api/employees/poste/{poste}` | Lister par poste | ✅ |

#### Modèle d'employé
```json
{
    "id": 1,
    "nom": "Dupont",
    "prenom": "Jean",
    "poste": "Développeur",
    "email": "jean.dupont@example.com",
    "dateEmbauche": "2024-01-15"
}
```

#### Créer un employé
```http
POST /api/employees
Content-Type: application/json
Authorization: Bearer <token>

{
    "nom": "Dupont",
    "prenom": "Jean",
    "poste": "Développeur",
    "email": "jean.dupont@example.com",
    "dateEmbauche": "2024-01-15"
}
```

#### Mettre à jour un employé
```http
PUT /api/employees/1
Content-Type: application/json
Authorization: Bearer <token>

{
    "nom": "Dupont",
    "prenom": "Jean",
    "poste": "Senior Développeur",
    "email": "jean.dupont@example.com",
    "dateEmbauche": "2024-01-15"
}
```

### 📊 Historique des Modifications

| Méthode | Endpoint | Description | Auth requise |
|---------|----------|-------------|--------------|
| `GET` | `/api/employees/{id}/history` | Historique d'un employé | ✅ |
| `GET` | `/api/history` | Tout l'historique | ✅ |
| `GET` | `/api/history/user/{username}` | Historique par utilisateur | ✅ |

## 🚀 Installation et Démarrage

### 1. Cloner le projet
```bash
git clone <url-du-repo>
cd employer
```

### 2. Configurer les bases de données

**MySQL :**
```sql
CREATE DATABASE employer_db;
```

**MongoDB :**
```bash
# Démarrer MongoDB
mongod
```

### 3. Configurer application.properties
Adaptez les paramètres de connexion dans `src/main/resources/application.properties` :
```properties
# MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/employer_db?createDatabaseIfNotExist=true
spring.datasource.username=votre_username
spring.datasource.password=votre_password

# MongoDB
spring.data.mongodb.uri=mongodb://localhost:27017/employee_history
```

### 4. Lancer l'application
```bash
# Avec Maven Wrapper
./mvnw spring-boot:run

# Ou avec Maven installé
mvn spring-boot:run
```

### 5. Vérifier le démarrage
L'application sera disponible sur : **http://localhost:8080**

### 6. Tester l'API
```bash
# Test de santé (si endpoint disponible)
curl http://localhost:8080/actuator/health

# Ou tester l'inscription
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"test","password":"test123","email":"test@example.com"}'
```

## 💡 Exemples d'utilisation

### Workflow complet avec cURL

#### 1. Inscription d'un utilisateur
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123",
    "email": "admin@example.com"
  }'
```

#### 2. Connexion et récupération du token
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123"
  }'
```

**Réponse :**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "username": "admin",
  "email": "admin@example.com"
}
```

#### 3. Créer un employé
```bash
export JWT_TOKEN="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."

curl -X POST http://localhost:8080/api/employees \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $JWT_TOKEN" \
  -d '{
    "nom": "Martin",
    "prenom": "Marie",
    "poste": "Chef de projet",
    "email": "marie.martin@example.com",
    "dateEmbauche": "2024-02-01"
  }'
```

#### 4. Lister tous les employés
```bash
curl -X GET http://localhost:8080/api/employees \
  -H "Authorization: Bearer $JWT_TOKEN"
```

#### 5. Rechercher un employé par nom
```bash
curl -X GET http://localhost:8080/api/employees/search/nom/Martin \
  -H "Authorization: Bearer $JWT_TOKEN"
```

#### 6. Consulter l'historique d'un employé
```bash
curl -X GET http://localhost:8080/api/employees/1/history \
  -H "Authorization: Bearer $JWT_TOKEN"
```

### Utilisation avec Postman

1. **Importer la collection** (créer un fichier `employer-api.postman_collection.json`)
2. **Configurer les variables d'environnement :**
   - `base_url` : `http://localhost:8080`
   - `jwt_token` : (sera rempli automatiquement après login)
3. **Tester les endpoints** dans l'ordre : Register → Login → Employee operations

## 📁 Structure du projet

```
src/main/java/com/icprecrutement/employer/
├── 📂 config/                          # Configuration de l'application
│   ├── GlobalExceptionHandler.java     # Gestion centralisée des erreurs
│   ├── JwtAuthenticationFilter.java    # Filtre d'authentification JWT
│   └── SecurityConfig.java             # Configuration Spring Security
├── 📂 controller/                      # Contrôleurs REST
│   ├── AuthController.java             # Endpoints d'authentification
│   └── EmployeeController.java         # Endpoints de gestion des employés
├── 📂 dto/                            # Objets de transfert de données
│   ├── JwtResponse.java               # Réponse JWT
│   ├── LoginRequest.java              # Requête de connexion
│   └── RegisterRequest.java           # Requête d'inscription
├── 📂 entity/                         # Entités JPA et MongoDB
│   ├── Employee.java                  # Entité employé (MySQL)
│   ├── EmployeeHistory.java           # Historique employé (MongoDB)
│   ├── Role.java                      # Rôles utilisateur
│   └── User.java                      # Entité utilisateur
├── 📂 repository/                     # Repositories de données
│   ├── EmployeeRepository.java        # Repository employés (MySQL)
│   ├── EmployeeHistoryRepository.java # Repository historique (MongoDB)
│   └── UserRepository.java           # Repository utilisateurs
├── 📂 service/                        # Services métier
│   ├── AuthenticationService.java     # Service d'authentification
│   ├── EmployeeService.java           # Service de gestion des employés
│   ├── EmployeeHistoryService.java    # Service d'historique
│   └── JwtService.java                # Service de gestion JWT
└── EmployerApplication.java           # Classe principale Spring Boot
```

## 🔧 Architecture et Patterns

### Couches de l'application
- **Controller** : Exposition des endpoints REST
- **Service** : Logique métier et règles de gestion
- **Repository** : Accès aux données (MySQL + MongoDB)
- **Entity** : Modèles de données
- **DTO** : Objets de transfert pour l'API
- **Config** : Configuration sécurité et filtres

### Patterns utilisés
- **Repository Pattern** : Abstraction de l'accès aux données
- **Service Layer** : Séparation de la logique métier
- **DTO Pattern** : Transfert de données sécurisé
- **Filter Pattern** : Authentification JWT
- **Exception Handler** : Gestion centralisée des erreurs

## 🛡️ Sécurité

### Authentification JWT
- **Algorithme** : HS256
- **Expiration** : 24 heures (configurable)
- **Claims** : username, email, roles

### Validation des données
- **Bean Validation** : Annotations sur les entités
- **Contraintes** : Email unique, champs obligatoires
- **Messages d'erreur** : Personnalisés en français

### Endpoints sécurisés
- **Publics** : `/api/auth/**`
- **Protégés** : `/api/employees/**`, `/api/history/**`
- **CORS** : Configuré pour le développement

## 📊 Base de données

### MySQL (Données principales)
```sql
-- Table users
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    role ENUM('USER', 'ADMIN') DEFAULT 'USER'
);

-- Table employees
CREATE TABLE employees (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    poste VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    date_embauche DATE NOT NULL
);
```

### MongoDB (Historique)
```javascript
// Collection employee_history
{
  "_id": ObjectId("..."),
  "employeeId": 1,
  "action": "CREATE|UPDATE|DELETE",
  "timestamp": ISODate("2024-01-15T10:30:00Z"),
  "modifiedBy": "admin",
  "oldData": { /* données avant modification */ },
  "newData": { /* données après modification */ }
}
```

## 🧪 Tests

### Lancer les tests
```bash
# Tests unitaires
./mvnw test

# Tests avec couverture
./mvnw test jacoco:report

# Tests d'intégration
./mvnw verify
```

### Structure des tests
```
src/test/java/com/icprecrutement/employer/
├── controller/          # Tests des contrôleurs
├── service/            # Tests des services
├── repository/         # Tests des repositories
└── integration/        # Tests d'intégration
```

## 🚀 Déploiement

### Variables d'environnement
```bash
# Base de données
DB_URL=jdbc:mysql://localhost:3306/employer_db
DB_USERNAME=root
DB_PASSWORD=password

# MongoDB
MONGO_URI=mongodb://localhost:27017/employee_history

# JWT
JWT_SECRET=your-secret-key
JWT_EXPIRATION=86400000

# Profil Spring
SPRING_PROFILES_ACTIVE=prod
```

### Docker (optionnel)
```dockerfile
FROM openjdk:17-jdk-slim
COPY target/employer-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

## 📝 Logs et Monitoring

### Configuration des logs
```properties
# application.properties
logging.level.com.icprecrutement.employer=DEBUG
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} - %msg%n
logging.file.name=logs/employer.log
```

### Endpoints de monitoring
- **Health Check** : `/actuator/health`
- **Metrics** : `/actuator/metrics`
- **Info** : `/actuator/info`

## 🤝 Contribution

1. **Fork** le projet
2. **Créer** une branche feature (`git checkout -b feature/nouvelle-fonctionnalite`)
3. **Commit** les changements (`git commit -am 'Ajout nouvelle fonctionnalité'`)
4. **Push** vers la branche (`git push origin feature/nouvelle-fonctionnalite`)
5. **Créer** une Pull Request

## 📄 Licence

Ce projet est sous licence MIT. Voir le fichier `LICENSE` pour plus de détails.

## 📞 Support

Pour toute question ou problème :
- **Issues** : Créer une issue sur GitHub
- **Email** : support@icprecrutement.com
- **Documentation** : Consulter ce README

---

**Développé avec ❤️ par l'équipe ICP Recrutement**