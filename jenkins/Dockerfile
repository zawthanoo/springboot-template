FROM openjdk:17.0.2-jdk
ENV TZ="Asia/Singapore"
RUN mkdir -p /opt/mutu/config 
ADD apps/springboot-demo.jar /opt/mutu/
WORKDIR /opt/mutu
ENTRYPOINT [ "sh", "-c", "java -Xms${MIN_JVM_MEMORY}m -Xmx${MAX_JVM_MEMORY}m -jar /opt/mutu/springboot-demo.jar --spring.config.additional-location=file:///opt/mutu/config/ --spring.config.name=application,application_config_override,application_secret_override" ]
