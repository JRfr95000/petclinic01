node {
    
    
  environment {
    //once you create ACR in Azure cloud, use that here
   // registryName = "pcregistre"
    //- update your credentials ID after creating credentials for connecting to ACR
   // registryCredential = 'ACR'
//   dockerImage = ''
//  registryUrl = 'pcregistre.azurecr.io'
        
        }
        
  stage ('SCM Checkout'){
    git credentialId: 'git-creds', url: 'https://github.com/JRfr95000/petclinic01'
  }  
  
  stage ('sonar scan'){
    withSonarQubeEnv('sq1'){
      sh 'mvn clean package sonar:sonar'
          }
      }
  
  
  // No need to occupy a node
  stage("Quality Gate"){
  timeout(time: 1, unit: 'HOURS') { // Just in case something goes wrong, pipeline will be killed after a timeout
    def qg = waitForQualityGate() // Reuse taskId previously collected by withSonarQubeEnv
    if (qg.status != 'OK') {
      error "Pipeline aborted due to quality gate failure: ${qg.status}"
    }
  }
}


  stage ('Mvn Package'){
    sh 'mvn clean package'
  }

  stage ('Build Docker Image'){
   sh 'whoami'  
   sh 'docker build -t petion/petclinic:2.0 .'
   sh 'docker tag petion/petclinic:2.0 ${ACR}/petclinic'
   // sh 'az acr login -n pcregistre -u pcregistre -p iKXWZr8dgJ0aqI4JAu8azd0+2JzAOZVp'
   //sh 'docker push ${ACRPROD}/petclinic'
   sh 'docker login ${ACR} -u jrrepo -p B+do7utaWerqPnRb4ADmXv4CXsygLwYp'
   sh 'docker push ${ACR}/petclinic'
   //def imageWithTag = "{$ACRPROD}/petclinic:latest"
   //def image = docker.build imageWithTag
   //image.push()
   // Update kubernetes deployment with new image.
   //WEB_IMAGE_NAME="${ACR}/petclinic:kube${BUILD_NUMBER}"
   //kubectl set image deployment/petclinic petclinic=$WEB_IMAGE_NAME
    }
  
  
   // Uploading Docker images into ACR
  //stage('Upload Image to ACR') {
  //// steps{   
  ////     script {
  ////        docker.withRegistry( "http://${registryUrl}", registryCredential ) {
  ////        dockerImage.push()
  ////        }
  ////    }
  ////  }
  //}
}

