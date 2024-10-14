package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.security.PrivateKey;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HomePage {
    private WebDriver driver;
    private Actions actions;

    private By fromStationLocator = By.xpath("//input[@class='ng-tns-c57-8 ui-inputtext ui-widget ui-state-default ui-corner-all ui-autocomplete-input ng-star-inserted']");
    private By toStationLocator = By.xpath("//input[@class='ng-tns-c57-9 ui-inputtext ui-widget ui-state-default ui-corner-all ui-autocomplete-input ng-star-inserted']");
    private By searchLocator = By.xpath("//button[normalize-space()='Search']");

    private By dateInputLocator = By.xpath("//input[@class='ng-tns-c58-10 ui-inputtext ui-widget ui-state-default ui-corner-all ng-star-inserted']");
    private By calendarDaysLocator = By.xpath("//table[@class='ui-datepicker-calendar ng-tns-c58-10']");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.actions = new Actions(driver);
    }

    public void clearAndEnterFromStation(String fromStation) {
        WebElement fromTextbox = driver.findElement(fromStationLocator);
        fromTextbox.clear(); // Clear the existing text
        fromTextbox.sendKeys(fromStation); // Enter the new value
    }

    public void clearAndEnterToStation(String toStation) {
        WebElement toTextbox = driver.findElement(toStationLocator);
        toTextbox.clear(); // Clear the existing text
        toTextbox.sendKeys(toStation); // Enter the new value
    }

    public void searchTrains(String fromStation, String toStation) {
        clearAndEnterFromStation(fromStation);
        clearAndEnterToStation(toStation);
        WebElement searchButton = driver.findElement(searchLocator);
        searchButton.click();
    }

    private void scrollDown() {
        actions.moveByOffset(0, 70).perform();
    }

    public String selectDateAfterDays(int days) {

        // Click on the date picker to open it
        WebElement dateInput = driver.findElement(dateInputLocator);
        dateInput.click();

        // Wait for the calendar to be visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(calendarDaysLocator));

        // Get today's date
        LocalDate today = LocalDate.now();
        LocalDate targetDate = today.plusDays(days); // Get the date 4 days after today

        // Format target date to extract day
        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dayToSelect = targetDate.format(dayFormatter);

        // Select the 5th day from today in the date picker
        List<WebElement> dayElements = driver.findElements(calendarDaysLocator);
        for (WebElement dayElement : dayElements) {
            if (dayElement.getText().equals(dayToSelect)) {
                dayElement.click(); // Click the desired date
                break;
            }
        }
        return targetDate.format(dayFormatter);
    }
}



//    public String getFormattedDate(int daysFromToday) {
//        LocalDate date = LocalDate.now().plusDays(daysFromToday);
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        String formattedDate = date.format(formatter);
//
//        // Click on the date picker to open it
//        driver.findElement(dateLocator).click();
//
//        // Select the desired date from the calendar
//        selectDateFromCalendar(formattedDate);
//        return date.format(formatter);
//    }
//    private void selectDateFromCalendar(String date) {
//        // Assuming the date picker displays dates in a grid
//        // You may need to adjust the locators and logic based on the actual HTML structure of the date picker
//
//        // Example: Locate the calendar elements
//        WebElement calendar = driver.findElement(By.className("ui-datepicker-calendar")); // Use the correct class
//
//        // Loop through the days in the calendar
//        for (WebElement day : calendar.findElements(By.tagName("td"))) {
//            if (day.getText().equals(date.split("/")[0])) { // Check the day part
//                day.click(); // Click the date
//                break; // Exit the loop once the date is selected
//            }
        //}


