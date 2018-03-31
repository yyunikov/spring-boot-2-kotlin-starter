FROM openjdk:8-jdk
MAINTAINER Yuriy Yunikov <yyunikov@email.com>
VOLUME /tmp
ADD build/libs/api.jar api.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/api.jar"]