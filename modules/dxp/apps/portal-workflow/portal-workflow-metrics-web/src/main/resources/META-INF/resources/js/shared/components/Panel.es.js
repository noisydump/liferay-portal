/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import getCN from 'classnames';
import React from 'react';

const Body = ({children, elementClasses}) => {
	const classes = getCN('panel-body', elementClasses);

	if (!children) {
		return null;
	}

	return (
		<div className={classes} data-testid="panelBody">
			{children}
		</div>
	);
};

const Footer = ({children, elementClasses, label}) => {
	const classes = getCN('panel-footer', elementClasses);

	if (!children) {
		return null;
	}

	return (
		<div className={classes}>
			{label && <div>{label}</div>}

			{!!children && <div>{children}</div>}
		</div>
	);
};

const Header = ({children, elementClasses, title}) => {
	const classes = getCN('panel-header', elementClasses);

	return (
		<div className={classes} data-testid="panelHeader">
			{title && <div className="panel-title">{title}</div>}
			{!!children && <div>{children}</div>}
		</div>
	);
};

const HeaderWithOptions = ({
	children,
	description,
	elementClasses,
	title,
	tooltipPosition = 'right',
}) => {
	return (
		<Header elementClasses={elementClasses}>
			<div className="autofit-row">
				<div className="autofit-col autofit-col-expand flex-row">
					<span className="mr-2">{title}</span>

					<ClayTooltipProvider>
						<span>
							<span
								className="workflow-tooltip"
								data-tooltip-align={tooltipPosition}
								title={description}
							>
								<ClayIcon symbol="question-circle-full" />
							</span>
						</span>
					</ClayTooltipProvider>
				</div>

				{children}
			</div>
		</Header>
	);
};

const Panel = ({children, elementClasses}) => {
	const classes = getCN('panel', 'panel-secondary', elementClasses);

	return (
		<div className="container-fluid-1280 mt-4">
			<div className={classes} data-testid="panel">
				{children}
			</div>
		</div>
	);
};

Panel.Body = Body;
Panel.Footer = Footer;
Panel.Header = Header;
Panel.HeaderWithOptions = HeaderWithOptions;

export default Panel;
