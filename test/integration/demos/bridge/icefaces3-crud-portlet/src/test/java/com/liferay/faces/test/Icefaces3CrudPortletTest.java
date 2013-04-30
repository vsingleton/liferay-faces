package com.liferay.faces.test;

import static org.junit.Assert.assertFalse;
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
// import java.net.URL;

@RunWith(Arquillian.class)
public class Icefaces3CrudPortletTest {
	
	private final static Logger logger = Logger.getLogger(Icefaces3CrudPortletTest.class.getName());
	
	// @ArquillianResource
	// URL portalURL;
	String signInUrl = "http://localhost:8080/web/guest/jsf2-sign-in";
	String url = "http://localhost:8080/group/bridge-demos/ice3-crud";

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
	private static final String signedInTextXpath = "//div[contains(text(),'You are signed in as')]";
	@FindBy(xpath = signedInTextXpath)
	private WebElement signedInText;
	
	// form tag found after submitting
	private static final String formTagXpath = "//form[@method='post']";
	@FindBy(xpath = formTagXpath)
	private WebElement formTag;
	
	// portlet topper and menu elements
	private static final String portletDisplayNameXpath = "//header[@class='portlet-topper']/h1/span";
	@FindBy(xpath = portletDisplayNameXpath)
	private WebElement portletDisplayName;
	private static final String menuButtonXpath = "//a[contains(@id,'menuButton')]";
	@FindBy(xpath = menuButtonXpath)
	private WebElement menuButton;
	
	// Delete button
	private static final String deleteButtonXpath = "//input[@type='submit' and @value='Delete Selected']";
	@FindBy(xpath = deleteButtonXpath)
	private WebElement deleteButton;
	
	// Add button
	private static final String addButtonXpath = "//input[@type='submit' and @value='Add New']";
	@FindBy(xpath = addButtonXpath)
	private WebElement addButton;
	
	// Elements for Customers detail view
	private static final String firstNameFieldXpath = "//input[contains(@id,':firstName')]";
	@FindBy(xpath = firstNameFieldXpath)
	private WebElement firstNameField;
	private static final String firstNameFieldErrorXpath = "//input[contains(@id,':firstName')]/following-sibling::span[1]/child::node()";
	@FindBy(xpath = firstNameFieldErrorXpath)
	private WebElement firstNameFieldError;

	private static final String lastNameFieldXpath = "//input[contains(@id,':lastName')]";
	@FindBy(xpath = lastNameFieldXpath)
	private WebElement lastNameField;
	private static final String lastNameFieldErrorXpath = "//input[contains(@id,':lastName')]/following-sibling::span[1]/child::node()";
	@FindBy(xpath = lastNameFieldErrorXpath)
	private WebElement lastNameFieldError;

	private static final String emailAddressFieldXpath = "//input[contains(@id,':emailAddress')]";
	@FindBy(xpath = emailAddressFieldXpath)
	private WebElement emailAddressField;
	private static final String emailAddressFieldErrorXpath = "//input[contains(@id,':emailAddress')]/following-sibling::span[1]/child::node()";
	@FindBy(xpath = emailAddressFieldErrorXpath)
	private WebElement emailAddressFieldError;

	private static final String phoneNumberFieldXpath = "//input[contains(@id,':phoneNumber')]";
	@FindBy(xpath = phoneNumberFieldXpath)
	private WebElement phoneNumberField;
	private static final String phoneNumberFieldErrorXpath = "//input[contains(@id,':phoneNumber')]/following-sibling::span[1]/child::span[text()='Value is required']";
	@FindBy(xpath = phoneNumberFieldErrorXpath)
	private WebElement phoneNumberFieldError;

	private static final String dateOfBirthFieldXpath = "//input[contains(@id,':dateOfBirth')]";
	@FindBy(xpath = dateOfBirthFieldXpath)
	private WebElement dateOfBirthField;
	private static final String dateOfBirthFieldErrorXpath = "//input[contains(@id,':dateOfBirth')]/following-sibling::span[1]/child::span[text()='Value is required']";
	@FindBy(xpath = dateOfBirthFieldErrorXpath)
	private WebElement dateOfBirthFieldError;

	private static final String cityFieldXpath = "//input[contains(@id,':city')]";
	@FindBy(xpath = cityFieldXpath)
	private WebElement cityField;
	private static final String cityFieldErrorXpath = "//input[contains(@id,':city')]/following-sibling::span[text()='Value is required']";
	@FindBy(xpath = cityFieldErrorXpath)
	private WebElement cityFieldError;
	
	private static final String provinceIdFieldXpath = "//select[contains(@id,':provinceId')]";
	@FindBy(xpath = provinceIdFieldXpath)
	private WebElement provinceIdField;
	private static final String provinceIdFieldErrorXpath = "//select[contains(@id,':provinceId')]/following-sibling::span[text()='Value is required']";
	@FindBy(xpath = provinceIdFieldErrorXpath)
	private WebElement provinceIdFieldError;
	
