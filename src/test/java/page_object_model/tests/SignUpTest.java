package page_object_model.tests;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;
import page_object_model.pages.SignUp;
import page_object_model.pages.UserAccount;
import org.testng.annotations.DataProvider;
import org.testng.asserts.SoftAssert;

public class SignUpTest extends HomePageTest {
    // Testing fields of signup form
    // Pairwise test design - since there are 7 fields to test, to minimize number of test cases, pairwise test design is used
    // Test case designed considering that phone number is a String of 10 digits

    // Use ReadFromXLUtility utility, to retrieve invalid signup dataset from Excel sheet
    @DataProvider(name = "invalidSignupData")
    public Object[][] invalidSignupData() {
        return readDataFromXL("InvalidSignUpData");
    }

    // Test whether fields of sign up form work properly for incorrect input
    @Test(dataProvider = "invalidSignupData")
    public void testInvalidSignUpData(String[] formData) {
        SoftAssert softAssert = new SoftAssert(); // To prevent failure of all later test cases
        clickSignUp(); // Navigate to Sign Up page from Home Page
        SignUp signUp = PageFactory.initElements(browserFactory.getDriver(), SignUp.class);
        sleep(6000); // During this time, user can manually close any advertisement that appears, since they appear in various dynamic forms, covering the whole screen
        signUp.emptyAllFields();
        sleep(2000); // To clear out all input fields, if they have values
        String currentUrl = signUp.submitSignUpForm(formData); // For each pair of password & re-enter password
        if(!(currentUrl.equals("https://riyasewana.com/register.php"))){
            softAssert.assertEquals(currentUrl, "https://riyasewana.com/register.php", "Error: Incorrect sign up data allowed");
            System.out.println("Error: Incorrect sign up data allowed ->  " + formData[0] + " - " + formData[1] + " - " + formData[2] + " - " + formData[3] + " - " + formData[4] + " - " + formData[5] + " - " + formData[6] + "\n");
            signUp.captureScreenShot("invalid_signup_error");
            UserAccount userAccount = PageFactory.initElements(browserFactory.getDriver(), UserAccount.class);
            userAccount.clickLogOut();
        } else {
            System.out.println("Incorrect signup data denied -> " + formData[0] + " - " + formData[1] + " - " + formData[2] + " - " + formData[3] + " - " + formData[4] + " - " + formData[5] + " - " + formData[6] + "\n");
        }
        sleep(4000); // During this time, tester can observe application feedback, before browser closes
        softAssert.assertAll(); // This will report all failures
    }

    // Use ReadFromXLUtility utility, to retrieve valid signup dataset from Excel sheet
    @DataProvider(name = "validSignupData")
    public Object[][] validSignupData() {
        return readDataFromXL("ValidSignUpData");
    }

    // Test whether fields of sign up form work properly for correct input
    @Test(dataProvider = "validSignupData")
    public void testValidSignUpData(String[] formData) {
        SoftAssert softAssert = new SoftAssert(); // To prevent failure of all later test cases
        clickSignUp(); // Navigate to Sign Up page from Home Page
        SignUp signUp = PageFactory.initElements(browserFactory.getDriver(), SignUp.class);
        sleep(6000); // During this time, user can manually close any advertisement that appears, since they appear in various dynamic forms, covering the whole screen
        signUp.emptyAllFields();
        sleep(2000); // To clear out all input fields, if they have values
        String currentUrl = signUp.submitSignUpForm(formData); // For each pair of password & re-enter password
        if(currentUrl.equals("https://riyasewana.com/register.php")){
            softAssert.assertNotEquals(currentUrl, "https://riyasewana.com/register.php", "Error: Correct sign up data not allowed");
            System.out.println("Error: Correct sign up data not allowed ->  " + formData[0] + " - " + formData[1] + " - " + formData[2] + " - " + formData[3] + " - " + formData[4] + " - " + formData[5] + " - " + formData[6] + "\n");
            signUp.captureScreenShot("valid_signup_error");
        } else {
            System.out.println("Correct signup data allowed -> " + formData[0] + " - " + formData[1] + " - " + formData[2] + " - " + formData[3] + " - " + formData[4] + " - " + formData[5] + " - " + formData[6] + "\n");
            UserAccount userAccount = PageFactory.initElements(browserFactory.getDriver(), UserAccount.class);
            userAccount.clickLogOut();
        }
        sleep(4000); // During this time, tester can observe application feedback, before browser closes
        softAssert.assertAll(); // This will report all failures
    }

}
