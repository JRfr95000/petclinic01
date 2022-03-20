FROM tomcat:8.5.51

copy ./petclinic.war /usr/local/webapps/ROOT.war

EXPOSE 8080
