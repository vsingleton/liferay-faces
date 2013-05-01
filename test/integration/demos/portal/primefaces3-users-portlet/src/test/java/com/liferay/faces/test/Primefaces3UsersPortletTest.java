/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
package com.liferay.faces.test;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.enricher.findby.FindBy;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


/**
 * @author  Liferay Faces Team
 */
@RunWith(Arquillian.class)
public class Primefaces3UsersPortletTest {

	// private static final Logger logger;
	private static final Logger logger = Logger.getLogger(Primefaces3UsersPortletTest.class.getName());

	// elements for logging in
	private static final String emailFieldXpath = "//input[contains(@id,':handle')]";
	private static final String passwordFieldXpath = "//input[contains(@id,':password')]";
	private static final String signInButtonXpath = "//input[@type='submit' and @value='Sign In']";
	private static final String portletBodyXpath = "//div[contains(text(),'You are signed in as')]";
	private static final String signedInTextXpath = "//div[contains(text(),'You are signed in as')]";

	// elements for Primefaces3Users
	private static final String portletTitleTextXpath = "//span[@class='portlet-title-text']";

	private static final String menuButtonXpath = "//a[contains(@id,'jsf2portlet') and contains(@id,'menuButton')]";
	private static final String menuPreferencesXpath =
		"//a[contains(@id,'jsf2portlet') and contains(@id,'menu_preferences')]";

	private static final String logoXpath = "//img[contains(@src,'liferay-logo.png')]";

	// Elements for users' list
	private static final String screenNameColumnHeaderXpath = "//th[contains(@id,'users:screenName')]/child::span[1]";
	private static final String screenNameColumnSortIconXpath =
		"//th[contains(@id,'users:screenName')]/child::span[contains(@class,'ui-sortable-column-icon')]";
	private static final String screenNameColumnFilterXpath = "//input[contains(@id,'users:screenName:filter')]";

	private static final String lastNameColumnHeaderXpath = "//th[contains(@id,'users:lastName')]/child::span[1]";
	private static final String lastNameColumnSortIconXpath =
		"//th[contains(@id,'users:lastName')]/child::span[contains(@class,'ui-sortable-column-icon')]";
	private static final String lastNameColumnFilterXpath = "//input[contains(@id,'users:lastName:filter')]";

	private static final String firstNameColumnHeaderXpath = "//th[contains(@id,'users:firstName')]/child::span[1]";
	private static final String firstNameColumnSortIconXpath =
		"//th[contains(@id,'users:firstName')]/child::span[contains(@class,'ui-sortable-column-icon')]";
	private static final String firstNameColumnFilterXpath = "//input[contains(@id,'users:firstName:filter')]";

	private static final String middleNameColumnHeaderXpath = "//th[contains(@id,'users:middleName')]/child::span[1]";
	private static final String middleNameColumnSortIconXpath =
		"//th[contains(@id,'users:middleName')]/child::span[contains(@class,'ui-sortable-column-icon')]";
	private static final String middleNameColumnFilterXpath = "//input[contains(@id,'users:middleName:filter')]";

	private static final String emailAddressColumnHeaderXpath =
		"//th[contains(@id,'users:emailAddress')]/child::span[1]";
	private static final String emailAddressColumnSortIconXpath =
		"//th[contains(@id,'users:emailAddress')]/child::span[contains(@class,'ui-sortable-column-icon')]";
	private static final String emailAddressColumnFilterXpath = "//input[contains(@id,'users:emailAddress:filter')]";

	private static final String jobTitleColumnHeaderXpath = "//th[contains(@id,'users:jobTitle')]/child::span[1]";
	private static final String jobTitleColumnSortIconXpath =
		"//th[contains(@id,'users:jobTitle')]/child::span[contains(@class,'ui-sortable-column-icon')]";
	private static final String jobTitleColumnFilterXpath = "//input[contains(@id,'users:jobTitle:filter')]";

