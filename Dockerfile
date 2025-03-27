# 1단계: 빌드 환경
FROM azul/zulu-openjdk-alpine:17 AS builder

WORKDIR /app

# Gradle 관련 파일 먼저 복사 (의존성 캐싱 최적화)
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./

# 의존성만 먼저 다운로드
RUN ./gradlew dependencies --no-daemon

# 소스코드 복사 및 빌드
COPY src src
RUN ./gradlew bootJar --no-daemon -x test

# 2단계: 실행 환경
FROM azul/zulu-openjdk-alpine:17-jre

WORKDIR /app

# 빌드 결과물만 복사
COPY --from=builder /app/build/libs/*.jar app.jar

# 컨테이너 시작 시 실행할 명령어
ENTRYPOINT ["java", "-jar", "app.jar"]
