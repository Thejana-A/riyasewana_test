package page_object_model.utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;


import static page_object_model.tests.HomePageTest.readConfigValues;

public class BrowserFactory {
    static BrowserFactory browserFactory;

    ThreadLocal<WebDriver> threadLocal = ThreadLocal.withInitial(()-> {
        WebDriver driver = null;
        String[] config = readConfigValues("config.json"); // Retrieve base url from config.json
        String browserName = config[3];
        String browserType = System.getProperty("browser", browserName);
        switch(browserType){
            case "chrome":
                driver = WebDriverManager.chromedriver().create();
                break;
            // Uncomment this block for headless mode
            /*case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--headless"); // Enable headless mode for Chrome
                driver = WebDriverManager.chromedriver().capabilities(chromeOptions).create();
                break; */
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "edge":
                driver = WebDriverManager.edgedriver().create();
                break;
            default: new RuntimeException("The browser is undefined");
        }
        driver.manage().window().maximize();
        return driver;
    });

    private BrowserFactory(){}

    //static keyword is to make this available as in singleton pattern
    public static BrowserFactory getBrowserFactory(){
        if(browserFactory == null){
            browserFactory = new BrowserFactory();
        }
        return browserFactory;
    }
    public WebDriver getDriver(){
        return threadLocal.get();
    }
}
