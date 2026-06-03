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

            // HTML report publish in Jenkins
            publishHTML(target: [
                reportDir: 'target/surefire-reports',
                reportFiles: 'index.html,emailable-report.html',
                reportName: 'TestNG Report',
                keepAll: true,
                alwaysLinkToLastBuild: true,
                allowMissing: true
            ])

            // Email send with report attachment
            emailext(
                to: 'uthayanu490@gmail.com',
                subject: "Jenkins Test Report - ${env.JOB_NAME} #${env.BUILD_NUMBER} - ${currentBuild.currentResult}",
                body: """
                Hi Uthayan,

                Test execution completed.

                Project: ${env.JOB_NAME}
                Build Number: ${env.BUILD_NUMBER}
                Status: ${currentBuild.currentResult}

                Jenkins Build URL:
                ${env.BUILD_URL}

                Report is attached.

                Regards,
                Jenkins
                """,
                attachmentsPattern: 'target/surefire-reports/*.html,target/surefire-reports/*.xml',
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
