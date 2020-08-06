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
  }
}

/*
 * Clone UI Stage.
*/
def cloneUIStage() {
  stageName = "Clone UI"
  stage("$stageName") {
    DisplayStageBanner("$stageName");
  }
}