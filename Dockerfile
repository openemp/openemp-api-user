FROM tomcat:jre11-temurin

MAINTAINER openemp

ARG VERSION=1.0.0-SNAPSHOT

COPY target/user-$VERSION.war /usr/local/tomcat/webapps/app.war
EXPOSE 8081

CMD ["java", "-jar", "/usr/local/tomcat/webapps/app.war"]