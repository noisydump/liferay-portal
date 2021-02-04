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

package com.liferay.jenkins.results.parser.spira.result;

import com.liferay.jenkins.results.parser.AxisBuild;
import com.liferay.jenkins.results.parser.TestResult;
import com.liferay.jenkins.results.parser.spira.SpiraCustomProperty;
import com.liferay.jenkins.results.parser.spira.SpiraCustomPropertyValue;
import com.liferay.jenkins.results.parser.spira.SpiraTestCaseRun;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * @author Michael Hashimoto
 */
public class FunctionalAxisSpiraTestResultValues
	extends BaseAxisSpiraTestResultValues {

	protected FunctionalAxisSpiraTestResultValues(
		FunctionalAxisSpiraTestResult functionalAxisSpiraTestResult) {

		super(functionalAxisSpiraTestResult);

		_functionalAxisSpiraTestResult = functionalAxisSpiraTestResult;
	}

	@Override
	protected String getBatchPropertyValue(String batchPropertyType) {
		String batchPropertyValue = super.getBatchPropertyValue(
			batchPropertyType);

		if ((batchPropertyValue != null) && !batchPropertyValue.isEmpty()) {
			return batchPropertyValue;
		}

		batchPropertyValue =
			_functionalAxisSpiraTestResult.getPoshiPropertyValue(
				batchPropertyType);

		if ((batchPropertyValue != null) && !batchPropertyValue.isEmpty()) {
			return batchPropertyValue;
		}

		return null;
	}

	@Override
	protected List<Callable<List<SpiraCustomPropertyValue>>> getCallables() {
		List<Callable<List<SpiraCustomPropertyValue>>> callables =
			super.getCallables();

		callables.add(
			new Callable<List<SpiraCustomPropertyValue>>() {

				@Override
				public List<SpiraCustomPropertyValue> call() throws Exception {
					return Collections.singletonList(_getTeamNameValue());
				}

			});

		return callables;
	}

	@Override
	protected SpiraCustomPropertyValue getErrorMessageValue() {
		SpiraBuildResult spiraBuildResult = getSpiraBuildResult();

		SpiraCustomProperty spiraCustomProperty =
			SpiraCustomProperty.createSpiraCustomProperty(
				spiraBuildResult.getSpiraProject(), SpiraTestCaseRun.class,
				"Error Message", SpiraCustomProperty.Type.TEXT, true);

		TestResult testResult = _functionalAxisSpiraTestResult.getTestResult();

		if (testResult == null) {
			AxisBuild axisBuild = _functionalAxisSpiraTestResult.getAxisBuild();

			if (axisBuild == null) {
				return SpiraCustomPropertyValue.createSpiraCustomPropertyValue(
					spiraCustomProperty, "The test failed to run.");
			}

			String status = axisBuild.getResult();

			if (!status.equals("SUCCESS")) {
				return SpiraCustomPropertyValue.createSpiraCustomPropertyValue(
					spiraCustomProperty,
					"The build failed prior to running the test.");
			}

			return null;
		}

		if (!testResult.isFailing()) {
			return null;
		}

		String errorDetails = testResult.getErrorDetails();

		if ((errorDetails == null) || errorDetails.isEmpty()) {
			return null;
		}

		errorDetails = StringEscapeUtils.escapeHtml(errorDetails);

		if (errorDetails.contains("\n")) {
			String[] errorDetailsLines = errorDetails.split("\n");

			StringBuilder sb = new StringBuilder();

			sb.append("<details><summary>");
			sb.append(errorDetailsLines[0]);
			sb.append("</summary>");

			for (int lineNumber = 1; lineNumber < errorDetailsLines.length;
				 lineNumber++) {

				sb.append(errorDetailsLines[lineNumber]);

				if (lineNumber > TEST_CASE_ERROR_MAX_LINES) {
					break;
				}

				if (lineNumber < (errorDetailsLines.length - 1)) {
					sb.append("<br />");
				}
			}

			sb.append("</details>");

			errorDetails = sb.toString();
		}

		return SpiraCustomPropertyValue.createSpiraCustomPropertyValue(
			spiraCustomProperty, errorDetails);
	}

	private SpiraCustomPropertyValue _getTeamNameValue() {
		String teamName = _functionalAxisSpiraTestResult.getTeamName();

		if ((teamName == null) || teamName.isEmpty()) {
			return null;
		}

		SpiraBuildResult spiraBuildResult = getSpiraBuildResult();

		SpiraCustomProperty spiraCustomProperty =
			SpiraCustomProperty.createSpiraCustomProperty(
				spiraBuildResult.getSpiraProject(), SpiraTestCaseRun.class,
				"Product Teams", SpiraCustomProperty.Type.MULTILIST);

		return SpiraCustomPropertyValue.createSpiraCustomPropertyValue(
			spiraCustomProperty, teamName);
	}

	private final FunctionalAxisSpiraTestResult _functionalAxisSpiraTestResult;

}