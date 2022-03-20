FROM Tomcat:8.5.51

copy /var/lib/jenkins/workspace/ /usr/local/webapps/ROOT.war

EXPOSE 8080