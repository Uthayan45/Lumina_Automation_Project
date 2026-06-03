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
            // Record Test Results in Jenkins UI
            junit 'target/surefire-reports/*.xml'

            // Archive artifacts so they are saved in Jenkins Dashboard
            archiveArtifacts artifacts: 'target/surefire-reports/*.html, target/surefire-reports/*.xml, reports/*.pdf, screenshots/*.png', allowEmptyArchive: true

            // Send Email using Extended Email Plugin
            emailext(
                to: 'uthayanu490@gmail.com',
                subject: "Lumina Automation Report - ${currentBuild.currentResult}",
                mimeType: 'text/html',
                body: """
                <html>
                <body>
                <p>Hi Uthayan,</p>
                <p>Automation test execution completed.</p>
                <br/>
                <b>Job Name:</b> ${env.JOB_NAME}<br/>
                <b>Build Number:</b> ${env.BUILD_NUMBER}<br/>
                <b>Build Status:</b> <span style="color: ${currentBuild.currentResult == 'SUCCESS' ? 'green' : 'red'}">${currentBuild.currentResult}</span><br/>
                <br/>
                <p>Build URL: <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                <p><i>PDF reports, HTML reports, and screenshots are attached below.</i></p>
                <br/>
                <p>Regards,<br/>Jenkins Automation Server</p>
                </body>
                </html>
                """,
                attachmentsPattern: 'reports/*.pdf,screenshots/*.png,target/surefire-reports/*.html',
                failOnError: false // Intha line mail-naala unga build fail aaguratha thadukkum
            )
        }

        success {
            echo 'Tests passed successfully!'
        }

        failure {
            echo 'Tests failed, please review logs/artifacts.'
        }
    }
}
