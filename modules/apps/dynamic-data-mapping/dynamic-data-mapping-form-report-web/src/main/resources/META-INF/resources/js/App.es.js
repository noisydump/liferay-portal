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

import React from 'react';

import Card from './components/Card.es';
import PieChart from './components/PieChart.es';

export default ({data}) => {
	const toArray = (values) =>
		Object.entries(values).map(([label, count]) => ({count, label}));

	const chartFactory = (type, values, totalEntries) => {
		if (type === 'radio') {
			return (
				<PieChart data={toArray(values)} totalEntries={totalEntries} />
			);
		}

		return null;
	};

	const sumTotalEntries = (values) =>
		Object.values(values).reduce((acc, value) => acc + value, 0);

	return Object.entries(data).map(([fieldName, {type, values}], index) => {
		const totalEntries = sumTotalEntries(values);
		const chart = chartFactory(type, values, totalEntries);

		if (chart === null) {
			return null;
		}

		return (
			<Card
				fieldName={fieldName}
				key={index}
				totalEntries={totalEntries}
				type={type}
				values={values}
			>
				{chart}
			</Card>
		);
	});
};