	// The Test user's list view
	private static final String testUserScreenNameCellXpath =
		"//span[contains(@id,':screenNameCell') and contains(text(), 'test')]";
	private static final String testUserLastNameCellXpath =
		"//span[contains(@id,':lastNameCell') and contains(text(), 'Test')]";
	private static final String testUserFirstNameCellXpath =
		"//span[contains(@id,':firstNameCell') and contains(text(), 'Test')]";
	private static final String testUserEmailAddressCellXpath =
		"//a[contains(@href,'mailto:') and contains(text(), 'test@liferay.com')]";

	// Elements for column 1 of the test user's detailed user view
	private static final String firstNameFieldXpath =
		"//span[@class='aui-field-element']/input[contains(@id,':firstName')]";
	private static final String firstNameFieldErrorXpath =
		"//span[@class='aui-field-element']/input[contains(@id,':firstName')]/../span[@class='portlet-msg-error' and text()='Value is required']";
	private static final String middleNameFieldXpath =
		"//span[@class='aui-field-element']/input[contains(@id,':middleName')]";
	private static final String lastNameFieldXpath =
		"//span[@class='aui-field-element']/input[contains(@id,':lastName')]";
	private static final String lastNameFieldErrorXpath =
		"//span[@class='aui-field-element']/input[contains(@id,':lastName')]/../span[@class='portlet-msg-error' and text()='Value is required']";
	private static final String emailAddressFieldXpath =
		"//span[@class='aui-field-element']/input[contains(@id,':emailAddress')]";
	private static final String emailAddressFieldErrorXpath =
		"//span[@class='aui-field-element']/input[contains(@id,':emailAddress')]/../span[@class='portlet-msg-error' and text()='Value is required']";
	private static final String jobTitleFieldXpath =
		"//span[@class='aui-field-element']/input[contains(@id,':jobTitle')]";
	private static final String submitButtonXpath = "//button[contains(@id, ':pushButtonSubmit') and @type='submit']";
	private static final String cancelButtonXpath = "//button[contains(@id, ':pushButtonCancel') and @type='submit']";

	// Elements for column 2 of the test user's detailed user view
	private static final String dropdownActiveFieldXpath =
		"//select[contains(@id,':s1')]/option[contains(@selected, 'selected') and contains(text(), 'Active')]";

	// Elements for column 3 of the test user's detailed user view
	private static final String portraitXpath = "//img[contains(@id, ':portrait')]";
	private static final String fileUploadButtonXpath =
		"//label[@role='button']/span[text()='Choose' and contains(@class, 'ui-button-text')]/following-sibling::input[@type='file' and contains(@id, ':fileEntryComp_input')]/..";

	// @ArquillianResource
	// URL portalURL;
	String signInUrl = "http://localhost:8080/web/guest/jsf2-sign-in";
	String url =
		"http://localhost:8080/group/control_panel/manage?p_p_id=1_WAR_primefaces3usersportlet&p_p_lifecycle=0&p_p_state=maximized&p_p_mode=view";

