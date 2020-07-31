#! /usr/bin/groovy

/************************************************************
/* Module: UI: CreateDevTestRelease.groovy
/*
/* Description: Build ReactJs UI and store in Artifactory.
/*
/* Change Log:
/*   * KPS 07-31-2020: Initial version.
/***********************************************************


import groovy.json.JsonSlurper

@Library('SharedLibrary') _
@Library('SpChartersSharedLibrary') _spchlib


node('Generic') {
  try {
    jobName = JOB_BASE_NAME;
    stage("Build Init ${jobName}") {
      echo "";
      echo "-----------------------------------------------------------------------------------------------------------------------";
      echo "----------------------------------------------------> Build Init. <----------------------------------------------------";
      echo "-----------------------------------------------------------------------------------------------------------------------";
	}
  } catch (Exception e) {
    echo "Exception caught: " + e.getMessage()";
  }
}