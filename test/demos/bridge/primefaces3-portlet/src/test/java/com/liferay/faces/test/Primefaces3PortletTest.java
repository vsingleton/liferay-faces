package com.liferay.faces.test;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.enricher.findby.FindBy;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.io.File;
import org.apache.commons.io.FileUtils;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.logging.Level;
// import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.jboss.arquillian.graphene.Graphene.waitGui;
import static org.jboss.arquillian.graphene.Graphene.waitAjax;
import static org.jboss.arquillian.graphene.Graphene.waitModel;

@RunWith(Arquillian.class)
public class Primefaces3PortletTest {
	
	private final static Logger logger = Logger.getLogger(Primefaces3PortletTest.class.getName());
	
	// @ArquillianResource
	// URL portalURL;
	String signInUrl = "http://localhost:8080/web/guest/signin";
	String url = "http://localhost:8080/group/bridge-demos/prime3";

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
	private static final String menuPreferencesXpath = "//a[contains(@id,'menu_preferences')]";
	@FindBy(xpath = menuPreferencesXpath)
	private WebElement menuPreferences;
	
	// preferences elements
	private static final String datePatternFieldXpath = "//input[contains(@id,':datePattern')]";
	@FindBy(xpath = datePatternFieldXpath)
	private WebElement datePatternField;
	private static final String resetButtonXpath = "//input[@type='submit' and @value='Reset']";
	@FindBy(xpath = resetButtonXpath)
	private WebElement resetButton;
	
	// elements for Job Applicants
	private static final String logoXpath = "//img[contains(@src,'liferay-logo.png')]";
	@FindBy(xpath = logoXpath)
	private WebElement logo;
	
	private static final String firstNameFieldXpath = "//input[contains(@id,':firstName')]";
	@FindBy(xpath = firstNameFieldXpath)
	private WebElement firstNameField;
	private static final String firstNameFieldErrorXpath = "//input[contains(@id,':firstName')]/following-sibling::*[1]";
	@FindBy(xpath = firstNameFieldErrorXpath)
	private WebElement firstNameFieldError;
	
	private static final String lastNameFieldXpath = "//input[contains(@id,':lastName')]";
	@FindBy(xpath = lastNameFieldXpath)
	private WebElement lastNameField;
	private static final String lastNameFieldErrorXpath = "//input[contains(@id,':lastName')]/following-sibling::*[1]";
	@FindBy(xpath = lastNameFieldErrorXpath)
	private WebElement lastNameFieldError;
	
	private static final String emailAddressFieldXpath = "//input[contains(@id,':emailAddress')]";
	@FindBy(xpath = emailAddressFieldXpath)
	private WebElement emailAddressField;
	private static final String emailAddressFieldErrorXpath = "//input[contains(@id,':emailAddress')]/following-sibling::*[1]";
	@FindBy(xpath = emailAddressFieldErrorXpath)
	private WebElement emailAddressFieldError;
	
	private static final String phoneNumberFieldXpath = "//input[contains(@id,':phoneNumber')]";
	@FindBy(xpath = phoneNumberFieldXpath)
	private WebElement phoneNumberField;
	private static final String phoneNumberFieldErrorXpath = "//input[contains(@id,':phoneNumber')]/following-sibling::*[1]";
	@FindBy(xpath = phoneNumberFieldErrorXpath)
	private WebElement phoneNumberFieldError;
	
	private static final String dateOfBirthFieldXpath = "//input[contains(@id,':dateOfBirth')]";
	@FindBy(xpath = dateOfBirthFieldXpath)
	private WebElement dateOfBirthField;
	private static final String dateOfBirthFieldErrorXpath = "//input[contains(@id,':dateOfBirth')]/../following-sibling::*[1]";
	@FindBy(xpath = dateOfBirthFieldErrorXpath)
	private WebElement dateOfBirthFieldError;
	
	private static final String cityFieldXpath = "//input[contains(@id,':city')]";
	@FindBy(xpath = cityFieldXpath)
	private WebElement cityField;
	private static final String cityFieldErrorXpath = "//input[contains(@id,':city')]/following-sibling::*[1]";
	@FindBy(xpath = cityFieldErrorXpath)
	private WebElement cityFieldError;
	
