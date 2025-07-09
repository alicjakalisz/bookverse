pipeline {
    agent any

    environment {
        IMAGE_NAME = 'bookverse-app'
        IMAGE_TAG = 'latest'
        DOCKERHUB_CREDENTIALS = 'dockerhub-creds' // optional
    }

    stages {
        stage('Checkout') {
            steps {
                git credentialsId: 'github-creds', url: 'https://github.com/your-username/bookverse.git'
            }
        }

        stage('Build JAR') {
            steps {
                sh './mvnw clean package -DskipTests=false'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("${IMAGE_NAME}:${IMAGE_TAG}")
                }
            }
        }

        stage('Run Integration Tests') {
            steps {
                sh 'docker-compose -f docker-compose.yml up -d mysql'
                sh './mvnw verify'
            }
        }

        stage('Deploy Locally with Docker Compose') {
            steps {
                sh 'docker-compose down'
                sh 'docker-compose up -d --build'
            }
        }

        // Optional: Push image to DockerHub
        // Uncomment if you want to push
        /*
        stage('Push to DockerHub') {
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', DOCKERHUB_CREDENTIALS) {
                        docker.image("${IMAGE_NAME}:${IMAGE_TAG}").push()
                    }
                }
            }
        }
        */
    }

    post {
        always {
            sh 'docker-compose down'
            cleanWs()
        }
    }
}
