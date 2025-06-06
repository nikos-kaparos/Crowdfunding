pipeline {

agent any

environment {
        DOCKER_TOKEN=credentials('docker-push-secret')
        DOCKER_USER='nikos-kaparos'
        DOCKER_SERVER='ghcr.io'
        DOCKER_BACKEND='ghcr.io/nikos-kaparos/crowdfunding-backend'
        DOCKER_FRONTEND='ghcr.io/nikos-kaparos/crowdfunding-frontend'
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
            sh '''
                ansible -i ~/workspace/ansible/hosts.yaml -m ping  deployment-vm
            '''

        }
    }

    stage('install docker and docker compose to deployment'){
        steps{
            sh '''
                cd ../ansible
                pwd
                ansible-playbook /var/lib/jenkins/workspace/ansible/playbook/docker.yaml
            '''
        }
    }

    stage('deploy docker compose'){
        steps{
            sh'''
                export ANSIBLE_CONFIG=ansible/ansible.cfg
                HEAD_COMMIT=$(git rev-parse --short HEAD)
                TAG=$HEAD_COMMIT-$BUILD_ID
                ansible-playbook -i ~/workspace/ansible/hosts.yaml /var/lib/jenkins/workspace/ansible/playbook/deploy_compose.yaml \
                -e github_user=$DOCKER_USER \
                -e github_token="$DOCKER_TOKEN" \
                -e backend_image=$DOCKER_BACKEND:$TAG \
                -e frontend_image=$DOCKER_FRONTEND:$TAG
        '''
        }
    }

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