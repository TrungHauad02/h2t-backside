# 🎓 H2T-English Backend 🚀

<div align="center">
  <img src="https://img.shields.io/badge/Spring%20Boot-3.2+-brightgreen?style=for-the-badge&logo=spring&logoColor=white" alt="Spring Boot"/>
  <img src="https://img.shields.io/badge/Java-17+-orange?style=for-the-badge&logo=java&logoColor=white" alt="Java"/>
  <img src="https://img.shields.io/badge/MySQL-8.0+-blue?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL"/>
  <img src="https://img.shields.io/badge/AI-Integrated-purple?style=for-the-badge&logo=openai&logoColor=white" alt="AI"/>
</div>

---

## 📋 Giới thiệu dự án

**H2T-English** là một hệ thống backend cho website học tập tiếng Anh tích hợp AI đánh giá kết quả học tập, được phát triển bằng Spring Boot. Dự án này là luận văn tốt nghiệp ngành Công nghệ Thông tin tại **Trường Đại học Sư phạm Kỹ thuật Thành phố Hồ Chí Minh (HCMUTE)**.

### 🎯 Mục tiêu dự án
- 🌐 Xây dựng một nền tảng học tập tiếng Anh toàn diện
- 🤖 Tích hợp AI để đánh giá tự động kỹ năng viết và nói
- 👥 Cung cấp hệ thống quản lý học tập có phân quyền rõ ràng
- 📊 Hỗ trợ các bài kiểm tra TOEIC chuẩn quốc tế

---

## 📑 Mục lục

<div align="center">

