package page_object_model.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchResult extends HomePage{
    // Constructor invoking driver of HomePage
    public SearchResult(WebDriver driver){
        super(driver);
    }

    @FindBy(xpath = "(//li[contains(@class, 'item round')])[1]/h2")
    public WebElement FirstItemHeader;

    @FindBy(xpath = "(//li[contains(@class, 'item round')])[1]/div[2]/div[1]")
    public WebElement FirstItemCity;

    @FindBy(xpath = "(//li[contains(@class, 'item round')])[1]/div[2]/div[2]")
    public WebElement FirstItemPrice;

    @FindBy(xpath = "//input[@name='pricemmin']")
    public WebElement MinimumPrice;

    @FindBy(xpath = "//input[@name='pricemmax']")
    public WebElement MaximumPrice;

    @FindBy(xpath = "//div[@id='content']/div")
    public WebElement ResultUnavailableMessage;

    // Note: This function is intended to be called by search facility of Home page, once some valid results are delivered. Not supposed to be called by a tester directly
    // In this test scenario, due to complex cohesion of logic with many UI components, test logic wasn't separated to a separate Test file, as it will complicate behaviour
    // Use this function to check valid search results retrieved for valid search facts searched from Home page
    public boolean testSearchResult(String[] vehicleData){
        String headerText = FirstItemHeader.getText();
        String subHeaderText = FirstItemCity.getText();
        scrollPage(0, 500);
        String priceText = FirstItemPrice.getText();

        boolean vehicleMake = true;
        boolean vehicleModel = (headerText.toLowerCase()).contains(vehicleData[1].toLowerCase());
        boolean vehicleType = true;
        boolean vehiclePrice = true;
        boolean vehicleCity = subHeaderText.contains(vehicleData[4]);

        // Vehicle Make - Toyota, Benz etc
        switch(vehicleData[0]){
            case "Any Make":
                vehicleMake = true;
                break;
            default:
                vehicleMake = ((headerText.toLowerCase()).contains(vehicleData[0].toLowerCase()));
        }
        // Vehicle Type - Car, Van etc
        switch(vehicleData[2]){
            case "Any":
                vehicleType = true;
                break;
            default:
                vehicleType = ((headerText.toLowerCase()).contains(vehicleData[2].toLowerCase()));
        }

        // Start - task of extracting numeric price value from string displayed in UI
        Pattern pattern = Pattern.compile("\\d+"); // Matches one or more digits
        Matcher matcher = pattern.matcher(priceText);
        StringBuilder digits = new StringBuilder(); // Use a StringBuilder to accumulate the digits
        while (matcher.find()) {
            digits.append(matcher.group()); // Append found digits to the StringBuilder
        }
        String extractedDigits = digits.toString(); // Convert StringBuilder to String
        int extractedPrice = 0; // Covert String to int
        try {
            extractedPrice = Integer.parseInt(extractedDigits);
        } catch (NumberFormatException e) {
            extractedPrice = 0;
        }
        // End - task of extracting numeric price value from string

        // Vehicle Price
        switch(vehicleData[3]){
            case "Any":
                vehiclePrice = true;
                break;
            case "< 100,000":
                if (extractedPrice < 100000){
                    vehiclePrice = true;
                } else {
                    vehiclePrice = false;
                }
                break;
            case "8,000,000 - 10 Million":
                if ((8000000 < extractedPrice) && (extractedPrice < 10000000)){
                    vehiclePrice = true;
                } else {
                    vehiclePrice = false;
                }
                break;
            case "10 Million - 15 Million":
                if ((10000000 < extractedPrice) && (extractedPrice < 15000000)){
                    vehiclePrice = true;
                } else {
                    vehiclePrice = false;
                }
                break;
            case "> 15 Million":
                if (15000000 < extractedPrice){
                    vehiclePrice = true;
                } else {
                    vehiclePrice = false;
                }
                break;
            default:
                int minimumPriceValue = Integer.parseInt(MinimumPrice.getText());
                int maximumPriceValue = Integer.parseInt(MaximumPrice.getText());
                if ((minimumPriceValue < extractedPrice) && (extractedPrice < maximumPriceValue)){
                    vehiclePrice = true;
                } else {
                    vehiclePrice = false;
                }
        }
        // Vehicle City
        switch(vehicleData[4]){
            case "Any City":
                vehicleCity = true;
                break;
            default:
                vehicleCity = ((subHeaderText.toLowerCase()).contains(vehicleData[4].toLowerCase()));
        }

        boolean testResult = ((vehicleMake) && (vehicleModel) && (vehicleType) && (vehiclePrice) && (vehicleCity));
        System.out.println(headerText + " " + priceText + " " + subHeaderText);
        System.out.println(Arrays.toString(vehicleData));
        if (testResult){
            System.out.println("Correct result was delivered by search facility" + "\n");
            return true;
        } else {
            System.out.println("Error: Irrelevant data was present in search result" + "\n");
            captureScreenShot("valid_search_error");
            return false;
        }
    }

    // Note: This function is intended to be called by search facility of Home page, once after searching with hope of receiving null result set. Not supposed to be called by a tester directly
    // Use this function to test search result, after searching for invalid facts (Expected null search results)
    public String testInvalidSearchResult(String[] vehicleData){
        String feedbackMessage = ResultUnavailableMessage.getText();
        return feedbackMessage;
    }


}
