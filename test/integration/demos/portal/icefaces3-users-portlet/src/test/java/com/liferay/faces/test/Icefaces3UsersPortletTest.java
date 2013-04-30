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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

@RunWith(Arquillian.class)
public class Icefaces3UsersPortletTest {

	// private static final Logger logger;
	private final static Logger logger = Logger.getLogger(Icefaces3UsersPortletTest.class.getName());
	
	// @ArquillianResource
	// URL portalURL;
	String signInUrl = "http://localhost:8080/web/guest/jsf2-sign-in";
	String url = "http://localhost:8080/group/control_panel/manage?p_p_id=1_WAR_icefaces3usersportlet&p_p_lifecycle=0&p_p_state=maximized&p_p_mode=view&doAsGroupId=10180&refererPlid=10183";

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

	// elements for Icefaces3Users
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
	
	// Elements for reseting the John Adams user before running the test 
	private static final String selectStatusBeforeXpath = "//select[contains(@id, 'status')]";
	@FindBy(xpath = selectStatusBeforeXpath)
	private WebElement selectStatusBefore;
	private static final String optionInactiveBeforeXpath = "//option[text()=' Inactive ']";
	@FindBy(xpath = optionInactiveBeforeXpath)
	private WebElement optionInactiveBefore;
	private static final String johnAdamsBeforeXpath = "//a[text()='john.adams']";
	@FindBy(xpath = johnAdamsBeforeXpath)
	private WebElement johnAdamsBefore;
	private static final String johnAdamsMenuBeforeXpath = "//a[contains(@id, 'john-adams_menuButton')]/span[text()='Actions']";///child::img[contains(@src, 'activate.png')]";
	@FindBy(xpath = johnAdamsMenuBeforeXpath)
	private WebElement johnAdamsMenuBefore;	
	private static final String activateJohnAdamsBeforeXpath = "//a[contains(@id, 'john-adams_menu_activate')]";///child::img[contains(@src, 'activate.png')]";
	@FindBy(xpath = activateJohnAdamsBeforeXpath)
	private WebElement activateJohnAdamsBefore;	
	private static final String deleteLinkBeforeXpath = "//span[@class='taglib-text' and text()='Delete']";
	@FindBy(xpath = deleteLinkBeforeXpath)
	private WebElement deleteLinkBefore;
	private static final String emailInputBeforeXpath = "//input[contains(@id, 'emailAddress')]";
	@FindBy(xpath = emailInputBeforeXpath)
	private WebElement emailInputBefore;
	private static final String firstNameInputBeforeXpath = "//input[contains(@id, 'firstName')]";
	@FindBy(xpath = firstNameInputBeforeXpath)
	private WebElement firstNameInputBefore;
	private static final String middleNameInputBeforeXpath = "//input[contains(@id, 'middleName')]";
	@FindBy(xpath = middleNameInputBeforeXpath)
	private WebElement middleNameInputBefore;
	private static final String lastNameInputBeforeXpath = "//input[contains(@id, 'lastName')]";
	@FindBy(xpath = lastNameInputBeforeXpath)
	private WebElement lastNameInputBefore;
	private static final String jobTitleInputBeforeXpath = "//input[contains(@id, 'jobTitle')]";
	@FindBy(xpath = jobTitleInputBeforeXpath)
	private WebElement jobTitleInputBefore;
	private static final String saveButtonBeforeXpath = "//input[@type='submit' and @value='Save']";
	@FindBy(xpath = saveButtonBeforeXpath)
	private WebElement saveButtonBefore;	
	private static final String errorMessageBeforeXpath = "//div[@class='portlet-msg-error' and text()='Your request failed to complete.']";
	@FindBy(xpath = errorMessageBeforeXpath)
	private static WebElement errorMessageBefore;
	private static final String errorPassword1BeforeXpath = "//input[contains(@id, 'password1') and @type='password']";
	@FindBy(xpath = errorPassword1BeforeXpath)
	private static WebElement errorPassword1Before;
	private static final String errorPassword2BeforeXpath = "//input[contains(@id, 'password2') and @type='password']";
	@FindBy(xpath = errorPassword2BeforeXpath)
	private static WebElement errorPassword2Before;
	
	// Elements for users' list
	private static final String screenNameColumnHeaderXpath = "//span[contains(@id,':users:screenName_text') and text()='Screen Name']";
	@FindBy(xpath = screenNameColumnHeaderXpath)
	private WebElement screenNameColumnHeader;
	private static final String screenNameColumnSortIconXpath = "//div[contains(@id,'users:screenName')]/child::span[@class='ui-header-right']/child::span[@class='ui-sortable-control']/child::span[contains(@class,'ui-sortable-column-icon')]";
	@FindBy(xpath = screenNameColumnSortIconXpath)
	private WebElement screenNameColumnSortIcon;
	private static final String screenNameColumnFilterXpath = "//input[contains(@id,'users:screenName_filter')]";
	@FindBy(xpath = screenNameColumnFilterXpath)
	private WebElement screenNameColumnFilter;
	
	private static final String lastNameColumnHeaderXpath = "//span[contains(@id,':users:lastName_text') and text()='Last Name']";
	@FindBy(xpath = lastNameColumnHeaderXpath)
	private WebElement lastNameColumnHeader;
	private static final String lastNameColumnSortIconXpath = "//div[contains(@id,'users:lastName')]/child::span[@class='ui-header-right']/child::span[@class='ui-sortable-control']/child::span[contains(@class,'ui-sortable-column-icon')]";
	@FindBy(xpath = lastNameColumnSortIconXpath)
	private WebElement lastNameColumnSortIcon;
	private static final String lastNameColumnFilterXpath = "//input[contains(@id,'users:lastName_filter')]";
	@FindBy(xpath = lastNameColumnFilterXpath)
	private WebElement lastNameColumnFilter;
	
