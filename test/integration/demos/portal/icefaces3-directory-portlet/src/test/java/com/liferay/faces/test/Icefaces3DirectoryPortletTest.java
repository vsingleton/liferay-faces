package com.liferay.faces.test;

import static org.junit.Assert.assertTrue;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.enricher.findby.FindBy;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@RunWith(Arquillian.class)
public class Icefaces3DirectoryPortletTest {
	
	// private static final Logger logger;
	private final static Logger logger = Logger.getLogger(Icefaces3DirectoryPortletTest.class.getName());
	
	// @ArquillianResource
	// URL portalURL;
	String signInUrl = "http://localhost:8080/web/guest/jsf2-sign-in";
	String url = "http://localhost:8080/group/portal-demos/ice3-dir";

	@Drone
	WebDriver browser;
	
	// elements for logging in
	private static final String emailFieldXpath = "//input[contains(@id,':handle')]";
	@FindBy(xpath = emailFieldXpath)
	private WebElement emailField;
	private static final String passwordFieldXpath = "//input[contains(@id,':password')]";
	@FindBy(xpath = passwordFieldXpath)
	private WebElement passwordField;
	private static final String signInButtonXpath = "//input[@type='submit' and @value='Sign In']";
	@FindBy(xpath = signInButtonXpath)
	private WebElement signInButton;
	private static final String portletBodyXpath = "//div[contains(text(),'You are signed in as')]";
	@FindBy(xpath = portletBodyXpath)
	private WebElement portletBody;
	private static final String signedInTextXpath = "//div[contains(text(),'You are signed in as')]";
	@FindBy(xpath = signedInTextXpath)
	private WebElement signedInText;
	
	// elements for Icefaces3Directory
	private static final String portletTitleTextXpath = "//span[@class='portlet-title-text']";
	@FindBy(xpath = portletTitleTextXpath)
	private WebElement portletTitleText;
	
	private static final String menuButtonXpath = "//a[contains(@id,'jsf2portlet') and contains(@id,'menuButton')]";
	@FindBy(xpath = menuButtonXpath)
	private WebElement menuButton;
	private static final String menuPreferencesXpath = "//a[contains(@id,'jsf2portlet') and contains(@id,'menu_preferences')]";
	@FindBy(xpath = menuPreferencesXpath)
	private WebElement menuPreferences;
	
	private static final String logoXpath = "//img[contains(@src,'liferay-logo.png')]";
	@FindBy(xpath = logoXpath)
	private WebElement logo;
	
	// Elements for the directory search
	private static final String lastNameSearchInputXpath = "//label[@class='aui-field-label' and contains(text(), 'Last Name')]/following-sibling::span[@class='aui-field-element']/input[contains(@class, 'iceInpTxt')]";
	@FindBy(xpath = lastNameSearchInputXpath)
	private WebElement lastNameSearchInput;
	private static final String firstNameSearchInputXpath = "//label[@class='aui-field-label' and contains(text(), 'First Name')]/following-sibling::span[@class='aui-field-element']/input[contains(@class, 'iceInpTxt')]";
	@FindBy(xpath = firstNameSearchInputXpath)
	private WebElement firstNameSearchInput;
	private static final String emailAddressSearchInputXpath = "//label[@class='aui-field-label' and contains(text(), 'Email Address')]/following-sibling::span[@class='aui-field-element']/input[contains(@class, 'iceInpTxt')]";
	@FindBy(xpath = emailAddressSearchInputXpath)
	private WebElement emailAddressSearchInput;
	private static final String dropdownSearchStatusXpath = "//select[contains(@id,':s1')]/option[@selected='true' and contains(text(), 'Any Status')]/..";
	@FindBy(xpath = dropdownSearchStatusXpath)
	private WebElement dropdownSearchStatusField;
	private static final String dropdownSearchOperatoreXpath = "//select[contains(@id,':s1')]/option[@selected='true' and contains(text(), 'Any')]/..";
	@FindBy(xpath = dropdownSearchOperatoreXpath)
	private WebElement dropdownSearchOperatorField;
	private static final String searchButtonXpath = "//input[contains(@id, 'cmdButton') and @type='submit' and contains(@value, 'Search')]";
	@FindBy(xpath = searchButtonXpath)
	private WebElement searchButton;
	
