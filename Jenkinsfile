pipeline {
    agent any
    environment {
        DOCKER_PREFIX = '683863474030.dkr.ecr.us-east-1.amazonaws.com'
    }
    tools {
        jdk 'openjdk-14'
    }
    stages {
        stage('Login') {
            steps {
                sh '$(aws ecr get-login --no-include-email --registry-ids 683863474030 --region us-east-1)'
            }
        }
        stage('Build') {
            steps {
                checkout scm
                sh "./gradlew clean build"
            }
        }
        stage('Sonar') {
            environment {
                scannerHome = tool name: 'sonarqube', type: 'hudson.plugins.sonar.SonarRunnerInstallation';
            }
            steps {
                withSonarQubeEnv( 'sonarqube') {
                    sh "${scannerHome}/bin/sonar-scanner"
                }
            }
        }
        stage('Push') {
            steps {
                sh '''
                  echo ECHO_DOCKER_PREFIX: $DOCKER_PREFIX
                  echo ECHO_BUILD_NUMBER: $BUILD_NUMBER
                  
                  export PROJ_VERS=$(./gradlew properties --no-daemon --console=plain -q | grep "^version:" | awk '{printf $2}')
                  echo ECHO_PROJ_VERS:$PROJ_VERS
                  
                  export PROJ_NAME=$(./gradlew properties --no-daemon --console=plain -q | grep "^name:" | awk '{printf $2}')
                  echo ECHO_PROJ_NAME:$PROJ_NAME
                  
                  export JAR_FILE=./build/libs/$PROJ_NAME-$PROJ_VERS.jar
                  echo ECHO_JAR_FILE: $JAR_FILE
                  
                  docker build . -t $PROJ_NAME --build-arg JAR_FILE=$JAR_FILE
                  docker tag $PROJ_NAME:latest $DOCKER_PREFIX/$PROJ_NAME:latest
                  docker tag $PROJ_NAME:latest $DOCKER_PREFIX/$PROJ_NAME:$PROJ_VERS-$BUILD_NUMBER

                  aws ecr describe-repositories --repository-names $PROJ_NAME --region us-east-1 || \
                  aws ecr create-repository --repository-name $PROJ_NAME --region us-east-1

                  docker push $DOCKER_PREFIX/$PROJ_NAME:latest
                  docker push $DOCKER_PREFIX/$PROJ_NAME:$PROJ_VERS-$BUILD_NUMBER
                '''
            }
        }
    }
}