	private static final String firstNameColumnHeaderXpath = "//span[contains(@id,':users:firstName_text') and text()='First Name']";
	@FindBy(xpath = firstNameColumnHeaderXpath)
	private WebElement firstNameColumnHeader;
	private static final String firstNameColumnSortIconXpath = "//div[contains(@id,'users:firstName')]/child::span[@class='ui-header-right']/child::span[@class='ui-sortable-control']/child::span[contains(@class,'ui-sortable-column-icon')]";
	@FindBy(xpath = firstNameColumnSortIconXpath)
	private WebElement firstNameColumnSortIcon;
	private static final String firstNameColumnFilterXpath = "//input[contains(@id,'users:firstName_filter')]";
	@FindBy(xpath = firstNameColumnFilterXpath)
	private WebElement firstNameColumnFilter;
	
	private static final String middleNameColumnHeaderXpath = "//span[contains(@id,':users:middleName_text') and text()='Middle Name']";
	@FindBy(xpath = middleNameColumnHeaderXpath)
	private WebElement middleNameColumnHeader;
	private static final String middleNameColumnSortIconXpath = "//div[contains(@id,'users:middleName')]/child::span[@class='ui-header-right']/child::span[@class='ui-sortable-control']/child::span[contains(@class,'ui-sortable-column-icon')]";
	@FindBy(xpath = middleNameColumnSortIconXpath)
	private WebElement middleNameColumnSortIcon;
	private static final String middleNameColumnFilterXpath = "//input[contains(@id,'users:middleName_filter')]";
	@FindBy(xpath = middleNameColumnFilterXpath)
	private WebElement middleNameColumnFilter;
	
	private static final String emailAddressColumnHeaderXpath = "//span[contains(@id,':users:emailAddress_text') and text()='Email Address']";
	@FindBy(xpath = emailAddressColumnHeaderXpath)
	private WebElement emailAddressColumnHeader;
	private static final String emailAddressColumnSortIconXpath = "//div[contains(@id,'users:emailAddress')]/child::span[@class='ui-header-right']/child::span[@class='ui-sortable-control']/child::span[contains(@class,'ui-sortable-column-icon')]";
	@FindBy(xpath = emailAddressColumnSortIconXpath)
	private WebElement emailAddressColumnSortIcon;
	private static final String emailAddressColumnFilterXpath = "//input[contains(@id,'users:emailAddress_filter')]";
	@FindBy(xpath = emailAddressColumnFilterXpath)
	private WebElement emailAddressColumnFilter;
	
	private static final String jobTitleColumnHeaderXpath = "//span[contains(@id,':users:jobTitle_text') and text()='Job Title']";
	@FindBy(xpath = jobTitleColumnHeaderXpath)
	private WebElement jobTitleColumnHeader;
	private static final String jobTitleColumnSortIconXpath = "//div[contains(@id,'users:jobTitle')]/child::span[@class='ui-header-right']/child::span[@class='ui-sortable-control']/child::span[contains(@class,'ui-sortable-column-icon')]";
	@FindBy(xpath = jobTitleColumnSortIconXpath)
	private WebElement jobTitleColumnSortIcon;
	private static final String jobTitleColumnFilterXpath = "//input[contains(@id,'users:jobTitle_filter')]";
	@FindBy(xpath = jobTitleColumnFilterXpath)
	private WebElement jobTitleColumnFilter;
	
	// John Adams users' list view 
	private static final String johnAdamsUserScreenNameCellXpath = "//span[contains(@id,':screenNameCell') and text()='john.adams']";
	@FindBy(xpath = johnAdamsUserScreenNameCellXpath)
	private WebElement johnAdamsUserScreenNameCell;
	private static final String johnAdamsUserLastNameCellXpath = "//span[contains(@id,':lastNameCell') and text()='Adams']";
	@FindBy(xpath = johnAdamsUserLastNameCellXpath)
	private WebElement johnAdamsUserLastNameCell;
	private static final String johnAdamsUserFirstNameCellXpath = "//span[contains(@id,':firstNameCell') and text()='John']";
	@FindBy(xpath = johnAdamsUserFirstNameCellXpath)
	private WebElement johnAdamsUserFirstNameCell;
	private static final String johnAdamsUserEmailAddressCellXpath = "//a[@href='mailto:john.adams@liferay.com']";
	@FindBy(xpath = johnAdamsUserEmailAddressCellXpath)
	private WebElement johnAdamsUserEmailAddressCell;
	
	// Samuel Adams screen name in users' list
	private static final String samuelAdamsUserScreenNameCellXpath = "//span[contains(@id,':screenNameCell') and text()='samuel.adams']";
	@FindBy(xpath = samuelAdamsUserScreenNameCellXpath)
	private WebElement samuelAdamsUserScreenNameCell;
	
