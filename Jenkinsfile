def skipDeployment = false

pipeline {

agent any

environment {
        DOCKER_TOKEN=credentials('docker-push-secret')
        DOCKER_USER='nikos-kaparos'
        DOCKER_SERVER='ghcr.io'
        DOCKER_BACKEND='ghcr.io/nikos-kaparos/crowdfunding-backend'
        DOCKER_FRONTEND='ghcr.io/nikos-kaparos/crowdfunding-frontend'
        SKIP_DEPLOYMENT=false
        ARGO_REPO='git@github.com:nikos-kaparos/argocd.git'
        SSH_CREDS_ID ='gtihub-ssh'
    }


stages {


    stage('Test') {
        steps {
            dir('backend'){
                sh '''
                pwd
                echo "Start testing"
                mvn clean test
            '''
            }
        }
    }

    stage('Docker build and push backend') {
            steps {
                dir('backend'){
                    sh '''
                    HEAD_COMMIT=$(git rev-parse --short HEAD)
                    TAG=$HEAD_COMMIT-$BUILD_ID
                    docker build --rm -t $DOCKER_BACKEND:$TAG -t $DOCKER_BACKEND:latest -f Dockerfile .
                '''
                    sh '''
                    echo $DOCKER_TOKEN | docker login $DOCKER_SERVER -u $DOCKER_USER --password-stdin
                    docker push $DOCKER_BACKEND --all-tags
                '''
                }
            }
        }

    stage('Docker build and push frontend'){
            steps{
                dir('frontend'){
                    sh '''
                    pwd
                    COMMIT=$(git rev-parse --short HEAD)
                    TAG=$COMMIT-$BUILD_ID
                    docker build --rm -t $DOCKER_FRONTEND:$TAG -t $DOCKER_FRONTEND:latest -f Dockerfile .
                    '''
                    sh '''
                    echo $DOCKER_TOKEN | docker login $DOCKER_SERVER -u $DOCKER_USER --password-stdin
                    docker push $DOCKER_FRONTEND --all-tags
                    '''
                }
            }
        }

    stage('run ansible job'){
        steps{
            build job: 'ansible'
        }
    }

    stage('test connection to deployment env'){
        steps{
                def result= sh(
                    script: "ansible -i ~/workspace/ansible/hosts.yaml -m ping  deployment-vm",
                    returnStatus: true
                )
                if (result != 0) {
                    echo "⚠️ Failed to connect to deployment VM. Skipping deploy stages..."
                    skipDeployment = true
                } else {
                    echo "✅ Deployment VM is reachable."
                }
            // script{
            //     catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE'){
                    
            //     }
            // }
            // sh '''
            //             ansible -i ~/workspace/ansible/hosts.yaml -m ping  deployment-vm
            // '''
        }
        // post {
        //     failure{
        //         script{
        //             echo "⚠️ Failed to connect to deployment VM. Skipping deploy stages..."
        //             env.SKIP_DEPLOYMENT = true
        //         }
        //     }
        // }
    }

    stage('install docker and docker compose to deployment'){
        when {
            expression { return !skipDeployment }
        }
        steps{
            sh '''
                cd ../ansible
                pwd
                ansible-playbook /var/lib/jenkins/workspace/ansible/playbook/docker.yaml
            '''
        }
    }

    stage('deploy docker compose'){
        when {
            expression { return !skipDeployment }
        }
        steps{
            withEnv(["GITHUB_TOKEN=$DOCKER_TOKEN"]){
                sh'''
                    echo $DOCKER_TOKEN
                    echo $DOCKER_USER
                    export ANSIBLE_CONFIG=~/workspace/ansible/ansible.cfg
                    HEAD_COMMIT=$(git rev-parse --short HEAD)
                    TAG=$HEAD_COMMIT-$BUILD_ID
                    ansible-playbook -i ~/workspace/ansible/hosts.yaml ~/workspace/ansible/playbook/deploy_compose.yaml \
                    -e github_user=$DOCKER_USER \
                    -e backend_image=$DOCKER_BACKEND:$TAG \
                    -e frontend_image=$DOCKER_FRONTEND:$TAG
            '''
            }
        }
    }

    // stage('update Argo images'){
    //     steps('Clone Argo Repo'){
    //         sshagent(credentials: [env.SSH_CREDS_ID]){
    //             sh '''
    //                 echo "[INFO] Cloning ArgoCD repo..."
    //                 rm -rf argocd-repo
    //                 git clone $ARGO_REPO argocd-repo
    //                 cd argocd-repo

    //                 HEAD_COMMIT=$(git rev-parse --short HEAD)
    //                 TAG=$HEAD_COMMIT-$BUILD_ID

    //                 echo "[INFO] Updating backend image..."
    //                 sed -i "s|image: $DOCKER_BACKEND:.*|image: $DOCKER_BACKEND:latest|" spring/spring-deployment.yaml

    //                 echo "[INFO] Updating frontend image..."
    //                 sed -i "s|image: $DOCKER_FRONTEND:.*|image: $DOCKER_FRONTEND:latest|" vue/vue-deploymnet.yaml

    //                 git config user.name "jenkins"
    //                 git config user.email "jenkins@example.com"
    //                 git add .
    //                 git commit -m "Update image tags to latest from Jenkins"
    //                 git push
    //             '''
    //         }
    //     }
    // }

    // stage('deploy to kubernetes') {
    //         steps {
    //             sh '''
    //                 HEAD_COMMIT=$(git rev-parse --short HEAD)
    //                 TAG=$HEAD_COMMIT-$BUILD_ID
    //                 export ANSIBLE_CONFIG=~/workspace/ansible/ansible.cfg
    //                 ansible-playbook -i ~/workspace/ansible/hosts.yaml -e new_image=$DOCKER_PREFIX:$TAG ~/workspace/ansible/playbooks/k8s-update-spring-deployment.yaml
    //             '''
    //         }
    //     }

}

}