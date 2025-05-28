package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;

public class DriverFactory {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver initDriver(String browser, String baseUrl) throws Exception {
        ChromeOptions options = new ChromeOptions();
        driver.set(new ChromeDriver());
        //options.addArguments("--remote-allow-origins=*");
       // driver.set(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options));
        getDriver().get(baseUrl);
        return getDriver();
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}