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

package com.liferay.portal.search.elasticsearch7.internal.connection;

import com.liferay.portal.kernel.util.Http;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Adam Brandizzi
 */
public class ProxyConfigTest {

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testShouldApplyConfigIfHttpHasProxyConfig() {
		Mockito.when(
			_http.hasProxyConfig()
		).thenReturn(
			true
		);

		ProxyConfig.Builder builder = ProxyConfig.builder(_http);

		ProxyConfig proxyConfig = builder.host(
			"http://proxy"
		).build();

		Assert.assertTrue(proxyConfig.shouldApplyConfig());
	}

	@Test
	public void testShouldApplyConfigWithHostAndPort() {
		ProxyConfig.Builder builder = ProxyConfig.builder(_http);

		ProxyConfig proxyConfig = builder.host(
			"http://proxy"
		).port(
			32000
		).build();

		Assert.assertTrue(proxyConfig.shouldApplyConfig());
	}

	@Test
	public void testShouldNotApplyConfigWithoutHost() {
		ProxyConfig.Builder builder = ProxyConfig.builder(_http);

		ProxyConfig proxyConfig = builder.port(
			32000
		).build();

		Assert.assertFalse(proxyConfig.shouldApplyConfig());
	}

	@Test
	public void testShouldNotApplyConfigWithoutPort() {
		ProxyConfig.Builder builder = ProxyConfig.builder(_http);

		ProxyConfig proxyConfig = builder.host(
			"http://proxy"
		).build();

		Assert.assertFalse(proxyConfig.shouldApplyConfig());
	}

	@Mock
	private Http _http;

}