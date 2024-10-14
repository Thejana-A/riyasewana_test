package page_object_model.tests;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.*;
import page_object_model.pages.ContactUs;
import page_object_model.utilities.ReadFromXLUtility;

import java.util.ArrayList;

public class ContactUsTest extends HomePageTest {
    // Testing whether Contact Number field works correctly, in Contact Us form
    // Equivalence partitioning - Consider that a contact number must be 10 digits in length
    // Data partitions as length of Contact Number are 0-9 (invalid), 10 (valid), and above 10 (invalid)

    @DataProvider(name = "phoneNumberData")
    public Object[][] readPhoneNumberFromXL() {
        ReadFromXLUtility readFromXLUtility = new ReadFromXLUtility();
        String XLFileName = "/home/thejana/Documents/assignments/year_04/is4102/practicals/riyasewana/TestDataset.xlsx";
        String XLSheetName = "ContactUs";
        ArrayList<ArrayList<String>> data = readFromXLUtility.readDataFromXL(XLFileName, XLSheetName);

        // Convert ArrayList<ArrayList<String>> to Object[][]
        Object[][] phoneNumberMatrix = new Object[data.size()][];
        for (int i = 0; i < data.size(); i++) {
            phoneNumberMatrix[i] = new Object[]{data.get(i).get(0)}; // Assuming you only want the first column
        }
        return phoneNumberMatrix;
    }

    @Test(dataProvider = "phoneNumberData") // Use the DataProvider
    public void testContactUsForm(String phoneNumber) { // Accept a single phone number
        clickContactUs(); // Navigate to Login page from Home Page
        ContactUs contactUs = PageFactory.initElements(browserFactory.getDriver(), ContactUs.class);
        sleep(10000); // During this time, user can manually close any advertisement that appears
        contactUs.submitContactUsForm(phoneNumber);
        sleep(8000); // User can observe application feedback, before browser closes
        contactUs.emptyAllFields();
        sleep(2000); // To clear out all input fields for next iteration
    }

}
