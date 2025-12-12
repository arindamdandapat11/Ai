# AI Email Assistant Backend

A Spring Boot-based backend service that powers an AI-driven email assistant. This application helps generate intelligent email responses, manage email workflows, and provide automated email assistance using AI capabilities.

## 🛠️ Tech Stack

<div align="center">

### Backend Framework
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/Apache_Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)

### AI & APIs
![Google Gemini](https://img.shields.io/badge/Google%20Gemini-8E75B2?style=for-the-badge&logo=googlegemini&logoColor=white)
![REST API](https://img.shields.io/badge/REST_API-009688?style=for-the-badge&logo=fastapi&logoColor=white)

### Database & Tools
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white)

### Development Tools
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ_IDEA-000000?style=for-the-badge&logo=intellij-idea&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)

</div>

## Table of Contents

- [Features](#features)
- [Technology Stack](#technology-stack)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [API Endpoints](#api-endpoints)
- [Configuration](#configuration)
- [Usage Examples](#usage-examples)
- [Project Structure](#project-structure)
- [Troubleshooting](#troubleshooting)
- [Contributing](#contributing)
- [License](#license)

## Features

- **AI-Powered Reply Generation**: Leverages Google Gemini 2.0 Flash model for intelligent responses
- **Multiple Tone Options**: Professional, Friendly, or Concise reply styles
- **Persistent Storage**: PostgreSQL database for reply history
- **Query Capabilities**: Search and filter replies by date, tone, or ID
- **RESTful API**: Clean REST endpoints for easy integration
- **Fast & Scalable**: Built with Spring Boot for enterprise-grade performance


## Prerequisites

- Java 17 or higher
- Maven 3.6+
- PostgreSQL 16+
- Google Gemini API key ([Get one here](https://aistudio.google.com/app/apikey))

## Getting Started

1. **Clone the repository**
   ```bash
   git clone https://github.com/arindamdandapat11/ai.git
   cd ai/email-assistant
   ```

2. **Install and start PostgreSQL**
   
   Create the database:
   ```sql
   CREATE DATABASE email_assistant;
   ```

3. **Run the database schema**
   ```bash
   psql -U postgres -d email_assistant -f init.sql
   ```

4. **Configure application**
   
   Edit `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/email_assistant
   spring.datasource.username=your_postgres_username
   spring.datasource.password=your_postgres_password
   ```

5. **Set Gemini API key as environment variable**
   
   **Windows (PowerShell):**
   ```powershell
   $env:GEMINI_API_KEY="your_gemini_api_key"
   ```
   
   **Mac/Linux:**
   ```bash
   export GEMINI_API_KEY=your_gemini_api_key
   ```

6. **Build and run the application**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

7. **Access the application**
   - API: http://localhost:8080
   - Health Check: http://localhost:8080/api/email/health

## API Endpoints

### Generate Email Reply
```http
POST /api/email/generate-reply
Content-Type: application/json

{
  "sender": "john@example.com",
  "subject": "Meeting Request",
  "body": "Hi, can we schedule a meeting for tomorrow at 3 PM?",
  "tone": "professional"
}
```

**Response:**
```json
{
  "id": 1,
  "sender": "john@example.com",
  "subject": "Meeting Request",
  "body": "Hi, can we schedule a meeting for tomorrow at 3 PM?",
  "tone": "professional",
  "generatedReply": "Thank you for reaching out. I would be happy to meet with you tomorrow at 3 PM. Please let me know the preferred location or if you'd like to meet virtually.",
  "createdAt": "2025-12-12T10:30:00Z",
  "updatedAt": "2025-12-12T10:30:00Z",
  "success": true,
  "message": "Reply generated successfully"
}
```

### Get All Replies
```http
GET /api/email/replies
```

### Get Reply by ID
```http
GET /api/email/replies/{id}
```

### Get Recent Replies (Last 30 Days)
```http
GET /api/email/replies/recent
```

### Health Check
```http
GET /api/email/health
```

## Configuration

### Tone Options

| Tone | Description | Use Case |
|------|-------------|----------|
| **professional** | Formal, business-appropriate language | Business emails, formal correspondence |
| **friendly** | Warm, conversational tone | Casual emails, networking |
| **concise** | Brief and to-the-point | Quick responses, busy schedules |

### Environment Variables

| Variable | Description | Required | Default |
|----------|-------------|----------|---------|
| `GEMINI_API_KEY` | Google Gemini API key | Yes | - |
| `SPRING_DATASOURCE_URL` | PostgreSQL connection URL | No | `jdbc:postgresql://localhost:5432/email_assistant` |
| `SPRING_DATASOURCE_USERNAME` | Database username | No | `postgres` |
| `SPRING_DATASOURCE_PASSWORD` | Database password | No | `Arindam123` |

## Usage Examples

### Using cURL

**Generate a Professional Reply:**
```bash
curl -X POST http://localhost:8080/api/email/generate-reply \
  -H "Content-Type: application/json" \
  -d '{
    "sender": "client@company.com",
    "subject": "Project Update",
    "body": "Can you provide an update on the project status?",
    "tone": "professional"
  }'
```

**Get All Replies:**
```bash
curl http://localhost:8080/api/email/replies
```

### Using Postman

1. **Create a new POST request**
2. **URL:** `http://localhost:8080/api/email/generate-reply`
3. **Headers:** 
   - Key: `Content-Type`
   - Value: `application/json`
4. **Body (raw JSON):**
   ```json
   {
     "sender": "test@example.com",
     "subject": "Test Email",
     "body": "This is a test email body",
     "tone": "friendly"
   }
   ```

## Project Structure

```
email-assistant/
├── src/
│   ├── main/
│   │   ├── java/com/cimbaai/emailassistant/
│   │   │   ├── EmailAssistantApplication.java    # Main application class
│   │   │   ├── config/
│   │   │   │   ├── CorsConfig.java               # CORS configuration
│   │   │   │   └── RestTemplateConfig.java       # HTTP client config
│   │   │   ├── controller/
│   │   │   │   └── EmailReplyController.java     # REST endpoints
│   │   │   ├── dto/
│   │   │   │   ├── EmailReplyRequest.java        # Request DTO
│   │   │   │   ├── EmailReplyResponse.java       # Response DTO
│   │   │   │   ├── GeminiRequest.java            # Gemini API request
│   │   │   │   └── GeminiResponse.java           # Gemini API response
│   │   │   ├── model/
│   │   │   │   └── EmailReply.java               # JPA entity
│   │   │   ├── repository/
│   │   │   │   └── EmailReplyRepository.java     # Data access layer
│   │   │   └── service/
│   │   │       ├── EmailReplyService.java        # Business logic
│   │   │       └── GeminiAIService.java          # AI integration
│   │   └── resources/
│   │       └── application.properties             # Configuration
│   └── test/                                      # Unit tests
├── init.sql                                        # Database schema
├── pom.xml                                         # Maven dependencies
└── README.md                                       # This file
```

## Troubleshooting

### Common Issues

#### 1. Database Connection Refused
**Problem:** `Connection to localhost:5432 refused`

**Solution:** Check PostgreSQL service is running:
```bash
# Windows
Get-Service -Name "*postgres*"

# Mac/Linux
sudo service postgresql status
```

#### 2. API Key Error 500
**Problem:** API returns 500 error with "API key expired"

**Solution:** 
- Get a new API key from Google AI Studio
- Update your environment variable with the new key
- Restart the application

#### 3. Port Already in Use
**Problem:** `Port 8080 is already allocated`

**Solution:** 
- Stop the service using port 8080
- Or change the port in `application.properties`:
  ```properties
  server.port=8081
  ```

#### 4. Maven Build Fails
**Problem:** `Failed to execute goal maven-compiler-plugin`

**Solution:** Ensure Java 17 is installed:
```bash
java -version
```

### Application Logs

## Database Schema

```sql
CREATE TABLE email_replies (
    id BIGSERIAL PRIMARY KEY,
    sender VARCHAR(255),
    subject VARCHAR(500),
    body TEXT NOT NULL,
    tone VARCHAR(50) NOT NULL CHECK (tone IN ('professional', 'friendly', 'concise')),
    generated_reply TEXT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);
```

## Security Best Practices

1. **Never commit API keys** - Always use environment variables
2. **Keep `.env` in `.gitignore`** - Prevent accidental commits
3. **Rotate API keys regularly** - Update keys periodically
4. **Use HTTPS in production** - Encrypt data in transit
5. **Implement rate limiting** - Prevent API abuse
6. **Use strong database passwords** - Change default credentials

## Deployment

### Production Deployment

1. **Build the application**
   ```bash
   mvn clean package
   ```

2. **Run the JAR file**
   ```bash
   java -jar target/email-assistant-0.0.1-SNAPSHOT.jar
   ```

3. **Configure for production**
   - Use environment variables for sensitive data
   - Set up a production PostgreSQL database
   - Configure HTTPS/SSL
   - Set up reverse proxy (nginx/Apache)

### Cloud Deployment Options

- **AWS**: EC2 + RDS PostgreSQL
- **Google Cloud**: Compute Engine + Cloud SQL
- **Azure**: App Service + Azure Database for PostgreSQL
- **Heroku**: Easy deployment with Heroku Postgres

## Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Author

**Arindam Dandapat**
- GitHub: [@arindamdandapat11](https://github.com/arindamdandapat11)
- Repository: [ai](https://github.com/arindamdandapat11/ai)

## Acknowledgments

- Google Gemini AI for powerful language generation
- Spring Boot team for the excellent framework
- PostgreSQL community for the robust database

## Support

For issues, questions, or contributions:
- Open an [Issue](https://github.com/arindamdandapat11/ai/issues)
- Submit a [Pull Request](https://github.com/arindamdandapat11/ai/pulls)

---

Made by Arindam Dandapat

---

**Note**: This is a backend service and requires a corresponding frontend application to provide a complete user interface. Make sure to check the frontend repository for the complete solution.
