/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.internal.blueprint.search.request.enhancer.body.contributor;

import com.liferay.portal.search.internal.searcher.SearchRequestBuilderFactoryImpl;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.search.experiences.internal.blueprint.search.request.body.contributor.GeneralSXPSearchRequestBodyContributor;
import com.liferay.search.experiences.rest.dto.v1_0.Configuration;
import com.liferay.search.experiences.rest.dto.v1_0.GeneralConfiguration;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Joshua Cords
 */
public class GeneralSXPSearchRequestBodyContributorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_generalSXPSearchRequestBodyContributor =
			new GeneralSXPSearchRequestBodyContributor();

		SearchRequestBuilderFactory searchRequestBuilderFactory =
			new SearchRequestBuilderFactoryImpl();

		_searchRequestBuilder = searchRequestBuilderFactory.builder();
	}

	@Test
	public void testSearchableAssetTypes() {
		List<String> searchableAssetTypes = new ArrayList<>();

		searchableAssetTypes.add("com.liferay.journal.model.JournalArticle");

		GeneralConfiguration generalConfiguration = new GeneralConfiguration();

		generalConfiguration.setSearchableAssetTypes(
			searchableAssetTypes.toArray(new String[1]));

		Configuration configuration = new Configuration();

		configuration.setGeneralConfiguration(generalConfiguration);

		_generalSXPSearchRequestBodyContributor.contribute(
			configuration, _searchRequestBuilder, null);

		SearchRequest searchRequest = _searchRequestBuilder.build();

		Assert.assertEquals(
			searchableAssetTypes, searchRequest.getEntryClassNames());
		Assert.assertEquals(
			searchableAssetTypes, searchRequest.getModelIndexerClassNames());
	}

	private GeneralSXPSearchRequestBodyContributor
		_generalSXPSearchRequestBodyContributor;
	private SearchRequestBuilder _searchRequestBuilder;

}