	// Elements for users' list
	private static final String pictureColumnHeaderXpath = "//th[contains(@class, 'iceDatTblColHdr') and @scope='col']/span[contains(@id,':users:') and text()='Picture']";
	@FindBy(xpath = pictureColumnHeaderXpath)
	private WebElement pictureColumnHeader;	
	private static final String lastNameColumnHeaderXpath = "//td[text()='Last Name']";
	@FindBy(xpath = lastNameColumnHeaderXpath)
	private WebElement lastNameColumnHeader;	
	private static final String firstNameColumnHeaderXpath = "//th[contains(@class, 'iceDatTblColHdr') and @scope='col']/a[1]/span[contains(@id,':users:') and text()='First Name']";
	@FindBy(xpath = firstNameColumnHeaderXpath)
	private WebElement firstNameColumnHeader;	
	private static final String emailAddressColumnHeaderXpath = "//th[contains(@class, 'iceDatTblColHdr') and @scope='col']/a[1]/span[contains(@id,':users:') and text()='Email Address']";
	@FindBy(xpath = emailAddressColumnHeaderXpath)
	private WebElement emailAddressColumnHeader;	
	private static final String jobTitleColumnHeaderXpath = "//th[contains(@class, 'iceDatTblColHdr') and @scope='col']/a[1]/span[contains(@id,':users:') and text()='Job Title']";
	@FindBy(xpath = jobTitleColumnHeaderXpath)
	private WebElement jobTitleColumnHeader;

	// Test user's cells in the list view
	private static final String testUserLastNameCellXpath = "//tr[contains(@class, 'iceDatTblRow') and contains(@id, ':users:')]/td[contains(@class, 'iceDatTblCol2')]/span[contains(text(), 'Test')]";
	@FindBy(xpath = testUserLastNameCellXpath)
	private WebElement testUserLastNameCell;
	private static final String testUserFirstNameCellXpath = "//tr[contains(@class, 'iceDatTblRow') and contains(@id, ':users:')]/td[contains(@class, 'iceDatTblCol1')]/span[contains(text(), 'Test')]";
	@FindBy(xpath = testUserFirstNameCellXpath)
	private WebElement testUserFirstNameCell;
	private static final String testUserEmailAddressCellXpath = "//a[@href='mailto:test@liferay.com']";
	@FindBy(xpath = testUserEmailAddressCellXpath)
	private WebElement testUserEmailAddressCell;
	
	@Before
	public void getNewSession() {

	}

	public void signIn() throws Exception {
		
		// Shut its dirty mouth
		java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
		
		logger.log(Level.INFO, "browser.navigate().to("+signInUrl+")");
		browser.navigate().to(signInUrl);
		logger.log(Level.INFO, "browser.getTitle() = " + browser.getTitle() + " before signing in ...");
		
		emailField.clear();
		emailField.sendKeys("test@liferay.com");
		passwordField.clear();
		passwordField.sendKeys("test");
		signInButton.click();
		logger.log(Level.INFO, "browser.getTitle() = " + browser.getTitle() + " after clicking the sign in button and waiting");
		logger.log(Level.INFO, signedInText.getText());
		
	}
	
	@Test
	@RunAsClient
	@InSequence(1000)
	public void usersListView() throws Exception {
			
		signIn();
		
		logger.log(Level.INFO, "browser.navigate().to("+url+")");
		browser.navigate().to(url);
		
		logger.log(Level.INFO, "browser.getTitle() = " + browser.getTitle());
		logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());
		logger.log(Level.INFO, "portletTitleText.getText() = " + portletTitleText.getText());
		
		assertTrue("portletTitleText.isDisplayed()", portletTitleText.isDisplayed());
		