	@Drone
	WebDriver browser;
	@FindBy(xpath = emailFieldXpath)
	private WebElement emailField;
	@FindBy(xpath = passwordFieldXpath)
	private WebElement passwordField;
	@FindBy(xpath = signInButtonXpath)
	private WebElement signInButton;
	@FindBy(xpath = portletBodyXpath)
	private WebElement portletBody;
	@FindBy(xpath = signedInTextXpath)
	private WebElement signedInText;
	@FindBy(xpath = portletTitleTextXpath)
	private WebElement portletTitleText;
	@FindBy(xpath = menuButtonXpath)
	private WebElement menuButton;
	@FindBy(xpath = menuPreferencesXpath)
	private WebElement menuPreferences;
	@FindBy(xpath = logoXpath)
	private WebElement logo;
	@FindBy(xpath = screenNameColumnHeaderXpath)
	private WebElement screenNameColumnHeader;
	@FindBy(xpath = screenNameColumnSortIconXpath)
	private WebElement screenNameColumnSortIcon;
	@FindBy(xpath = screenNameColumnFilterXpath)
	private WebElement screenNameColumnFilter;
	@FindBy(xpath = lastNameColumnHeaderXpath)
	private WebElement lastNameColumnHeader;
	@FindBy(xpath = lastNameColumnSortIconXpath)
	private WebElement lastNameColumnSortIcon;
	@FindBy(xpath = lastNameColumnFilterXpath)
	private WebElement lastNameColumnFilter;
	@FindBy(xpath = firstNameColumnHeaderXpath)
	private WebElement firstNameColumnHeader;
	@FindBy(xpath = firstNameColumnSortIconXpath)
	private WebElement firstNameColumnSortIcon;
	@FindBy(xpath = firstNameColumnFilterXpath)
	private WebElement firstNameColumnFilter;
	@FindBy(xpath = middleNameColumnHeaderXpath)
	private WebElement middleNameColumnHeader;
	@FindBy(xpath = middleNameColumnSortIconXpath)
	private WebElement middleNameColumnSortIcon;
	@FindBy(xpath = middleNameColumnFilterXpath)
	private WebElement middleNameColumnFilter;
	@FindBy(xpath = emailAddressColumnHeaderXpath)
	private WebElement emailAddressColumnHeader;
	@FindBy(xpath = emailAddressColumnSortIconXpath)
	private WebElement emailAddressColumnSortIcon;
	@FindBy(xpath = emailAddressColumnFilterXpath)
	private WebElement emailAddressColumnFilter;
	@FindBy(xpath = jobTitleColumnHeaderXpath)
	private WebElement jobTitleColumnHeader;
	@FindBy(xpath = jobTitleColumnSortIconXpath)
	private WebElement jobTitleColumnSortIcon;
	@FindBy(xpath = jobTitleColumnFilterXpath)
	private WebElement jobTitleColumnFilter;
	@FindBy(xpath = testUserScreenNameCellXpath)
	private WebElement testUserScreenNameCell;
	@FindBy(xpath = testUserLastNameCellXpath)
	private WebElement testUserLastNameCell;
	@FindBy(xpath = testUserFirstNameCellXpath)
	private WebElement testUserFirstNameCell;
	@FindBy(xpath = testUserEmailAddressCellXpath)
	private WebElement testUserEmailAddressCell;
	@FindBy(xpath = firstNameFieldXpath)
	private WebElement firstNameField;
	@FindBy(xpath = firstNameFieldErrorXpath)
	private WebElement firstNameFieldError;
	@FindBy(xpath = middleNameFieldXpath)
	private WebElement middleNameField;
	@FindBy(xpath = lastNameFieldXpath)
	private WebElement lastNameField;
	@FindBy(xpath = lastNameFieldErrorXpath)
	private WebElement lastNameFieldError;
	@FindBy(xpath = emailAddressFieldXpath)
	private WebElement emailAddressField;
	@FindBy(xpath = emailAddressFieldErrorXpath)
	private WebElement emailAddressFieldError;
	@FindBy(xpath = jobTitleFieldXpath)
	private WebElement jobTitleField;
	@FindBy(xpath = submitButtonXpath)
	private WebElement submitButton;
	@FindBy(xpath = cancelButtonXpath)
	private WebElement cancelButton;
	@FindBy(xpath = dropdownActiveFieldXpath)
	private WebElement dropdownActiveField;
	@FindBy(xpath = portraitXpath)
	private WebElement portrait;
	@FindBy(xpath = fileUploadButtonXpath)
	private WebElement fileUploadButton;

	@Before
	public void getNewSession() {
	}

	public void signIn() throws Exception {

		// Shut its dirty mouth
		java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);

