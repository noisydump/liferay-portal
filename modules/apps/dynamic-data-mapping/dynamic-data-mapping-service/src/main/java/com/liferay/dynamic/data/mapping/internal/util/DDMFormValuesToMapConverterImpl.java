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

package com.liferay.dynamic.data.mapping.internal.util;

import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesToMapConverter;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;

/**
 * @author Leonardo Barros
 */
@Component(immediate = true, service = DDMFormValuesToMapConverter.class)
public class DDMFormValuesToMapConverterImpl
	implements DDMFormValuesToMapConverter {

	@Override
	public Map<String, Object> convert(
			DDMFormValues ddmFormValues, DDMStructure ddmStructure)
		throws PortalException {

		if (ddmFormValues == null) {
			return Collections.emptyMap();
		}

		Map<String, DDMFormField> ddmFormFields =
			ddmStructure.getFullHierarchyDDMFormFieldsMap(true);

		Map<String, Object> values = new HashMap<>(ddmFormFields.size());

		for (DDMFormFieldValue ddmFormFieldValue :
				ddmFormValues.getDDMFormFieldValues()) {

			_addValues(ddmFormFields, ddmFormFieldValue, values);
		}

		return values;
	}

	private void _addValue(
		DDMFormField ddmFormField, DDMFormFieldValue ddmFormFieldValue,
		Map<String, Object> values) {

		if (ddmFormField == null) {
			return;
		}

		String name = ddmFormField.getName();

		Value value = ddmFormFieldValue.getValue();

		if (value == null) {
			values.put(name, null);

			return;
		}

		if (ddmFormField.isRepeatable()) {
			if (ddmFormField.isLocalizable()) {
				Map<String, Object> localizedValues =
					(Map<String, Object>)values.getOrDefault(
						name, new HashMap<>());

				LocalizedValue localizedValue = (LocalizedValue)value;

				Set<Locale> availableLocales =
					localizedValue.getAvailableLocales();

				for (Locale locale : availableLocales) {
					String languageId = LanguageUtil.getLanguageId(locale);

					List<Object> list =
						(List<Object>)localizedValues.getOrDefault(
							languageId, new ArrayList<>());

					list.add(localizedValue.getString(locale));

					localizedValues.put(languageId, list);
				}

				values.put(name, localizedValues);
			}
			else {
				List<Object> list = (List<Object>)values.getOrDefault(
					name, new ArrayList<>());

				list.add(value.getString(value.getDefaultLocale()));

				values.put(name, list);
			}
		}
		else if (ddmFormField.isLocalizable()) {
			values.put(
				name,
				_toLocalizedMap(ddmFormField.getType(), (LocalizedValue)value));
		}
		else {
			values.put(name, value.getString(value.getDefaultLocale()));
		}
	}

	private void _addValues(
		Map<String, DDMFormField> ddmFormFields,
		DDMFormFieldValue ddmFormFieldValue, Map<String, Object> values) {

		DDMFormField ddmFormField = ddmFormFields.get(
			ddmFormFieldValue.getName());

		_addValue(ddmFormField, ddmFormFieldValue, values);

		for (DDMFormFieldValue nestedDDMFormFieldValue :
				ddmFormFieldValue.getNestedDDMFormFieldValues()) {

			_addValues(ddmFormFields, nestedDDMFormFieldValue, values);
		}
	}

	private Map<String, Object> _toLocalizedMap(
		String fieldType, LocalizedValue localizedValue) {

		Set<Locale> availableLocales = localizedValue.getAvailableLocales();

		Stream<Locale> stream = availableLocales.stream();

		if (fieldType.equals(DDMFormFieldType.CHECKBOX_MULTIPLE) ||
			fieldType.equals(DDMFormFieldType.SELECT)) {

			return stream.collect(
				Collectors.toMap(
					LanguageUtil::getLanguageId,
					locale -> _toStringList(locale, localizedValue)));
		}

		return stream.collect(
			Collectors.toMap(
				LanguageUtil::getLanguageId, localizedValue::getString));
	}

	private List<String> _toStringList(
		Locale locale, LocalizedValue localizedValue) {

		try {
			return JSONUtil.toStringList(
				JSONFactoryUtil.createJSONArray(
					localizedValue.getString(locale)));
		}
		catch (JSONException jsonException) {
			return Collections.emptyList();
		}
	}

}