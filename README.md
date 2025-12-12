# AI Email Assistant Backend

A Spring Boot-based backend service that powers an AI-driven email assistant. This application helps generate intelligent email responses, manage email workflows, and provide automated email assistance using AI capabilities.

## 🛠️ Tech Stack

<div align="center">

### Backend Framework
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/Apache_Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)

### AI & APIs
![OpenAI](https://img.shields.io/badge/OpenAI-412991?style=for-the-badge&logo=openai&logoColor=white)
![REST API](https://img.shields.io/badge/REST_API-009688?style=for-the-badge&logo=fastapi&logoColor=white)

### Database & Tools
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)

### Development Tools
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ_IDEA-000000?style=for-the-badge&logo=intellij-idea&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)

</div>

## 🚀 Features

- **AI-Powered Email Generation**: Generate professional email responses using AI
- **Multiple Tone Support**: Support for various email tones (Professional, Casual, Friendly, etc.)
- **RESTful API**: Clean and well-documented REST endpoints
- **Spring Boot Framework**: Built on robust Spring Boot architecture
- **Maven Build System**: Easy dependency management and build process

## 📋 Prerequisites

Before you begin, ensure you have the following installed:

- **Java JDK 17+** (or compatible version)
- **Maven 3.6+**
- **Git**
- An **AI API Key** (e.g., OpenAI, Google Gemini, or similar)

## 🛠️ Installation

### 1. Clone the Repository

```bash
git clone https://github.com/arindamdandapat11/Ai_email_assistant_backend.git
cd Ai_email_assistant_backend
```

### 2. Configure Environment Variables

Create a `.env` file in the project root or configure `application.properties`:

```properties
# AI API Configuration
AI_API_KEY=your_api_key_here
AI_MODEL=gpt-4  # or your preferred model

# Server Configuration
server.port=8080

# CORS Configuration (adjust for your frontend URL)
cors.allowed.origins=http://localhost:3000
```

### 3. Install Dependencies

Using Maven Wrapper (recommended):

```bash
./mvnw clean install
```

Or using Maven directly:

```bash
mvn clean install
```

## 🚦 Running the Application

### Development Mode

```bash
./mvnw spring-boot:run
```

Or:

```bash
mvn spring-boot:run
```

The server will start on `http://localhost:8080` by default.

### Production Build

```bash
./mvnw clean package
java -jar target/ai-email-assistant-backend-*.jar
```

## 📡 API Endpoints

### Generate Email Response

**POST** `/api/email/generate`

Generates an AI-powered email response based on the input.

**Request Body:**
```json
{
  "originalEmail": "The email content to respond to",
  "tone": "professional",
  "context": "Optional additional context"
}
```

**Response:**
```json
{
  "generatedResponse": "AI-generated email response",
  "tone": "professional",
  "timestamp": "2024-12-12T10:30:00Z"
}
```

### Health Check

**GET** `/api/health`

Returns the health status of the application.

## 🏗️ Project Structure

```
Ai_email_assistant_backend/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── emailassistant/
│   │   │           ├── controller/    # REST controllers
│   │   │           ├── service/       # Business logic
│   │   │           ├── model/         # Data models
│   │   │           ├── config/        # Configuration classes
│   │   │           └── util/          # Utility classes
│   │   └── resources/
│   │       ├── application.properties
│   │       └── static/
│   └── test/
│       └── java/                       # Unit tests
├── .mvn/
├── pom.xml                             # Maven configuration
├── .env                                # Environment variables
├── .gitignore
└── README.md
```

## 🎯 Architecture

<div align="center">

```mermaid
graph TB
    A[Frontend Client] -->|HTTP/REST| B[Spring Boot Backend]
    B -->|API Call| C[AI Service OpenAI/Gemini]
    B -->|Query/Store| D[Database MySQL]
    B -->|Cache| E[Redis Optional]
    
    subgraph Backend Services
        B --> F[Email Controller]
        F --> G[Email Service]
        G --> H[AI Integration Service]
        G --> I[Email Repository]
    end
    
    style A fill:#61dafb,stroke:#333,stroke-width:2px
    style B fill:#6db33f,stroke:#333,stroke-width:2px
    style C fill:#412991,stroke:#333,stroke-width:2px
    style D fill:#4479a1,stroke:#333,stroke-width:2px
```

</div>

## 🔧 Configuration

### Application Properties

Edit `src/main/resources/application.properties`:

```properties
# Application Name
spring.application.name=ai-email-assistant-backend

# Server Port
server.port=8080

# AI Service Configuration
ai.service.endpoint=https://api.openai.com/v1
ai.service.model=gpt-4
ai.service.max-tokens=1000
ai.service.temperature=0.7

# CORS Configuration
spring.web.cors.allowed-origins=http://localhost:3000,https://yourdomain.com
spring.web.cors.allowed-methods=GET,POST,PUT,DELETE,OPTIONS
spring.web.cors.allowed-headers=*
```

## 🧪 Testing

Run unit tests:

```bash
./mvnw test
```

Run tests with coverage:

```bash
./mvnw test jacoco:report
```

## 🐳 Docker Deployment

### Build Docker Image

```dockerfile
# Dockerfile example
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

Build and run:

```bash
docker build -t ai-email-assistant-backend .
docker run -p 8080:8080 --env-file .env ai-email-assistant-backend
```

## 🌐 Integration with Frontend

This backend is designed to work with a frontend application. Configure CORS settings to allow your frontend domain:

```java
@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("http://localhost:3000")
                        .allowedMethods("GET", "POST", "PUT", "DELETE");
            }
        };
    }
}
```

## 🔐 Security Considerations

- **API Keys**: Never commit API keys to version control
- **Environment Variables**: Use `.env` file or environment variables for sensitive data
- **HTTPS**: Use HTTPS in production
- **Rate Limiting**: Implement rate limiting to prevent abuse
- **Input Validation**: Always validate and sanitize user inputs

## 📝 Environment Variables

| Variable | Description | Required | Default |
|----------|-------------|----------|---------|
| `AI_API_KEY` | API key for AI service | Yes | - |
| `AI_MODEL` | AI model to use | No | gpt-4 |
| `SERVER_PORT` | Server port | No | 8080 |
| `CORS_ORIGINS` | Allowed CORS origins | No | * |

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📜 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👤 Author

**Arindam Dandapat**
- GitHub: [@arindamdandapat11](https://github.com/arindamdandapat11)

## 🐛 Troubleshooting

### Common Issues

**Issue**: Application fails to start
- **Solution**: Ensure Java 17+ is installed and JAVA_HOME is set correctly

**Issue**: AI API calls failing
- **Solution**: Verify your API key is correct and has sufficient credits/quota

**Issue**: CORS errors
- **Solution**: Check that your frontend URL is added to allowed origins

**Issue**: Port already in use
- **Solution**: Change the port in `application.properties` or stop the conflicting service

## 📚 Additional Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Maven Documentation](https://maven.apache.org/guides/)
- [OpenAI API Documentation](https://platform.openai.com/docs)

## 🗺️ Roadmap

- [ ] Add email scheduling functionality
- [ ] Implement email template management
- [ ] Add support for multiple AI providers
- [ ] Implement caching for frequently used responses
- [ ] Add email analytics and insights
- [ ] Support for attachments processing

## 💬 Support

For support, email arindamdandapat11@example.com or open an issue in the GitHub repository.

---

**Note**: This is a backend service and requires a corresponding frontend application to provide a complete user interface. Make sure to check the frontend repository for the complete solution.
