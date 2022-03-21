FROM tomcat:8.5.51

ADD /petclinic.war /usr/local/tomcat/webapps/

EXPOSE 8080
