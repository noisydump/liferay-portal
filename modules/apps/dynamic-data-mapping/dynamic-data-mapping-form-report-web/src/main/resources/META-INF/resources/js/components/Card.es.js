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

import ClayCard from '@clayui/card';
import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import React from 'react';

export default ({children, fieldName, totalEntries, type}) => (
	<div className="col-md-8">
		<div className="sheet">
			<div className="col-md-12">
				<ClayCard displayType="image">
					<ClayCard.AspectRatio className="card-header card-item-first">
						<ClayTooltipProvider>
							<div
								className="aspect-ratio-item aspect-ratio-item-center-left aspect-ratio-item-fluid card-symbol card-type-asset-icon"
								data-tooltip-align="bottom"
								data-tooltip-delay={300}
								title={`${type}-field-type-label`}
							>
								<ClayIcon symbol={`${type}-button`} />
							</div>
						</ClayTooltipProvider>

						<div className="field-info">
							<ClayCard.Description displayType="title">
								{fieldName}
							</ClayCard.Description>

							<ClayCard.Description
								displayType="text"
								truncate={false}
							>
								{`${totalEntries} ${Liferay.Language.get(
									'entries'
								).toLowerCase()}`}
							</ClayCard.Description>
						</div>
					</ClayCard.AspectRatio>

					<ClayCard.Body>{children}</ClayCard.Body>
				</ClayCard>
			</div>
		</div>
	</div>
);
