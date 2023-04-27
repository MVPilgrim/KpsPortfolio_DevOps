#! /usr/bin/groovy

/************************************************************
/* Module: UI: CreateDevTestRelease.groovy
/*
/* Description: Build ReactJs UI and store in Artifactory.
/*
/* Change Log:
/*   * KPS 07-31-2020: Initial version.
/**********************************************************/


import groovy.json.JsonSlurper

//@Library('SharedLibrary') _
@Library('KpsPortfolioSharedLibrary') _kpspflib


node('Generic') {
  try {
    jobName = JOB_BASE_NAME;
    initStage();
    buildUIStage();
    deployUIStage();
  } catch (Exception e) {
    echo "Exception caught: " + e.getMessage();
  }
}


/*
 * Intialize stage.
*/
def initStage() {
  stageName = "Init $jobName";
  stage("$stageName") {
    DisplayStageBanner(stageName);
    sh "whoami";
    env.PATH="$env.PATH:/home/ec2-user/.nvm/versions/node/v16.20.0/bin/node";
    ngnxDir="/usr/share/nginx";
  }
}


/*
 * Clone UI Stage.
*/
def buildUIStage() {
  stageName = "Build UI"
  stage("$stageName") {
    DisplayStageBanner("$stageName");

    //git credentialsId: 'Github', url: 'https://github.com/MVPilgrim/KpsPortfolio_UI/';
    git url: 'https://github.com/MVPilgrim/KpsPortfolio_UI/'

    sh """
      pwd
      ls -al
      npm run-script build
	  """
  }
}


/*
 * Deploy to webserver.
*/
def deployUIStage() {
  stageName = "Deploy UI"
  stage("$stageName") {
    DisplayStageBanner("$stageName");
	  sh """
	    nginxPath=/usr/share/nginx/html
      sudo mkdir -p $nginxPath
      sudo rm -f $nginxPath/*
      sudo cp -r dist/* $nginxPath
	  """
  }
}
