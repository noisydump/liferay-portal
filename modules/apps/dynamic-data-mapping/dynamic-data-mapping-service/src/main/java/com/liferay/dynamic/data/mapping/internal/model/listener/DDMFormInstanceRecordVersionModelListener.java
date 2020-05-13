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

package com.liferay.dynamic.data.mapping.internal.model.listener;

import com.liferay.dynamic.data.mapping.constants.DDMFormInstanceReportConstants;
import com.liferay.dynamic.data.mapping.exception.NoSuchFormInstanceReportException;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceReport;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceReportLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcos Martins
 */
@Component(immediate = true, service = ModelListener.class)
public class DDMFormInstanceRecordVersionModelListener
	extends BaseModelListener<DDMFormInstanceRecordVersion> {

	@Override
	public void onAfterCreate(
			DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion)
		throws ModelListenerException {

		try {
			_updateDDMFormInstanceReport(
				ddmFormInstanceRecordVersion,
				DDMFormInstanceReportConstants.EVENT_ADD_RECORD_VERSION);
		}
		catch (Exception exception) {
			_log.error(
				"Unable to update dynamic data mapping form instance report " +
					"for dynamic data mapping form instance record " +
						ddmFormInstanceRecordVersion.getFormInstanceRecordId(),
				exception);
		}
	}

	@Override
	public void onBeforeRemove(
			DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion)
		throws ModelListenerException {

		try {
			_updateDDMFormInstanceReport(
				ddmFormInstanceRecordVersion,
				DDMFormInstanceReportConstants.EVENT_DELETE_RECORD_VERSION);
		}
		catch (Exception exception) {
			_log.error(
				"Unable to update dynamic data mapping form instance report " +
					"for dynamic data mapping form instance record " +
						ddmFormInstanceRecordVersion.getFormInstanceRecordId(),
				exception);
		}
	}

	private void _updateDDMFormInstanceReport(
			DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion,
			String formInstanceReportEvent)
		throws PortalException {

		try {
			DDMFormInstanceReport ddmFormInstanceReport =
				_ddmFormInstanceReportLocalService.
					getFormInstanceReportByFormInstanceId(
						ddmFormInstanceRecordVersion.getFormInstanceId());

			_ddmFormInstanceReportLocalService.updateFormInstanceReport(
				ddmFormInstanceReport.getFormInstanceReportId(),
				ddmFormInstanceRecordVersion.getFormInstanceRecordVersionId(),
				formInstanceReportEvent);
		}
		catch (NoSuchFormInstanceReportException
					noSuchFormInstanceReportException) {

			if (_log.isWarnEnabled()) {
				_log.warn(
					noSuchFormInstanceReportException,
					noSuchFormInstanceReportException);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormInstanceRecordVersionModelListener.class);

	@Reference
	private DDMFormInstanceReportLocalService
		_ddmFormInstanceReportLocalService;

}