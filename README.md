<img width="100" height="100" alt="Navi Beta" src="https://github.com/user-attachments/assets/9fe1a21a-a27c-44c6-a471-ee91c82a81ee" />
<h1>Internmap</h1>


InternMap is a work-in-progress application designed to help students and recruiters connect through an interactive internship discovery experience. Built with a Spring Boot backend and an React frontend.

---

## Tech Stack

**Backend**
- Java 25 + Spring Boot 3.5
- Spring Security with JWT authentication (JJWT 0.12)
- Spring Data JPA + MySQL 9
- Flyway for database migrations
- WebSockets (STOMP over SockJS)
- Thymeleaf templating
- BCrypt password hashing
- Spring Dotenv for environment config
- Spring Boot Actuator

**Frontend**
- TypeScript React
- HTML / CSS

---

## Project Structure

```
InternMap/
├── src/main/           # Spring Boot application (Java)
├── Frontend/           # Angular application (TypeScript)
├── pom.xml             # Maven build configuration
└── mvnw / mvnw.cmd     # Maven wrapper scripts
```

---

## Getting Started

### Prerequisites

- Java 25+
- Node.js & npm
- MySQL database
- Maven (or use the included `./mvnw` wrapper)

### 1. Clone the repository

```bash
git clone https://github.com/Trofixisme/InternMap.git
cd InternMap
```

### 2. Configure the database

Create a MySQL database and set up your `.env` file in the project root:

```env
DB_URL=jdbc:mysql://localhost:3306/internmap
DB_USERNAME=your_db_user
DB_PASSWORD=your_db_password
JWT_SECRET=your_jwt_secret
```

Flyway will handle schema migrations automatically on startup.

### 3. Run the backend

```bash
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8050`.

### 4. Run the frontend

```bash
cd Frontend
npm install
npm start
```

The frontend will be available at `http://localhost:5173`.

---

## Features (WIP)

- [ ] User registration and login (JWT-secured)
- [ ] Internship listings and search
- [ ] Real-time notifications via WebSockets
- [ ] Role-based access (student / recruiter/admin)


---

## Status

🚧 **This project is currently a work in progress.** Features and documentation will be updated as development continues.

---

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you'd like to change.

---

## License

This project does not currently specify a license.

### WIP
