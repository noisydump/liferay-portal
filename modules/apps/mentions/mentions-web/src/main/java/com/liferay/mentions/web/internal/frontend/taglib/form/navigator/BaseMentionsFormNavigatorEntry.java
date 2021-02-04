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

package com.liferay.mentions.web.internal.frontend.taglib.form.navigator;

import com.liferay.frontend.taglib.form.navigator.BaseJSPFormNavigatorEntry;
import com.liferay.frontend.taglib.form.navigator.FormNavigatorEntry;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.Locale;

/**
 * @author Sergio González
 */
public abstract class BaseMentionsFormNavigatorEntry
	extends BaseJSPFormNavigatorEntry<Object>
	implements FormNavigatorEntry<Object> {

	@Override
	public String getKey() {
		return "mentions";
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "mentions");
	}

}