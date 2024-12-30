pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "docker_image"        
	    DOCKER_REGISTRY_CREDENTIALS = 'docker_credentials'
            }

    stages {
        stage('Checkout') {
            steps {
                echo "Clonando el repositorio .................."
                git branch: main, 
		    url: 'https://github.com/sport-enlace-sas/frontend-challenge-base'

            }
        }

        stage('Build Docker Image') {
            steps {
                echo "Construyendo imagen .................."
                script {
                    docker.build("${DOCKER_IMAGE}:latest")
                }
            }
        }

        stage('Test') {
            steps {
                echo "Ejecutando tests npm......................"
                script {
                    sh "docker run ${DOCKER_IMAGE}:latest npm test"
                }
            }
        }

        stage('Publish Image..............') {
            steps {
                echo "Publicando la imagen Docker en el registro"
                script {
                    docker.withRegistry('https://registry.hub.docker.com', "${DOCKER_REGISTRY_CREDENTIALS}") {
                        docker.image("${DOCKER_IMAGE}:latest").push()
                    }
                }
            }
        }

        stage('Deploy Kubernetes...................') {
            steps {
                echo "Desplegando Kubernetes"
                script {
                    sh 'kubectl apply -f project.yaml'
                }
            }
        }
    }

    post {
        always {
            echo "Pipeline completado."
        }
        success {
            echo "Pipeline ejecutado correctamente."
        }
        failure {
            echo "Pipeline fallido."
        }
    }
}