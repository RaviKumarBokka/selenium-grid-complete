package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class GoogleHomePage {
    private WebDriver driver;
    private By searchBox = By.name("q");

    public GoogleHomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void searchFor(String term) {
        driver.findElement(searchBox).sendKeys(term + "\n");
    }

    public String getTitle() {
        return driver.getTitle();
    }
}