	private static final String provinceIdFieldXpath = "//select[contains(@id,':provinceId')]";
	@FindBy(xpath = provinceIdFieldXpath)
	private WebElement provinceIdField;
	private static final String provinceIdFieldErrorXpath = "//select[contains(@id,':provinceId')]/following-sibling::*[1]";
	@FindBy(xpath = provinceIdFieldErrorXpath)
	private WebElement provinceIdFieldError;
	
	private static final String postalCodeFieldXpath = "//input[contains(@id,':postalCode')]";
	@FindBy(xpath = postalCodeFieldXpath)
	private WebElement postalCodeField;
	private static final String postalCodeFieldErrorXpath = "//input[contains(@id,':postalCode')]/following-sibling::*[1]/following-sibling::*[1]";
	@FindBy(xpath = postalCodeFieldErrorXpath)
	private WebElement postalCodeFieldError;
	
	private static final String postalCodeToolTipXpath = "//img[contains(@title,'Type any of these ZIP codes')]";
	@FindBy(xpath = postalCodeToolTipXpath)
	private WebElement postalCodeToolTip;
	
	private static final String multiFileUploadButtonXpath = "//input[@type='file' and @multiple='multiple']";
	@FindBy(xpath = multiFileUploadButtonXpath)
	private WebElement multiFileUploadButton;
	private static final String submitFilesButtonXpath = "//span[contains(text(),'Upload')]/..";
	@FindBy(xpath = submitFilesButtonXpath)
	private WebElement submitFilesButton;
	private static final String cancelFilesButtonXpath = "//span[contains(text(),'Cancel')]/..";
	@FindBy(xpath = cancelFilesButtonXpath)
	private WebElement cancelFilesButton;
	
	private static final String showCommentsLinkXpath = "//a[contains(text(),'Show Comments')]";
	@FindBy(xpath = showCommentsLinkXpath)
	private WebElement showCommentsLink;
	private static final String hideCommentsLinkXpath = "//a[contains(text(),'Hide Comments')]";
	@FindBy(xpath = hideCommentsLinkXpath)
	private WebElement hideCommentsLink;
	private static final String commentsXpath = "//textarea[contains(@id,':comments')]";
	@FindBy(xpath = commentsXpath)
	private WebElement comments;
	
	private static final String submitButtonXpath = "//span[contains(text(),'Submit')]/..";
	@FindBy(xpath = submitButtonXpath)
	private WebElement submitButton;
	private static final String preferencesSubmitButtonXpath = "//input[@type='submit' and @value='Submit']";
	@FindBy(xpath = preferencesSubmitButtonXpath)
	private WebElement preferencesSubmitButton;
	private static final String editPreferencesButtonXpath = "//span[contains(text(),'Edit Preferences')]/..";
	@FindBy(xpath = editPreferencesButtonXpath)
	private WebElement editPreferencesButton;
	private static final String returnLinkXpath = "//a[contains(text(),'Return to Full Page')]";
	@FindBy(xpath = returnLinkXpath)
	private WebElement returnLink;
	
	private static final String mojarraVersionXpath = "//*[contains(text(),'Mojarra')]";
	@FindBy(xpath = mojarraVersionXpath)
	private WebElement mojarraVersion;
	private static final String primeFacesVersionXpath = "//*[contains(text(),'PrimeFaces ')]";
	@FindBy(xpath = primeFacesVersionXpath)
	private WebElement primeFacesVersion;
	private static final String alloyVersionXpath = "//*[contains(text(),'Liferay Faces Alloy')]";
	@FindBy(xpath = alloyVersionXpath)
	private WebElement alloyVersion;
	private static final String bridgeVersionXpath = "//*[contains(text(),'Liferay Faces Bridge')]";
	@FindBy(xpath = bridgeVersionXpath)
	private WebElement bridgeVersion;
	
	@Before
	public void beforeEachTest() {
		
//		browser.manage().deleteAllCookies();
//		logger.log(Level.INFO, "browser.manage().deleteAllCookies() ...");
		
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
		// assertTrue("You are signed in", signedInText.getText().contains("You are signed in"));
		
	}
	
	@Test
	@RunAsClient
	@InSequence(1000)
	public void jobApplicantFieldsRender() throws Exception {
			
		signIn();
		logger.log(Level.INFO, "browser.navigate().to("+url+")");
		browser.navigate().to(url);
		logger.log(Level.INFO, "browser.getTitle() = " + browser.getTitle());
		logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());
		logger.log(Level.INFO, "portletDisplayName.getText() = " + portletDisplayName.getText());
		
