FROM tomcat:8.5.51

sudo ADD /var/lib/jenkins/workspace/petclinic/target/petclinic.war  /usr/local/tomcat/webapps/

EXPOSE 8080
