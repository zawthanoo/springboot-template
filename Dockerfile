FROM openjdk:17.0.2-jdk
ENV TZ="Asia/Singapore"
RUN mkdir -p /opt/jp/config 
ADD target/springboot-template.jar /opt/jp/
WORKDIR /opt/jp
ENTRYPOINT [ "sh", "-c", "java -jar /opt/jp/springboot-template.jar --spring.config.additional-location=file:///opt/jp/config/ --spring.config.name=application,application_config_override,application_secret_override" ]