		assertTrue("portletDisplayName.isDisplayed()", portletDisplayName.isDisplayed());
		assertTrue("menuButton.isDisplayed()", menuButton.isDisplayed());
		assertTrue("menuPreferences is NOT displayed()", !menuPreferences.isDisplayed());
		
		// logger.log(Level.INFO, "browser.getPageSource() = " + browser.getPageSource());
		
		assertTrue("logo.isDisplayed()",logo.isDisplayed());
		
		assertTrue("firstNameField.isDisplayed()", firstNameField.isDisplayed());
		assertTrue("lastNameField.isDisplayed()", lastNameField.isDisplayed());
		assertTrue("emailAddressField.isDisplayed()", emailAddressField.isDisplayed());
		assertTrue("phoneNumberField.isDisplayed()", phoneNumberField.isDisplayed());
		
		assertTrue("dateOfBirthField.isDisplayed()", dateOfBirthField.isDisplayed());
		assertTrue("cityField.isDisplayed()", cityField.isDisplayed());
		assertTrue("provinceIdField.isDisplayed()", provinceIdField.isDisplayed());
		assertTrue("postalCodeField.isDisplayed()", postalCodeField.isDisplayed());
		assertTrue("postalCodeToolTip.isDisplayed()", postalCodeToolTip.isDisplayed());
		
		logger.log(Level.INFO, "multiFileUploadButton.getAttribute('id') = " + multiFileUploadButton.getAttribute("id"));
		logger.log(Level.INFO, "multiFileUploadButton.getAttribute('class') = " + multiFileUploadButton.getAttribute("class"));
		// assertTrue("multiFileUploadButton.isDisplayed()", multiFileUploadButton.isDisplayed());
		assertTrue("submitFilesButton.isDisplayed()", submitFilesButton.isDisplayed());
		logger.log(Level.INFO, "submitFilesButton.getTagName() = " + submitFilesButton.getTagName());
		assertTrue("cancelFilesButton.isDisplayed()", cancelFilesButton.isDisplayed());
		logger.log(Level.INFO, "cancelFilesButton.getTagName() = " + cancelFilesButton.getTagName());
		
		assertTrue("showCommentsLink.isDisplayed()", showCommentsLink.isDisplayed());
		
		assertTrue("submitButton.isDisplayed()", submitButton.isDisplayed());
		logger.log(Level.INFO, "submitButton.getTagName() = " + submitButton.getTagName());
		assertTrue("editPreferencesButton.isDisplayed()", editPreferencesButton.isDisplayed());
		logger.log(Level.INFO, "editPreferencesButton.getTagName() = " + editPreferencesButton.getTagName());
		
		assertTrue("mojarraVersion.isDisplayed()", mojarraVersion.isDisplayed());
		assertTrue("primeFacesVersion.isDisplayed()", primeFacesVersion.isDisplayed());
		assertTrue("alloyVersion.isDisplayed()", alloyVersion.isDisplayed());
		assertTrue("bridgeVersion.isDisplayed()", bridgeVersion.isDisplayed());

