package page_object_model.tests;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import page_object_model.pages.Login;

public class LoginTest extends HomePageTest {
    // Testing login functionality
    // Casewise testing - Testing different combinations for username, password pair exhaustively

    String[] config = readConfigValues("config.json"); // Retrieve correct credentials from config.json

    @DataProvider(name = "invalidLoginCredentials") // Test whether incorrect credentials are correctly validated in login
    public Object[][] invalidLoginCredentials() {
        return readDataFromXL("InvalidLoginCredentials");
    }

    @Test(dataProvider = "invalidLoginCredentials") // Test whether incorrect credentials are correctly validated in login
    public void testInvalidLogin(String[] credentials) {
        clickLogin(); // Navigate to Login page from Home Page
        Login login = PageFactory.initElements(browserFactory.getDriver(), Login.class);
        sleep(6000); // During this time, user can manually close any advertisement that appears, since they appear in various dynamic forms, covering the whole screen
        String currentUrl = login.submitLoginForm(credentials[0], credentials[1]);
        if (!(currentUrl.equals("https://riyasewana.com/login.php"))){
            Assert.assertEquals(currentUrl, "https://riyasewana.com/login.php", "Error in credential validation: Incorrect credentials were allowed ");
            System.out.println("Error in credential validation: Incorrect credentials were allowed ");
            login.captureScreenShot("invalid_login_error");
        } else {
            System.out.println("Incorrect credentials were denied successfully: " + credentials[0] + " - " + credentials[1]);
        }
        sleep(4000); // Observe application feedback
        login.emptyAllFields();
        sleep(2000); // Clear input fields for the next iteration
    }

    @Test // Test whether username & password fields work properly for correct credentials
    public void testValidLogin() {
        String correctUserName = config[0];
        String correctPassword = config[1];
        clickLogin(); // Navigate to Login page from Home Page
        Login login = PageFactory.initElements(browserFactory.getDriver(), Login.class);
        sleep(6000); // During this time, user can manually close any advertisement that appears, since they appear in various dynamic forms, covering the whole screen
        String currentUrl = login.submitLoginForm(correctUserName, correctPassword);
        if (currentUrl.equals("https://riyasewana.com/login.php")){
            Assert.assertNotEquals(currentUrl, "https://riyasewana.com/login.php", "Error in credential validation: Correct credentials were denied");
            System.out.println("Error in credential validation: Correct credentials were denied");
            login.captureScreenShot("valid_login_error");
        } else {
            System.out.println("Correct credentials were allowed successfully");
        }
    }

}
