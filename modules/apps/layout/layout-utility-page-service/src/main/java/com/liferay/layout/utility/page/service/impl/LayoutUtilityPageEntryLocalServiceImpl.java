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

package com.liferay.layout.utility.page.service.impl;

import com.liferay.layout.utility.page.exception.DuplicateLayoutUtilityPageEntryExternalReferenceCodeException;
import com.liferay.layout.utility.page.exception.LayoutUtilityPageEntryNameException;
import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.layout.utility.page.service.base.LayoutUtilityPageEntryLocalServiceBaseImpl;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.layout.utility.page.model.LayoutUtilityPageEntry",
	service = AopService.class
)
public class LayoutUtilityPageEntryLocalServiceImpl
	extends LayoutUtilityPageEntryLocalServiceBaseImpl {

	@Override
	public LayoutUtilityPageEntry addLayoutUtilityPageEntry(
			String externalReferenceCode, long userId, long groupId, long plid,
			String name, int type)
		throws PortalException {

		_validateExternalReferenceCode(externalReferenceCode, groupId);
		_validateLayout(groupId, plid);
		_validateName(name);

		LayoutUtilityPageEntry layoutUtilityPageEntry =
			layoutUtilityPageEntryPersistence.create(
				counterLocalService.increment());

		layoutUtilityPageEntry.setGroupId(groupId);

		User user = _userLocalService.getUser(userId);

		layoutUtilityPageEntry.setCompanyId(user.getCompanyId());
		layoutUtilityPageEntry.setUserId(user.getUserId());
		layoutUtilityPageEntry.setUserName(user.getFullName());

		layoutUtilityPageEntry.setExternalReferenceCode(externalReferenceCode);
		layoutUtilityPageEntry.setPlid(plid);
		layoutUtilityPageEntry.setName(name);
		layoutUtilityPageEntry.setType(type);

		return layoutUtilityPageEntryPersistence.update(layoutUtilityPageEntry);
	}

	@Override
	public LayoutUtilityPageEntry getDefaultLayoutUtilityPageEntry(
			long groupId, int type)
		throws PortalException {

		return layoutUtilityPageEntryPersistence.findByG_D_T_First(
			groupId, true, type, null);
	}

	@Override
	public List<LayoutUtilityPageEntry> getLayoutUtilityPageEntries(
		long groupId) {

		return layoutUtilityPageEntryPersistence.findByGroupId(groupId);
	}

	@Override
	public List<LayoutUtilityPageEntry> getLayoutUtilityPageEntries(
		long groupId, int type, int start, int end,
		OrderByComparator<LayoutUtilityPageEntry> orderByComparator) {

		return layoutUtilityPageEntryPersistence.findByG_T(
			groupId, type, start, end, orderByComparator);
	}

	@Override
	public List<LayoutUtilityPageEntry> getLayoutUtilityPageEntries(
		long groupId, int start, int end,
		OrderByComparator<LayoutUtilityPageEntry> orderByComparator) {

		return layoutUtilityPageEntryPersistence.findByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public int getLayoutUtilityPageEntriesCount(long groupId) {
		return layoutUtilityPageEntryPersistence.countByGroupId(groupId);
	}

	@Override
	public LayoutUtilityPageEntry setDefaultLayoutUtilityPageEntry(
			long layoutUtilityPageEntryId)
		throws PortalException {

		LayoutUtilityPageEntry layoutUtilityPageEntry =
			layoutUtilityPageEntryPersistence.findByPrimaryKey(
				layoutUtilityPageEntryId);

		LayoutUtilityPageEntry defaultLayoutUtilityPageEntry =
			layoutUtilityPageEntryPersistence.fetchByG_D_T_First(
				layoutUtilityPageEntry.getGroupId(), true,
				layoutUtilityPageEntry.getType(), null);

		if (defaultLayoutUtilityPageEntry != null) {
			defaultLayoutUtilityPageEntry.setDefaultLayoutUtilityPageEntry(
				false);

			layoutUtilityPageEntryPersistence.update(
				defaultLayoutUtilityPageEntry);
		}

		layoutUtilityPageEntry.setDefaultLayoutUtilityPageEntry(true);

		return layoutUtilityPageEntryPersistence.update(layoutUtilityPageEntry);
	}

	@Override
	public LayoutUtilityPageEntry updateLayoutUtilityPageEntry(
			long layoutUtilityPageEntryId, long plid, String name, int type)
		throws PortalException {

		LayoutUtilityPageEntry layoutUtilityPageEntry =
			layoutUtilityPageEntryPersistence.fetchByPrimaryKey(
				layoutUtilityPageEntryId);

		_validateLayout(layoutUtilityPageEntry.getGroupId(), plid);

		_validateName(name);

		layoutUtilityPageEntry.setPlid(plid);
		layoutUtilityPageEntry.setName(name);
		layoutUtilityPageEntry.setType(type);

		return layoutUtilityPageEntryPersistence.update(layoutUtilityPageEntry);
	}

	private void _validateExternalReferenceCode(
			String externalReferenceCode, long groupId)
		throws PortalException {

		if (Validator.isNull(externalReferenceCode)) {
			return;
		}

		LayoutUtilityPageEntry layoutUtilityPageEntry =
			layoutUtilityPageEntryPersistence.fetchByG_ERC(
				groupId, externalReferenceCode);

		if (layoutUtilityPageEntry != null) {
			throw new DuplicateLayoutUtilityPageEntryExternalReferenceCodeException(
				StringBundler.concat(
					"Duplicate layout utility page entry external reference ",
					"code ", externalReferenceCode, " in group ", groupId));
		}
	}

	private void _validateLayout(long groupId, long plid)
		throws PortalException {

		Layout layout = _layoutLocalService.fetchLayout(plid);

		if (plid <= 0) {
			return;
		}

		if ((layout == null) || (layout.getGroupId() != groupId) ||
			layout.isDraftLayout() || layout.isTypeAssetDisplay()) {

			throw new NoSuchLayoutException(
				StringBundler.concat(
					"Layout ", plid, " is invalid for group ", groupId));
		}
	}

	private void _validateName(String name) throws PortalException {
		if (Validator.isNull(name)) {
			throw new LayoutUtilityPageEntryNameException("Name is null");
		}

		int nameMaxLength = ModelHintsUtil.getMaxLength(
			LayoutUtilityPageEntry.class.getName(), "name");

		if (name.length() > nameMaxLength) {
			throw new LayoutUtilityPageEntryNameException(
				"Name has more than " + nameMaxLength + " characters");
		}
	}

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private UserLocalService _userLocalService;

}