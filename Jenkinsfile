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
                    credentialsId: 'github-credentials',
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

            archiveArtifacts artifacts: '''
                target/surefire-reports/*.html,
                target/surefire-reports/*.xml,
                reports/*.pdf,
                screenshots/*.png
            ''', allowEmptyArchive: true

            emailext(
                to: 'uthayanu490@gmail.com',
                subject: "Lumina Automation Report - ${currentBuild.currentResult}",
                body: """
Hi Uthayan,

Automation test execution completed.

Job Name: ${env.JOB_NAME}
Build Number: ${env.BUILD_NUMBER}
Build Status: ${currentBuild.currentResult}

Build URL:
${env.BUILD_URL}

PDF report and screenshots are attached.

Regards,
Jenkins
""",
                attachmentsPattern: 'reports/*.pdf,screenshots/*.png,target/surefire-reports/*.html',
                mimeType: 'text/plain'
            )
        }

        success {
            echo 'Tests passed'
        }

        failure {
            echo 'Tests failed'
        }
    }
}
