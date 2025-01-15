# ODEL E-commerce Test Automation Framework

This test automation framework is built to test the ODEL e-commerce website using Selenium WebDriver and TestNG, following the Page Object Model design pattern.

## Project Structure

src/
├── main/
│   └── java/
│       └── com/
│           └── example/
│               ├── pages/           # Page Objects
│               │   ├── BasePage.java
│               │   ├── HomePage.java
│               │   └── SearchResultsPage.java
│               └── utils/           # Core utilities
│                   └── DriverManager.java
└── test/
├── java/
│   └── com/
│       └── example/
│           ├── tests/           # Test classes
│           │   ├── BaseTest.java
│           │   └── SearchTest.java
│           ├── utils/           # Test utilities
│           │   └── TestListener.java
│           └── reports/         # Reporting
│               └── ExtentReportManager.java
└── resources/
├── testng.xml
└── testData.xlsx


```markdown
## Features
- Page Object Model implementation
- Data-driven testing using Excel
- ExtentReports integration
- Screenshot capture on test failure
- Test execution through TestNG XML

## Test Cases Covered
1. Search with Multiple Filters
   - Search for "shirt"
   - Apply MEN department filter
   - Apply Black color filter
   - Verify results

2. Search with Department Filter Only
   - Search for "shirt"
   - Apply MEN department filter
   - Verify results

3. Search Different Product
   - Search for "pants"
   - Apply department filter
   - Verify results

## Setup Instructions
1. Prerequisites:
   - Java 11 installed
   - Maven installed
   - Chrome browser installed

2. Clone the repository:
   ```bash
   git clone [repository-url]

3. Install dependencies:
    mvn clean install

4. Running tests:
    mvn test
    
Test Reports

Test reports are generated in test-output folder
Screenshots for failed tests are captured in test-output/screenshots

Tools & Technologies

Selenium WebDriver
TestNG
ExtentReports
Apache POI
Java 11
 