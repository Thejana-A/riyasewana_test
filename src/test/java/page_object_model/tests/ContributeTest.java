package page_object_model.tests;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import page_object_model.pages.Contribute;
import page_object_model.utilities.ReadFromXLUtility;

import java.util.ArrayList;

public class ContributeTest extends HomePageTest {
    // Testing whether incorrect Contact Number, Email fields are validated in Contribute form
    // Boundary value analysis - considering that phone number must be a string of 10 digits, testing with strings of lengths 9 and 11.
    // Data pattern analysis - considering that email must contain an @ in it

    @Test(dataProvider = "emailPhoneData") // Test whether incorrect Phone numbers, emails are validated correctly
    public void testContributeForm(String email, String phone) {
        clickContribute(); // Navigate to Login page from Home Page
        Contribute contribute = PageFactory.initElements(browserFactory.getDriver(), Contribute.class);
        sleep(10000); // During this time, user can manually close any advertisement that appears

        contribute.submitContributeForm(email, phone);
        sleep(8000); // During this time, user can observe application feedback, before browser closes
        contribute.emptyAllFields();
        sleep(2000); // To clear out all input fields, for next iteration
    }

    @DataProvider(name = "emailPhoneData") // Use ReadFromXLUtility utility, to retrieve email. phone dataset from Excel sheet
    public Object[][] readEmailAndPhoneFromXL() {
        ReadFromXLUtility readFromXLUtility = new ReadFromXLUtility();
        String XLFileName = "/home/thejana/Documents/assignments/year_04/is4102/practicals/riyasewana/TestDataset.xlsx";
        String XLSheetName = "ContributeFormData";
        ArrayList<ArrayList<String>> data = readFromXLUtility.readDataFromXL(XLFileName, XLSheetName);

        Object[][] emailPhoneMatrix = new Object[data.size()][2];
        for (int i = 0; i < data.size(); i++) {
            emailPhoneMatrix[i][0] = data.get(i).get(0); // Email
            emailPhoneMatrix[i][1] = data.get(i).get(1); // Phone
        }
        return emailPhoneMatrix;
    }
}
