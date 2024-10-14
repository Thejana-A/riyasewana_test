package page_object_model.tests;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import page_object_model.pages.Login;
import page_object_model.utilities.ReadFromXLUtility;

import java.util.ArrayList;

public class LoginTest extends HomePageTest {
    // Testing login functionality
    // Casewise testing - Testing different combinations for username, password pair exhaustively

    @Test(dataProvider = "credentialsData") // Test whether incorrect credentials are correctly validated in login
    public void testIncorrectLogin(String username, String password) {
        clickLogin(); // Navigate to Login page from Home Page
        Login login = PageFactory.initElements(browserFactory.getDriver(), Login.class);
        sleep(10000); // During this time, user can manually close any advertisement that appears

        login.submitIncorrectCredentials(username, password);
        sleep(8000); // Observe application feedback
        login.emptyAllFields();
        sleep(2000); // Clear input fields for the next iteration
    }

    @Test // Test whether username & password fields work properly for correct credentials
    public void testCorrectLogin() {
        String[] config = readConfigValues("config.json"); // Retrieve correct credentials from config.json
        String correctUserName = config[0];
        String correctPassword = config[1];
        clickLogin(); // Navigate to Login page from Home Page
        Login login = PageFactory.initElements(browserFactory.getDriver(), Login.class);
        sleep(10000); // During this time, user can manually close any advertisement that appears
        login.submitCorrectCredentials(correctUserName, correctPassword);
    }

    // Use ReadFromXLUtility() to retrieve data from Excel sheet
    @DataProvider(name = "credentialsData")
    public Object[][] readCredentialsFromXL() {
        ReadFromXLUtility readFromXLUtility = new ReadFromXLUtility();
        String XLFileName = "/home/thejana/Documents/assignments/year_04/is4102/practicals/riyasewana/TestDataset.xlsx";
        String XLSheetName = "LoginCredentials";
        ArrayList<ArrayList<String>> data = readFromXLUtility.readDataFromXL(XLFileName, XLSheetName);

        Object[][] credentialsMatrix = new Object[data.size()][2];
        for (int i = 0; i < data.size(); i++) {
            credentialsMatrix[i][0] = data.get(i).get(0); // Username
            credentialsMatrix[i][1] = data.get(i).get(1); // Password
        }
        return credentialsMatrix;
    }
}
