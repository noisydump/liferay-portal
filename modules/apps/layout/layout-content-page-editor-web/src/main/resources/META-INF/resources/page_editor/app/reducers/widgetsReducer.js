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

import {ADD_FRAGMENT_ENTRY_LINKS, DELETE_WIDGETS} from '../actions/types';

export default function widgetsReducer(widgets, action) {
	switch (action.type) {
		case ADD_FRAGMENT_ENTRY_LINKS: {
			const fragmentEntryLinks = action.fragmentEntryLinks || [];

			let nextWidgets = widgets;

			fragmentEntryLinks.forEach((fragmentEntryLink) => {
				if (
					fragmentEntryLink.editableValues &&
					fragmentEntryLink.editableValues.portletId &&
					!fragmentEntryLink.editableValues.instanceable
				) {
					const widgetPath = getWidgetPath(
						widgets,
						fragmentEntryLink.editableValues.portletId
					);

					nextWidgets = setWidgetUsage(nextWidgets, widgetPath, {
						used: true,
					});
				}
			});

			return nextWidgets;
		}

		case DELETE_WIDGETS: {
			let nextWidgets = widgets;

			action.fragmentEntryLinks.forEach((fragmentEntryLink) => {
				if (
					fragmentEntryLink.editableValues &&
					fragmentEntryLink.editableValues.portletId &&
					!fragmentEntryLink.editableValues.instanceable
				) {
					const widgetPath = getWidgetPath(
						nextWidgets,
						fragmentEntryLink.editableValues.portletId
					);

					nextWidgets = setWidgetUsage(nextWidgets, widgetPath, {
						used: false,
					});
				}
			});

			return nextWidgets;
		}

		default:
			return widgets;
	}
}

/**
 * Get widget path from the widgets tree by portletId
 */
function getWidgetPath(widgets, portletId, path = []) {
	let widgetPath = null;

	for (
		let categoryIndex = 0;
		categoryIndex < widgets.length;
		categoryIndex += 1
	) {
		const {categories = [], portlets = []} = widgets[categoryIndex];

		const categoryPortletIndex = portlets.findIndex(
			(_portlet) => _portlet.portletId === portletId
		);

		const subCategoryPortletPath = getWidgetPath(categories, portletId, [
			...path,
			categoryIndex.toString(),
			'categories',
		]);

		if (categoryPortletIndex !== -1) {
			widgetPath = [
				...path,
				categoryIndex,
				'portlets',
				categoryPortletIndex,
			];
		}

		if (subCategoryPortletPath) {
			widgetPath = subCategoryPortletPath;
		}
	}

	return widgetPath;
}

/**
 * Iterates the widgets array recursively to set a given widget 'used' property
 * and returns a new array with the given widget modified
 */
function setWidgetUsage(widgets, path, usage) {
	if (!path.length) {
		return {
			...widgets,
			used: usage.used,
		};
	}

	const [currentPath, ...restPath] = path;

	if (Array.isArray(widgets)) {
		return widgets.map((widget, index) => {
			if (index === currentPath) {
				return setWidgetUsage(widgets[currentPath], restPath, usage);
			}

			return widget;
		});
	}
	else {
		return {
			...widgets,
			[currentPath]: setWidgetUsage(
				widgets[currentPath],
				restPath,
				usage
			),
		};
	}
}
