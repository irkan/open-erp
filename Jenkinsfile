def PipelineMethods = new PipelineMethods(this)
def globalMethods = new GlobalMethods(this)

pipeline {

    agent {
        label "master"
    }

    environment {
        PROJECT_NAME = sh(script: "echo $env.GIT_URL | awk -F '/' '{print \$(NF-1)}'", returnStdout: true).trim()
        JAVA_HOME = '/usr/lib/jvm/zulu-11-amd64/'
	    DOCKER_REPO = 'bb'
	    MS_NAME = env.GIT_URL.replaceFirst(/^.*\/([^\/]+?).git$/, '$1')
	    DOCKER_REPO_PASSWORD = credentials('docker_api_token')
        DEPLOY_ENV = "${env.GIT_BRANCH}"
        CONTAINER_NAME = "$PROJECT_NAME-$MS_NAME"
        DOCKER_IMG_NAME = "nexus.kblab.local:8083/v1/repositories/$DOCKER_REPO/$PROJECT_NAME/$DEPLOY_ENV/$MS_NAME:$BUILD_TIMESTAMP"
        APP_PORT = "7782"
	EXPOSED_PORT = "8080"
	DEVELOP_ENV_HOST = '10.0.32.201'
	PREPROD_ENV_HOST = '10.0.4.81'
	MASTER_ENV_HOST = '10.0.6.181'
    }

    stages {
        stage ("Setup Jenkins Jobs name"){
            steps {
                script {
		    echo "Start condition check"
    		    build job: 'printuser'
                    def slaveJob = build job: 'printuser'
                    println slaveJob.rawBuild.log  
                    currentBuild.displayName = "${GIT_BRANCH}-${BUILD_NUMBER}"
                }
            }
        }

        stage("Check branch name") {
            steps {
                echo "${DEPLOY_ENV}"
                echo "${GIT_BRANCH}"
                echo "${PROJECT_NAME}-${MS_NAME}"
                echo "${CONTAINER_NAME}"
                echo "${env.JOB_NAME}"
            }
        }

        stage("Compile and Build war file") {
            steps {
                echo 'Package building has being started'
                sh "chmod +x ./gradlew && ./gradlew bootWar -Pjenkins -Pprofiles=${DEPLOY_ENV} --no-daemon"
            }
        }

        /* stage('SonarQube analysis') {
            environment {
                scannerHome = tool 'SonarQube Scanner'
            }
            steps {
               withSonarQubeEnv('sonarqubeprod') {
                   sh '${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=smecb-$MS_NAME-$GIT_BRANCH -Dsonar.sources=. -Dsonar.java.binaries=src/main/java/'
            }
               script { globalMethods.waitSonarQube 3, 30 }
        }
       } */

       stage ('Fortify Check'){
            when { expression { env.DEPLOY_ENV == "preprod" } }
            steps{
                 script {

                    PipelineMethods.fortifySscUpload "./gradlew bootWar -Pjenkins -Pprofiles=${DEPLOY_ENV} --no-daemon", "${DEPLOY_ENV}"
                 }
            }
       }



        stage ("Build docker image and push to Nexus Registry"){
            steps{
                echo "Scip Stage"
                sh "docker build --rm --build-arg DEPLOY_ENV=$DEPLOY_ENV -t $DOCKER_IMG_NAME ."
                sh "docker login -u jenkins -p $DOCKER_REPO_PASSWORD $DOCKER_IMG_NAME"
                echo 'Start pushing to nexus repo...'
                sh  "docker push $DOCKER_IMG_NAME"
                echo "Deleting local Docker image"
                sh "docker rmi $DOCKER_IMG_NAME"
            }
        }

        stage("Deploy to Develop Docker"){
            when { expression { env.DEPLOY_ENV == "develop" } }
            steps{
                echo "Scip Stage"
                withCredentials([usernamePassword(credentialsId: "bb-vault-${env.DEPLOY_ENV }", usernameVariable: 'VAULT_ID', passwordVariable: 'SECRET_ID')]){
               	 	sh 'ssh jenkins@$DEVELOP_ENV_HOST "docker rm -f $CONTAINER_NAME || exit 0"'
               	 	sh 'ssh jenkins@$DEVELOP_ENV_HOST "docker login -u jenkins -p $DOCKER_REPO_PASSWORD $DOCKER_IMG_NAME"'
               		sh 'ssh jenkins@$DEVELOP_ENV_HOST "docker run --restart unless-stopped -d -t -m 6g --network=bb_subnet -p $APP_PORT:$EXPOSED_PORT -e VAULT_ROLE_ID=$VAULT_ID -e VAULT_SECRET_ID=$SECRET_ID  -v /var/auth/docs:/var/auth/docs -v /opt/bb-logs:/var/mblogs --name=$CONTAINER_NAME $DOCKER_IMG_NAME"'
                }
            } 
        }
        stage("Deploy to Preprod Docker"){
            when { expression { env.DEPLOY_ENV == "preprod" } }
            steps{
                withCredentials([usernamePassword(credentialsId: "bb-vault-${env.DEPLOY_ENV }", usernameVariable: 'VAULT_ID', passwordVariable: 'SECRET_ID')]){
               	 	sh 'ssh jenkins@$PREPROD_ENV_HOST "docker rm -f $CONTAINER_NAME || exit 0"'
                	sh 'ssh jenkins@$PREPROD_ENV_HOST "docker login -u jenkins -p $DOCKER_REPO_PASSWORD $DOCKER_IMG_NAME"'
               		sh 'ssh jenkins@$DEVELOP_ENV_HOST "docker run --restart unless-stopped -d -t -m 6g --network=bb_subnet -p $APP_PORT:$EXPOSED_PORT -e VAULT_ROLE_ID=$VAULT_ID -e VAULT_SECRET_ID=$SECRET_ID  --log-driver=fluentd --log-opt  fluentd-address=10.0.32.201:24224 --log-opt tag="bb-birbank-business" -v /var/auth/docs:/var/auth/docs -v /opt/bb-logs:/var/mblogs --name=$CONTAINER_NAME $DOCKER_IMG_NAME"'
                }
            }
        }

        stage("Deploy to Master Docker"){
            when { expression { env.DEPLOY_ENV == "master"} }
            steps{
                withCredentials([usernamePassword(credentialsId: "bb-vault-${env.DEPLOY_ENV }", usernameVariable: 'VAULT_ID', passwordVariable: 'SECRET_ID')]){
                	sh 'ssh jenkins@$MASTER_ENV_HOST "docker rm -f $CONTAINER_NAME || exit 0"'
                	sh 'ssh jenkins@$MASTER_ENV_HOST "docker login -u jenkins -p $DOCKER_REPO_PASSWORD $DOCKER_IMG_NAME"'
               		sh 'ssh jenkins@$DEVELOP_ENV_HOST "docker run --restart unless-stopped -d -t -m 6g --network=bb_subnet -p $APP_PORT:$EXPOSED_PORT -e VAULT_ROLE_ID=$VAULT_ID -e VAULT_SECRET_ID=$SECRET_ID  --log-driver=fluentd --log-opt  fluentd-address=10.0.32.201:24224 --log-opt tag="bb-birbank-business" -v /var/auth/docs:/var/auth/docs -v /opt/bb-logs:/var/mblogs --name=$CONTAINER_NAME $DOCKER_IMG_NAME"'
                }
            }
        }

    }


    post {
        always {
          	cleanWs()
            script {
                currentBuild.result = currentBuild.result ?: 'SUCCESS'
                notifyBitbucket(
                        credentialsId: '5563c8eb-e93d-42ee-8130-b5b534dfc531',
                        disableInprogressNotification: false,
                        stashServerBaseUrl: 'https://bitbucket.kapitalbank.az'
                )
            }
        }
    }
}  
