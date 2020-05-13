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

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {useEventListener} from 'frontend-js-react-web';
import {closest} from 'metal-dom';
import PropTypes from 'prop-types';
import React from 'react';

import {Z_KEYCODE} from '../../config/constants/keycodes';
import {useSelector} from '../../store/index';

const isTextElement = (element) => {
	return (
		(element.tagName === 'INPUT' && element.type === 'text') ||
		element.tagName === 'TEXTAREA'
	);
};

const isCommentsAlloyEditor = (element) => {
	return (
		element.classList.contains('alloy-editor') &&
		element.parentElement.classList.contains('alloy-editor-container') &&
		closest(element, '.page-editor__sidebar')
	);
};

const isWithinIframe = () => {
	return window.top !== window.self;
};

const isUndoCombination = (event) => {
	return event.keyCode === Z_KEYCODE && (event.ctrlKey || event.metaKey);
};

export default function Undo({onRedo = () => {}, onUndo = () => {}}) {
	useEventListener(
		'keydown',
		(event) => {
			if (
				isUndoCombination(event) &&
				!isTextElement(event.target) &&
				!isCommentsAlloyEditor(event.target) &&
				!isWithinIframe()
			) {
				event.preventDefault();

				onUndo();
			}
		},
		true,
		window
	);

	const undoHistory = useSelector((state) => state.undoHistory);

	return (
		<ClayButton.Group className="d-block d-none mr-3">
			<ClayButtonWithIcon
				aria-label={Liferay.Language.get('undo')}
				className="btn-monospaced"
				disabled={!undoHistory || !undoHistory.length}
				displayType="secondary"
				onClick={onUndo}
				small
				symbol="undo"
				title={Liferay.Language.get('undo')}
			/>
			<ClayButtonWithIcon
				aria-label={Liferay.Language.get('redo')}
				className="btn-monospaced"
				disabled
				displayType="secondary"
				onClick={onRedo}
				small
				symbol="redo"
				title={Liferay.Language.get('redo')}
			/>
		</ClayButton.Group>
	);
}

Undo.propTypes = {
	onRedo: PropTypes.func,
	onUndo: PropTypes.func,
};
