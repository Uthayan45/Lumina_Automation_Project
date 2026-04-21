pipeline {
    agent any

    tools {
        jdk 'jdk-21'
        maven 'Maven3'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'master',
                    credentialsId: 'github-token',
                    url: 'https://github.com/Uthayan45/Lumina_Automation_Project.git'
            }
        }

        stage('Run TestNG Tests') {
            steps {
                bat 'mvn clean test'
            }
        }
    }

    post {
        always {
            junit 'target/surefire-reports/*.xml'
        }
        success {
            echo 'Tests passed'
        }
        failure {
            echo 'Tests failed'
        }
    }
}
