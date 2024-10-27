package page_object_model.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class HomePage extends BasePage {
    // Invoking WebDriver of BasePage class
    public HomePage(WebDriver driver){
        super(driver);
    }

    // Testing vehicle search facility present in Home Page of Riyasewana
    // Focussed fields: Make, Model, Type, Price range, City (Different combinations will be tested)
    // Ignored fields: Condition (Because there is no proper definition to verify its accuracy depending on visible data)

    @FindBy(xpath = "//select[@name='make']")
    public WebElement Make;

    @FindBy(xpath = "//input[@name='model']")
    public WebElement Model;

    @FindBy(xpath = "//select[@name='vtype']")
    public WebElement Type;

    @FindBy(xpath = "//select[@name='price']")
    public WebElement Price;

    @FindBy(xpath = "//select[@name='city']")
    public WebElement City;

    @FindBy(xpath = "//input[@name='srch_btn']")
    public WebElement SearchButton;

    // Testing 5 fields in Search facility
    // Case-wise testing: Testing against pre-defined test cases, to see whether results delivered by Search are correct
    // Use this function to submit search form, by passing search facts as parameters
    public void submitSearchFields(String[] searchValues){
        if(searchValues[0].equals("")){
            searchValues[0] = "Any Make";
        }
        if(searchValues[2].equals("")){
            searchValues[2] = "Any";
        }
        if(searchValues[3].equals("")){
            searchValues[3] = "Any";
        }
        if(searchValues[4].equals("")){
            searchValues[4] = "Any City";
        }
        new Select(Make).selectByVisibleText(searchValues[0]);
        Model.sendKeys(searchValues[1]);
        new Select(Type).selectByVisibleText(searchValues[2]);
        new Select(Price).selectByVisibleText(searchValues[3]);
        new Select(City).selectByVisibleText(searchValues[4]);
        SearchButton.click();
    }

    // Clear all fields for next test round
    public void emptyAllFields(){
        Model.clear();
        // No need to clear other fields (Select fields), as they don't append new values to Input fields, in next test round
    }

    // Testing whether links in Home Page work correctly
    public <T> T clickLink(String visibleText){
        driver.findElement(By.xpath("//a[text()='" + visibleText + "']")).click();
        if(visibleText.equals("Login")){
            return (T) PageFactory.initElements(driver, Login.class);
        }else if(visibleText.equals("Contact Us")){
            return (T)PageFactory.initElements(driver, ContactUs.class);
        }else if(visibleText.equals("Contribute")){
            return (T)PageFactory.initElements(driver, Contribute.class);
        }else if(visibleText.equals("Leasing Offer")){
            return (T)PageFactory.initElements(driver, LeasingOffer.class);
        }else{
            return (T)PageFactory.initElements(driver, SignUp.class);
        }
    }

}
