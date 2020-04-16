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

package com.liferay.portal.db.partition.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.lang.SafeClosable;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.dao.init.DBInitUtil;
import com.liferay.portal.db.partition.DBPartitionUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.jdbc.CurrentConnection;
import com.liferay.portal.kernel.dao.jdbc.CurrentConnectionUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.AssumeTestRule;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alberto Chaparro
 */
@RunWith(Arquillian.class)
public class DBPartitionUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new AssumeTestRule("assume"), new LiferayIntegrationTestRule());

	public static void assume() {
		_db = DBManagerUtil.getDB();

		Assume.assumeTrue(_db.getDBType() == DBType.MYSQL);
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
		_connection = DataAccess.getConnection();
		_currentDatabasePartitionEnabledValue =
			ReflectionTestUtil.getAndSetFieldValue(
				DBPartitionUtil.class, "_DATABASE_PARTITION_ENABLED", true);
		_currentDatabasePartitionInstanceIdValue =
			ReflectionTestUtil.getAndSetFieldValue(
				DBPartitionUtil.class, "_DATABASE_PARTITION_INSTANCE_ID",
				_DB_PARTITION_INSTANCE_ID);
		_currentDataSource = ReflectionTestUtil.getFieldValue(
			DBInitUtil.class, "_dataSource");

		_defaultSchemaName = _connection.getCatalog();

		DBPartitionUtil.setDefaultCompanyId(_portal.getDefaultCompanyId());

		DataSource dbPartitionDataSource = DBPartitionUtil.wrapDataSource(
			_currentDataSource);

		ReflectionTestUtil.setFieldValue(
			DBInitUtil.class, "_dataSource", dbPartitionDataSource);
		ReflectionTestUtil.setFieldValue(
			InfrastructureUtil.class, "_dataSource", dbPartitionDataSource);

		_db.runSQL("create schema " + _getSchemaName() + " character set utf8");
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_db.runSQL("drop schema " + _getSchemaName());

		DataAccess.cleanUp(_connection);

		ReflectionTestUtil.setFieldValue(
			DBInitUtil.class, "_dataSource", _currentDataSource);
		ReflectionTestUtil.setFieldValue(
			DBPartitionUtil.class, "_DATABASE_PARTITION_ENABLED",
			_currentDatabasePartitionEnabledValue);
		ReflectionTestUtil.setFieldValue(
			DBPartitionUtil.class, "_DATABASE_PARTITION_INSTANCE_ID",
			_currentDatabasePartitionInstanceIdValue);
		ReflectionTestUtil.setFieldValue(
			InfrastructureUtil.class, "_dataSource", _currentDataSource);
	}

	@After
	public void tearDown() throws SQLException {
		try (Statement statement = _connection.createStatement()) {
			statement.execute("use " + _defaultSchemaName);
		}
	}

	@Test
	public void testAccessCompanyByCompanyThreadLocal() throws SQLException {
		try (SafeClosable safeClosable =
				CompanyThreadLocal.setInitializingCompanyId(_COMPANY_ID);
			Connection connection = DataAccess.getConnection();
			Statement statement = connection.createStatement()) {

			statement.executeUpdate(
				StringBundler.concat(
					"create table ", _getSchemaName(), ".TestTable ",
					"(testColumn int)"));

			statement.execute("select 1 from TestTable");

			statement.execute("use " + _defaultSchemaName);
		}
	}

	@Test
	public void testAccessDefaultCompanyByCompanyThreadLocal()
		throws SQLException {

		long currentCompanyId = CompanyThreadLocal.getCompanyId();

		CompanyThreadLocal.setCompanyId(_portal.getDefaultCompanyId());

		try (Connection connection = DataAccess.getConnection();
			Statement statement = connection.createStatement()) {

			statement.execute("select 1 from CompanyInfo");

			statement.execute("use " + _defaultSchemaName);
		}
		finally {
			CompanyThreadLocal.setCompanyId(currentCompanyId);
		}
	}

	@Test
	public void testAddDBPartition() throws Exception {
		CurrentConnection defaultCurrentConnection =
			CurrentConnectionUtil.getCurrentConnection();

		try {
			CurrentConnection currentConnection = new CurrentConnection() {

				@Override
				public Connection getConnection(DataSource dataSource) {
					return _connection;
				}

			};

			ReflectionTestUtil.setFieldValue(
				CurrentConnectionUtil.class, "_currentConnection",
				currentConnection);

			DBPartitionUtil.addDBPartition(_COMPANY_ID);

			try (Statement statement = _connection.createStatement()) {
				statement.execute(
					"select 1 from " + _getSchemaName() + ".CompanyInfo");
			}
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				CurrentConnectionUtil.class, "_currentConnection",
				defaultCurrentConnection);
		}
	}

	@Test
	public void testAddDefaultDBPartition() throws PortalException {
		Assert.assertFalse(
			DBPartitionUtil.addDBPartition(_portal.getDefaultCompanyId()));
	}

	private static String _getSchemaName() {
		return _DB_PARTITION_INSTANCE_ID + StringPool.UNDERLINE + _COMPANY_ID;
	}

	private static final long _COMPANY_ID = 1L;

	private static final String _DB_PARTITION_INSTANCE_ID =
		"dbPartitionUtilTest";

	private static Connection _connection;
	private static boolean _currentDatabasePartitionEnabledValue;
	private static String _currentDatabasePartitionInstanceIdValue;
	private static DataSource _currentDataSource;
	private static DB _db;
	private static String _defaultSchemaName;

	@Inject
	private static Portal _portal;

}