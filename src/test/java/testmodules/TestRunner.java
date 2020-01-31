package testmodules;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;


@RunWith(Cucumber.class)
@CucumberOptions(
 features = "src/test/java/features"
 ,glue={"src/test/java/stepdefinitions.ReqresUser.java"}
 ,plugin = {"pretty", "html:target/report-html", "json:target/report.json"}
 ,dryRun = true
 ,monochrome =true
 ,strict= true
 )
 
public class TestRunner {

}





