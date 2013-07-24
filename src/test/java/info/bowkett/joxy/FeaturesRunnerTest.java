package info.bowkett.joxy;

import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@Cucumber.Options(features = {"src/test/features"}
//        ,
//        format = {"pretty"}/*,
//                "json:target/results.json",
//                "html:target/cucumber",
//                "junit:target/cucumber/junit.xml"}*/
)
public class FeaturesRunnerTest {

}

