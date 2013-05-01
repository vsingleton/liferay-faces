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

import static org.junit.Assert.assertTrue;

import java.util.logging.Level;
// import java.net.URL;
import java.util.logging.Logger;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.enricher.findby.FindBy;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


/**
 * @author  Liferay Faces Team
 */
@RunWith(Arquillian.class)
public class FACES1478PortletTest {

	private static final Logger logger = Logger.getLogger(FACES1478PortletTest.class.getName());

	// portlet topper and menu elements
	private static final String portletDisplayNameXpath = "//header[@class='portlet-topper']/h1/span";

	private static final String formTagXpath = "//form[@method='post']";

	private static final String secondLinkXpath = "//form[@method='post']/a[2]";

	// @ArquillianResource
	// URL portalURL;
	String signInUrl = "http://localhost:8080/web/guest/signin";
	String url = "http://localhost:8080/web/bridge-issues/faces-1478";

	@Drone
	WebDriver browser;
	@FindBy(xpath = portletDisplayNameXpath)
	private WebElement portletDisplayName;
	@FindBy(xpath = formTagXpath)
	private WebElement formTag;
	@FindBy(xpath = secondLinkXpath)
	private WebElement secondLink;

	@Test
	@RunAsClient
	@InSequence(1000)
	public void FACES1478PortletParameters() throws Exception {

		logger.log(Level.INFO, "browser.navigate().to(" + url + ")");
		browser.navigate().to(url);
		logger.log(Level.INFO, "browser.getTitle() = " + browser.getTitle());
		logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());
		logger.log(Level.INFO, "portletDisplayName.getText() = " + portletDisplayName.getText());
		logger.log(Level.INFO, "formTag.getText() = " + formTag.getText());
		logger.log(Level.INFO, "secondLink.getAttribute('href') = " + secondLink.getAttribute("href"));

		assertTrue("portletDisplayName.isDisplayed()", portletDisplayName.isDisplayed());
		assertTrue("formTag.isDisplayed()", formTag.isDisplayed());
		assertTrue("secondLink.isDisplayed()", secondLink.isDisplayed());

		int firstParameter = secondLink.getAttribute("href").indexOf("testParam=foo");
		logger.log(Level.INFO, "The firstParameter was found at position = " + firstParameter);

		int secondParameter = secondLink.getAttribute("href").indexOf("testParam=bar");
		logger.log(Level.INFO, "The secondParameter was found at position = " + secondParameter);

		assertTrue("firstParameter is in the url", firstParameter > -1);
		assertTrue("secondParameter is in the url", secondParameter > -1);
		assertTrue("firstParameter occurs before the secondParameter", firstParameter < secondParameter);

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