	// Elements for column 1 of John Adam's detailed user view
	private static final String firstNameFieldXpath = "//input[contains(@class, 'ui-inputfield') and contains(@class, 'ui-textentry') and contains(@id,':firstName_input')]"; 
	@FindBy(xpath = firstNameFieldXpath)
	private WebElement firstNameField;
	private static final String firstNameFieldErrorXpath = "//input[contains(@class, 'ui-inputfield') and contains(@class, 'ui-textentry') and contains(@id,':firstName_input')]/../../following-sibling::span[1]/span[@class='portlet-msg-error' and text()='Value is required']"; 
	@FindBy(xpath = firstNameFieldErrorXpath)
	private WebElement firstNameFieldError;
	private static final String middleNameFieldXpath = "//input[contains(@class, 'ui-inputfield') and contains(@class, 'ui-textentry') and contains(@id,':middleName_input')]";
	@FindBy(xpath = middleNameFieldXpath)
	private WebElement middleNameField;
	private static final String lastNameFieldXpath = "//input[contains(@class, 'ui-inputfield') and contains(@class, 'ui-textentry') and contains(@id,':lastName_input')]";
	@FindBy(xpath = lastNameFieldXpath)
	private WebElement lastNameField;
	private static final String lastNameFieldErrorXpath = "//input[contains(@class, 'ui-inputfield') and contains(@class, 'ui-textentry') and contains(@id,':lastName_input')]/../../following-sibling::span[1]/span[@class='portlet-msg-error' and text()='Value is required']";
	@FindBy(xpath = lastNameFieldErrorXpath)
	private WebElement lastNameFieldError;
	private static final String emailAddressFieldXpath = "//input[contains(@class, 'ui-inputfield') and contains(@class, 'ui-textentry') and contains(@id,':emailAddress_input')]";
	@FindBy(xpath = emailAddressFieldXpath)
	private WebElement emailAddressField;
	private static final String emailAddressFieldErrorXpath = "//input[contains(@class, 'ui-inputfield') and contains(@class, 'ui-textentry') and contains(@id,':emailAddress_input')]/../../following-sibling::span[1]/span[@class='portlet-msg-error' and text()='Value is required']";
	@FindBy(xpath = emailAddressFieldErrorXpath)
	private WebElement emailAddressFieldError;
	private static final String jobTitleFieldXpath = "//input[contains(@class, 'ui-inputfield') and contains(@class, 'ui-textentry') and contains(@id,':jobTitle_input')]";
	@FindBy(xpath = jobTitleFieldXpath)
	private WebElement jobTitleField;
	private static final String submitButtonXpath = "//span[text()='Submit']/parent::button[contains(@name, ':pushButtonSubmit_button') and @type='button']";
	@FindBy(xpath = submitButtonXpath)
	private WebElement submitButton;
	private static final String cancelButtonXpath = "//span[text()='Cancel']/parent::button[contains(@name, ':pushButtonCancel_button') and @type='button']";
	@FindBy(xpath = cancelButtonXpath)
	private WebElement cancelButton;
	
	// Elements for column 2 of John Adam's detailed user view
	private static final String dropdownActiveFieldXpath = "//select[contains(@id,':s1')]/option[text()='Active']";
	@FindBy(xpath = dropdownActiveFieldXpath)
	private WebElement dropdownActiveField;
	private static final String dropdownActiveSelectedFieldXpath = "//select[contains(@id,':s1')]/option[@selected='selected' and text()='Active']";
	@FindBy(xpath = dropdownActiveSelectedFieldXpath)
	private WebElement dropdownActiveSelectedField;
	private static final String dropdownInactiveFieldXpath = "//select[contains(@id,':s1')]/option[text()='Inactive']";
	@FindBy(xpath = dropdownInactiveFieldXpath)
	private WebElement dropdownInactiveField;
	private static final String dropdownInactiveSelectedFieldXpath = "//select[contains(@id,':s1')]/option[@selected='selected' and text()='Inactive']";
	@FindBy(xpath = dropdownInactiveSelectedFieldXpath)
	private WebElement dropdownInactiveSelectedField;
	
	// Elements for column 3 of John Adam's detailed user view
	private static final String portraitXpath = "//input[@type='file' and contains(@id, ':fileEntryComp')]/../../../img[contains(@src, 'user_male_portrait')]";
	@FindBy(xpath = portraitXpath)
	private WebElement portrait;
	private static final String changedPortraitXpath = "//input[@type='file' and contains(@id, ':fileEntryComp')]/../../../img[not(contains(@src, 'user_male_portrait'))]";
	@FindBy(xpath = changedPortraitXpath)
	private WebElement changedPortrait;
	private static final String fileEntryXpath = "//input[@type='file' and contains(@id, ':fileEntryComp')]";
	@FindBy(xpath = fileEntryXpath)
	private WebElement fileEntry;
	private static final String fileUploadButtonXpath = "//input[@type='submit' and contains(@value, 'Upload Image')]";
	@FindBy(xpath = fileUploadButtonXpath)
	private WebElement fileUploadButton;
	
	// Elements for the changed version of John Adams
	private static final String changedUserLastNameCellXpath = "//span[contains(@id,':lastNameCell') and text()='Aa']";
	@FindBy(xpath = changedUserLastNameCellXpath)
	private WebElement changedUserLastNameCell;
	private static final String changedUserFirstNameCellXpath = "//span[contains(@id,':firstNameCell') and text()='Aa']";
	@FindBy(xpath = changedUserFirstNameCellXpath)
	private WebElement changedUserFirstNameCell;
	private static final String changedUserMiddleNameCellXpath = "//span[contains(@id,':middleNameCell') and text()='Aa']";
	@FindBy(xpath = changedUserMiddleNameCellXpath)
	private WebElement changedUserMiddleNameCell;
	private static final String changedUserEmailAddressCellXpath = "//a[@href='mailto:A@A.com']";
	@FindBy(xpath = changedUserEmailAddressCellXpath)
	private WebElement changedUserEmailAddressCell;
	private static final String changedUserJobTitleCellXpath = "//span[contains(@id,':jobTitleCell') and text()='Aa']";
	@FindBy(xpath = changedUserJobTitleCellXpath)
	private WebElement changedUserJobTitleCell;
	
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
	@InSequence(0)
	public void testSetup() throws Exception{
		
		signIn();
		browser.navigate().to("http://localhost:8080/group/control_panel/manage?p_p_id=125&p_p_lifecycle=0&p_p_state=maximized&p_p_mode=view&doAsGroupId=10180&refererPlid=10183&_125_struts_action=%2Fusers_admin%2Fview&_125_tabs1=users&_125_usersListView=flat-users&_125_saveUsersListView=true");
		
		selectStatusBefore.click();
		(new Actions(browser)).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.TAB).perform();
		selectStatusBefore.submit();
		
