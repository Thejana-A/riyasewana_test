package page_object_model.tests;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import page_object_model.pages.ContactUs;

public class ContactUsTest extends HomePageTest {
    // Testing whether Contact data fields work correctly, in Contact Us form
    // Boundary value analysis - Consider that a contact number must be 10 digits in length
    // Invalid boundary lengths - 9 and 11 ; valid boundary - 10

    // Data provider to retrieve invalid data from Excel file
    @DataProvider(name = "invalidContactData")
    public Object[][] invalidContactData(){
        return readDataFromXL("InvalidContactData");
    }

    // This method is to test invalid contact data, submitted to Contact Us form
    @Test(dataProvider = "invalidContactData") // Use the DataProvider
    public void testInvalidContactData(String[] contactData) {
        SoftAssert softAssert = new SoftAssert(); // To prevent failure of all later test cases
        clickContactUs(); // Navigate to Login page from Home Page
        ContactUs contactUs = PageFactory.initElements(browserFactory.getDriver(), ContactUs.class);
        sleep(5000); // During this time, user can manually close any advertisement that appears, since they appear in various dynamic forms, covering the whole screen
        contactUs.emptyAllFields();
        sleep(2000); // To clear out all input fields for next iteration
        String systemResponse = contactUs.submitContactUsForm(contactData);
        if(systemResponse.equals(" Mail Sent")){
            softAssert.assertNotEquals(systemResponse, " Mail Sent", "Error: Incorrect contact data was allowed");
            System.out.println("Error: Incorrect contact data was allowed - " + contactData[0] + "-" + contactData[1] + "-" + contactData[2] + "-" + contactData[3] + "\n");
            contactUs.captureScreenShot("invalid_contact_error");
        } else {
            System.out.println("Incorrect data was was denied - " + contactData[0] + "-" + contactData[1] + "-" + contactData[2] + "-" + contactData[3] + "\n");
        }
        softAssert.assertAll();
    }

    // Data provider to retrieve valid data from Excel file
    @DataProvider(name = "validContactData")
    public Object[][] validContactData(){
        return readDataFromXL("ValidContactData");
    }

    // This method is to test valid contact data, submitted to Contact Us form
    @Test(dataProvider = "validContactData") // Use the DataProvider
    public void testValidContactData(String[] contactData) {
        SoftAssert softAssert = new SoftAssert(); // To prevent failure of all later test cases
        clickContactUs(); // Navigate to Login page from Home Page
        ContactUs contactUs = PageFactory.initElements(browserFactory.getDriver(), ContactUs.class);
        sleep(5000); // During this time, user can manually close any advertisement that appears, since they appear in various dynamic forms, covering the whole screen
        contactUs.emptyAllFields();
        sleep(2000); // To clear out all input fields for next iteration
        String systemResponse = contactUs.submitContactUsForm(contactData);
        if(!(systemResponse.equals(" Mail Sent"))){
            softAssert.assertEquals(systemResponse, " Mail Sent", "Error: Correct contact data was denied");
            System.out.println("Error: Correct contact data was denied - " + contactData[0] + "-" + contactData[1] + "-" + contactData[2] + "-" + contactData[3] + "\n");
            contactUs.captureScreenShot("valid_contact_error");
        } else {
            System.out.println("Correct contact data was was allowed - " + contactData[0] + "-" + contactData[1] + "-" + contactData[2] + "-" + contactData[3] + "\n");
        }
        softAssert.assertAll();
    }

}
