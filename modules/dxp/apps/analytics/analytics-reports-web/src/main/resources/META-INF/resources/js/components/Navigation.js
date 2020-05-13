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

import ClayAlert from '@clayui/alert';
import PropTypes from 'prop-types';
import React, {useCallback, useContext, useState} from 'react';

import ConnectionContext from '../context/ConnectionContext';
import {StoreContext, useHistoricalWarning, useWarning} from '../context/store';
import {numberFormat} from '../utils/numberFormat';
import Detail from './Detail';
import Main from './Main';

export default function Navigation({
	api,
	authorName,
	defaultTimeRange,
	defaultTimeSpanKey,
	languageTag,
	pagePublishDate,
	pageTitle,
	timeSpanOptions,
	trafficSources,
}) {
	const [currentPage, setCurrentPage] = useState({view: 'main'});

	const [trafficSourceName, setTrafficSourceName] = useState('');

	const {validAnalyticsConnection} = useContext(ConnectionContext);

	const [hasWarning] = useWarning();

	const [hasHistoricalWarning] = useHistoricalWarning();

	const {getHistoricalReads, getHistoricalViews} = api;

	const handleCurrentPage = useCallback((currentPage) => {
		setCurrentPage({view: currentPage.view});
	}, []);

	const handleTotalReads = useCallback(() => {
		return api.getTotalReads().then((response) => {
			return numberFormat(
				languageTag,
				response.analyticsReportsTotalReads
			);
		});
	}, [api, languageTag]);

	const handleTotalViews = useCallback(() => {
		return api.getTotalViews().then((response) => {
			return numberFormat(
				languageTag,
				response.analyticsReportsTotalViews
			);
		});
	}, [api, languageTag]);

	const handleTrafficShare = useCallback(() => {
		const trafficSource = trafficSources.find((trafficSource) => {
			return trafficSource['name'] === trafficSourceName;
		});

		return Promise.resolve(trafficSource ? trafficSource.share : '-');
	}, [trafficSourceName, trafficSources]);

	const handleTrafficSourceClick = useCallback(
		(trafficSourceName) => {
			setTrafficSourceName(trafficSourceName);

			api.getTrafficSourceDetails(trafficSourceName).then(
				(trafficSourceData) => {
					setCurrentPage({
						data: trafficSourceData,
						view: 'traffic-source-detail',
					});
				}
			);
		},
		[api]
	);

	const handleTrafficSourceName = useCallback((trafficSourceName) => {
		setTrafficSourceName(trafficSourceName);
	}, []);

	const handleTrafficVolume = useCallback(() => {
		const trafficSource = trafficSources.find((trafficSource) => {
			return trafficSource['name'] === trafficSourceName;
		});

		return Promise.resolve(trafficSource ? trafficSource.value : '-');
	}, [trafficSourceName, trafficSources]);

	const [{readsEnabled}] = useContext(StoreContext);

	const chartDataProviders = readsEnabled
		? [getHistoricalViews, getHistoricalReads]
		: [getHistoricalViews];

	return (
		<>
			{!validAnalyticsConnection && (
				<ClayAlert
					className="p-0"
					displayType="danger"
					variant="stripe"
				>
					{Liferay.Language.get('an-unexpected-error-occurred')}
				</ClayAlert>
			)}

			{validAnalyticsConnection && (hasWarning || hasHistoricalWarning) && (
				<ClayAlert
					className="p-0"
					displayType="warning"
					variant="stripe"
				>
					{Liferay.Language.get(
						'some-data-is-temporarily-unavailable'
					)}
				</ClayAlert>
			)}

			{currentPage.view === 'main' && (
				<div className="p-3">
					<Main
						authorName={authorName}
						chartDataProviders={chartDataProviders}
						defaultTimeRange={defaultTimeRange}
						defaultTimeSpanOption={defaultTimeSpanKey}
						languageTag={languageTag}
						onTrafficSourceClick={handleTrafficSourceClick}
						pagePublishDate={pagePublishDate}
						pageTitle={pageTitle}
						timeSpanOptions={timeSpanOptions}
						totalReadsDataProvider={handleTotalReads}
						totalViewsDataProvider={handleTotalViews}
						trafficSources={trafficSources}
					/>
				</div>
			)}

			{currentPage.view === 'traffic-source-detail' && (
				<Detail
					currentPage={currentPage}
					languageTag={languageTag}
					onCurrentPageChange={handleCurrentPage}
					onTrafficSourceNameChange={handleTrafficSourceName}
					trafficShareDataProvider={handleTrafficShare}
					trafficVolumeDataProvider={handleTrafficVolume}
				/>
			)}
		</>
	);
}

Navigation.proptypes = {
	api: PropTypes.object.isRequired,
	authorName: PropTypes.string.isRequired,
	defaultTimeRange: PropTypes.objectOf(
		PropTypes.shape({
			endDate: PropTypes.string.isRequired,
			startDate: PropTypes.string.isRequired,
		})
	).isRequired,
	defaultTimeSpanKey: PropTypes.string.isRequired,
	languageTag: PropTypes.string.isRequired,
	pagePublishDate: PropTypes.number.isRequired,
	pageTitle: PropTypes.string.isRequired,
	timeSpanOptions: PropTypes.arrayOf(
		PropTypes.shape({
			key: PropTypes.string.isRequired,
			label: PropTypes.string.isRequired,
		})
	).isRequired,
	trafficSources: PropTypes.array.isRequired,
};
