This test framework is developed using Selenium, TestNG, Java and Maven.
Purpose is to test RIYASEWANA site (an online Sri Lankan vehicle market) at https://riyasewana.com/

For executing tests:
1) clone the repository in local environment
2) Open project in IntelliJ Idea Community Edition
2) Move to src/test/java/page_object_model/tests
3) Execute the test you need

For more different test data sets:
1) Move to TestDataset.xlsx
2) Edit the relevant sheet 
(Test data are categorized separately as valid test data and invalid test data for better clarity and accuracy of test results)
(Note: In Sign Up form, avoid using already occupied usernames under valid input category)

For extending the framework:
1) Add new Page objects in src/test/java/page_object_model/pages
2) Create correspoding test scripts in src/test/java/page_object_model/tests
3) Add required new Utilities in src/test/java/page_object_model/utilities
4) Add new data sheets in data file TestDataset.xlsx
5) Execute the test
6) Add new test suites in textng.xml, as per requirement

Currently available Utilities:
1) PDF log generation and txt type log generation for each test case
2) Capturing automated screenshots when a test fails

Additional notes:
- Since this is a commercial website, advertisements appear in various dynamic formats while execiting automated tests.
- therefore, in test cases, some idle time is provided to tetser to observe and quit them manually, since automation doesn't quit them successfully.
