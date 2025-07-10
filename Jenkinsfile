pipeline {
    agent any

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
