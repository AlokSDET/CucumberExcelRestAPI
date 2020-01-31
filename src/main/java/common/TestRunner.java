package common;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;


@RunWith(Cucumber.class)
@CucumberOptions(
 features = "src/test/java/features/ReqresUser.feature"
 ,glue={"stepdefinitions"}
 ,dryRun = false
 ,monochrome =true
 ,strict= false
 ,plugin = {"pretty", "html:target/cucumber-html-report", "json:target/cucumber-json-report"}
 )
 
public class TestRunner {

}