		logger.log(Level.INFO, "browser.navigate().to(" + signInUrl + ")");
		browser.navigate().to(signInUrl);
		logger.log(Level.INFO, "browser.getTitle() = " + browser.getTitle() + " before signing in ...");

		emailField.clear();
		emailField.sendKeys("test@liferay.com");
		passwordField.clear();
		passwordField.sendKeys("test");
		signInButton.click();
		logger.log(Level.INFO,
			"browser.getTitle() = " + browser.getTitle() + " after clicking the sign in button and waiting");
		logger.log(Level.INFO, signedInText.getText());
	}

	@Test
	@RunAsClient
	@InSequence(1000)
	public void usersListView() throws Exception {

		signIn();

		logger.log(Level.INFO, "browser.navigate().to(" + url + ")");
		browser.navigate().to(url);
		logger.log(Level.INFO, "browser.getTitle() = " + browser.getTitle());
		logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());
		logger.log(Level.INFO, "portletTitleText.getText() = " + portletTitleText.getText());
		assertTrue("portletTitleText.isDisplayed()", portletTitleText.isDisplayed());

		logger.log(Level.INFO, "screenNameColumnHeader.isDisplayed() = " + screenNameColumnHeader.isDisplayed());
		assertTrue("The Screen Name Column Header should be displayed on the page at this point but it is not.",
			screenNameColumnHeader.isDisplayed());
		logger.log(Level.INFO, "screenNameColumnSortIcon.isDisplayed() = " + screenNameColumnSortIcon.isDisplayed());
		assertTrue("The Screen Name Column Sort Icon should be displayed on the page at this point but it is not.",
			screenNameColumnSortIcon.isDisplayed());
		logger.log(Level.INFO, "screenNameColumnFilter.isDisplayed() = " + screenNameColumnFilter.isDisplayed());
		assertTrue("The Screen Name Column Text Filter should be displayed on the page at this point but it is not.",
			screenNameColumnFilter.isDisplayed());

		logger.log(Level.INFO, "lastNameColumnHeader.isDisplayed() = " + lastNameColumnHeader.isDisplayed());
		assertTrue("The Last Name Column Header should be displayed on the page at this point but it is not.",
			lastNameColumnHeader.isDisplayed());
		logger.log(Level.INFO, "lastNameColumnSortIcon.isDisplayed() = " + lastNameColumnSortIcon.isDisplayed());
		assertTrue("The Last Name Column Sort Icon should be displayed on the page at this point but it is not.",
			lastNameColumnSortIcon.isDisplayed());
		logger.log(Level.INFO, "lastNameColumnFilter.isDisplayed() = " + lastNameColumnFilter.isDisplayed());
		assertTrue("The Last Name Column Text Filter should be displayed on the page at this point but it is not.",
			lastNameColumnFilter.isDisplayed());

		logger.log(Level.INFO, "firstNameColumnHeader.isDisplayed() = " + firstNameColumnHeader.isDisplayed());
		assertTrue("The First Name Column Header should be displayed on the page at this point but it is not.",
			firstNameColumnHeader.isDisplayed());
		logger.log(Level.INFO, "firstNameColumnSortIcon.isDisplayed() = " + firstNameColumnSortIcon.isDisplayed());
		assertTrue("The First Name Column Sort Icon should be displayed on the page at this point but it is not.",
			firstNameColumnSortIcon.isDisplayed());
		logger.log(Level.INFO, "firstNameColumnFilter.isDisplayed() = " + firstNameColumnFilter.isDisplayed());
		assertTrue("The First Name Column Text Filter should be displayed on the page at this point but it is not.",
			firstNameColumnFilter.isDisplayed());

		logger.log(Level.INFO, "middleNameColumnHeader.isDisplayed() = " + middleNameColumnHeader.isDisplayed());
		assertTrue("The Middle Name Column Header should be displayed on the page at this point but it is not.",
			middleNameColumnHeader.isDisplayed());
		logger.log(Level.INFO, "middleNameColumnSortIcon.isDisplayed() = " + middleNameColumnSortIcon.isDisplayed());
		assertTrue("The Middle Name Column Sort Icon should be displayed on the page at this point but it is not.",
			middleNameColumnSortIcon.isDisplayed());
		logger.log(Level.INFO, "middleNameColumnFilter.isDisplayed() = " + middleNameColumnFilter.isDisplayed());
		assertTrue("The Middle Name Column Text Filter should be displayed on the page at this point but it is not.",
			middleNameColumnFilter.isDisplayed());

		logger.log(Level.INFO, "emailAddressColumnHeader.isDisplayed() = " + emailAddressColumnHeader.isDisplayed());
		assertTrue("The Email Address Column Header should be displayed on the page at this point but it is not.",
			emailAddressColumnHeader.isDisplayed());
		logger.log(Level.INFO,
			"emailAddressColumnSortIcon.isDisplayed() = " + emailAddressColumnSortIcon.isDisplayed());
		assertTrue("The Email Address Column Sort Icon should be displayed on the page at this point but it is not.",
			emailAddressColumnSortIcon.isDisplayed());
		logger.log(Level.INFO, "emailAddressColumnFilter.isDisplayed() = " + emailAddressColumnFilter.isDisplayed());
		assertTrue("The Email Address Column Text Filter should be displayed on the page at this point but it is not.",
			emailAddressColumnFilter.isDisplayed());

		logger.log(Level.INFO, "jobTitleColumnHeader.isDisplayed() = " + jobTitleColumnHeader.isDisplayed());
		assertTrue("The Job Title Column Header should be displayed on the page at this point but it is not.",
			jobTitleColumnHeader.isDisplayed());
		logger.log(Level.INFO, "jobTitleColumnSortIcon.isDisplayed() = " + jobTitleColumnSortIcon.isDisplayed());
		assertTrue("The Job Title Column Sort Icon should be displayed on the page at this point but it is not.",
			jobTitleColumnSortIcon.isDisplayed());
		logger.log(Level.INFO, "jobTitleColumnFilter.isDisplayed() = " + jobTitleColumnFilter.isDisplayed());
		assertTrue("The Job Title Column Text Filter should be displayed on the page at this point but it is not.",
			jobTitleColumnFilter.isDisplayed());

		screenNameColumnFilter.sendKeys("test");

		Thread.sleep(1000);

		logger.log(Level.INFO, "testUserScreenNameCell.isDisplayed() = " + testUserScreenNameCell.isDisplayed());
		assertTrue(
			"The Screen Name Cell of the Test user should be displayed on the page as test at this point but it is not.",
			testUserScreenNameCell.isDisplayed());
		logger.log(Level.INFO, "testUserLastNameCell.isDisplayed() = " + testUserLastNameCell.isDisplayed());
		assertTrue(
			"The Last Name Cell of the Test user should be displayed on the page as Test at this point but it is not.",
			testUserLastNameCell.isDisplayed());
		logger.log(Level.INFO, "testUserLastNameCell.isDisplayed() = " + testUserFirstNameCell.isDisplayed());
		assertTrue(
			"The First Name Cell of the Test user should be displayed on the page as Test at this point but it is not.",
			testUserFirstNameCell.isDisplayed());
		logger.log(Level.INFO, "testUserEmailAddressCell.isDisplayed() = " + testUserEmailAddressCell.isDisplayed());
		assertTrue(
			"The Email Address Cell of the Test user should be displayed on the page as test@liferay.com at this point but it is not.",
			testUserEmailAddressCell.isDisplayed());
	}

	@Test
	@RunAsClient
	@InSequence(2000)
	public void specificUserView() throws Exception {

		testUserScreenNameCell.click();

		Thread.sleep(1000);

		logger.log(Level.INFO, "firstNameField.isDisplayed() = " + firstNameField.isDisplayed());
		assertTrue("The First Name Text Entry Field should be displayed on the page at this point but it is not.",
			firstNameField.isDisplayed());
		logger.log(Level.INFO, "middleNameField.isDisplayed() = " + middleNameField.isDisplayed());
		assertTrue("The Middle Name Text Entry Field should be displayed on the page at this point but it is not.",
			middleNameField.isDisplayed());
		logger.log(Level.INFO, "lastNameField.isDisplayed() = " + lastNameField.isDisplayed());
		assertTrue("The Last Name Text Entry Field should be displayed on the page at this point but it is not.",
			lastNameField.isDisplayed());
		logger.log(Level.INFO, "emailAddressField.isDisplayed() = " + emailAddressField.isDisplayed());
		assertTrue("The Email Address Text Entry Field should be displayed on the page at this point but it is not.",
			emailAddressField.isDisplayed());
		logger.log(Level.INFO, "jobTitleField.isDisplayed() = " + jobTitleField.isDisplayed());
		assertTrue("The Job Title Text Entry Field should be displayed on the page at this point but it is not.",
			jobTitleField.isDisplayed());
		logger.log(Level.INFO, "submitButton.isDisplayed() = " + submitButton.isDisplayed());
		assertTrue("The Submit Button should be displayed on the page at this point but it is not.",
			submitButton.isDisplayed());
		logger.log(Level.INFO, "cancelButton.isDisplayed() = " + cancelButton.isDisplayed());
		assertTrue("The Cancel Button should be displayed on the page at this point but it is not.",
			cancelButton.isDisplayed());

		logger.log(Level.INFO, "dropdownActiveField.isDisplayed() = " + dropdownActiveField.isDisplayed());
		assertTrue(
			"The The Dropdown Menu should be displayed  on the page and Active should be the selected item at this point but it is not.",
			dropdownActiveField.isDisplayed());

		logger.log(Level.INFO, "portrait.isDisplayed() = " + portrait.isDisplayed());
		assertTrue("The User Portrait should be displayed on the page at this point but it is not.",
			portrait.isDisplayed());
		logger.log(Level.INFO, "fileUploadButton.isDisplayed() = " + fileUploadButton.isDisplayed());
		assertTrue("The File Upload Button should be displayed on the page at this point but it is not.",
			fileUploadButton.isDisplayed());

	}

	@Test
	@RunAsClient
	@InSequence(3000)
	public void checkRequiredFields() throws Exception {

		firstNameField.clear();
		middleNameField.clear();
		lastNameField.clear();
		emailAddressField.clear();
		jobTitleField.clear();

		submitButton.click();

		Thread.sleep(2000);

		logger.log(Level.INFO, "firstNameFieldError.isDisplayed() = " + firstNameFieldError.isDisplayed());
		assertTrue("The First Name Validation Error should be displayed on the page at this point but it is not.",
			firstNameFieldError.isDisplayed());

		logger.log(Level.INFO, "lastNameFieldError.isDisplayed() = " + lastNameFieldError.isDisplayed());
		assertTrue("The Last Name Validation Error should be displayed on the page at this point but it is not.",
			lastNameFieldError.isDisplayed());

		logger.log(Level.INFO, "emailAddressFieldError.isDisplayed() = " + emailAddressFieldError.isDisplayed());
		assertTrue("The Email Address Validation Error should be displayed on the page at this point but it is not.",
			emailAddressFieldError.isDisplayed());

		cancelButton.click();

	}

	public boolean isThere(String xpath) {
		boolean isThere = false;
		int count = 0;
		count = browser.findElements(By.xpath(xpath)).size();

		if (count == 0) {
			isThere = false;
		}

		if (count > 0) {
			isThere = true;
		}

		if (count > 1) {
			logger.log(Level.WARNING,
				"The method 'isThere(xpath)' found " + count + " matches using xpath = " + xpath +
				" ... the word 'is' implies singluar, or 1, not " + count);
		}

		return isThere;
	}

}
