# ODEL E-commerce Test Automation Framework

This test automation framework is built to test the ODEL e-commerce website using Selenium WebDriver and TestNG, following the Page Object Model design pattern.

## Table of Contents
- [Project Structure](#project-structure)
- [Features](#features)
- [Test Cases](#test-cases)
- [Setup Instructions](#setup-instructions)
- [Tools & Technologies](#tools--technologies)
- [Test Reports](#test-reports)
- [CI/CD Integration](#cicd-integration)

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


## Features
- Page Object Model (POM) design pattern implementation
- Data-driven testing using Excel
- ExtentReports integration for detailed reporting
- Automatic screenshot capture on test failure
- Cross-browser testing capability
- Jenkins CI/CD integration
- TestNG XML configuration
- Headless browser support

## Test Cases
1. Search with Multiple Filters
    - Search for "shirt"
    - Apply MEN department filter
    - Apply Black color filter
    - Verify filtered results

2. Search with Department Filter Only
    - Search for "shirt"
    - Apply MEN department filter
    - Verify department-filtered results

3. Search Different Product
    - Search for "pants"
    - Apply department filter
    - Verify search functionality with different product

## Setup Instructions
1. Prerequisites:
    - Java 11 installed
    - Maven installed
    - Chrome browser installed
    - Git installed

2. Clone the repository:
   ```bash
   git clone 'https://github.com/DulajMaya/ODEL-Automation-Testing.git'

3. Install dependencies:
   bash  
   mvn clean install

4. Running tests:
   bash
   mvn test

Tools & Technologies

Java 11
Selenium WebDriver 4.16.1
TestNG 7.8.0
Maven
ExtentReports 5.1.1
Apache POI 5.2.3
Jenkins (for CI/CD)

Test Reports

Test reports are generated in test-output folder
ExtentReports HTML report available in test-output/OdelTestReport.html
Screenshots for failed tests captured in test-output/screenshots
TestNG HTML reports in test-output/index.html

CI/CD Integration
Jenkins Pipeline Configuration

Configure Jenkins job with repository URL
Set up build triggers
Configure test execution
Enable report generation
Set up email notifications

Build and Test Execution

Automated test execution on git push
Report generation after test completion
Artifact archival including test reports
Email notifications for build status

Bonus Features

Screenshot capture on test failures
Headless browser testing support
Cross-browser testing capability
Jenkins integration for CI/CD
Detailed test reporting

Author

D M Mayadunna
MIT4201 - Software Quality Assurance
