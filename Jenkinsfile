pipeline {
    agent {
        // Use an agent that is a Docker container.
        // This image should have your build tools (e.g., Maven/JDK).
        docker {
            image 'maven:3.9-eclipse-temurin-17' // An example image with Maven and Java 17
            // This is the crucial part!
            // It maps the host's docker.sock to the container's docker.sock
            args '-v /var/run/docker.sock:/var/run/docker.sock'
        }
    }

    environment {
        IMAGE_NAME = 'bookverse-app'
        IMAGE_TAG = 'latest'
    }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/alicjakalisz/bookverse.git', credentialsId: 'github-creds'
            }
        }

        stage('Unit & Integration Tests') {
            steps {
                sh './mvnw clean verify'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t bookverse-app:latest .'
            }
        }

        stage('Start MySQL') {
            steps {
                sh 'docker-compose down || true'     // Cleanup previous state (optional)
                sh 'docker-compose up -d mysql'
                sh 'sleep 15'  // give MySQL time to be ready
            }
        }

        stage('Run App Container') {
            steps {
                sh 'docker run -d --name bookverse-app --network bookverse_net -p 8090:8080 bookverse-app:latest'
            }
        }
    }

    post {
        always {
            echo 'Cleaning up...'
            sh 'docker stop bookverse-app || true'
            sh 'docker rm bookverse-app || true'
            sh 'docker-compose down'
            cleanWs()
        }
    }
}
