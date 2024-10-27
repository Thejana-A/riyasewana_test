package page_object_model.tests;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import page_object_model.pages.LeasingOffer;
import org.testng.annotations.DataProvider;

public class LeasingOfferTest extends HomePageTest{
    // Testing whether fields in Leasing Offer form work correctly
    // Equivalence partitioning - considering that phone number must be a string of 10 digits, testing with strings of lengths < 10 and 10 <.
    // Pattern matching - considering that name should contain only letters

    // Use ReadFromXLUtility utility, to retrieve invalid leasing offer data from Excel sheet
    @DataProvider(name = "invalidLeasingOfferData")
    public Object[][] invalidLeasingOfferData() {
        return readDataFromXL("InvalidLeasingOfferData");
    }

    @Test(dataProvider = "invalidLeasingOfferData") // Test whether incorrect values are validated correctly
    public void testInvalidLeasingOfferData(String[] leasingOfferData) {
        SoftAssert softAssert = new SoftAssert(); // To prevent failure of all later test cases
        clickLeasingOffer(); // Navigate to Login page from Home Page
        LeasingOffer leasingOffer = PageFactory.initElements(browserFactory.getDriver(), LeasingOffer.class);
        sleep(5000); // During this time, user can manually close any advertisement that appears, since they appear in various dynamic forms, covering the whole screen
        leasingOffer.emptyAllFields();
        sleep(2000); // To clear out all input fields, for next iteration
        String systemResponse = leasingOffer.submitLeasingOfferForm(leasingOfferData);

        if(systemResponse.equals("Thank you, One of our leasing agents will contact you.")){
            softAssert.assertNotEquals(systemResponse, "Thank you, One of our leasing agents will contact you.", "Error: Incorrect leasing data was allowed");
            System.out.println("Error: Incorrect leasing data was allowed -> " + leasingOfferData[0] + " - " + leasingOfferData[1] + " - " + leasingOfferData[2] + "\n");
            leasingOffer.captureScreenShot("invalid_leasing_offer_error");
        } else {
            System.out.println("Incorrect leasing data was denied successfully ->  " + leasingOfferData[0] + " - " + leasingOfferData[1] + " - " + leasingOfferData[2] + "\n");
        }
        softAssert.assertAll(); // This will report all failures
    }

    // Use ReadFromXLUtility utility, to retrieve invalid leasing offer data from Excel sheet
    @DataProvider(name = "validLeasingOfferData")
    public Object[][] validLeasingOfferData() {
        return readDataFromXL("ValidLeasingOfferData");
    }

    @Test(dataProvider = "validLeasingOfferData") // Test whether correct values are validated correctly
    public void testValidLeasingOfferData(String[] leasingOfferData) {
        SoftAssert softAssert = new SoftAssert(); // To prevent failure of all later test cases
        clickLeasingOffer(); // Navigate to Login page from Home Page
        LeasingOffer leasingOffer = PageFactory.initElements(browserFactory.getDriver(), LeasingOffer.class);
        sleep(5000); // During this time, user can manually close any advertisement that appears, since they appear in various dynamic forms, covering the whole screen
        leasingOffer.emptyAllFields();
        sleep(2000); // To clear out all input fields, for next iteration
        String systemResponse = leasingOffer.submitLeasingOfferForm(leasingOfferData);

        if(!(systemResponse.equals("Thank you, One of our leasing agents will contact you."))){
            softAssert.assertEquals(systemResponse, "Thank you, One of our leasing agents will contact you.", "Error: Correct leasing data was denied");
            System.out.println("Error: Correct leasing data was fenied -> " + leasingOfferData[0] + " - " + leasingOfferData[1] + " - " + leasingOfferData[2] + "\n");
            leasingOffer.captureScreenShot("valid_leasing_offer_error");
        } else {
            System.out.println("Correct leasing data was allowed successfully ->  " + leasingOfferData[0] + " - " + leasingOfferData[1] + " - " + leasingOfferData[2] + "\n");
        }
        softAssert.assertAll(); // This will report all failures
    }

}
