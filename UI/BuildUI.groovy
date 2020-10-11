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
	cloneUIStage();
  } catch (Exception e) {
    echo "Exception caught: " + e.getMessage();
  }
}


/*
 * Intialize stage.
*/
def initStage() {
  stageName = "Build Init $jobName";
  stage("$stageName") {
    DisplayStageBanner(stageName);
    whoami
    env.PATH="$env.PATH:/home/ec2-user/.nvm/versions/node/v11.0.0/bin";
  }
}

/*
 * Clone UI Stage.
*/
def cloneUIStage() {
  stageName = "Clone UI"
  stage("$stageName") {
    DisplayStageBanner("$stageName");
	
	git credentialsId: 'Github', url: 'https://github.com/MVPilgrim/KpsPortfolio_UI/';
	
	sh """
	  pwd
	  ls -al
	  touch .npmrc
   	  npm config set strict-ssl false
   	  npm config set registry https://registry.npmjs.org
   	  
   	  rm -f package-lock.json || echo "cloneUIStage(): package-lock.json not found."
      whoami
      
      npm cache clean --force
      npm install -g npm@latest
      
   	  npm install
      
	  npm run-script build
	"""
  }
}