package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchResults {

    private WebDriver driver;

    public SearchResults(WebDriver driver) { this.driver = driver; }

    public int getTrainCount() {
        List<WebElement> trains = driver.findElements(By.xpath("//*[@id=\"divMain\"]/div/app-train-list/div[4]/div/div[5]/div/div[1]/app-train-avl-enq/div[1]/div[1]/div[1]/strong"));
        return trains.size();
    }

    public void printTrainNames() {
        List<WebElement> trains = driver.findElements(By.xpath("//*[@id=\"divMain\"]/div/app-train-list/div[4]/div/div[5]/div/div[1]/app-train-avl-enq/div[1]/div[1]/div[1]/strong"));
        int count=1;
        for (WebElement train : trains) {
            System.out.println("Train " +count+ ":"  +count +train.getText());
            count++;
        }
    }

    public String getPageTitle() { return driver.getTitle(); }
}
