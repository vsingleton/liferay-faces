/**
 * Copyright (c) 2000-2014 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.bridge.renderkit.primefaces;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

import com.liferay.faces.bridge.component.ResourceComponent;
import com.liferay.faces.bridge.renderkit.html_basic.HeadRendererBridgeImpl;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * This class replaces the PrimeFaces HeadRenderer because it renders a &lt;head&gt;...&lt;/head&gt; element to the
 * response writer which is forbidden in a portlet environment.
 *
 * @author  Neil Griffin
 */
public class HeadRendererPrimeFacesImpl extends HeadRendererBridgeImpl {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(HeadRendererPrimeFacesImpl.class);

	// Private Constants
	private static final String PRIMEFACES_THEME_DEFAULT = "aristo";
	private static final String PRIMEFACES_THEME_PARAM = "primefaces.THEME";
	private static final String PRIMEFACES_THEME_NONE = "none";
	private static final String PRIMEFACES_THEME_PREFIX = "primefaces-";
	private static final String PRIMEFACES_THEME_RESOURCE_NAME = "theme.css";
	private static final Renderer PRIMEFACES_HEAD_RENDERER;

	static {

		Renderer primeFacesHeadRenderer = null;

		try {
			Class<?> headRendererClass = Class.forName("org.primefaces.renderkit.HeadRenderer");
			primeFacesHeadRenderer = (Renderer) headRendererClass.newInstance();
		}
		catch (Exception e) {
			logger.error(e);
		}

		PRIMEFACES_HEAD_RENDERER = primeFacesHeadRenderer;
	}

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Invoke the PrimeFaces HeadRenderer so that it has the opportunity to add css and/or script resources to the
		// view root. However, the PrimeFaces HeadRenderer must be captured (and thus prevented from actually rendering
		// any resources) so that they can instead be rendered by the superclass.
		ResponseWriter origResponseWriter = facesContext.getResponseWriter();
		PrimeFacesHeadResponseWriter primeFacesHeadResponseWriter = new PrimeFacesHeadResponseWriter();
		facesContext.setResponseWriter(primeFacesHeadResponseWriter);

		UIViewRoot originalUIViewRoot = facesContext.getViewRoot();
		ResourceCapturingUIViewRoot resourceCapturingUIViewRoot = new ResourceCapturingUIViewRoot();
		facesContext.setViewRoot(resourceCapturingUIViewRoot);
		PRIMEFACES_HEAD_RENDERER.encodeBegin(facesContext, uiComponent);
		facesContext.setViewRoot(originalUIViewRoot);
		facesContext.setResponseWriter(origResponseWriter);

		// Add each component resources that was captured to the real view root so that they will be rendered by the
		// superclass.
		List<UIComponent> capturedResources = resourceCapturingUIViewRoot.getCapturedComponentResources("head");

		for (UIComponent componentResource : capturedResources) {
			originalUIViewRoot.addComponentResource(facesContext, componentResource, "head");
		}

		// FACES-2061: If the PrimeFaces HeadRenderer attempted to render an inline script (as is the case when
		// PrimeFaces client side validation is activated) then add a component that can render the script to the view
		// root.
		String inlineScriptText = primeFacesHeadResponseWriter.toString();

		if ((inlineScriptText != null) && (inlineScriptText.length() > 0)) {
			PrimeFacesInlineScript primeFacesInlineScript = new PrimeFacesInlineScript(inlineScriptText);
			originalUIViewRoot.addComponentResource(facesContext, primeFacesInlineScript, "head");
		}

		// Delegate rendering to the superclass so that it can write resources found in the view root to the head
		// section of the portal page.
		super.encodeBegin(facesContext, uiComponent);
	}

	@Override
	protected List<UIComponent> getFirstResources(FacesContext facesContext, UIComponent uiComponent) {

		List<UIComponent> resources = super.getFirstResources(facesContext, uiComponent);

		// PrimeFaces Theme
		ExternalContext externalContext = facesContext.getExternalContext();
		String primeFacesThemeName = externalContext.getInitParameter(PRIMEFACES_THEME_PARAM);

		if (primeFacesThemeName != null) {
			ELContext elContext = facesContext.getELContext();
			ExpressionFactory expressionFactory = facesContext.getApplication().getExpressionFactory();
			ValueExpression valueExpression = expressionFactory.createValueExpression(elContext, primeFacesThemeName,
					String.class);
			primeFacesThemeName = (String) valueExpression.getValue(elContext);

		}
		else {
			primeFacesThemeName = PRIMEFACES_THEME_DEFAULT;
		}

		if ((primeFacesThemeName != null) && !primeFacesThemeName.equals(PRIMEFACES_THEME_NONE)) {

			if (resources == null) {
				resources = new ArrayList<UIComponent>();
			}

			String resourceLibrary = PRIMEFACES_THEME_PREFIX + primeFacesThemeName;
			ResourceComponent primeFacesStyleSheet = new ResourceComponent(facesContext, PRIMEFACES_THEME_RESOURCE_NAME,
					resourceLibrary, StringPool.HEAD);
			resources.add(primeFacesStyleSheet);
		}

		return resources;
	}
}