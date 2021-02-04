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

import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import {useChannel} from '../hooks/useChannel';
import {ClosableAlert} from './ClosableAlert';
import {Editor} from './Editor';
import {Sidebar} from './Sidebar';

export default function App({
	editorAutocompleteData,
	editorMode: initialEditorMode,
	portletNamespace,
	script: initialScript,
	showCacheableWarning,
	showLanguageChangeWarning,
	templateVariableGroups,
}) {
	const [editorMode, setEditorMode] = useState(initialEditorMode);
	const inputChannel = useChannel();

	useEffect(() => {
		const modeSelect = document.getElementById(
			`${portletNamespace}language`
		);

		const onModeChange = (event) => {
			setEditorMode(
				{
					ftl: 'ftl',
					vm: 'velocity',
					xsl: 'xml',
				}[event.target.value]
			);
		};

		if (modeSelect) {
			modeSelect.addEventListener('change', onModeChange);

			return () => {
				modeSelect.removeEventListener('change', onModeChange);
			};
		}
	}, [portletNamespace]);

	return (
		<div className="ddm_template_editor__App">
			{editorMode !== 'xml' && (
				<div className="ddm_template_editor__App-sidebar">
					<Sidebar
						onButtonClick={(item) =>
							inputChannel.sendData(item.content)
						}
						templateVariableGroups={templateVariableGroups}
					/>
				</div>
			)}

			<div className="ddm_template_editor__App-content">
				<ClosableAlert
					message={Liferay.Language.get(
						'changing-the-language-does-not-automatically-translate-the-existing-template-script'
					)}
					visible={showLanguageChangeWarning}
				/>

				<ClosableAlert
					id={`${portletNamespace}-cacheableWarningMessage`}
					linkedCheckboxId={`${portletNamespace}cacheable`}
					message={Liferay.Language.get(
						'this-template-is-marked-as-cacheable.-avoid-using-code-that-uses-request-handling,-the-cms-query-api,-taglibs,-or-other-dynamic-features.-uncheck-the-cacheable-property-if-dynamic-behavior-is-needed'
					)}
					visible={showCacheableWarning}
				/>

				<Editor
					autocompleteData={editorAutocompleteData}
					editorMode={editorMode}
					initialScript={initialScript}
					inputChannel={inputChannel}
					portletNamespace={portletNamespace}
				/>
			</div>
		</div>
	);
}

App.propTypes = {
	editorAutocompleteData: PropTypes.object.isRequired,
	editorMode: PropTypes.string.isRequired,
	portletNamespace: PropTypes.string.isRequired,
	script: PropTypes.string.isRequired,
	showCacheableWarning: PropTypes.bool.isRequired,
	showLanguageChangeWarning: PropTypes.bool.isRequired,
	templateVariableGroups: PropTypes.any.isRequired,
};
