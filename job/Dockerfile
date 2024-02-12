FROM mcr.microsoft.com/java/jdk:17-zulu-alpine

ARG APP_ROOT_PATH="/srv/careers"
ARG JAR_FILE_ARG="job-1.0.0.jar"
ARG PROFILE_ARG="dev"
ARG JASYPT_PASSWORD_ARG="password"

ENV JAR_FILE=${JAR_FILE_ARG}
ENV PROFILE=${PROFILE_ARG}
ENV JASYPT_PASSWORD=${JASYPT_PASSWORD_ARG}

EXPOSE 8080

RUN mkdir -p ${APP_ROOT_PATH}
WORKDIR ${APP_ROOT_PATH}
ADD build/libs/job-1.0.0.jar .

CMD ["sh", "-c", "java -jar ${JAR_FILE} --spring.profiles.active=${PROFILE} --jasypt.encryptor.password=${JASYPT_PASSWORD}"]
