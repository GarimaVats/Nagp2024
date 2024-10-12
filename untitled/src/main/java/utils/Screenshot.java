package utils;

import logger.Log;
import mobile.AppiumHelper;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Screenshot {

    public String createDirectory() {
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        SimpleDateFormat month = new SimpleDateFormat("MMM");
        SimpleDateFormat day = new SimpleDateFormat("dd");
        Calendar now = Calendar.getInstance();
        String dirPath = null;

        try {

            dirPath = System.getProperty("user.dir") + File.separator + "target"
                    + File.separator + day.format(now.getTime()) + "-"
                    + month.format(now.getTime()) + "-" + year.format(now.getTime());
            File theDir = new File(dirPath);
            if (!theDir.exists()) {
                boolean result = false;
                try {
                    if (theDir.mkdirs())
                        result = true;
                } catch (SecurityException localSecurityException) {
                    return dirPath;
                }
                if (result) {
                    System.out.println("False");
                }
            }
        } catch (Exception e) {
            Log.error(e.getMessage());
        }
        return dirPath;
    }

    /**
     * Method to take a Create Directory & take Mobile screenshot.
     */
    private String captureMobileScreenshot() {
        String dirPath = null;
        try {
            dirPath = createDirectory();
            dirPath = dirPath + File.separator + System.currentTimeMillis() + ".png";
            File scrFile = ((TakesScreenshot) AppiumHelper.driverInit()).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File(dirPath));
        } catch (IOException e) {
            Log.error(e.getMessage());
        }
        return dirPath;
    }

    public File[] takesMobileScreenshot() {
        File[] file = null;
        file = new File[]{new File(captureMobileScreenshot())};
        return file;
    }

}