		logger.log(Level.INFO, "lastNameSearchInput.isDisplayed() = " + lastNameSearchInput.isDisplayed());
		assertTrue("The Last Name Search Box should be displayed on the page at this point but it is not.", lastNameSearchInput.isDisplayed());
		logger.log(Level.INFO, "firstNameSearchInput.isDisplayed() = " + firstNameSearchInput.isDisplayed());
		assertTrue("The First Name Search Box should be displayed on the page at this point but it is not.", firstNameSearchInput.isDisplayed());
		logger.log(Level.INFO, "emailAddressSearchInput.isDisplayed() = " + emailAddressSearchInput.isDisplayed());
		assertTrue("The Email Address Search Box should be displayed on the page at this point but it is not.", emailAddressSearchInput.isDisplayed());
		logger.log(Level.INFO, "dropdownSearchStatusField.isDisplayed() = " + dropdownSearchStatusField.isDisplayed());
		assertTrue("The Status Dropdown should be displayed on the page at this point but it is not.", dropdownSearchStatusField.isDisplayed());
		logger.log(Level.INFO, "dropdownSearchOperatorField.isDisplayed() = " + dropdownSearchOperatorField.isDisplayed());
		assertTrue("The Search Operator Dropdown should be displayed on the page at this point but it is not.", dropdownSearchOperatorField.isDisplayed());
		logger.log(Level.INFO, "searchButton.isDisplayed() = " + searchButton.isDisplayed());
		assertTrue("The Search Button should be displayed on the page at this point but it is not.", searchButton.isDisplayed());
		
		logger.log(Level.INFO, "pictureColumnHeader.isDisplayed() = " + pictureColumnHeader.isDisplayed());
		assertTrue("The Screen Name Column Header should be displayed on the page at this point but it is not.", pictureColumnHeader.isDisplayed());
		
		logger.log(Level.INFO, "lastNameColumnHeader.isDisplayed() = " + lastNameColumnHeader.isDisplayed());
		assertTrue("The Last Name Column Header should be displayed on the page at this point but it is not.", lastNameColumnHeader.isDisplayed());
		logger.log(Level.INFO, "firstNameColumnHeader.isDisplayed() = " + firstNameColumnHeader.isDisplayed());
		assertTrue("The First Name Column Header should be displayed on the page at this point but it is not.", firstNameColumnHeader.isDisplayed());
		logger.log(Level.INFO, "emailAddressColumnHeader.isDisplayed() = " + emailAddressColumnHeader.isDisplayed());
		assertTrue("The Email Address Column Header should be displayed on the page at this point but it is not.", emailAddressColumnHeader.isDisplayed());
		logger.log(Level.INFO, "jobTitleColumnHeader.isDisplayed() = " + jobTitleColumnHeader.isDisplayed());
		assertTrue("The Job Title Column Header should be displayed on the page at this point but it is not.", jobTitleColumnHeader.isDisplayed());	
	}
	
	@Test
	@RunAsClient
	@InSequence(2000)
	public void search() throws Exception {
		
		lastNameSearchInput.sendKeys("test");
		
		searchButton.click();
		
		Thread.sleep(1000);

		logger.log(Level.INFO, "testUserLastNameCell.isDisplayed() = " + testUserLastNameCell.isDisplayed());
		assertTrue("The Last Name Cell of the Test user should be displayed on the page as Test at this point but it is not.", testUserLastNameCell.isDisplayed());
		logger.log(Level.INFO, "testUserLastNameCell.isDisplayed() = " + testUserFirstNameCell.isDisplayed());
		assertTrue("The First Name Cell of the Test user should be displayed on the page as Test at this point but it is not.", testUserFirstNameCell.isDisplayed());
		logger.log(Level.INFO, "testUserEmailAddressCell.isDisplayed() = " + testUserEmailAddressCell.isDisplayed());
		assertTrue("The Email Address Cell of the Test user should be displayed on the page as test@liferay.com at this point but it is not.", testUserEmailAddressCell.isDisplayed());
	}
	
	public boolean isThere(String xpath) {
		boolean isThere = false;
		int count = 0;
		count = browser.findElements(By.xpath(xpath)).size();
		if (count == 0) { isThere = false; }
		if (count > 0) { isThere = true; }
		if (count > 1) {
			logger.log(Level.WARNING, "The method 'isThere(xpath)' found "+count+" matches using xpath = " + xpath + 
					" ... the word 'is' implies singluar, or 1, not " + count
				);
		}
		return isThere;
	}

}