		if(isThere(johnAdamsBeforeXpath)) {
			johnAdamsMenuBefore.click();
			activateJohnAdamsBefore.click();
		}
		
		browser.navigate().to("http://localhost:8080/group/control_panel/manage?p_p_id=125&p_p_lifecycle=0&p_p_state=maximized&p_p_mode=view&doAsGroupId=10180&refererPlid=10183&_125_struts_action=%2Fusers_admin%2Fedit_user&_125_redirect=http%3A%2F%2Flocalhost%3A8080%2Fgroup%2Fcontrol_panel%2Fmanage%3Fp_p_id%3D125%26p_p_lifecycle%3D0%26p_p_state%3Dmaximized%26p_p_mode%3Dview%26doAsGroupId%3D10180%26refererPlid%3D10183%26_125_refererPlid%3D10183%26_125_doAsGroupId%3D10180%26_125_cur1%3D1%26_125_delta1%3D20%26_125_keywords%3D%26_125_advancedSearch%3Dfalse%26_125_andOperator%3Dtrue%26_125_city%3D%26_125_countryId%3D0%26_125_name%3D%26_125_parentOrganizationId%3D0%26_125_regionId%3D0%26_125_street%3D%26_125_zip%3D%26_125_orderByCol%3Dlast-name%26_125_orderByType%3Dasc%26_125_resetCur%3Dfalse%26_125_cur2%3D1%26_125_delta2%3D20%26_125_status%3D0%26_125_emailAddress%3D%26_125_firstName%3D%26_125_lastName%3D%26_125_middleName%3D%26_125_organizationId%3D0%26_125_roleId%3D0%26_125_screenName%3D%26_125_userGroupId%3D0&_125_p_u_i_d=11616");
		
		if(deleteLinkBefore.isDisplayed()) {
			deleteLinkBefore.click();
		}
		
		emailInputBefore.clear();
		emailInputBefore.sendKeys("john.adams@liferay.com");
		firstNameInputBefore.clear();
		firstNameInputBefore.sendKeys("John");
		middleNameInputBefore.clear();
		lastNameInputBefore.clear();
		lastNameInputBefore.sendKeys("Adams");
		jobTitleInputBefore.clear();
		saveButtonBefore.click();
		
