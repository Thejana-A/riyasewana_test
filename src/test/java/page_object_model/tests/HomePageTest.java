package page_object_model.tests;

import jdk.jfr.Description;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import page_object_model.pages.*;
import page_object_model.utilities.*;
import org.testng.annotations.DataProvider;

import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.ArrayList;

public class HomePageTest extends BrowserUtility {

    String[] config = readConfigValues("config.json"); // Retrieve base url from config.json

    // Read values from config.json file
    public static String[] readConfigValues(String filePath) {
        String[] configValues = new String[5]; // Array to hold username, password, and URL
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONObject json = new JSONObject(content);
            configValues[0] = json.getJSONObject("settings").getString("username"); // Correct username
            configValues[1] = json.getJSONObject("settings").getString("password"); // Correct password
            configValues[2] = json.getJSONObject("settings").getString("url"); // Home page url
            configValues[3] = json.getJSONObject("settings").getString("browser"); // Default browser
            configValues[4] = json.getJSONObject("settings").getString("datafile"); // link to Excel file containing test data
        } catch (Exception e) {
            e.printStackTrace();
        }
        return configValues;
    }

    // Use this function to retrieve data from a sheet in Excel file, by passing sheet name as parameter
    public Object[][] readDataFromXL(String sheetName) {
        ReadFromXLUtility readFromXLUtility = new ReadFromXLUtility();
        String XLFileName = config[4];
        String XLSheetName = sheetName;
        ArrayList<ArrayList<String>> data = readFromXLUtility.readDataFromXL(XLFileName, XLSheetName);

        // Convert ArrayList to Object[][], since @DataProvider supports Object[][] type
        Object[][] dataArray = new Object[data.size()][];
        for (int i = 0; i < data.size(); i++) {
            dataArray[i] = data.get(i).toArray();
        }
        return dataArray;
    }

    // Navigate to Home Page of Riyasewana
    public HomePage openHomePage(){
        BasePage basePage = PageFactory.initElements(browserFactory.getDriver(), BasePage.class);
        String baseUrl = config[2];
        HomePage homePage = basePage.load_url(baseUrl);
        return homePage;
    }

    // Usage of LoggerUtility
    private LoggerUtility loggerUtility;
    private final String logFilePath = "testLog.txt"; // Logging to txt file
    private final String pdfFilePath = "testLog.pdf"; // Logging to pdf file

    // Set up the LoggerUtility before starting a test
    @BeforeTest
    public void setUp() {
        loggerUtility = new LoggerUtility();
        loggerUtility.startLogging(logFilePath);
    }

    // Stop logging after finishing a test, to prevent multiple tests in same log
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

    @Test // Test whether Contribute form link works
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

    // Use ReadFromXLUtility utility, to retrieve correct search data from Excel sheet
    @DataProvider(name = "validSearchData")
    public Object[][] validSearchData() {
        return readDataFromXL("ValidSearchData");
    }

    // Test whether vehicle search facility works correctly
    // Focussed fields: Make, Model, Type, Price range, City (Different combinations will be tested)
    // Ignored fields: Condition (Because there is no proper definition to verify its accuracy depending on visible data)
    @Test(dataProvider = "validSearchData")
    public void testValidSearch(String[] vehicleDataArray) {
        HomePage homePage = openHomePage();

        // To exit the loop, when data from Excel sheet is over
        if ((vehicleDataArray[0].isEmpty()) && (vehicleDataArray[1].isEmpty()) &&
                (vehicleDataArray[2].isEmpty()) && (vehicleDataArray[3].isEmpty()) &&
                (vehicleDataArray[4].isEmpty())) {
            return;
        }

        homePage.submitSearchFields(vehicleDataArray);
        SearchResult searchResult = PageFactory.initElements(browserFactory.getDriver(), SearchResult.class);
        sleep(5000); // During this time, user can manually close any advertisement that appears
        searchResult.scrollPage(0, 400);
        searchResult.testSearchResult(vehicleDataArray);
        sleep(4000);
        openHomePage();
        sleep(4000); // To close any advertisement appearing, manually
    }

    // Use ReadFromXLUtility utility, to retrieve invalid search data from Excel sheet
    @DataProvider(name = "invalidSearchData")
    public Object[][] invalidSearchData() {
        return readDataFromXL("InvalidSearchData");
    }

    @Test(dataProvider = "invalidSearchData")
    public void testInvalidSearch(String[] vehicleDataArray) {
        SoftAssert softAssert = new SoftAssert(); // To prevent failure of all later test cases
        HomePage homePage = openHomePage();

        // To exit the loop, when data from Excel sheet is over
        if ((vehicleDataArray[0].isEmpty()) && (vehicleDataArray[1].isEmpty()) &&
                (vehicleDataArray[2].isEmpty()) && (vehicleDataArray[3].isEmpty()) &&
                (vehicleDataArray[4].isEmpty())) {
            return;
        }

        homePage.submitSearchFields(vehicleDataArray);
        SearchResult searchResult = PageFactory.initElements(browserFactory.getDriver(), SearchResult.class);
        sleep(5000); // During this time, user can manually close any advertisement that appears, since they appear in various dynamic forms, covering the whole screen
        searchResult.scrollPage(0, 400);
        String feedbackMessage = searchResult.testInvalidSearchResult(vehicleDataArray);
        if(!(feedbackMessage.equals("No matching vehicles found. Please try with less search options."))){
            softAssert.assertEquals(feedbackMessage, "No matching vehicles found. Please try with less search options.", "Error: Invalid result was delivered");
            System.out.println("Error: Invalid result was delivered ->  " + vehicleDataArray[0] + " - " + vehicleDataArray[1] + " - " + vehicleDataArray[2] + " - " + vehicleDataArray[3] + " - " + vehicleDataArray[4] + "\n");
            searchResult.captureScreenShot("invalid_search_result");
        } else {
            System.out.println("Invalid result was denied successfully -> " + vehicleDataArray[0] + " - " + vehicleDataArray[1] + " - " + vehicleDataArray[2] + " - " + vehicleDataArray[3] + " - " + vehicleDataArray[4] + "\n");
        }
        sleep(4000);
        openHomePage();
        sleep(4000); // To close any advertisement appearing, manually
        softAssert.assertAll(); // This will report all failures
    }

    // Length adjustable sleep function for delaying execution whenever necessary, in any child class
    // In Riyasewana site, mostly this is useful for letting tester to quit commercial advertisements that appear in different dynamic forms
    public void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
