FROM tomcat:jre11-temurin

LABEL maintainer="support@openemp.org"
ARG VERSION 1.0.0-SNAPSHOT

COPY target/user-$VERSION.war /usr/local/tomcat/webapps/
EXPOSE 8081

CMD ["catalina.sh", "run"]