		logger.log(Level.INFO, mojarraVersion.getText());
		logger.log(Level.INFO, primeFacesVersion.getText());
		logger.log(Level.INFO, alloyVersion.getText());
		logger.log(Level.INFO, bridgeVersion.getText());
		
	}
	
	@Test
	@RunAsClient
	@InSequence(2000)
	public void validateEmail() throws Exception {
		
		int tags = 0;
		int tagsWhileInvalid = 2;
		int tagsWhileValid = 0;
		
		// checks an invalid email address
		emailAddressField.sendKeys("test");
		phoneNumberField.click();
		Thread.sleep(500);
		logger.log(Level.INFO, "emailAddressField.getAttribute('value') = " + emailAddressField.getAttribute("value"));
		tags = browser.findElements(By.xpath("//span[contains(text(),'Invalid e-mail address')]")).size();
		logger.log(Level.INFO, "# of error tags = " + tags);
		assertTrue("# of error tags == tagsWhileInvalid", tags == tagsWhileInvalid);
		assertTrue("Invalid e-mail address validation message displayed", 
				emailAddressFieldError.getText().contains("Invalid e-mail address"));
		logger.log(Level.INFO, "emailAddressFieldError.isDisplayed() = " + emailAddressFieldError.isDisplayed());
		logger.log(Level.INFO, "emailAddressFieldError.getText() = " + emailAddressFieldError.getText());
		
		// checks a valid email address
		emailAddressField.clear();
		Thread.sleep(500);
		emailAddressField.sendKeys("test@liferay.com");
		phoneNumberField.click();
		Thread.sleep(500);
		logger.log(Level.INFO, "emailAddressField.getAttribute('value') = " + emailAddressField.getAttribute("value"));
		tags = browser.findElements(By.xpath("//span[contains(text(),'Invalid e-mail address')]")).size();
		logger.log(Level.INFO, "tags = " + tags);
		assertTrue("# of error tags == tagsWhileValid", tags == tagsWhileValid);
		
	}
	
	@Test
	@RunAsClient
	@InSequence(3000)
	public void preferencesAreWorking() throws Exception {
		
		// test for both
		int dateLengthAfterChange = 8;
		int dateLengthAfterReset = 10;
		
		if (dateOfBirthField.getAttribute("value").equals("")) {
			// try because it may be read-only (as is the case with richfaces4)
			try {
				dateOfBirthField.sendKeys("07/04/1776");
			} catch (Exception e) {
				logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
				assertTrue("No exceptions occured when entering a dateOfBirth", false);
			}
		}
		
		menuButton.click();
		Thread.sleep(500);
		menuPreferences.click();
		Thread.sleep(500);
		logger.log(Level.INFO, "datePatternField.getAttribute('value') = " + datePatternField.getAttribute("value"));
		logger.log(Level.INFO, "resetButton.isDisplayed() = " + resetButton.isDisplayed());
		// MM/dd/yyyy
		datePatternField.clear();
		datePatternField.sendKeys("MM/dd/yy");
		preferencesSubmitButton.click();
		// preferencesSubmitButton takes us back to the job app submission form
		// so we expect to see date of birth next
		// we can check it for the reset datePattern, no need for extra navigation
		Thread.sleep(1000);
		logger.log(Level.INFO, "dateOfBirthField.getAttribute('value') = " + dateOfBirthField.getAttribute("value"));
		logger.log(Level.INFO, "dateOfBirthField.getAttribute('value').length() = " + 
				dateOfBirthField.getAttribute("value").length()
			);
		
		assertTrue(
				"date of birth has "+dateLengthAfterChange+" characters after changing preferences to MM/dd/yy", 
				dateOfBirthField.getAttribute("value").length() == dateLengthAfterChange
			);
		
		editPreferencesButton.click();
		logger.log(Level.INFO, "editPreferencesButton.click() ...");
		Thread.sleep(500);
		resetButton.click();
		logger.log(Level.INFO, "resetButton.click() ...");
		Thread.sleep(1000);
		// Yikes ... we need some more consistency here
		logger.log(Level.INFO, "browser.navigate().to("+url+")");
		browser.navigate().to(url);
		Thread.sleep(500);
		logger.log(Level.INFO, "dateOfBirthField.getAttribute('value') = " + dateOfBirthField.getAttribute("value"));
		logger.log(Level.INFO, "dateOfBirthField.getAttribute('value').length() = " + dateOfBirthField.getAttribute("value").length());
		
		assertTrue(
				"date of birth has "+dateLengthAfterReset+" characters after resetting preferences", 
				dateOfBirthField.getAttribute("value").length() == dateLengthAfterReset
			);
		
	}
	
	@Test
	@RunAsClient
	@InSequence(4000)
	public void reset() throws Exception {
		
		// because some test failures throw us into a strange state, 
		// let's reset preferences and the page we are on
		logger.log(Level.INFO, "browser.navigate().to("+url+")");
		browser.navigate().to(url);
		Thread.sleep(500);
		menuButton.click();
		Thread.sleep(500);
		menuPreferences.click();
		Thread.sleep(500);
		resetButton.click();
		logger.log(Level.INFO, "resetButton.click() ...");
		Thread.sleep(500);
		logger.log(Level.INFO, "browser.navigate().to("+url+")");
		browser.navigate().to(url);
		Thread.sleep(500);
		logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());
		assertTrue("We are on the correct page, which should be, url = " + url, browser.getCurrentUrl().contains(url));
		
	}
	
	@Test
	@RunAsClient
	@InSequence(5000)
	public void allFieldsRequiredUponSubmit() throws Exception {
		
		int tagsWhileInvalid = 1;
		
		logger.log(Level.INFO, "clearing fields ...");
		firstNameField.clear();
		lastNameField.clear();
		emailAddressField.clear();
		phoneNumberField.clear();
		dateOfBirthField.clear();
		cityField.clear();
		postalCodeField.clear();
		logger.log(Level.INFO, "clicking submit ...");
		submitButton.click();
		Thread.sleep(500);
		
		logger.log(Level.INFO, "checking for error tags on firstNameField ...");
		int tags = browser.findElements(By.xpath(firstNameFieldErrorXpath)).size();
		logger.log(Level.INFO, "tags = " + tags);
		assertTrue("# of error tags == tagsWhileInvalid", tags == tagsWhileInvalid);
		
		logger.log(Level.INFO, "firstNameFieldError.getText() = " + firstNameFieldError.getText());
		logger.log(Level.INFO, "lastNameFieldError.getText() = " + lastNameFieldError.getText());
		logger.log(Level.INFO, "emailAddressFieldError.getText() = " + emailAddressFieldError.getText());
		logger.log(Level.INFO, "phoneNumberFieldError.getText() = " + phoneNumberFieldError.getText());
		logger.log(Level.INFO, "dateOfBirthFieldError.getText() = " + dateOfBirthFieldError.getText());
		logger.log(Level.INFO, "cityFieldError.getText() = " + cityFieldError.getText());
		logger.log(Level.INFO, "provinceIdFieldError.getText() = " + provinceIdFieldError.getText());
		logger.log(Level.INFO, "postalCodeFieldError.getText() = " + postalCodeFieldError.getText());
		
		assertTrue("firstNameFieldError contains Value is required", firstNameFieldError.getText().contains("Value is required"));
		assertTrue("lastNameFieldError contains Value is required", lastNameFieldError.getText().contains("Value is required"));
		assertTrue("emailAddressFieldError contains Value is required", emailAddressFieldError.getText().contains("Value is required"));
		assertTrue("phoneNumberFieldError contains Value is required", phoneNumberFieldError.getText().contains("Value is required"));
		assertTrue("dateOfBirthFieldError contains Value is required", dateOfBirthFieldError.getText().contains("Value is required"));
		assertTrue("cityFieldError contains Value is required", cityFieldError.getText().contains("Value is required"));
		assertTrue("provinceIdFieldError contains Value is required", provinceIdFieldError.getText().contains("Value is required"));
		assertTrue("postalCodeFieldError contains Value is required", postalCodeFieldError.getText().contains("Value is required"));
		
	}
	
	@Test
	@RunAsClient
	@InSequence(6000)
	public void cityAndStateAutoPopulate() throws Exception {
		
		logger.log(Level.INFO, "before cityField.getAttribute('value') = " + cityField.getAttribute("value"));
		logger.log(Level.INFO, "before provinceIdField.getAttribute('value') = " + provinceIdField.getAttribute("value"));
		logger.log(Level.INFO, "before postalCodeField.getAttribute('value') = " + postalCodeField.getAttribute("value"));
		assertTrue("cityField is empty", (cityField.getAttribute("value").length() == 0));
		assertTrue("provinceIdField is empty", (provinceIdField.getAttribute("value").length() == 0));
		assertTrue("postalCodeField is empty", (postalCodeField.getAttribute("value").length() == 0));
		
		postalCodeField.sendKeys("32801");
		phoneNumberField.click();
		Thread.sleep(500);
		logger.log(Level.INFO, "after cityField.getAttribute('value') = " + cityField.getAttribute("value"));
		logger.log(Level.INFO, "after provinceIdField.getAttribute('value') = " + provinceIdField.getAttribute("value"));
		logger.log(Level.INFO, "after postalCodeField.getAttribute('value') = " + postalCodeField.getAttribute("value"));
		assertTrue("cityField contains Orlando", cityField.getAttribute("value").contains("Orlando"));
		assertTrue("provinceIdField contains 3", provinceIdField.getAttribute("value").contains("3"));
		assertTrue("postalCodeField contains 32801", postalCodeField.getAttribute("value").contains("32801"));
		
	}
	
	@Test
	@RunAsClient
	@InSequence(7000)
	public void commentsFunctioning() throws Exception {
		
		String testing123 = "testing 1, 2, 3";
		int tags = 0;
		int tagsWhileHidden = 1;
		int tagsWhileShowing = 2;
		
		showCommentsLink.click();
		Thread.sleep(500);
		
		int hideCommentsLinks = browser.findElements(By.xpath("//a[contains(text(),'Hide Comments')]")).size();
		logger.log(Level.INFO, "# of hideCommentsLinks = " + hideCommentsLinks);
		assertTrue("# of hideCommentsLinks == 1", hideCommentsLinks == 1);
		logger.log(Level.INFO, "comments.isDisplayed() = " + comments.isDisplayed());
		assertTrue("comments textarea is displayed", comments.isDisplayed());
		tags = browser.findElements(By.xpath("//a[contains(text(),'Hide Comments')]/../../child::node()")).size();
		logger.log(Level.INFO, "tags = " + tags);
		assertTrue("tag for Hide and tag for textarea are showing", tags == tagsWhileShowing);
		
		comments.sendKeys(testing123);
		phoneNumberField.click();
		Thread.sleep(500);
		hideCommentsLink.click();
		Thread.sleep(500);
		tags = browser.findElements(By.xpath("//a[contains(text(),'Show Comments')]/../../child::node()")).size();
		logger.log(Level.INFO, "tags = " + tags);
		assertTrue("no textarea is showing", tags == tagsWhileHidden);
		logger.log(Level.INFO, "showCommentsLink.isDisplayed() = " + showCommentsLink.isDisplayed());
		showCommentsLink.click();
		Thread.sleep(500);
		logger.log(Level.INFO, "after hide and show comments.getAttribute('value') = " + comments.getAttribute("value"));
		assertTrue("comments are still there after hide and show", testing123.equals(comments.getAttribute("value")));
		
	}
	
	@Test
	@RunAsClient
	@InSequence(8000)
	public void dateValidation() throws Exception {
		
		int tags = 0;
		int tagsWhileInvalid = 2;
		int tagsWhileValid = 0;
		
		// checks an invalid dateOfBirth
		try {
			dateOfBirthField.clear();
			logger.log(Level.INFO, "No exceptions occured when clearing the dateOfBirthField");
		} catch (Exception e) {
			logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
			assertTrue("No exceptions occured when clearing the dateOfBirthField", false);
		}
		Thread.sleep(500);
		dateOfBirthField.sendKeys("12/34/5678");
		Thread.sleep(500);
		submitButton.click();
		Thread.sleep(500);
		logger.log(Level.INFO, "dateOfBirthField.getAttribute('value') = " + dateOfBirthField.getAttribute("value"));
		logger.log(Level.INFO, "dateOfBirthFieldError.isDisplayed() = " + dateOfBirthFieldError.isDisplayed());
		logger.log(Level.INFO, "dateOfBirthFieldError.getText() = " + dateOfBirthFieldError.getText());
		assertTrue("Invalid dateOfBirthField validation message displayed", 
				dateOfBirthFieldError.getText().contains("Invalid date format"));
		tags = browser.findElements(By.xpath("//input[contains(@id,':dateOfBirth')]/../following-sibling::*[1]/child::node()")).size();
		logger.log(Level.INFO, "tags = " + tags);
		logger.log(Level.INFO, "asserting: tags == tagsWhileInvalid? "+tags+" == "+tagsWhileInvalid+"? ...");
		assertTrue("tags == tagsWhileInvalid? "+tags+" == "+tagsWhileInvalid+"?", tags == tagsWhileInvalid);
		
		// checks with no dateOfBirth
		dateOfBirthField.clear();
		Thread.sleep(500);
		phoneNumberField.click();
		Thread.sleep(500);
		logger.log(Level.INFO, "dateOfBirthField.getAttribute('value') = " + dateOfBirthField.getAttribute("value"));
		logger.log(Level.INFO, "dateOfBirthFieldError.isDisplayed() = " + dateOfBirthFieldError.isDisplayed());
		logger.log(Level.INFO, "dateOfBirthFieldError.getText() = " + dateOfBirthFieldError.getText());
		// Should I be this lenient?
		assertTrue("Value is required for dateOfBirthField message displayed", 
				dateOfBirthFieldError.getText().contains("Value is required") ||
				dateOfBirthFieldError.getText().contains("Invalid date format")
			);
		tags = browser.findElements(By.xpath("//input[contains(@id,':dateOfBirth')]/../following-sibling::*[1]/child::node()")).size();
		logger.log(Level.INFO, "tags = " + tags);
		logger.log(Level.INFO, "asserting: tags == tagsWhileInvalid? "+tags+" == "+tagsWhileInvalid+"? ...");
		assertTrue("tags == tagsWhileInvalid? "+tags+" == "+tagsWhileInvalid+"?", tags == tagsWhileInvalid);
		
		// checks a valid dateOfBirth
		dateOfBirthField.clear();
		Thread.sleep(500);
		logger.log(Level.INFO, "Entering a valid dateOfBirth = 01/02/3456 ...");
		dateOfBirthField.sendKeys("01/02/3456");
		Thread.sleep(500);
		logger.log(Level.INFO, "Clicking into the phoneNumberField ...");
		phoneNumberField.click();
		Thread.sleep(500);
		logger.log(Level.INFO, "Should still contain the valid dateOfBirthField.getAttribute('value') = " + dateOfBirthField.getAttribute("value"));
		assertTrue("dateOfBirthField is currently showing 01/02/3456 ?", "01/02/3456".equals(dateOfBirthField.getAttribute("value")));
		tags = browser.findElements(By.xpath("//input[contains(@id,':dateOfBirth')]/../following-sibling::*[1]/child::node()")).size();
		logger.log(Level.INFO, "tags = " + tags);
		assertTrue("tags == tagsWhileValid? "+tags+" == "+tagsWhileValid+"?", tags == tagsWhileValid);
		
	}
	
	@Test
	@RunAsClient
	@InSequence(9000)
	public void submitAndValidate() throws Exception {
		
		logger.log(Level.INFO, "clearing fields ...");
		dateOfBirthField.clear();
		emailAddressField.clear();
		postalCodeField.clear();
		
		int commentsTextAreas = browser.findElements(By.xpath("//textarea[contains(@id,':comments')]")).size();
		logger.log(Level.INFO, "# of commentsTextAreas = " + commentsTextAreas);
		assertTrue("# of commentsTextAreas == 1", commentsTextAreas == 1);
		
		comments.clear();
		Thread.sleep(500);
		logger.log(Level.INFO, "fields were cleared now, but let's see ...");
		logger.log(Level.INFO, "emailAddressField.getAttribute('value') = " + emailAddressField.getAttribute("value"));
		assertTrue("emailAddressField is empty after clearing and clicking into another field", "".equals(emailAddressField.getAttribute("value")));
		
		logger.log(Level.INFO, "entering data ...");
		firstNameField.sendKeys("David");
		lastNameField.sendKeys("Samuel");
		emailAddressField.sendKeys("no_need@just.pray");
		phoneNumberField.sendKeys("(way) too-good");
		dateOfBirthField.sendKeys("01/02/3456");
		postalCodeField.sendKeys("32801");
		phoneNumberField.click();
		Thread.sleep(500);
		comments.sendKeys("If as one people speaking the same language, they have begun to do this ...");
		Thread.sleep(500);
		
		// asserting correct data is still there
		assertTrue("firstNameField.getText().equals('David')", firstNameField.getAttribute("value").equals("David"));
		assertTrue("lastNameField.getText().equals('Samuel')", lastNameField.getAttribute("value").equals("Samuel"));
		assertTrue("emailAddressField.getText().equals('no_need@just.pray')", emailAddressField.getAttribute("value").equals("no_need@just.pray"));
		assertTrue("phoneNumberField.getText().equals('(way) too-good')", phoneNumberField.getAttribute("value").equals("(way) too-good"));
		assertTrue("dateOfBirthField.getText().equals('01/02/3456')", dateOfBirthField.getAttribute("value").equals("01/02/3456"));
		assertTrue("postalCodeField.getText().equals('32801')", postalCodeField.getAttribute("value").equals("32801"));
		assertTrue("comments.getText().equals('If as one people speaking the same language, they have begun to do this ...')",
				comments.getAttribute("value").equals("If as one people speaking the same language, they have begun to do this ..."));
		
		submitButton.click();
		Thread.sleep(500);
		
		logger.log(Level.INFO, "formTag.getText() = " + formTag.getText());
		assertTrue("Dear David", formTag.getText().contains("Dear David"));
		
	}

}
