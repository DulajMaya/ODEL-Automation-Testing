package com.example.tests;

import com.example.pages.HomePage;
import com.example.pages.SearchResultsPage;
import com.example.utils.ExcelDataProvider;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Map;

public class SearchTest extends BaseTest {

    @DataProvider(name = "searchData")
    public Object[][] getSearchData() {
        return ExcelDataProvider.getTestData();
    }

    @Test(dataProvider = "searchData")
    public void testSearch(String testCase, String searchTerm, boolean expectedResult) {
        try {
            HomePage homePage = new HomePage(driver);
            homePage.navigateToHome();
            Thread.sleep(1000); // Wait after navigation

            homePage.searchProduct(searchTerm);
            Thread.sleep(2000); // Wait after search

            SearchResultsPage searchResultsPage = new SearchResultsPage(driver);

            if(testCase.equals("testBasicSearch")) {
                searchResultsPage.applyFilters();
            } else {
                searchResultsPage.applyDepartmentFilter();
            }

            Thread.sleep(2000); // Wait after applying filters

            Assert.assertTrue(searchResultsPage.hasResults(), "No search results found");

            Map<String, String> productDetails = searchResultsPage.getFirstProductDetails();
            System.out.println("Test Case: " + testCase);
            System.out.println("Product Name: " + productDetails.get("name"));
            System.out.println("Product Price: " + productDetails.get("price"));
            System.out.println("Discount: " + productDetails.get("discount"));

            Thread.sleep(1000); // Wait before next test

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }






}