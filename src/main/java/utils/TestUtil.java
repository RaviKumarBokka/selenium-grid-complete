package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import base.DriverFactory;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestUtil {
    public static String takeScreenshot(String name) throws Exception {
        File src = ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.FILE);
        String path = "screenshots/" + name + "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".png";
        File dest = new File(path);
        FileUtils.copyFile(src, dest);
        return dest.getAbsolutePath();
    }
}