	// FL element of dropdown
	private static final String provinceFlXpath = "//select[contains(@id,':provinceId')]/child::option[contains(@value, '3')]";
	@FindBy(xpath = provinceFlXpath)
	private WebElement provinceFl;
	
	private static final String postalCodeFieldXpath = "//input[contains(@id,':postalCode')]";
	@FindBy(xpath = postalCodeFieldXpath)
	private WebElement postalCodeField;
	private static final String postalCodeFieldErrorXpath = "//input[contains(@id,':postalCode')]/following-sibling::span[text()='Value is required']";
	@FindBy(xpath = postalCodeFieldErrorXpath)
	private WebElement postalCodeFieldError;
		
	// Save button
	private static final String saveButtonXpath = "//input[@type='submit' and @value='Save']";
	@FindBy(xpath = saveButtonXpath)
	private WebElement saveButton;
	
	// Cancel button
	private static final String cancelButtonXpath = "//input[@type='submit' and @value='Cancel']";
	@FindBy(xpath = cancelButtonXpath)
	private WebElement cancelButton;
	
	@Before
	public void beforeEachTest() {}

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
	public void customerMasterViewMode() throws Exception {
			
		signIn();
		logger.log(Level.INFO, "browser.navigate().to("+url+")");
		browser.navigate().to(url);
		logger.log(Level.INFO, "browser.getTitle() = " + browser.getTitle());
		logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());
		logger.log(Level.INFO, "portletDisplayName.getText() = " + portletDisplayName.getText());
		
		assertTrue("portletDisplayName.isDisplayed()", portletDisplayName.isDisplayed());
		assertTrue("menuButton.isDisplayed()", menuButton.isDisplayed());
		
		logger.log(Level.INFO, "addButton.isDisplayed() = " + addButton.isDisplayed());
		assertTrue("The \"Add New\" button should be displayed on the page at this point but it is not.", addButton.isDisplayed());
		
