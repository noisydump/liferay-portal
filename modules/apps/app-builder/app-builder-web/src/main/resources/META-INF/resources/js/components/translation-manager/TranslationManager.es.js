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

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React, {useState} from 'react';

export const formatLabel = (label) => label.replace('_', '-');

export const formatIcon = (label) => formatLabel(label).toLowerCase();

export const TranslationManagerLabel = ({
	languageId,
	translatedLanguageIds,
}) => {
	let className = 'label-warning';
	let label = Liferay.Language.get('not-translated');

	if (languageId === Liferay.ThemeDisplay.getDefaultLanguageId()) {
		className = 'label-info';
		label = Liferay.Language.get('default');
	}
	else if (translatedLanguageIds[languageId]) {
		className = 'label-success';
		label = Liferay.Language.get('translated');
	}

	return (
		<span className="autofit-col">
			<span className={classNames('label', className)}>
				<span className="label-item label-item-expand">{label}</span>
			</span>
		</span>
	);
};

export default ({
	editingLanguageId,
	onChangeLanguageId,
	translatedLanguageIds,
}) => {
	const [active, setActive] = useState(false);

	return (
		<ClayDropDown
			active={active}
			className="localizable-dropdown"
			onActiveChange={(newVal) => setActive(newVal)}
			trigger={
				<ClayButton
					displayType="secondary"
					small
					symbol={formatLabel(editingLanguageId)}
				>
					<span className="inline-item">
						<ClayIcon symbol={formatIcon(editingLanguageId)} />
					</span>

					<span className="btn-section">
						{formatLabel(editingLanguageId)}
					</span>
				</ClayButton>
			}
		>
			<ClayDropDown.ItemList className="localizable-dropdown-ul">
				{Object.keys(Liferay.Language.available).map(
					(languageId, index) => (
						<ClayDropDown.Item
							className="autofit-row"
							key={index}
							onClick={() => {
								onChangeLanguageId(languageId);
								setActive(false);
							}}
						>
							<span className="autofit-col autofit-col-expand">
								<span className="autofit-section">
									<span className="inline-item inline-item-before">
										<ClayIcon
											symbol={formatIcon(languageId)}
										/>
									</span>

									{formatLabel(languageId)}
								</span>
							</span>

							<TranslationManagerLabel
								languageId={languageId}
								translatedLanguageIds={translatedLanguageIds}
							/>
						</ClayDropDown.Item>
					)
				)}
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
};
