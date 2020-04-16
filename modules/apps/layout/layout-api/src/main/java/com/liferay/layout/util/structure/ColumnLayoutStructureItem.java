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

package com.liferay.layout.util.structure;

import com.liferay.layout.util.constants.LayoutDataItemTypeConstants;
import com.liferay.petra.lang.HashUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

import java.util.Objects;

/**
 * @author Eudaldo Alonso
 */
public class ColumnLayoutStructureItem extends LayoutStructureItem {

	public ColumnLayoutStructureItem(String parentItemId) {
		super(parentItemId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ColumnLayoutStructureItem)) {
			return false;
		}

		ColumnLayoutStructureItem columnLayoutStructureItem =
			(ColumnLayoutStructureItem)obj;

		if (!Objects.equals(_size, columnLayoutStructureItem._size)) {
			return false;
		}

		return super.equals(obj);
	}

	@Override
	public JSONObject getItemConfigJSONObject() {
		return JSONUtil.put("size", _size);
	}

	@Override
	public String getItemType() {
		return LayoutDataItemTypeConstants.TYPE_COLUMN;
	}

	public int getSize() {
		return _size;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, getItemId());
	}

	public void setSize(int size) {
		_size = size;
	}

	@Override
	public void updateItemConfig(JSONObject itemConfigJSONObject) {
		if (itemConfigJSONObject.has("size")) {
			setSize(itemConfigJSONObject.getInt("size"));
		}
	}

	private int _size;

}