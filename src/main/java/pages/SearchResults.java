package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchResults {

    private WebDriver driver;

    public SearchResults(WebDriver driver) {
        this.driver = driver;
    }

    public int getTrainCount() {
        List<WebElement> trains = driver.findElements(By.cssSelector(".trainName"));
        return trains.size();
    }

    public void printTrainNames() {
        List<WebElement> trains = driver.findElements(By.cssSelector(".trainName"));
        for (WebElement train : trains) {
            System.out.println(train.getText());
        }
    }

    public String getPageTitle() {
        return driver.getTitle();
    }
}
