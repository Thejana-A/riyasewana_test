package page_object_model.tests;

import jdk.jfr.Description;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import page_object_model.pages.*;
import page_object_model.utilities.BrowserUtility;
import page_object_model.utilities.LoggerUtility;
import page_object_model.utilities.PDFUtility;
import page_object_model.utilities.ReadFromXLUtility;
import org.testng.annotations.DataProvider;

import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.ArrayList;

public class HomePageTest extends BrowserUtility {

    // Read values from config.json file
    public static String[] readConfigValues(String filePath) {
        String[] configValues = new String[4]; // Array to hold username, password, and URL
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONObject json = new JSONObject(content);
            configValues[0] = json.getJSONObject("settings").getString("username");
            configValues[1] = json.getJSONObject("settings").getString("password");
            configValues[2] = json.getJSONObject("settings").getString("url");
            configValues[3] = json.getJSONObject("settings").getString("browser");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return configValues;
    }

    // Navigate to Home Page of Riyasewana
    public HomePage openHomePage(){
        BasePage basePage = PageFactory.initElements(browserFactory.getDriver(), BasePage.class);
        String[] config = readConfigValues("config.json"); // Retrieve base url from config.json
        String baseUrl = config[2];
        HomePage homePage = basePage.load_url(baseUrl);
        return homePage;
    }

    private LoggerUtility loggerUtility;
    private final String logFilePath = "testLog.txt";
    private final String pdfFilePath = "testLog.pdf";

    @BeforeTest
    public void setUp() {
        loggerUtility = new LoggerUtility();
        loggerUtility.startLogging(logFilePath);
    }

    @AfterTest
    public void tearDown() {
        loggerUtility.stopLogging();
        PDFUtility.createPDF(logFilePath, pdfFilePath);
        System.out.println("Log has been written to PDF.");
    }

    @Test // Test whether Login link works
    public void clickLogin() {
        HomePage homePage = openHomePage();
        Login login = homePage.clickLink("Login");
        login.scrollPage(0,20);
    }

    @Test // Test whether Contact Us link works
    public void clickContactUs() {
        HomePage homePage = openHomePage();
        ContactUs contactUs = homePage.clickLink("Contact Us");
        contactUs.scrollPage(0,20);
    }

    @Test // Test whether Contribute link works
    public void clickContribute() {
        HomePage homePage = openHomePage();
        Contribute contribute = homePage.clickLink("Contribute");
        contribute.scrollPage(0,20);
    }

    @Test // Test whether Leasing Offer link works
    public void clickLeasingOffer() {
        HomePage homePage = openHomePage();
        LeasingOffer leasingOffer = homePage.clickLink("Leasing Offer");
        leasingOffer.scrollPage(0,20);
    }

    @Test // Test whether Sign Up ("Post a Free Ad") link works
    public void clickSignUp() {
        HomePage homePage = openHomePage();
        SignUp signUp = homePage.clickLink("Post a Free Ad");
        signUp.scrollPage(0, 20);
    }

    // Use ReadFromXLUtility utility, to retrieve searching vehicle dataset from Excel sheet
    @DataProvider(name = "vehicleDataProvider")
    public Object[][] readVehicleDataFromXL() {
        ReadFromXLUtility readFromXLUtility = new ReadFromXLUtility();
        String XLFileName = "/home/thejana/Documents/assignments/year_04/is4102/practicals/riyasewana/TestDataset.xlsx";
        String XLSheetName = "SearchVehilcleForm";
        ArrayList<ArrayList<String>> data = readFromXLUtility.readDataFromXL(XLFileName, XLSheetName);

        // Convert ArrayList to Object[][]
        Object[][] dataArray = new Object[data.size()][];
        for (int i = 0; i < data.size(); i++) {
            dataArray[i] = data.get(i).toArray();
        }
        return dataArray;
    }

    // Test whether vehicle search facility works correctly
    // Focussed fields: Make, Model, Type, Price range, City (Different combinations will be tested)
    // Ignored fields: Condition (Because there is no proper definition to verify its accuracy depending on visible data)
    @Test(dataProvider = "vehicleDataProvider")
    public void testVehicleSearchFacility(String[] vehicleDataArray) {
        HomePage homePage = openHomePage();

        // To exit the loop, when data from Excel sheet is over
        if ((vehicleDataArray[0].isEmpty()) && (vehicleDataArray[1].isEmpty()) &&
                (vehicleDataArray[2].isEmpty()) && (vehicleDataArray[3].isEmpty()) &&
                (vehicleDataArray[4].isEmpty())) {
            return;
        }

        homePage.submitSearchFields(vehicleDataArray);
        SearchResult searchResult = PageFactory.initElements(browserFactory.getDriver(), SearchResult.class);
        sleep(8000); // During this time, user can manually close any advertisement that appears
        searchResult.scrollPage(0, 400);
        searchResult.testSearchResult(vehicleDataArray);
        sleep(5000);
        openHomePage();
        sleep(6000); // To close any advertisement appearing, manually
    }

    // Length adjustable sleep function for delaying execution whenever necessary, in any child class
    public void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
