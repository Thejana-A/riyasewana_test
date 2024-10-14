package page_object_model.tests;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;
import page_object_model.pages.LeasingOffer;
import page_object_model.utilities.ReadFromXLUtility;
import org.testng.annotations.DataProvider;


import java.util.ArrayList;

public class LeasingOfferTest extends HomePageTest{
    // Testing whether Name, Phone number fields work correctly, in Leasing Offer form
    // Boundary value analysis - considering that phone number must be a string of 10 digits, testing with strings of lengths 9 (invalid range 0-9), 10 (valid range) and 11 (invalid range 11 onwards).
    // Boundary value analysis - considering that name must be longer than 0 characters, testing with lengths 0 (invalid length range of 0 only; no negative lengths) and 1 (valid length range of 1 onwards)

    // Use ReadFromXLUtility utility, to retrieve leasing offer data from Excel sheet
    @DataProvider(name = "namePhoneDataProvider")
    public Object[][] readNameAndPhoneFromXL() {
        ReadFromXLUtility readFromXLUtility = new ReadFromXLUtility();
        String XLFileName = "/home/thejana/Documents/assignments/year_04/is4102/practicals/riyasewana/TestDataset.xlsx";
        String XLSheetName = "LeasingOfferData";
        ArrayList<ArrayList<String>> data = readFromXLUtility.readDataFromXL(XLFileName, XLSheetName);

        // Convert ArrayList to Object[][]
        Object[][] dataArray = new Object[data.size()][];
        for (int i = 0; i < data.size(); i++) {
            dataArray[i] = data.get(i).toArray();
        }
        return dataArray;
    }

    @Test(dataProvider = "namePhoneDataProvider") // Test whether incorrect Name, Phone input values are validated correctly
    public void testLeasingOfferForm(String name, String phone) {
        clickLeasingOffer(); // Navigate to Login page from Home Page
        LeasingOffer leasingOffer = PageFactory.initElements(browserFactory.getDriver(), LeasingOffer.class);
        sleep(10000); // During this time, user can manually close any advertisement that appears

        leasingOffer.submitLeasingOfferForm(name, phone);
        sleep(8000); // During this time, user can observe application feedback, before browser closes
        leasingOffer.emptyAllFields();
        sleep(2000); // To clear out all input fields, for next iteration
    }

}
