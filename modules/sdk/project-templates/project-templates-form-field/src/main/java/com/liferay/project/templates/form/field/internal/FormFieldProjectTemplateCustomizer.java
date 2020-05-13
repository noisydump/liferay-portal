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

package com.liferay.project.templates.form.field.internal;

import com.liferay.project.templates.extensions.ProjectTemplateCustomizer;
import com.liferay.project.templates.extensions.ProjectTemplatesArgs;

import java.io.File;

import java.nio.file.Path;

import java.util.ArrayList;
import java.util.List;

import org.apache.maven.archetype.ArchetypeGenerationRequest;
import org.apache.maven.archetype.ArchetypeGenerationResult;

/**
 * @author Renato Rego
 */
public class FormFieldProjectTemplateCustomizer
	implements ProjectTemplateCustomizer {

	@Override
	public String getTemplateName() {
		return "form-field";
	}

	@Override
	public void onAfterGenerateProject(
			ProjectTemplatesArgs projectTemplatesArgs, File destinationDir,
			ArchetypeGenerationResult archetypeGenerationResult)
		throws Exception {

		String liferayVersion = projectTemplatesArgs.getLiferayVersion();

		List<String> fileNames = new ArrayList<>();

		String name = projectTemplatesArgs.getName();

		if (liferayVersion.startsWith("7.0")) {
			fileNames.add(".babelrc");
			fileNames.add(".npmbundlerrc");
			fileNames.add("package.json");
			fileNames.add(
				"src/main/resources/META-INF/resources/" + name + ".es.js");
		}

		if (liferayVersion.startsWith("7.2")) {
			fileNames.add("src/main/resources/META-INF/resources/config.js");
			fileNames.add(
				"src/main/resources/META-INF/resources/" + name + "_field.js");

			StringBuilder sb = new StringBuilder();

			String[] nameParts = name.split("-");

			if (nameParts.length == 0) {
				sb.append(name);
			}
			else {
				for (String namePart : nameParts) {
					sb.append(namePart);
					sb.append("/");
				}
			}

			String className = projectTemplatesArgs.getClassName();

			fileNames.add(
				"src/main/java/" + sb.toString() + "form/field/" + className +
					"DDMFormFieldRenderer.java");
		}
		else {
			fileNames.add(
				"src/main/resources/META-INF/resources/" + name +
					"Register.soy");
		}

		Path destinationDirPath = destinationDir.toPath();

		Path projectDirPath = destinationDirPath.resolve(name);

		for (String fileName : fileNames) {
			ProjectTemplateCustomizer.deleteFileInPath(
				fileName, projectDirPath);
		}
	}

	@Override
	public void onBeforeGenerateProject(
			ProjectTemplatesArgs projectTemplatesArgs,
			ArchetypeGenerationRequest archetypeGenerationRequest)
		throws Exception {
	}

}