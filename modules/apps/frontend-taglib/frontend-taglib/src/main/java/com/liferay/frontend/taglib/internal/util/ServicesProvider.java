/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.frontend.taglib.internal.util;

import com.liferay.frontend.js.module.launcher.JSModuleLauncher;
import com.liferay.frontend.js.module.launcher.JSModuleResolver;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(service = {})
public class ServicesProvider {

	public static JSModuleLauncher getJSModuleLauncher() {
		return _jsModuleLauncher;
	}

	public static JSModuleResolver getJSModuleResolver() {
		return _jsModuleResolver;
	}

	@Reference(unbind = "-")
	public void setJsModuleLauncher(JSModuleLauncher jsModuleLauncher) {
		_jsModuleLauncher = jsModuleLauncher;
	}

	@Reference(unbind = "-")
	public void setJSModuleResolver(JSModuleResolver jsModuleResolver) {
		_jsModuleResolver = jsModuleResolver;
	}

	private static JSModuleLauncher _jsModuleLauncher;
	private static JSModuleResolver _jsModuleResolver;

}