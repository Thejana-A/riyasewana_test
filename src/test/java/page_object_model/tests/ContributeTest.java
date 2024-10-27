package page_object_model.tests;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import page_object_model.pages.Contribute;

public class ContributeTest extends HomePageTest {
    // Testing whether incorrect Name, Contact Number, Email fields are validated in Contribute form
    // Boundary value analysis - considering that phone number must be a string of 10 digits, testing with strings of lengths 9 and 11.

    @DataProvider(name = "invalidContributeData") // Use ReadFromXLUtility utility, to retrieve invalid contribute form data from Excel sheet
    public Object[][] invalidContributeData() {
        return readDataFromXL("InvalidContributeData");
    }

    @Test(dataProvider = "invalidContributeData") // Test whether incorrect contribute form data are validated correctly
    public void testInvalidContributeData(String[] contributeData) {
        SoftAssert softAssert = new SoftAssert(); // To prevent failure of all later test cases
        clickContribute(); // Navigate to Login page from Home Page
        Contribute contribute = PageFactory.initElements(browserFactory.getDriver(), Contribute.class);
        sleep(6000); // During this time, user can manually close any advertisement that appears, since they appear in various dynamic forms, covering the whole screen
        contribute.emptyAllFields();
        sleep(2000); // To clear out all input fields, for next iteration
        String currentUrl = contribute.submitContributeForm(contributeData);
        if (currentUrl.equals("https://www.payhere.lk/pay/checkout")){
            softAssert.assertNotEquals(currentUrl, "https://www.payhere.lk/pay/checkout", "Error in validation of contribute form data");
            System.out.println("Error: Invalid data was allowed " + contributeData[0] + " - " + contributeData[1] + " - " + contributeData[2] + "\n");
            contribute.captureScreenShot("invalid_contribute_form_error");
        } else {
            System.out.println("Invalid values were denied successfully: " + contributeData[0] + " - " + contributeData[1] + " - " + contributeData[2] + "\n");
        }
        softAssert.assertAll(); // This will report all failures
    }

    @DataProvider(name = "validContributeData") // Use ReadFromXLUtility utility, to retrieve valid contribute form data from Excel sheet
    public Object[][] validContributeData() {
        return readDataFromXL("ValidContributeData");
    }

    @Test(dataProvider = "validContributeData") // Test whether correct contribute form data are validated correctly
    public void testValidContributeData(String[] contributeData) {
        SoftAssert softAssert = new SoftAssert(); // To prevent failure of all later test cases
        clickContribute(); // Navigate to Login page from Home Page
        Contribute contribute = PageFactory.initElements(browserFactory.getDriver(), Contribute.class);
        sleep(6000); // During this time, user can manually close any advertisement that appears, since they appear in various dynamic forms, covering the whole screen
        contribute.emptyAllFields();
        sleep(2000); // To clear out all input fields, for next iteration
        String currentUrl = contribute.submitContributeForm(contributeData);
        if (!(currentUrl.equals("https://www.payhere.lk/pay/checkout"))){
            softAssert.assertEquals(currentUrl, "https://www.payhere.lk/pay/checkout", "Error in validation of correct data in contribute form");
            System.out.println("Error: Valid data was denied " + contributeData[0] + " - " + contributeData[1] + " - " + contributeData[2] + "\n");
            contribute.captureScreenShot("valid_contribute_form_error");
        } else {
            System.out.println("Valid values were allowed successfully: " + contributeData[0] + " - " + contributeData[1] + " - " + contributeData[2] + "\n");
        }
        softAssert.assertAll(); // This will report all failures
    }


}