		if(isThere(errorMessageBeforeXpath)) {
			errorPassword1Before.clear();
			errorPassword1Before.sendKeys("testtest");
			errorPassword2Before.clear();
			errorPassword2Before.sendKeys("testtest");
			saveButtonBefore.click();
		}
	}

	@Test
	@RunAsClient
	@InSequence(1000)
	public void usersListView() throws Exception {
		
		logger.log(Level.INFO, "browser.navigate().to("+url+")");
		browser.navigate().to(url);
		logger.log(Level.INFO, "browser.getTitle() = " + browser.getTitle());
		logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());
		logger.log(Level.INFO, "portletTitleText.getText() = " + portletTitleText.getText());
		
		assertTrue("portletTitleText.isDisplayed()", portletTitleText.isDisplayed());
		
		logger.log(Level.INFO, "screenNameColumnHeader.isDisplayed() = " + screenNameColumnHeader.isDisplayed());
		assertTrue("The Screen Name Column Header should be displayed on the page at this point but it is not.", screenNameColumnHeader.isDisplayed());
		logger.log(Level.INFO, "screenNameColumnSortIcon.isDisplayed() = " + screenNameColumnSortIcon.isDisplayed());
		assertTrue("The Screen Name Column Sort Icon should be displayed on the page at this point but it is not.", screenNameColumnSortIcon.isDisplayed());
		logger.log(Level.INFO, "screenNameColumnFilter.isDisplayed() = " + screenNameColumnFilter.isDisplayed());
		assertTrue("The Screen Name Column Text Filter should be displayed on the page at this point but it is not.", screenNameColumnFilter.isDisplayed());
		
		logger.log(Level.INFO, "lastNameColumnHeader.isDisplayed() = " + lastNameColumnHeader.isDisplayed());
		assertTrue("The Last Name Column Header should be displayed on the page at this point but it is not.", lastNameColumnHeader.isDisplayed());
		logger.log(Level.INFO, "lastNameColumnSortIcon.isDisplayed() = " + lastNameColumnSortIcon.isDisplayed());
		assertTrue("The Last Name Column Sort Icon should be displayed on the page at this point but it is not.", lastNameColumnSortIcon.isDisplayed());
		logger.log(Level.INFO, "lastNameColumnFilter.isDisplayed() = " + lastNameColumnFilter.isDisplayed());
		assertTrue("The Last Name Column Text Filter should be displayed on the page at this point but it is not.", lastNameColumnFilter.isDisplayed());
		
		logger.log(Level.INFO, "firstNameColumnHeader.isDisplayed() = " + firstNameColumnHeader.isDisplayed());
		assertTrue("The First Name Column Header should be displayed on the page at this point but it is not.", firstNameColumnHeader.isDisplayed());
		logger.log(Level.INFO, "firstNameColumnSortIcon.isDisplayed() = " + firstNameColumnSortIcon.isDisplayed());
		assertTrue("The First Name Column Sort Icon should be displayed on the page at this point but it is not.", firstNameColumnSortIcon.isDisplayed());
		logger.log(Level.INFO, "firstNameColumnFilter.isDisplayed() = " + firstNameColumnFilter.isDisplayed());
		assertTrue("The First Name Column Text Filter should be displayed on the page at this point but it is not.", firstNameColumnFilter.isDisplayed());
		
		logger.log(Level.INFO, "middleNameColumnHeader.isDisplayed() = " + middleNameColumnHeader.isDisplayed());
		assertTrue("The Middle Name Column Header should be displayed on the page at this point but it is not.", middleNameColumnHeader.isDisplayed());
		logger.log(Level.INFO, "middleNameColumnSortIcon.isDisplayed() = " + middleNameColumnSortIcon.isDisplayed());
		assertTrue("The Middle Name Column Sort Icon should be displayed on the page at this point but it is not.", middleNameColumnSortIcon.isDisplayed());
		logger.log(Level.INFO, "middleNameColumnFilter.isDisplayed() = " + middleNameColumnFilter.isDisplayed());
		assertTrue("The Middle Name Column Text Filter should be displayed on the page at this point but it is not.", middleNameColumnFilter.isDisplayed());
		
		logger.log(Level.INFO, "emailAddressColumnHeader.isDisplayed() = " + emailAddressColumnHeader.isDisplayed());
		assertTrue("The Email Address Column Header should be displayed on the page at this point but it is not.", emailAddressColumnHeader.isDisplayed());
		logger.log(Level.INFO, "emailAddressColumnSortIcon.isDisplayed() = " + emailAddressColumnSortIcon.isDisplayed());
		assertTrue("The Email Address Column Sort Icon should be displayed on the page at this point but it is not.", emailAddressColumnSortIcon.isDisplayed());
		logger.log(Level.INFO, "emailAddressColumnFilter.isDisplayed() = " + emailAddressColumnFilter.isDisplayed());
		assertTrue("The Email Address Column Text Filter should be displayed on the page at this point but it is not.", emailAddressColumnFilter.isDisplayed());
		
		logger.log(Level.INFO, "jobTitleColumnHeader.isDisplayed() = " + jobTitleColumnHeader.isDisplayed());
		assertTrue("The Job Title Column Header should be displayed on the page at this point but it is not.", jobTitleColumnHeader.isDisplayed());
		logger.log(Level.INFO, "jobTitleColumnSortIcon.isDisplayed() = " + jobTitleColumnSortIcon.isDisplayed());
		assertTrue("The Job Title Column Sort Icon should be displayed on the page at this point but it is not.", jobTitleColumnSortIcon.isDisplayed());
		logger.log(Level.INFO, "jobTitleColumnFilter.isDisplayed() = " + jobTitleColumnFilter.isDisplayed());
		assertTrue("The Job Title Column Text Filter should be displayed on the page at this point but it is not.", jobTitleColumnFilter.isDisplayed());

		logger.log(Level.INFO, "johnAdamsUserScreenNameCell.isDisplayed() = " + johnAdamsUserScreenNameCell.isDisplayed());
		assertTrue("The Screen Name Cell of the John Adams user should be displayed on the page as john.adams at this point but it is not.", johnAdamsUserScreenNameCell.isDisplayed());
		logger.log(Level.INFO, "johnAdamsUserLastNameCell.isDisplayed() = " + johnAdamsUserLastNameCell.isDisplayed());
		assertTrue("The Last Name Cell of the John Adams user should be displayed on the page as Adams at this point but it is not.", johnAdamsUserLastNameCell.isDisplayed());
		logger.log(Level.INFO, "johnAdamsUserLastNameCell.isDisplayed() = " + johnAdamsUserFirstNameCell.isDisplayed());
		assertTrue("The First Name Cell of the John Adams user should be displayed on the page as John at this point but it is not.", johnAdamsUserFirstNameCell.isDisplayed());
		logger.log(Level.INFO, "johnAdamsUserEmailAddressCell.isDisplayed() = " + johnAdamsUserEmailAddressCell.isDisplayed());
		assertTrue("The Email Address Cell of the John Adams user should be displayed on the page as john.adams@liferay.com at this point but it is not.", johnAdamsUserEmailAddressCell.isDisplayed());	
	}
	
	@Test
	@RunAsClient
	@InSequence(2000)
	public void specificUserView() throws Exception {

		(new Actions(browser)).doubleClick(johnAdamsUserScreenNameCell).perform();
		
		Thread.sleep(1000);
		
		logger.log(Level.INFO, "firstNameField.isDisplayed() = " + firstNameField.isDisplayed());
		assertTrue("The First Name Text Entry Field should be displayed on the page at this point but it is not.", firstNameField.isDisplayed());
		logger.log(Level.INFO, "middleNameField.isDisplayed() = " + middleNameField.isDisplayed());
		assertTrue("The Middle Name Text Entry Field should be displayed on the page at this point but it is not.", middleNameField.isDisplayed());
		logger.log(Level.INFO, "lastNameField.isDisplayed() = " + lastNameField.isDisplayed());
		assertTrue("The Last Name Text Entry Field should be displayed on the page at this point but it is not.", lastNameField.isDisplayed());
		logger.log(Level.INFO, "emailAddressField.isDisplayed() = " + emailAddressField.isDisplayed());
		assertTrue("The Email Address Text Entry Field should be displayed on the page at this point but it is not.", emailAddressField.isDisplayed());
		logger.log(Level.INFO, "jobTitleField.isDisplayed() = " + jobTitleField.isDisplayed());
		assertTrue("The Job Title Text Entry Field should be displayed on the page at this point but it is not.", jobTitleField.isDisplayed());
		logger.log(Level.INFO, "submitButton.isDisplayed() = " + submitButton.isDisplayed());
		assertTrue("The Submit Button should be displayed on the page at this point but it is not.", submitButton.isDisplayed());
		logger.log(Level.INFO, "cancelButton.isDisplayed() = " + cancelButton.isDisplayed());
		assertTrue("The Cancel Button should be displayed on the page at this point but it is not.", cancelButton.isDisplayed());
		
		logger.log(Level.INFO, "dropdownActiveField.isDisplayed() = " + dropdownActiveField.isDisplayed());
		assertTrue("The The Dropdown Menu should be displayed  on the page and Active should be the selected item at this point but it is not.", dropdownActiveField.isDisplayed());
		
		logger.log(Level.INFO, "portrait.isDisplayed() = " + portrait.isDisplayed());
		assertTrue("The User Portrait should be displayed on the page at this point but it is not.", portrait.isDisplayed());
		logger.log(Level.INFO, "fileEntry.isDisplayed() = " + fileEntry.isDisplayed());
		assertTrue("The File Entry should be displayed on the page at this point but it is not.", fileEntry.isDisplayed());	
		logger.log(Level.INFO, "fileUploadButton.isDisplayed() = " + fileUploadButton.isDisplayed());
		assertTrue("The File Upload Button should be displayed on the page at this point but it is not.", fileUploadButton.isDisplayed());	
		
	}
	
	@Test
	@RunAsClient
	@InSequence(3000)
	public void checkRequiredFieldsAndCancel() throws Exception {
		
		firstNameField.clear();
		middleNameField.clear();
		lastNameField.clear();
		emailAddressField.clear();
		jobTitleField.clear();
		submitButton.click();
		
		Thread.sleep(2000);
		
		logger.log(Level.INFO, "firstNameFieldError.isDisplayed() = " + firstNameFieldError.isDisplayed());
		assertTrue("The First Name Validation Error should be displayed on the page at this point but it is not.", firstNameFieldError.isDisplayed());
		logger.log(Level.INFO, "lastNameFieldError.isDisplayed() = " + lastNameFieldError.isDisplayed());
		assertTrue("The Last Name Validation Error should be displayed on the page at this point but it is not.", lastNameFieldError.isDisplayed());	
		logger.log(Level.INFO, "emailAddressFieldError.isDisplayed() = " + emailAddressFieldError.isDisplayed());
		assertTrue("The Email Address Validation Error should be displayed on the page at this point but it is not.", emailAddressFieldError.isDisplayed());	
		
		cancelButton.click();
		
		Thread.sleep(500);		
		
	}
	
	@Test
	@RunAsClient
	@InSequence(4000)
	public void screenNameColumnFilter() throws Exception {
		
		screenNameColumnFilter.sendKeys("john.adams");
		
		Thread.sleep(500);

		logger.log(Level.INFO, "johnAdamsUserScreenNameCell.isDisplayed() = " + johnAdamsUserScreenNameCell.isDisplayed());
		assertTrue("The Screen Name Cell of the John Adams user should be displayed on the page as john.adams at this point but it is not.", johnAdamsUserScreenNameCell.isDisplayed());
		logger.log(Level.INFO, "johnAdamsUserLastNameCell.isDisplayed() = " + johnAdamsUserLastNameCell.isDisplayed());
		assertTrue("The Last Name Cell of the John Adams user should be displayed on the page as John at this point but it is not.", johnAdamsUserLastNameCell.isDisplayed());
		logger.log(Level.INFO, "johnAdamsUserLastNameCell.isDisplayed() = " + johnAdamsUserFirstNameCell.isDisplayed());
		assertTrue("The First Name Cell of the John Adams user should be displayed on the page as Adams at this point but it is not.", johnAdamsUserFirstNameCell.isDisplayed());
		logger.log(Level.INFO, "johnAdamsUserEmailAddressCell.isDisplayed() = " + johnAdamsUserEmailAddressCell.isDisplayed());
		assertTrue("The Email Address Cell of the John Adams user should be displayed on the page as john.adams@liferay.com at this point but it is not.", johnAdamsUserEmailAddressCell.isDisplayed());			

		logger.log(Level.INFO, "isThere(samuelAdamsUserScreenNameCellXpath) = " + isThere(samuelAdamsUserScreenNameCellXpath));
		assertFalse("The row for Samuel Adams should NOT be displayed now becuase of filtering, but it is displayed.", isThere(samuelAdamsUserScreenNameCellXpath));
		
		for(int i = 0; i < "john.adams".length(); i++)
			screenNameColumnFilter.sendKeys(Keys.BACK_SPACE);
		Thread.sleep(1000);
		
	}
	
	@Test
	@RunAsClient
	@InSequence(6000)
	public void testImageChosenButNotUploaded() throws Exception {
		
		(new Actions(browser)).doubleClick(johnAdamsUserScreenNameCell).perform();
		Thread.sleep(250);
		(new Actions(browser)).doubleClick(johnAdamsUserScreenNameCell).perform();
		
		Thread.sleep(500);
		
		fileEntry.sendKeys("/tmp/liferay-jsf-jersey.png");
		cancelButton.click();
		
		Thread.sleep(500);
		
		logger.log(Level.INFO, "screenNameColumnHeader.isDisplayed() = " + screenNameColumnHeader.isDisplayed());
		assertTrue("The Screen Name Column Header should be displayed on the page at this point but it is not.", screenNameColumnHeader.isDisplayed());
		logger.log(Level.INFO, "screenNameColumnSortIcon.isDisplayed() = " + screenNameColumnSortIcon.isDisplayed());
		assertTrue("The Screen Name Column Sort Icon should be displayed on the page at this point but it is not.", screenNameColumnSortIcon.isDisplayed());
		logger.log(Level.INFO, "screenNameColumnFilter.isDisplayed() = " + screenNameColumnFilter.isDisplayed());
		assertTrue("The Screen Name Column Text Filter should be displayed on the page at this point but it is not.", screenNameColumnFilter.isDisplayed());
		
		(new Actions(browser)).doubleClick(johnAdamsUserScreenNameCell).perform();
		Thread.sleep(250);
		(new Actions(browser)).doubleClick(johnAdamsUserScreenNameCell).perform();
		
		Thread.sleep(500);
		
		logger.log(Level.INFO, "portrait.isDisplayed() = " + portrait.isDisplayed());
		assertTrue("The User Portrait should be displayed on the page at this point but it is not.", portrait.isDisplayed());
		
		fileEntry.sendKeys("/tmp/liferay-jsf-jersey.png");
		submitButton.click();
		
		Thread.sleep(500);
		
		logger.log(Level.INFO, "screenNameColumnHeader.isDisplayed() = " + screenNameColumnHeader.isDisplayed());
		assertTrue("The Screen Name Column Header should be displayed on the page at this point but it is not.", screenNameColumnHeader.isDisplayed());
		logger.log(Level.INFO, "screenNameColumnSortIcon.isDisplayed() = " + screenNameColumnSortIcon.isDisplayed());
		assertTrue("The Screen Name Column Sort Icon should be displayed on the page at this point but it is not.", screenNameColumnSortIcon.isDisplayed());
		logger.log(Level.INFO, "screenNameColumnFilter.isDisplayed() = " + screenNameColumnFilter.isDisplayed());
		assertTrue("The Screen Name Column Text Filter should be displayed on the page at this point but it is not.", screenNameColumnFilter.isDisplayed());
		
		(new Actions(browser)).doubleClick(johnAdamsUserScreenNameCell).perform();
		Thread.sleep(250);
		(new Actions(browser)).doubleClick(johnAdamsUserScreenNameCell).perform();
		
		Thread.sleep(500);
		
		logger.log(Level.INFO, "portrait.isDisplayed() = " + portrait.isDisplayed());
		assertTrue("The User Portrait should be displayed on the page at this point but it is not.", portrait.isDisplayed());
		
		cancelButton.click();
		Thread.sleep(500);
	}
	
	@Test
	@RunAsClient
	@InSequence(6000)
	public void testFileUpload() throws Exception {
		
		(new Actions(browser)).doubleClick(johnAdamsUserScreenNameCell).perform();
		Thread.sleep(250);
		(new Actions(browser)).doubleClick(johnAdamsUserScreenNameCell).perform();
		
		Thread.sleep(500);
		
		fileEntry.sendKeys("/tmp/liferay-jsf-jersey.png");
		fileUploadButton.click();
		
		Thread.sleep(500);
		
		logger.log(Level.INFO, "changedPortrait.isDisplayed() = " + changedPortrait.isDisplayed());
		assertTrue("The Changed User Portrait should be displayed on the page at this point but it is not.", changedPortrait.isDisplayed());
		
		cancelButton.click();
		
		Thread.sleep(500);
		(new Actions(browser)).doubleClick(johnAdamsUserScreenNameCell).perform();
		Thread.sleep(250);
		(new Actions(browser)).doubleClick(johnAdamsUserScreenNameCell).perform();
		Thread.sleep(500);
		
		logger.log(Level.INFO, "portrait.isDisplayed() = " + portrait.isDisplayed());
		assertTrue("The User Portrait should be displayed on the page at this point but it is not.", portrait.isDisplayed());
		
		fileEntry.sendKeys("/tmp/liferay-jsf-jersey.png");
		fileUploadButton.click();
		
		Thread.sleep(500);
		submitButton.click();
		
		Thread.sleep(1000);
		(new Actions(browser)).doubleClick(johnAdamsUserScreenNameCell).perform();
		Thread.sleep(250);
		(new Actions(browser)).doubleClick(johnAdamsUserScreenNameCell).perform();
		Thread.sleep(500);
		
		logger.log(Level.INFO, "changedPortrait.isDisplayed() = " + changedPortrait.isDisplayed());
		assertTrue("The Changed User Portrait should be displayed on the page at this point but it is not.", changedPortrait.isDisplayed());
		
		Thread.sleep(500);
		cancelButton.click();
	}
		
	@Test
	@RunAsClient
	@InSequence(7000)
	public void changeUserAttributes() throws Exception {
		
		Thread.sleep(500);
		
		(new Actions(browser)).doubleClick(johnAdamsUserScreenNameCell).perform();
		Thread.sleep(250);
		(new Actions(browser)).doubleClick(johnAdamsUserScreenNameCell).perform();
		
		Thread.sleep(250);
		
		firstNameField.clear();
		firstNameField.sendKeys("Aa");
		middleNameField.clear();
		middleNameField.sendKeys("Aa");
		lastNameField.clear();
		lastNameField.sendKeys("Aa");
		emailAddressField.clear();
		emailAddressField.sendKeys("A@A.com");
		jobTitleField.clear();
		jobTitleField.sendKeys("Aa");
		dropdownInactiveField.click();
		submitButton.click();
		
		logger.log(Level.INFO, "firstNameFieldError.isThere() = " + isThere(firstNameFieldErrorXpath));
		assertFalse("The First Name Validation Error should be displayed on the page at this point but it is not.", isThere(firstNameFieldErrorXpath));
		
		logger.log(Level.INFO, "lastNameFieldError.isThere() = " + isThere(lastNameFieldErrorXpath));
		assertFalse("The Last Name Validation Error should be displayed on the page at this point but it is not.", isThere(lastNameFieldErrorXpath));
		
		logger.log(Level.INFO, "emailAddressFieldError.isThere() = " + isThere(emailAddressFieldErrorXpath));
		assertFalse("The Email Address Validation Error should be displayed on the page at this point but it is not.", isThere(emailAddressFieldErrorXpath));
		
		Thread.sleep(500);
		
		logger.log(Level.INFO, "changedUserLastNameCell.isDisplayed() = " + changedUserLastNameCell.isDisplayed());
		assertTrue("The Last Name Cell of the changed user should be displayed on the page as A at this point but it is not.", changedUserLastNameCell.isDisplayed());
		logger.log(Level.INFO, "changedUserLastNameCell.isDisplayed() = " + changedUserFirstNameCell.isDisplayed());
		assertTrue("The First Name Cell of the changed user should be displayed on the page as A at this point but it is not.", changedUserFirstNameCell.isDisplayed());
		logger.log(Level.INFO, "changedUserMiddleNameCell.isDisplayed() = " + changedUserMiddleNameCell.isDisplayed());
		assertTrue("The Middle Name Cell of the changed user should be displayed on the page as A at this point but it is not.", changedUserMiddleNameCell.isDisplayed());
		logger.log(Level.INFO, "changedUserEmailAddressCell.isDisplayed() = " + changedUserEmailAddressCell.isDisplayed());
		assertTrue("The Email Address Cell of the changed user should be displayed on the page as A@A.com at this point but it is not.", changedUserEmailAddressCell.isDisplayed());
		logger.log(Level.INFO, "changedUserJobTitleCell.isDisplayed() = " + changedUserJobTitleCell.isDisplayed());
		assertTrue("The Job Title Cell of the changed user should be displayed on the page as A at this point but it is not.", changedUserJobTitleCell.isDisplayed());
		
		(new Actions(browser)).doubleClick(johnAdamsUserScreenNameCell).perform();
		Thread.sleep(250);
		(new Actions(browser)).doubleClick(johnAdamsUserScreenNameCell).perform();
		
		Thread.sleep(500);
		
		logger.log(Level.INFO, "dropdownInactiveField.isDisplayed() = " + dropdownInactiveField.isDisplayed());
		assertTrue("The dropdown Inactive Field should be selected now, but it is not.", dropdownInactiveField.isDisplayed());
		
		firstNameField.clear();
		firstNameField.sendKeys("John");
		middleNameField.clear();
		lastNameField.clear();
		lastNameField.sendKeys("Adams");
		emailAddressField.clear();
		emailAddressField.sendKeys("john.adams@liferay.com");
		jobTitleField.clear();
		dropdownActiveField.click();
		submitButton.click();
		
		logger.log(Level.INFO, "firstNameFieldError.isThere() = " + isThere(firstNameFieldErrorXpath));
		assertFalse("The First Name Validation Error should be displayed on the page at this point but it is not.", isThere(firstNameFieldErrorXpath));
		logger.log(Level.INFO, "lastNameFieldError.isThere() = " + isThere(lastNameFieldErrorXpath));
		assertFalse("The Last Name Validation Error should be displayed on the page at this point but it is not.", isThere(lastNameFieldErrorXpath));
		logger.log(Level.INFO, "emailAddressFieldError.isThere() = " + isThere(emailAddressFieldErrorXpath));
		assertFalse("The Email Address Validation Error should be displayed on the page at this point but it is not.", isThere(emailAddressFieldErrorXpath));
		
		Thread.sleep(250);
		
		logger.log(Level.INFO, "johnAdamsUserScreenNameCell.isDisplayed() = " + johnAdamsUserScreenNameCell.isDisplayed());
		assertTrue("The Screen Name Cell of the John Adams user should be displayed on the page as john.adams at this point but it is not.", johnAdamsUserScreenNameCell.isDisplayed());
		logger.log(Level.INFO, "johnAdamsUserLastNameCell.isDisplayed() = " + johnAdamsUserLastNameCell.isDisplayed());
		assertTrue("The Last Name Cell of the John Adams user should be displayed on the page as John at this point but it is not.", johnAdamsUserLastNameCell.isDisplayed());
		logger.log(Level.INFO, "johnAdamsUserLastNameCell.isDisplayed() = " + johnAdamsUserFirstNameCell.isDisplayed());
		assertTrue("The First Name Cell of the John Adams user should be displayed on the page as Adams at this point but it is not.", johnAdamsUserFirstNameCell.isDisplayed());
		logger.log(Level.INFO, "johnAdamsUserEmailAddressCell.isDisplayed() = " + johnAdamsUserEmailAddressCell.isDisplayed());
		assertTrue("The Email Address Cell of the John Adams user should be displayed on the page as john.adams@liferay.com at this point but it is not.", johnAdamsUserEmailAddressCell.isDisplayed());	
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
