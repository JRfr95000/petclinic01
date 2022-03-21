FROM tomcat:8.5.51

ADD /*.war /usr/local/tomcat/webapps/

EXPOSE 8080