| 📖 Phần | 🔗 Liên kết |
|---------|-------------|
| 📋 Giới thiệu dự án | [#-giới-thiệu-dự-án](#-giới-thiệu-dự-án) |
| 👥 Thông tin nhóm | [#-thông-tin-nhóm-thực-hiện](#-thông-tin-nhóm-thực-hiện) |
| ✨ Tính năng chính | [#-tính-năng-chính](#-tính-năng-chính) |
| 🛠️ Công nghệ | [#️-công-nghệ-sử-dụng](#️-công-nghệ-sử-dụng) |
| 🚀 Cài đặt & Chạy | [#-cài-đặt-và-chạy-dự-án](#-cài-đặt-và-chạy-dự-án) |
| 🏗️ Cấu trúc dự án | [#️-cấu-trúc-dự-án](#️-cấu-trúc-dự-án) |
| 📚 API Endpoints | [#-api-endpoints-theo-controller](#-api-endpoints-theo-controller) |
| 🔒 Xác thực | [#-xác-thực-và-phân-quyền](#-xác-thực-và-phân-quyền) |
| 🛡️ Xử lý lỗi | [#️-hệ-thống-xử-lý-lỗi](#️-hệ-thống-xử-lý-lỗi) |
| 🗄️ Database | [#️-cấu-trúc-database](#️-cấu-trúc-database) |
| 📈 Performance | [#-hiệu-suất-và-khả-năng-mở-rộng](#-hiệu-suất-và-khả-năng-mở-rộng) |
| 🧪 Testing | [#-testing-và-documentation](#-testing-và-documentation) |

</div>

---

## 👥 Thông tin nhóm thực hiện

<div align="center">

### 🎓 **Luận văn tốt nghiệp HCMUTE** 🏫

<table>
<tr>
<td align="center">
<img src="https://avatars.githubusercontent.com/u/97101001?s=400&u=c2e995d2acff0cb120417bf042d6c1205bd4bbb4&v=4" width="100px;" alt="Nguyễn Trung Hậu"/><br />
<b>👑 Nguyễn Trung Hậu</b><br />
<sub>🆔 21110434</sub><br />
<sub>💻 Backend Developer</sub><br />
<sub>🏆 Trưởng nhóm</sub>
</td>
<td align="center">
<img src="https://avatars.githubusercontent.com/u/113422566?v=4" width="100px;" alt="Cáp Lê Hữu Tân"/><br />
<b>👨‍💻 Cáp Lê Hữu Tân</b><br />
<sub>🆔 21110920</sub><br />
<sub>💻 Backend Developer</sub>
</td>
<td align="center">
<img src="https://avatars.githubusercontent.com/u/123963752?s=400&u=c2e995d2acff0cb120417bf042d6c1205bd4bbb4&v=4" width="100px;" alt="Thái Thanh Hưng"/><br />
<b>👨‍💻 Thái Thanh Hưng</b><br />
<sub>🆔 21110487</sub><br />
<sub>💻 Backend Developer</sub>
</td>
</tr>
</table>

📖 **Đề tài:** Thiết kế và xây dựng website học tập tiếng anh tích hợp AI đánh giá kết quả học tập

</div>

---

## ✨ Tính năng chính

### 🤖 Tính năng AI
- 📝 **Chấm điểm bài viết tự động** - Sử dụng AI để đánh giá văn phong, ngữ pháp và từ vựng
- 🗣️ **Chấm điểm bài nói tự động** - Phân tích phát âm, độ trôi chảy và độ chính xác
- 🔊 **Text-to-Speech** - Chuyển đổi văn bản thành giọng nói tự nhiên
- 📊 **Quản lý phản hồi AI** - Hệ thống đánh giá và quản lý kết quả AI

### 🎓 Hệ thống học tập
- 🗺️ **Lộ trình học có cấu trúc** - Từ cơ bản đến nâng cao
- 📚 **Bài học đa dạng** - Từ vựng, ngữ pháp, nghe, nói, đọc, viết
- ✅ **Bài kiểm tra kỹ năng** - Đánh giá từng kỹ năng riêng biệt
- 🏆 **Bài thi TOEIC** - Luyện thi TOEIC chuẩn quốc tế
- 🥇 **Bài thi thi đấu** - Có bảng xếp hạng cho người học

### 👤 Hệ thống phân quyền

<div align="center">

| Vai trò | Icon | Quyền hạn |
|---------|------|-----------|
| 🟢 **Học sinh** (STUDENT) | 📖 | Truy cập lộ trình học, làm bài kiểm tra, quản lý thông tin cá nhân |
| 🟡 **Giáo viên** (TEACHER) | 👨‍🏫 | Quản lý lộ trình học, tạo bài học, quản lý bài kiểm tra |
| 🟠 **Giáo viên nâng cao** (TEACHER_ADVANCE) | 👨‍🔬 | Quản lý bài thi TOEIC, đánh giá kết quả AI + quyền giáo viên |
| 🔴 **Admin** (ADMIN) | 👨‍💼 | Quản lý toàn hệ thống, người dùng, error logs |

</div>

### 🛡️ Tính năng bảo mật và xử lý lỗi
- 🚨 **Xử lý lỗi thông minh** - Tự động tạo error log và gửi thông báo Discord
- 🔐 **JWT Authentication** - Bảo mật với access token và refresh token
- 🔑 **Phân quyền chi tiết** - Kiểm soát truy cập theo vai trò
- 🌐 **CORS Configuration** - Cấu hình bảo mật cho frontend

---

## 🛠️ Công nghệ sử dụng

### 💼 Framework Backend
<div align="center">
<img src="https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=spring&logoColor=white" alt="Spring Boot"/>
<img src="https://img.shields.io/badge/Spring%20Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white" alt="Spring Security"/>
<img src="https://img.shields.io/badge/Spring%20Data%20JPA-6DB33F?style=for-the-badge&logo=spring&logoColor=white" alt="Spring Data JPA"/>
</div>

- 🚀 **Spring Boot** - Framework chính
- 🛡️ **Spring Security** - Bảo mật và xác thực
- 💾 **Spring Data JPA** - ORM và truy cập database
- ✅ **Spring Validation** - Validation dữ liệu

### 🗄️ Database & Storage
<div align="center">
<img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL"/>
<img src="https://img.shields.io/badge/MinIO-C72E29?style=for-the-badge&logo=minio&logoColor=white" alt="MinIO"/>
</div>

- 🐬 **MySQL 8** - Database chính
- 📁 **MinIO** - Object storage cho file

### 🔐 Bảo mật & Xác thực
<div align="center">
<img src="https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white" alt="JWT"/>
</div>

- 🎫 **JWT (Nimbus JOSE)** - Token-based authentication
- 🔒 **BCrypt** - Hash password

### 🌐 APIs bên ngoài
<div align="center">
<img src="https://custom-icon-badges.demolab.com/badge/Deepseek-4D6BFF?logo=deepseek&logoColor=white" alt="DeepSeek" height="30"/>
<img src="https://img.shields.io/badge/Discord-5865F2?style=for-the-badge&logo=discord&logoColor=white" alt="Discord" height="30"/>
</div>

- 🧠 **OpenRouter** - LLM API cho tính năng AI
- 🔑 **Google OAuth** - Đăng nhập Google
- 📧 **Email Service** - Gửi email xác thực

### 📚 Documentation & Development
<div align="center">
<img src="https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black" alt="Swagger"/>
<img src="https://img.shields.io/badge/MapStruct-E97627?style=for-the-badge&logo=java&logoColor=white" alt="MapStruct"/>
<img src="https://img.shields.io/badge/Lombok-BC4A32?style=for-the-badge&logo=lombok&logoColor=white" alt="Lombok"/>
</div>

- 📖 **Swagger/OpenAPI** - Tài liệu API
- 🔄 **MapStruct** - Object mapping
- 🧹 **Lombok** - Giảm boilerplate code

---

## 🚀 Cài đặt và chạy dự án

### 📋 Yêu cầu hệ thống
<div align="center">
<img src="https://img.shields.io/badge/Java-17+-orange?style=for-the-badge&logo=java&logoColor=white" alt="Java"/>
<img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white" alt="Docker"/>
<img src="https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white" alt="Maven"/>
</div>

### 🐳 1. Khởi tạo các container cần thiết

```bash
# 🐬 MySQL Database
docker run --detach \
  --env MYSQL_ROOT_PASSWORD=123456 \
  --env MYSQL_USER=study-english \
  --env MYSQL_PASSWORD=123456 \
  --env MYSQL_DATABASE=study-english \
  --name mysql \
  --publish 3307:3306 \
  mysql:8-oracle

# 📝 LanguageTool (Kiểm tra ngữ pháp)
docker run -d -p 8081:8010 erikvl87/languagetool

# 🎤 Vosk (Speech-to-Text)
docker run -d -p 7860:7860 trunghauad02/speech-evaluation-app

# 🔊 Kokoro (Text-to-Speech)
docker run -p 8880:8880 ghcr.io/remsky/kokoro-fastapi-cpu:latest
```

### ⚙️ 2. Cấu hình biến môi trường

Tạo file `application.properties` và thay thế các giá trị **[THAY_ĐỔI_TẠI_ĐÂY]** bằng thông tin thật:

```properties
# 📱 Thông tin ứng dụng
spring.application.name=h2t-backside

# 🗄️ Cấu hình Database
spring.datasource.url=jdbc:mysql://localhost:3307/study-english
spring.datasource.username=root
spring.datasource.password=123456
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# 🎫 Cấu hình JWT
jwt.signerkey=[THAY_ĐỔI_JWT_SECRET_KEY_TẠI_ĐÂY]
security.jwt.expiration.access=86400000
security.jwt.expiration.refresh=2592000000

# 📧 Cấu hình Email
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=[THAY_ĐỔI_EMAIL_TẠI_ĐÂY]
spring.mail.password=[THAY_ĐỔI_APP_PASSWORD_TẠI_ĐÂY]

# 📁 Cấu hình MinIO
minio.endpoint=http://138.2.91.94:9000
minio.accessKey=[THAY_ĐỔI_MINIO_ACCESS_KEY_TẠI_ĐÂY]
minio.secretKey=[THAY_ĐỔI_MINIO_SECRET_KEY_TẠI_ĐÂY]
minio.bucketName=h2t-english

# 🧠 OpenRouter (LLM)
openrouter.api.key=[THAY_ĐỔI_OPENROUTER_API_KEY_TẠI_ĐÂY]
openrouter.api.url=https://openrouter.ai/api/v1/chat/completions
openrouter.model=deepseek/deepseek-chat-v3-0324:free

# 🔑 Google OAuth
gg_client_id=[THAY_ĐỔI_GOOGLE_CLIENT_ID_TẠI_ĐÂY]
gg_client_secret_id=[THAY_ĐỔI_GOOGLE_CLIENT_SECRET_TẠI_ĐÂY]

# 🔊 Text-to-Speech
tts.api.url=http://localhost:8880/v1/audio
tts.voice.audio.directory=./voiceAudio

# 🎤 Speech-to-Text
vosk.api.url=http://localhost:7860/api

# 💬 Quote API
quote.api.url=https://api.api-ninjas.com/v1/quotes
quote.api.key=[THAY_ĐỔI_QUOTE_API_KEY_TẠI_ĐÂY]

# 🚨 Discord Webhook
discord.webhook.url=[THAY_ĐỔI_DISCORD_WEBHOOK_URL_TẠI_ĐÂY]

# 📤 Cấu hình File Upload
spring.servlet.multipart.max-file-size=50MB
spring.servlet.multipart.max-request-size=50MB
server.tomcat.connection-timeout=300000ms
```

### 🏃‍♂️ 3. Chạy ứng dụng

```bash
# 📥 Clone repository
git clone <repository-url>
cd h2t-english-backend

# 🔨 Build project
mvn clean install

# ▶️ Chạy ứng dụng
mvn spring-boot:run
```

### ✅ 4. Kiểm tra kết nối

<div align="center">

| Service | URL | Mô tả |
|---------|-----|-------|
| 📖 **API Documentation** | http://localhost:8080/swagger-ui.html | Tài liệu API tương tác |
| 🗄️ **H2 Console** | http://localhost:8080/h2-console | Console database (nếu được bật) |
| ❤️ **Health Check** | http://localhost:8080/actuator/health | Kiểm tra sức khỏe hệ thống |

</div>

---

## 🏗️ Cấu trúc dự án

### 📁 Tổng quan cấu trúc thư mục

```
📦 src/
├── 📂 main/
│   ├── ☕ java/com/englishweb/h2t_backside/
│   │   ├── 🚀 H2tBacksideApplication.java           # Main class khởi động ứng dụng
│   │   │
│   │   ├── ⚙️ config/                              # Cấu hình hệ thống
│   │   │   └── 📁 MinioConfig.java                 # Cấu hình MinIO storage
│   │   │
│   │   ├── 🌐 controller/                          # REST API Controllers
│   │   │   ├── 🤖 ai/                             # Controllers cho tính năng AI
│   │   │   │   ├── 🗣️ ScoreSpeakingController.java # Chấm điểm bài nói
│   │   │   │   └── 🔊 TextToSpeechController.java  # Text-to-Speech
│   │   │   ├── 🔐 auth/                           # Controllers xác thực
│   │   │   │   ├── 🎫 AuthenticateController.java # Đăng nhập/đăng xuất
│   │   │   │   └── 👤 UserController.java         # Quản lý người dùng
│   │   │   ├── ⭐ feature/                        # Controllers tính năng chính
│   │   │   │   ├── 📊 AdminDashboardController.java # Dashboard admin
│   │   │   │   └── 📁 MinioController.java        # Quản lý file
│   │   │   ├── 📚 lesson/                         # Controllers nội dung học
│   │   │   │   ├── 📝 GrammarController.java      # Bài học ngữ pháp
│   │   │   │   └── 📖 VocabularyController.java   # Từ vựng
│   │   │   └── 📊 test/                           # Controllers kiểm tra
│   │   │       ├── 🏆 ToeicController.java        # Bài thi TOEIC
│   │   │       └── 🥇 CompetitionTestController.java # Bài thi đấu
│   │   │
│   │   ├── 📋 dto/                                # Data Transfer Objects
│   │   │   ├── 🤖 ai/                            # DTOs cho AI features
│   │   │   │   ├── 🗣️ SpeakingScoreDTO.java      # Kết quả chấm điểm nói
│   │   │   │   └── ✍️ WritingScoreDTO.java       # Kết quả chấm điểm viết
│   │   │   ├── 📚 lesson/                        # DTOs nội dung học
│   │   │   │   ├── 📝 GrammarDTO.java            # DTO bài học ngữ pháp
│   │   │   │   └── 📖 VocabularyDTO.java         # DTO từ vựng
│   │   │   ├── 🔍 filter/                        # DTOs cho filtering
│   │   │   │   └── 📋 LessonFilterDTO.java       # Filter bài học
│   │   │   └── 📤 response/                      # DTOs phản hồi
│   │   │       └── 📨 ResponseDTO.java           # Response wrapper chung
│   │   │
│   │   ├── 🚨 exception/                          # Xử lý ngoại lệ
│   │   │   ├── 🛡️ GlobalExceptionHandler.java    # Handler lỗi toàn cục
│   │   │   └── 🔐 AuthenticateException.java     # Lỗi xác thực
│   │   │
│   │   ├── 🔄 mapper/                            # Object Mapping (MapStruct)
│   │   │   ├── 📚 lesson/                        # Mappers cho lesson
│   │   │   │   └── 📝 GrammarMapper.java         # Mapper Grammar entity <-> DTO
│   │   │   └── 📊 test/                          # Mappers cho test
│   │   │       └── 🏆 ToeicMapper.java           # Mapper TOEIC entity <-> DTO
│   │   │
│   │   ├── 🗃️ model/                             # Entities (JPA Models)
│   │   │   ├── 🏷️ enummodel/                     # Enum definitions
│   │   │   │   ├── 👤 RoleEnum.java              # Enum vai trò người dùng
│   │   │   │   └── 📊 TestTypeEnum.java          # Enum loại bài kiểm tra
│   │   │   ├── ⭐ features/                      # Core feature entities
│   │   │   │   ├── 👤 User.java                  # Entity người dùng
│   │   │   │   └── 🗺️ Route.java                 # Entity lộ trình học
│   │   │   ├── 📚 lesson/                        # Learning content entities
│   │   │   │   ├── 📝 Grammar.java               # Entity bài học ngữ pháp
│   │   │   │   └── 📖 Vocabulary.java            # Entity từ vựng
│   │   │   └── 📊 test/                          # Test entities
│   │   │       ├── 🏆 Toeic.java                 # Entity bài thi TOEIC
│   │   │       └── 🥇 CompetitionTest.java       # Entity bài thi đấu
│   │   │
│   │   ├── 🔍 repository/                        # Data Access Layer
│   │   │   ├── 📚 lesson/                        # Repositories cho lesson
│   │   │   │   └── 📝 GrammarRepository.java     # Repository bài học ngữ pháp
│   │   │   ├── 📊 test/                          # Repositories cho test
│   │   │   │   └── 🏆 ToeicRepository.java       # Repository TOEIC
│   │   │   └── 🔍 specifications/                # JPA Specifications
│   │   │       └── 📋 LessonSpecification.java   # Specs cho lesson queries
│   │   │
│   │   ├── 🔐 security/                          # Bảo mật và xác thực
│   │   │   ├── 🎫 JwtUtil.java                   # Utilities JWT
│   │   │   └── 🛡️ SecurityConfig.java            # Cấu hình Spring Security
│   │   │
│   │   ├── 💼 service/                           # Business Logic Layer
│   │   │   ├── 🤖 ai/                           # Services cho AI features
│   │   │   │   ├── 🗣️ ScoreSpeakingService.java # Logic chấm điểm nói
│   │   │   │   └── 📁 impl/                     # Implementations
│   │   │   ├── ⭐ feature/                      # Core feature services
│   │   │   │   ├── 👤 UserService.java          # Logic quản lý người dùng
│   │   │   │   └── 📁 impl/                     # Implementations
│   │   │   ├── 📚 lesson/                       # Learning content services
│   │   │   │   ├── 📝 GrammarService.java       # Logic bài học ngữ pháp
│   │   │   │   └── 📁 impl/                     # Implementations
│   │   │   └── 📊 test/                         # Test services
│   │   │       ├── 🏆 ToeicService.java         # Logic bài thi TOEIC
│   │   │       └── 📁 impl/                     # Implementations
│   │   │
│   │   └── 🛠️ utils/                            # Utilities và Helpers
│   │       ├── 📄 LessonPagination.java         # Phân trang bài học
│   │       └── ✅ ValidationData.java           # Validation utilities
│   │
│   └── 📦 resources/                            # Tài nguyên ứng dụng
│       ├── ⚙️ application.properties            # Cấu hình ứng dụng
│       └── 📚 wordnet/                         # WordNet dictionary data
│           ├── 📝 data.noun                    # Danh từ
│           └── 🔍 index.verb                   # Động từ
│
└── 🧪 test/                                    # Unit Tests
    └── ☕ java/com/englishweb/h2t_backside/
        └── 🧪 H2tBacksideApplicationTests.java # Test class chính
```

### 💡 Giải thích các package chính

#### 🎯 **Controller Layer** (`controller/`)
Chứa các REST API controllers được phân nhóm theo chức năng:
- 🤖 **`ai/`** - Các endpoint AI như chấm điểm tự động, text-to-speech
- 🔐 **`auth/`** - Xác thức, đăng nhập, quản lý người dùng
- ⭐ **`feature/`** - Các tính năng chính như dashboard, file management
- 📚 **`lesson/`** - Quản lý nội dung học tập (grammar, vocabulary, etc.)
- 📊 **`test/`** - Quản lý bài kiểm tra và thi (TOEIC, competition)

#### 📋 **DTO Layer** (`dto/`)
Data Transfer Objects để truyền dữ liệu giữa các layer:
- 🤖 **`ai/`** - DTOs cho AI responses và requests
- 📚 **`lesson/`** - DTOs cho nội dung học tập
- 🔍 **`filter/`** - DTOs cho filtering và searching
- 📤 **`response/`** - DTOs cho API responses

#### 🛡️ **Exception Handling** (`exception/`)
Quản lý lỗi toàn hệ thống với các custom exceptions và global handler.

#### 🔄 **Mapper Layer** (`mapper/`)
Sử dụng MapStruct để convert giữa Entities và DTOs một cách tự động.

#### 🗃️ **Model Layer** (`model/`)
JPA Entities đại diện cho các bảng database:
- 🏷️ **`enummodel/`** - Các enum definitions
- ⭐ **`features/`** - Core entities (User, Route, etc.)
- 📚 **`lesson/`** - Entities cho nội dung học tập
- 📊 **`test/`** - Entities cho bài kiểm tra

#### 🔍 **Repository Layer** (`repository/`)
Data Access Layer với JPA Repositories và Specifications cho complex queries.

#### 🔐 **Security Layer** (`security/`)
Xử lý JWT authentication và Spring Security configuration.

#### 💼 **Service Layer** (`service/`)
Business Logic Layer được tổ chức theo pattern Interface + Implementation:
- 🤖 **`ai/`** - Logic xử lý AI features
- ⭐ **`feature/`** - Logic các tính năng chính
- 📚 **`lesson/`** - Logic quản lý nội dung học
- 📊 **`test/`** - Logic bài kiểm tra

#### 🛠️ **Utils** (`utils/`)
Các utility classes và helper functions cho pagination, validation, etc.

### 🏛️ Kiến trúc tổng quan

Dự án sử dụng **Layered Architecture** với các layer rõ ràng:

<div align="left">

```
🌐 Controller ←→ 💼 Service ←→ 🔍 Repository ←→ 🗄️ Database
      ↕             ↕
    📋 DTO       🗃️ Entity/Model
      ↕
   🔄 Mapper
```

</div>

1. 🌐 **Controller** nhận requests và trả về responses
2. 💼 **Service** chứa business logic
3. 🔍 **Repository** truy cập database
4. 🔄 **Mapper** convert giữa DTO và Entity
5. 🛡️ **Exception Handler** xử lý lỗi toàn hệ thống

---

## 📚 API Endpoints theo Controller

### 🔐 Authentication Controller (`/api/auth`)
- `POST /api/auth/login` - 🔑 Đăng nhập với email và mật khẩu
- `POST /api/auth/login-with-google` - 🔍 Đăng nhập bằng Google OAuth
- `POST /api/auth/logout` - 🚪 Đăng xuất khỏi hệ thống
- `POST /api/auth/refresh-token` - 🔄 Làm mới access token
- `GET /api/auth/validate-token` - ✅ Kiểm tra tính hợp lệ của token

### 👤 User Controller (`/api/users`)
- `GET /api/users` - 📋 Lấy danh sách người dùng (có phân trang và lọc)
- `POST /api/users` - ➕ Tạo tài khoản người dùng mới
- `GET /api/users/{id}` - 👀 Lấy thông tin chi tiết người dùng
- `PUT /api/users/{id}` - ✏️ Cập nhật thông tin người dùng
- `DELETE /api/users/{id}` - 🗑️ Xóa tài khoản người dùng
- `GET /api/users/{userId}/process-by-route-id/{routeId}` - 📊 Lấy tiến độ học của user theo lộ trình
- `GET /api/users/{userId}/complete-route-node/{routeNodeId}` - ✅ Hoàn thành một nút trong lộ trình

### 🗂️ Topic Controller (`/api/topics`)
- `GET /api/topics` - 📋 Lấy danh sách chủ đề học tập
- `POST /api/topics` - ➕ Tạo chủ đề mới
- `GET /api/topics/{id}` - 👀 Lấy thông tin chi tiết chủ đề
- `PUT /api/topics/{id}` - ✏️ Cập nhật thông tin chủ đề
- `DELETE /api/topics/{id}` - 🗑️ Xóa chủ đề
- `GET /api/topics/{id}/verify` - ✅ Xác thực chủ đề
- `GET /api/topics/questions` - ❓ Lấy câu hỏi theo chủ đề

### 📖 Vocabulary Controller (`/api/vocabularies`)
- `GET /api/vocabularies` - 📋 Lấy danh sách từ vựng theo chủ đề
- `POST /api/vocabularies` - ➕ Thêm từ vựng mới
- `GET /api/vocabularies/{id}` - 👀 Lấy thông tin chi tiết từ vựng
- `PUT /api/vocabularies/{id}` - ✏️ Cập nhật từ vựng
- `DELETE /api/vocabularies/{id}` - 🗑️ Xóa từ vựng

### 📝 Grammar Controller (`/api/grammars`)
- `GET /api/grammars` - 📋 Lấy danh sách bài học ngữ pháp
- `POST /api/grammars` - ➕ Tạo bài học ngữ pháp mới
- `GET /api/grammars/{id}` - 👀 Lấy chi tiết bài học ngữ pháp
- `PUT /api/grammars/{id}` - ✏️ Cập nhật bài học ngữ pháp
- `DELETE /api/grammars/{id}` - 🗑️ Xóa bài học ngữ pháp
- `GET /api/grammars/{id}/verify` - ✅ Xác thực bài học ngữ pháp
- `GET /api/grammars/questions` - ❓ Lấy câu hỏi ngữ pháp

### 👂 Listening Controller (`/api/listenings`)
- `GET /api/listenings` - 📋 Lấy danh sách bài học nghe
- `POST /api/listenings` - ➕ Tạo bài học nghe mới
- `GET /api/listenings/{id}` - 👀 Lấy chi tiết bài học nghe
- `PUT /api/listenings/{id}` - ✏️ Cập nhật bài học nghe
- `DELETE /api/listenings/{id}` - 🗑️ Xóa bài học nghe
- `GET /api/listenings/{id}/verify` - ✅ Xác thực bài học nghe
- `GET /api/listenings/questions` - ❓ Lấy câu hỏi theo bài nghe

### 👁️ Reading Controller (`/api/readings`)
- `GET /api/readings` - 📋 Lấy danh sách bài học đọc
- `POST /api/readings` - ➕ Tạo bài học đọc mới
- `GET /api/readings/{id}` - 👀 Lấy chi tiết bài học đọc
- `PUT /api/readings/{id}` - ✏️ Cập nhật bài học đọc
- `DELETE /api/readings/{id}` - 🗑️ Xóa bài học đọc
- `GET /api/readings/{id}/verify` - ✅ Xác thực bài học đọc
- `GET /api/readings/questions` - ❓ Lấy câu hỏi theo bài đọc

### 🗣️ Speaking Controller (`/api/speakings`)
- `GET /api/speakings` - 📋 Lấy danh sách bài học nói
- `POST /api/speakings` - ➕ Tạo bài học nói mới
- `GET /api/speakings/{id}` - 👀 Lấy chi tiết bài học nói
- `PUT /api/speakings/{id}` - ✏️ Cập nhật bài học nói
- `DELETE /api/speakings/{id}` - 🗑️ Xóa bài học nói
- `GET /api/speakings/{id}/verify` - ✅ Xác thực bài học nói

### ✍️ Writing Controller (`/api/writings`)
- `GET /api/writings` - 📋 Lấy danh sách bài học viết
- `POST /api/writings` - ➕ Tạo bài học viết mới
- `GET /api/writings/{id}` - 👀 Lấy chi tiết bài học viết
- `PUT /api/writings/{id}` - ✏️ Cập nhật bài học viết
- `DELETE /api/writings/{id}` - 🗑️ Xóa bài học viết
- `GET /api/writings/{id}/verify` - ✅ Xác thực bài học viết

### 📊 Test Controller (`/api/tests`)
- `GET /api/tests` - 📋 Lấy danh sách bài kiểm tra
- `POST /api/tests` - ➕ Tạo bài kiểm tra mới
- `GET /api/tests/{id}` - 👀 Lấy chi tiết bài kiểm tra
- `PUT /api/tests/{id}` - ✏️ Cập nhật bài kiểm tra
- `DELETE /api/tests/{id}` - 🗑️ Xóa bài kiểm tra
- `GET /api/tests/{id}/verify` - ✅ Xác thực bài kiểm tra

### 📈 TOEIC Controller (`/api/toeic`)
- `GET /api/toeic` - 📋 Lấy danh sách đề thi TOEIC
- `POST /api/toeic` - ➕ Tạo đề thi TOEIC mới
- `GET /api/toeic/{id}` - 👀 Lấy chi tiết đề thi TOEIC
- `PUT /api/toeic/{id}` - ✏️ Cập nhật đề thi TOEIC
- `DELETE /api/toeic/{id}` - 🗑️ Xóa đề thi TOEIC
- `GET /api/toeic/{id}/verify` - ✅ Xác thực đề thi TOEIC

### 🏆 Competition Test Controller (`/api/competition-tests`)
- `GET /api/competition-tests` - 📋 Lấy danh sách cuộc thi
- `POST /api/competition-tests` - ➕ Tạo cuộc thi mới
- `GET /api/competition-tests/{id}` - 👀 Lấy chi tiết cuộc thi
- `PUT /api/competition-tests/{id}` - ✏️ Cập nhật cuộc thi
- `DELETE /api/competition-tests/{id}` - 🗑️ Xóa cuộc thi
- `GET /api/competition-tests/{id}/verify` - ✅ Xác thực cuộc thi

### 🛣️ Route Controller (`/api/routes`)
- `GET /api/routes` - 📋 Lấy danh sách lộ trình học
- `POST /api/routes` - ➕ Tạo lộ trình học mới
- `GET /api/routes/{id}` - 👀 Lấy chi tiết lộ trình học
- `PUT /api/routes/{id}` - ✏️ Cập nhật lộ trình học
- `DELETE /api/routes/{id}` - 🗑️ Xóa lộ trình học
- `GET /api/routes/{id}/verify` - ✅ Xác thực lộ trình học
- `GET /api/routes/longest` - 📏 Lấy lộ trình dài nhất

### 🤖 AI Features Controllers

#### 🔊 Text-to-Speech Controller (`/api/text-to-speech`)
- `POST /api/text-to-speech` - 🗣️ Chuyển đổi văn bản thành giọng nói
- `GET /api/text-to-speech/voices` - 🎤 Lấy danh sách giọng nói có sẵn

#### ✍️ Score Writing Controller (`/api/score-writing`)
- `POST /api/score-writing` - 📝 Chấm điểm bài viết sử dụng AI

#### 🗣️ Score Speaking Controller (`/api/score-speaking`)
- `POST /api/score-speaking` - 🎤 Chấm điểm bài nói sử dụng AI
- `POST /api/score-speaking/speech-in-topic` - 💬 Chấm điểm nói theo chủ đề
- `POST /api/score-speaking/multiple` - 🎵 Chấm điểm nhiều file âm thanh

#### 🧠 LLM Controller (`/api/llm`)
- `POST /api/llm` - 💭 Gửi prompt đến AI model và nhận phản hồi

#### 🤖 AI Response Controller (`/api/ai-response`)
- `GET /api/ai-response` - 📋 Lấy danh sách phản hồi AI
- `GET /api/ai-response/teacher-view` - 👨‍🏫 Xem phản hồi AI dành cho giáo viên
- `GET /api/ai-response/{id}` - 👀 Lấy chi tiết phản hồi AI
- `PUT /api/ai-response/{id}` - ✏️ Cập nhật đánh giá phản hồi AI

### 📊 Dashboard Controllers

#### 👨‍💼 Admin Dashboard (`/api/admin/dashboard`)
- `GET /api/admin/dashboard` - 📈 Lấy thống kê tổng quan cho admin

#### 👨‍🏫 Teacher Dashboard (`/api/teacher-dashboard`)
- `GET /api/teacher-dashboard` - 📊 Lấy thống kê cho giáo viên

#### 👨‍🔬 Teacher Advance Dashboard (`/api/teacher-advance/dashboard`)
- `GET /api/teacher-advance/dashboard` - 📈 Lấy thống kê cho giáo viên nâng cao

### 📧 Email Controller (`/api/email`)
- `POST /api/email/send-otp` - 📤 Gửi mã OTP qua email
- `POST /api/email/verify-otp` - ✅ Xác thực mã OTP
- `POST /api/email/reset-password` - 🔄 Đặt lại mật khẩu

### 📁 MinIO Controller (`/api/minio`)
- `POST /api/minio` - 📤 Upload file lên MinIO storage
- `GET /api/minio/{objectName}` - 📥 Download file từ MinIO
- `DELETE /api/minio` - 🗑️ Xóa file khỏi MinIO

### 🏠 Home Controller (`/api/home`)
- `GET /api/home/hero-info` - 📊 Lấy thông tin thống kê trang chủ
- `GET /api/home/quotes` - 💬 Lấy câu quote ngẫu nhiên
- `GET /api/home/feature-lesson/most-viewed` - 👀 Lấy bài học được xem nhiều nhất
- `GET /api/home/feature-lesson/most-recent` - 🆕 Lấy bài học mới nhất
- `GET /api/home/routes/recent` - 🗺️ Lấy lộ trình mới nhất
- `GET /api/home/routes/longest` - 📏 Lấy lộ trình dài nhất
- `GET /api/home/tests/recent` - 📊 Lấy bài kiểm tra mới nhất
- `GET /api/home/toeic/recent` - 🏆 Lấy đề TOEIC mới nhất
- `GET /api/home/competition-tests/recent` - 🥇 Lấy cuộc thi mới nhất

### 📝 Submit Controllers (Nộp bài)
- **Submit Test** (`/api/submit-tests`) - 📤 Quản lý việc nộp bài kiểm tra
- **Submit TOEIC** (`/api/submit-toeic`) - 🏆 Quản lý việc nộp bài TOEIC
- **Submit Competition** (`/api/submit-competitions`) - 🥇 Quản lý việc nộp bài thi đấu

### 📋 Error Log Controller (`/api/error-logs`)
- `GET /api/error-logs` - 📋 Lấy danh sách error logs
- `GET /api/error-logs/{id}` - 👀 Lấy chi tiết error log
- `DELETE /api/error-logs/{id}` - 🗑️ Xóa error log
- `DELETE /api/error-logs/bulk` - 🗑️ Xóa nhiều error logs

---

## 🔒 Xác thực và Phân quyền

### 🎫 Cấu trúc JWT Token

<div align="center">

| Token Type | ⏰ Thời gian | 📋 Claims | 🎯 Sử dụng |
|------------|-------------|----------|------------|
| 🔑 **Access Token** | 24 giờ | id, email, role, token_type | Xác thực API |
| 🔄 **Refresh Token** | 30 ngày | id, email, is_refresh, token_type | Làm mới token |

</div>

### 🛡️ Cấu hình bảo mật

- 🌐 **Public Endpoints:** Xác thực, xác minh email, trang chủ
- 🔐 **Protected Endpoints:** Tất cả `/api/**` trừ public endpoints
- 🌏 **CORS:** Được cấu hình cho `http://localhost:3000`
- ✅ **JWT Validation:** Tự động kiểm tra token với phân quyền theo vai trò

### 👥 Kiểm soát truy cập theo vai trò

```java
// 🔴 Ví dụ: Chỉ ADMIN mới có thể quản lý người dùng
@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<PageUserDTO> getUsers() { ... }

// 🟡 Ví dụ: TEACHER trở lên có thể quản lý bài học
@PreAuthorize("hasAnyRole('TEACHER', 'TEACHER_ADVANCE', 'ADMIN')")
public ResponseEntity<LessonDTO> createLesson() { ... }
```

---

## 🛡️ Hệ thống xử lý lỗi

### 🚨 Quản lý lỗi toàn diện

#### 🎯 Global Exception Handler
- ❌ **Lỗi Validation** - MethodArgumentNotValidException
- 🔍 **Không tìm thấy tài nguyên** - ResourceNotFoundException
- 🔐 **Lỗi xác thực** - AuthenticateException
- 🌐 **Lỗi API bên ngoài** - OpenRouterException, SpeechProcessingException
- 📁 **Lỗi xử lý file** - IOException, JsonProcessingException

#### 📝 Ghi log lỗi & Thông báo
```java
@ExceptionHandler(Exception.class)
public ResponseDTO<String> handleException(Exception ex, HttpServletRequest request) {
    // 📋 Tạo log lỗi chi tiết
    ErrorDTO errorDTO = ErrorDTO.builder()
        .message("Mô tả lỗi")
        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .detail(ex.getMessage())
        .instance(request.getMethod() + " " + request.getRequestURI())
        .timestamp(LocalDateTime.now())
        .errorCode(ErrorApiCodeContent.UNEXPECTED_ERROR)
        .severity(SeverityEnum.HIGH)
        .build();
    
    // 🚨 Gửi thông báo đến Discord
    discordNotifier.buildErrorAndSend(errorDTO);
    
    return ResponseDTO.fail("Có lỗi xảy ra", ex.getMessage());
}
```

#### 🚦 Mức độ nghiêm trọng của lỗi

<div align="center">

| Mức độ | Icon | Mô tả |
|--------|------|-------|
| 🟢 **LOW** | ⚠️ | Lỗi validation, vấn đề nhỏ |
| 🟡 **MEDIUM** | ⚠️ | Lỗi business logic, lỗi xử lý dữ liệu |
| 🔴 **HIGH** | 🚨 | Lỗi hệ thống, vấn đề bảo mật, lỗi API bên ngoài |

</div>

#### 💬 Tích hợp Discord
Thông báo lỗi tự động được gửi đến Discord webhook bao gồm:
- 📝 Chi tiết lỗi và stack trace
- 🌐 Thông tin request (method, URI, parameters)
- 👤 Context người dùng và thời gian
- 🚦 Mức độ nghiêm trọng và mã lỗi

---

## 🗄️ Cấu trúc Database

### 📊 Các bảng chính (Tổng cộng 52 bảng)

#### 👤 Quản lý người dùng
- 👥 `users` - Tài khoản và hồ sơ người dùng
- 📈 `process` - Theo dõi tiến độ học tập

#### 📚 Nội dung học tập
- 🗂️ `topic` - Chủ đề học tập
- 📖 `vocabulary` - Định nghĩa và ví dụ từ vựng
- 📝 `grammar` - Quy tắc và bài tập ngữ pháp
- 👁️ `reading` - Tài liệu đọc hiểu
- 👂 `listening` - Bài học dựa trên âm thanh
- 🗣️ `speaking` - Phiên luyện nói
- ✍️ `writing` - Bài tập viết

#### 📊 Hệ thống đánh giá
- 📋 `test` - Cấu trúc bài kiểm tra chung
- 👁️ `test_reading`, 👂 `test_listening`, 🗣️ `test_speaking`, ✍️ `test_writing` - Kiểm tra theo kỹ năng
- 🏆 `toeic` - Quản lý bài thi TOEIC
- 📊 `toeic_part1` đến `toeic_part7` - Các phần của bài thi TOEIC
- 🥇 `competition_test` - Đánh giá thi đấu

#### 📝 Theo dõi bài nộp
- 📤 `submit_test` - Bài kiểm tra đã nộp
- 🏆 `submit_toeic` - Bài TOEIC đã nộp
- 🥇 `submit_competition` - Bài thi đấu đã nộp
- 📋 Các bảng câu trả lời liên quan để theo dõi chi tiết

#### ⚙️ Quản lý hệ thống
- 🚨 `error_log` - Theo dõi lỗi hệ thống
- 🤖 `airesponse` - Quản lý phản hồi AI

### ⚙️ Cấu hình Database
```properties
# 🐬 MySQL 8 với cấu hình tối ưu
spring.datasource.url=jdbc:mysql://localhost:3307/study-english
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

---

## 📈 Hiệu suất và Khả năng mở rộng

### 📤 Cấu hình File Upload
```properties
spring.servlet.multipart.max-file-size=50MB
spring.servlet.multipart.max-request-size=50MB
server.tomcat.connection-timeout=300000ms
server.tomcat.max-http-form-post-size=50MB
```

### ⚡ Cấu hình Async
```properties
spring.mvc.async.request-timeout=300000
server.undertow.io-threads=4
server.undertow.worker-threads=20
```

---

## 🧪 Testing và Documentation

### 📖 Tài liệu API
- 📚 **Swagger UI:** Có sẵn tại `/swagger-ui.html`
- 📋 **OpenAPI 3.0:** Tài liệu API toàn diện
- 🧪 **Testing tương tác:** Test endpoints trực tiếp từ trình duyệt

### 🛠️ Công cụ phát triển
- 🔄 **Spring Boot DevTools** - Hot reload trong quá trình phát triển
- 🗃️ **H2 Console** - Kiểm tra database (chỉ development)
- 📝 **Logging** - Logging toàn diện với các mức độ khác nhau

---

## 🤝 Đóng góp

### 🔄 Quy trình phát triển
1. 🍴 Fork repository
2. 🌿 Tạo feature branch
3. ✏️ Thực hiện thay đổi
4. 🧪 Viết tests cho tính năng mới
5. 📤 Submit pull request

### 📏 Chuẩn code
- ✅ Tuân theo best practices của Spring Boot
- 🛡️ Sử dụng xử lý lỗi phù hợp
- 📖 Tài liệu hóa API endpoints mới
- 📊 Duy trì test coverage

---

## 📞 Hỗ trợ và Liên hệ

<div align="center">

| 📧 **Email** | 🏫 **Trường** | 🎓 **Khoa** |
|-------------|---------------|-------------|
| 21110434@student.hcmute.edu.vn | Đại học Sư phạm Kỹ thuật TP.HCM | Công nghệ Thông tin |
| (Trưởng nhóm) | (HCMUTE) | |

</div>

---

## 📝 Giấy phép

Dự án này là một phần của luận văn tốt nghiệp và được dự định cho mục đích giáo dục.

---

<div align="center">

### 🎓 **H2T-English Backend** 🚀
**Được phát triển với ❤️ bởi sinh viên HCMUTE**

<img src="https://img.shields.io/badge/Made%20with-❤️-red?style=for-the-badge" alt="Made with Love"/>
<img src="https://img.shields.io/badge/HCMUTE-2024-blue?style=for-the-badge" alt="HCMUTE 2024"/>

</div>