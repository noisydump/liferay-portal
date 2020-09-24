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

package com.liferay.dispatch.talend.web.internal.executor;

import com.liferay.dispatch.executor.ScheduledTaskExecutor;
import com.liferay.dispatch.model.DispatchLog;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.dispatch.service.DispatchLogLocalService;
import com.liferay.dispatch.service.DispatchTriggerLocalService;
import com.liferay.petra.process.CollectorOutputProcessor;
import com.liferay.petra.process.ConsumerOutputProcessor;
import com.liferay.petra.process.ProcessException;
import com.liferay.petra.process.ProcessUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.io.File;
import java.io.InputStream;

import java.nio.charset.StandardCharsets;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true,
	property = "scheduled.task.executor.type=" + DispatchTalendScheduledTaskExecutor.SCHEDULED_TASK_EXECUTOR_TYPE_TALEND,
	service = ScheduledTaskExecutor.class
)
public class DispatchTalendScheduledTaskExecutor
	implements ScheduledTaskExecutor {

	public static final String SCHEDULED_TASK_EXECUTOR_TYPE_TALEND = "talend";

	@Override
	public void execute(long dispatchTriggerId) throws PortalException {
		DispatchLog dispatchLog = null;
		DispatchTrigger dispatchTrigger = null;

		String rootDirectoryName = null;

		Date startDate = new Date();

		File tempFile = null;

		try {
			FileEntry fileEntry =
				_dispatchTalendScheduledTaskExecutorHelper.getFileEntry(
					dispatchTriggerId);

			InputStream inputStream = fileEntry.getContentStream();

			tempFile = FileUtil.createTempFile(inputStream);

			File tempFolder = FileUtil.createTempFolder();

			FileUtil.unzip(tempFile, tempFolder);

			rootDirectoryName = tempFolder.getAbsolutePath();

			String shFileName = _getSHFileName(rootDirectoryName);

			_addExecutePermission(shFileName);

			dispatchTrigger = _dispatchTriggerLocalService.getDispatchTrigger(
				dispatchTriggerId);

			dispatchLog = _dispatchLogLocalService.addDispatchLog(
				dispatchTrigger.getUserId(),
				dispatchTrigger.getDispatchTriggerId(), null, null, null,
				startDate, BackgroundTaskConstants.STATUS_IN_PROGRESS);

			Future<Map.Entry<byte[], byte[]>> future = ProcessUtil.execute(
				CollectorOutputProcessor.INSTANCE,
				_getArguments(dispatchTrigger, rootDirectoryName, shFileName));

			Map.Entry<byte[], byte[]> entry = future.get();

			_dispatchLogLocalService.updateDispatchLog(
				dispatchLog.getDispatchLogId(), new Date(),
				new String(entry.getValue(), StandardCharsets.UTF_8),
				new String(entry.getKey(), StandardCharsets.UTF_8),
				BackgroundTaskConstants.STATUS_SUCCESSFUL);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			_dispatchLogLocalService.updateDispatchLog(
				dispatchLog.getDispatchLogId(), new Date(),
				exception.getMessage(), null,
				BackgroundTaskConstants.STATUS_FAILED);
		}
		finally {
			FileUtil.deltree(rootDirectoryName);

			if (tempFile != null) {
				FileUtil.delete(tempFile);
			}
		}
	}

	@Override
	public String getName() {
		return SCHEDULED_TASK_EXECUTOR_TYPE_TALEND;
	}

	private void _addExecutePermission(String shFileName)
		throws ProcessException {

		ProcessUtil.execute(
			ConsumerOutputProcessor.INSTANCE, "chmod", "+x", shFileName);
	}

	private List<String> _getArguments(
		DispatchTrigger dispatchTrigger, String rootDirectoryName,
		String shFileName) {

		List<String> arguments = new ArrayList<>();

		arguments.add(shFileName);

		arguments.add(
			"--context_param companyId=" + dispatchTrigger.getCompanyId());

		Date lastRunStateDate =
			_dispatchTriggerLocalService.getPreviousFireDate(
				dispatchTrigger.getDispatchTriggerId());

		if (lastRunStateDate != null) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss'Z'");

			arguments.add(
				"--context_param lastRunStartDate=" +
					simpleDateFormat.format(lastRunStateDate));
		}

		arguments.add("--context_param jobWorkDirectory=" + rootDirectoryName);

		UnicodeProperties typeSettingsUnicodeProperties =
			dispatchTrigger.getTypeSettingsProperties();

		if (typeSettingsUnicodeProperties != null) {
			for (Map.Entry<String, String> propEntry :
					typeSettingsUnicodeProperties.entrySet()) {

				StringBundler contextSB = new StringBundler(4);

				contextSB.append("--context_param ");
				contextSB.append(propEntry.getKey());
				contextSB.append(StringPool.EQUAL);
				contextSB.append(propEntry.getValue());

				arguments.add(contextSB.toString());
			}
		}

		return arguments;
	}

	private String _getSHFileName(String rootDirectoryName) {
		String[] strings = FileUtil.find(rootDirectoryName, "**\\*.sh", null);

		return strings[0];
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DispatchTalendScheduledTaskExecutor.class);

	@Reference
	private DispatchLogLocalService _dispatchLogLocalService;

	@Reference
	private DispatchTalendScheduledTaskExecutorHelper
		_dispatchTalendScheduledTaskExecutorHelper;

	@Reference
	private DispatchTriggerLocalService _dispatchTriggerLocalService;

}