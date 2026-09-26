```
pipeline {
    agent any
    environment {
        registry = "311068231547.dkr.ecr.us-east-2.amazonaws.com/my-cicd-repo"
    }
    
    stages {
        stage('Cloning Git') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/main']], userRemoteConfigs: [[url: 'https://github.com/SiddheshM36/Basic-SpringBoot-Docker']]])
            }
        }

        // Building the JAR file
        stage('Building JAR') {
            steps {
                sh 'mvn clean package'
            }
        }

       

        // Uploading Docker images into AWS ECR
       stage('Pushing to ECR') {
            steps{  
                script {
                    sh 'aws ecr-public get-login-password --region us-east-1 | docker login --username AWS --password-stdin public.ecr.aws/t2j3i4r1'
					sh 'docker build -t public.ecr.aws/t2j3i4r1/my-cicd-repo:latest .'
					sh 'docker push public.ecr.aws/t2j3i4r1/my-cicd-repo:latest'
                }
            }
        }

        // Stopping Docker containers for cleaner Docker run
        stage('Stop previous containers') {
            steps {
                sh 'docker stop myjavaContainer || true'
                sh 'docker rm myjavaContainer || true'
            }
        }

        // Running Docker container
        stage('Docker Run') {
            steps {
                sh 'docker run -d -p 8081:8081 --name myjavaContainer public.ecr.aws/t2j3i4r1/my-cicd-repo:latest'
            }
        }
    }
}
```
