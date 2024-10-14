package page_object_model.tests;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;
import page_object_model.pages.SignUp;
import page_object_model.utilities.ReadFromXLUtility;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;

public class SignUpTest extends HomePageTest {
    // Testing password & re-enter password
    // Error guessing technique - assuming that re-enter password functionality may not work correctly

    // Use ReadFromXLUtility utility, to retrieve password dataset from Excel sheet
    @DataProvider(name = "passwordDataProvider")
    public Object[][] readPasswordFromXL() {
        ReadFromXLUtility readFromXLUtility = new ReadFromXLUtility();
        String XLFileName = "/home/thejana/Documents/assignments/year_04/is4102/practicals/riyasewana/TestDataset.xlsx";
        String XLSheetName = "SignUpPassword";
        ArrayList<ArrayList<String>> data = readFromXLUtility.readDataFromXL(XLFileName, XLSheetName);

        // Convert ArrayList to Object[][]
        Object[][] dataArray = new Object[data.size()][];
        for (int i = 0; i < data.size(); i++) {
            dataArray[i] = data.get(i).toArray();
        }
        return dataArray;
    }

    // Test whether password and re-enter password fields match and work properly
    @Test(dataProvider = "passwordDataProvider")
    public void testReEnterPassword(String password, String reEnterPassword) {
        clickSignUp(); // Navigate to Sign Up page from Home Page
        SignUp signUp = PageFactory.initElements(browserFactory.getDriver(), SignUp.class);
        sleep(10000); // During this time, user can manually close any advertisement that appears

        signUp.submitSignUpForm(password, reEnterPassword); // For each pair of password & re-enter password
        sleep(8000); // During this time, user can observe application feedback, before browser closes
        signUp.emptyAllFields();
        sleep(2000); // To clear out all input fields, for next iteration
    }

}
