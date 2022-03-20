FROM tomcat:8.5.51

copy /var/jenkins_home/workspace/petclinic01/target/petclinic.war /usr/local/webapps/ROOT.war

EXPOSE 8080