		logger.log(Level.INFO, "deleteButton.isDisplayed() = " + deleteButton.isDisplayed());
		assertTrue("The \"Delete Selected\" button should be displayed on the page at this point but it is not.", deleteButton.isDisplayed());
		
	}
	
	@Test
	@RunAsClient
	@InSequence(2000)
	public void customerDetailViewMode() throws Exception {
		
		addButton.click();
		
		Thread.sleep(500);
		
		logger.log(Level.INFO, "firstNameField.isDisplayed() = " + firstNameField.isDisplayed());
		assertTrue("The First Name Field should be displayed on the page at this point but it is not.", firstNameField.isDisplayed());
		
		logger.log(Level.INFO, "lastNameField.isDisplayed() = " + lastNameField.isDisplayed());
		assertTrue("The Last Name Field should be displayed on the page at this point but it is not.", lastNameField.isDisplayed());
		
		logger.log(Level.INFO, "emailAddressField.isDisplayed() = " + emailAddressField.isDisplayed());
		assertTrue("The Email Address Field should be displayed on the page at this point but it is not.", emailAddressField.isDisplayed());
		
		logger.log(Level.INFO, "phoneNumberField.isDisplayed() = " + phoneNumberField.isDisplayed());
		assertTrue("The Phone Number Field should be displayed on the page at this point but it is not.", phoneNumberField.isDisplayed());
		
		logger.log(Level.INFO, "dateOfBirthField.isDisplayed() = " + dateOfBirthField.isDisplayed());
		assertTrue("The Date Of Birth Field should be displayed on the page at this point but it is not.", dateOfBirthField.isDisplayed());
		
		logger.log(Level.INFO, "cityField.isDisplayed() = " + cityField.isDisplayed());
		assertTrue("The City Field should be displayed on the page at this point but it is not.", cityField.isDisplayed());
		
		logger.log(Level.INFO, "provinceIdField.isDisplayed() = " + provinceIdField.isDisplayed());
		assertTrue("The Province ID Field should be displayed on the page at this point but it is not.", provinceIdField.isDisplayed());
		
		logger.log(Level.INFO, "postalCodeField.isDisplayed() = " + postalCodeField.isDisplayed());
		assertTrue("The Postal Code Field should be displayed on the page at this point but it is not.", postalCodeField.isDisplayed());
		
		logger.log(Level.INFO, "saveButton.isDisplayed() = " + saveButton.isDisplayed());
		assertTrue("The \"Save\" button should be displayed on the page at this point but it is not.", saveButton.isDisplayed());
		
		logger.log(Level.INFO, "cancelButton.isDisplayed() = " + cancelButton.isDisplayed());
		assertTrue("The \"Cancel\" button should be displayed on the page at this point but it is not.", cancelButton.isDisplayed());
		
	}
	
	@Test
	@RunAsClient
	@InSequence(3000)
	public void allFieldsRequired() throws Exception {
		
		saveButton.click();
		
		Thread.sleep(250);
		
		logger.log(Level.INFO, "firstNameFieldError.isDisplayed() = " + firstNameFieldError.isDisplayed());
		assertTrue("The First Name Validation Error should be displayed on the page at this point but it is not.", firstNameFieldError.isDisplayed());
		
		logger.log(Level.INFO, "lastNameFieldError.isDisplayed() = " + lastNameFieldError.isDisplayed());
		assertTrue("The Last Name Validation Error should be displayed on the page at this point but it is not.", lastNameFieldError.isDisplayed());
		
		logger.log(Level.INFO, "emailAddressFieldError..isDisplayed() = " + emailAddressFieldError.isDisplayed());
		assertTrue("The Email Address Validation Error should be displayed on the page at this point but it is not.", emailAddressFieldError.isDisplayed());
		
		logger.log(Level.INFO, "phoneNumberFieldError.isDisplayed() = " + phoneNumberFieldError.isDisplayed());
		assertTrue("The Phone Number Validation Error should be displayed on the page at this point but it is not.", phoneNumberFieldError.isDisplayed());
		
		logger.log(Level.INFO, "dateOfBirthFieldError.isDisplayed() = " + dateOfBirthFieldError.isDisplayed());
		assertTrue("The Date of Birth Validation Error should be displayed on the page at this point but it is not.", dateOfBirthFieldError.isDisplayed());
		
		logger.log(Level.INFO, "cityFieldError.isDisplayed() = " + cityFieldError.isDisplayed());
		assertTrue("The City Validation Error should be displayed on the page at this point but it is not.", cityFieldError.isDisplayed());
		
		logger.log(Level.INFO, "provinceIdFieldError.isDisplayed() = " + provinceIdFieldError.isDisplayed());
		assertTrue("The Province ID Validation Error should be displayed on the page at this point but it is not.", provinceIdFieldError.isDisplayed());
		
		logger.log(Level.INFO, "postalCodeFieldError..isDisplayed() = " + postalCodeFieldError.isDisplayed());
		assertTrue("The Postal Code Validation Error should be displayed on the page at this point but it is not.", postalCodeFieldError.isDisplayed());
		
		cancelButton.click();
		
	}
	
	@Test
	@RunAsClient
	@InSequence(4000)
	public void addCustomer() throws Exception {
		
		Thread.sleep(250);
		
		addButton.click();
		
		Thread.sleep(250);
		
		firstNameField.sendKeys("A");
		lastNameField.sendKeys("A");
		emailAddressField.sendKeys("A@liferay.com");
		phoneNumberField.sendKeys("9876543210");
		dateOfBirthField.sendKeys("11/11/1111");
		cityField.sendKeys("A");
		provinceFl.click();
		postalCodeField.sendKeys("11111");
		saveButton.click();
		
		Thread.sleep(250);
		
		logger.log(Level.INFO, "firstNameFieldError.isThere() = " + isThere(firstNameFieldErrorXpath));
		assertFalse("There should NOT be a First Name Validation Error on the but there is.", isThere(firstNameFieldErrorXpath));
		
		logger.log(Level.INFO, "lastNameFieldError.isThere() = " + isThere(lastNameFieldErrorXpath));
		assertFalse("There should NOT be a Last Name Validation Error on the but there is.", isThere(lastNameFieldErrorXpath));
		
		logger.log(Level.INFO, "emailAddressFieldError.isThere() = " + isThere(emailAddressFieldErrorXpath));
		assertFalse("There should NOT be a Email Validation Error on the but there is.", isThere(emailAddressFieldErrorXpath));
		
		logger.log(Level.INFO, "phoneNumberFieldError.isThere() = " + isThere(phoneNumberFieldErrorXpath));
		assertFalse("There should NOT be a Phone Number Validation Error on the but there is.", isThere(phoneNumberFieldErrorXpath));
		
		logger.log(Level.INFO, "dateOfBirthFieldError.isThere() = " + isThere(dateOfBirthFieldErrorXpath));
		assertFalse("There should NOT be a Date of Birth Validation Error on the but there is.", isThere(dateOfBirthFieldErrorXpath));
		
		logger.log(Level.INFO, "cityFieldError.isThere() = " + isThere(cityFieldErrorXpath));
		assertFalse("There should NOT be a City Validation Error on the but there is.", isThere(cityFieldErrorXpath));
		
		logger.log(Level.INFO, "provinceIdFieldError.isThere() = " + isThere(provinceIdFieldErrorXpath));
		assertFalse("There should NOT be a province Validation Error on the but there is.", isThere(provinceIdFieldErrorXpath));
		
		logger.log(Level.INFO, "postalCodeFieldError.isThere() = " + isThere(postalCodeFieldErrorXpath));
		assertFalse("There should NOT be a Postal Code Validation Error on the but there is.", isThere(postalCodeFieldErrorXpath));
		
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
