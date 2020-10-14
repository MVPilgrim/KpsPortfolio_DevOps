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
	deployStage();
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
    sh "whoami"'
    env.PATH="$env.PATH:/opt/node-v12.19.0-linux-x64/bin";
	ngnxDir="/usr/share/nginx";
  }
}


/*
 * Clone UI Stage.
*/
def buildStage() {
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
      
      npm cache clean --force
      npm install -g npm@latest
      npm install
	  
	  npm run-script build
	"""
  }
}


/*
 * Deploy to webserver.
*/
def deployStage() {
  stageName = "Clone UI"
  stage("$stageName") {
    DisplayStageBanner("$stageName");
	sh """
	  mv $ngnxDir/html $nginxDir/htmlBackup
	  mkdir -p $ngnxDir/html
	  cp dist/* $ngnxDir/html
	"""
  }
}