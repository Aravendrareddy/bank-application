pipeline {
    agent any
    
    environment {
        ECR_REGISTRY = '123456789012.dkr.ecr.us-west-2.amazonaws.com'
        IMAGE_NAME = 'banking-app'
    }
    
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
        
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh 'mvn sonar:sonar'
                }
            }
        }
        
        stage('Build and Push Docker Image') {
            steps {
                script {
                    docker.build("${ECR_REGISTRY}/${IMAGE_NAME}:${BUILD_NUMBER}")
                    sh "aws ecr get-login-password --region us-west-2 | docker login --username AWS --password-stdin ${ECR_REGISTRY}"
                    docker.image("${ECR_REGISTRY}/${IMAGE_NAME}:${BUILD_NUMBER}").push()
                    docker.image("${ECR_REGISTRY}/${IMAGE_NAME}:${BUILD_NUMBER}").push('latest')
                }
            }
        }
        
        stage('Deploy to EKS') {
            steps {
                script {
                    sh """
                        aws eks update-kubeconfig --region us-west-2 --name banking-cluster
                        kubectl apply -f k8s/
                    """
                }
            }
        }
    }
    
    post {
        always {
            cleanWs()
        }
    }
}