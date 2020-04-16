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

package com.liferay.account.rest.resource.v1_0.test;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.rest.client.dto.v1_0.AccountRole;
import com.liferay.account.rest.dto.v1_0.Account;
import com.liferay.account.rest.dto.v1_0.AccountUser;
import com.liferay.account.rest.resource.v1_0.AccountResource;
import com.liferay.account.rest.resource.v1_0.AccountUserResource;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Drew Brokke
 */
@RunWith(Arquillian.class)
public class AccountRoleResourceTest extends BaseAccountRoleResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		User companyAdminUser = UserTestUtil.getAdminUser(
			testCompany.getCompanyId());

		_accountResource.setContextCompany(testCompany);
		_accountResource.setContextUser(companyAdminUser);

		_accountUserResource.setContextAcceptLanguage(
			new AcceptLanguage() {

				@Override
				public List<Locale> getLocales() {
					return null;
				}

				@Override
				public String getPreferredLanguageId() {
					Locale defaultLocale = LocaleUtil.getDefault();

					return defaultLocale.getLanguage();
				}

				@Override
				public Locale getPreferredLocale() {
					return LocaleUtil.getDefault();
				}

			});
		_accountUserResource.setContextCompany(testCompany);
		_accountUserResource.setContextUser(companyAdminUser);
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		_deleteAccounts(_accounts);
		_deleteAccountUsers(_accountUsers);
	}

	@Override
	public void testDeleteAccountRoleUserAssociation() throws Exception {
		Account account = _addAccount();

		AccountRole accountRole = _addAccountRole(account);
		AccountUser accountUser = _addAccountUser(account);

		_assertAccountRoleUserAssociation(
			account, accountRole, accountUser, false);

		_accountRoleLocalService.associateUser(
			account.getId(), accountRole.getId(), accountUser.getId());

		_assertAccountRoleUserAssociation(
			account, accountRole, accountUser, true);

		assertHttpResponseStatusCode(
			204,
			accountRoleResource.deleteAccountRoleUserAssociationHttpResponse(
				account.getId(), accountRole.getId(), accountUser.getId()));

		_assertAccountRoleUserAssociation(
			account, accountRole, accountUser, false);
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetAccountRolesPage() throws Exception {
	}

	@Override
	public void testPostAccountRoleUserAssociation() throws Exception {
		Account account = _addAccount();

		AccountRole accountRole = _addAccountRole(account);
		AccountUser accountUser = _addAccountUser(account);

		_assertAccountRoleUserAssociation(
			account, accountRole, accountUser, false);

		assertHttpResponseStatusCode(
			204,
			accountRoleResource.postAccountRoleUserAssociationHttpResponse(
				account.getId(), accountRole.getId(), accountUser.getId()));

		_assertAccountRoleUserAssociation(
			account, accountRole, accountUser, true);

		assertHttpResponseStatusCode(
			404,
			accountRoleResource.postAccountRoleUserAssociationHttpResponse(
				account.getId(), 0L, accountUser.getId()));
	}

	protected AccountUser randomAccountUser() {
		return new AccountUser() {
			{
				emailAddress =
					StringUtil.toLowerCase(RandomTestUtil.randomString()) +
						"@liferay.com";
				firstName = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				lastName = RandomTestUtil.randomString();
				middleName = RandomTestUtil.randomString();
				prefix = RandomTestUtil.randomString();
				screenName = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				suffix = RandomTestUtil.randomString();
			}
		};
	}

	@Override
	protected AccountRole testGetAccountRolesPage_addAccountRole(
			Long accountId, AccountRole accountRole)
		throws Exception {

		return accountRoleResource.postAccountRole(accountId, accountRole);
	}

	@Override
	protected Long testGetAccountRolesPage_getAccountId() throws Exception {
		Account account = _addAccount();

		return account.getId();
	}

	@Override
	protected Long testGetAccountRolesPage_getIrrelevantAccountId()
		throws Exception {

		Account account = _addAccount();

		return account.getId();
	}

	@Override
	protected AccountRole testGraphQLAccountRole_addAccountRole()
		throws Exception {

		Account account = _addAccount();

		return accountRoleResource.postAccountRole(
			account.getId(), randomAccountRole());
	}

	@Override
	protected AccountRole testPostAccountRole_addAccountRole(
			AccountRole accountRole)
		throws Exception {

		Account account = _addAccount();

		return accountRoleResource.postAccountRole(
			account.getId(), accountRole);
	}

	private Account _addAccount() throws Exception {
		Account account = _accountResource.postAccount(_randomAccount());

		_accounts.add(account);

		return account;
	}

	private AccountRole _addAccountRole(Account account) throws Exception {
		return accountRoleResource.postAccountRole(
			account.getId(), randomAccountRole());
	}

	private AccountUser _addAccountUser(Account account) throws Exception {
		AccountUser accountUser = _accountUserResource.postAccountUser(
			account.getId(), randomAccountUser());

		_accountUsers.add(accountUser);

		return accountUser;
	}

	private void _assertAccountRoleUserAssociation(
			Account account, AccountRole accountRole, AccountUser accountUser,
			boolean hasAssociation)
		throws Exception {

		AccountEntry accountEntry = _accountEntryLocalService.getAccountEntry(
			account.getId());

		UserGroupRole userGroupRole =
			_userGroupRoleLocalService.fetchUserGroupRole(
				accountUser.getId(), accountEntry.getAccountEntryGroupId(),
				accountRole.getRoleId());

		if (hasAssociation) {
			Assert.assertNotNull(userGroupRole);
		}
		else {
			Assert.assertNull(userGroupRole);
		}
	}

	private void _deleteAccounts(List<Account> accounts) {
		for (Account account : accounts) {
			try {
				_accountEntryLocalService.deleteAccountEntry(account.getId());
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception, exception);
				}
			}
		}
	}

	private void _deleteAccountUsers(List<AccountUser> accountUsers) {
		for (AccountUser accountUser : accountUsers) {
			try {
				_userLocalService.deleteUser(accountUser.getId());
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception, exception);
				}
			}
		}
	}

	private Account _randomAccount() {
		return new Account() {
			{
				description = RandomTestUtil.randomString(20);
				domains = new String[0];
				name = RandomTestUtil.randomString(20);
				parentAccountId = AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT;
				status = WorkflowConstants.STATUS_APPROVED;
			}
		};
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AccountRoleResourceTest.class);

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private AccountResource _accountResource;

	@Inject
	private AccountRoleLocalService _accountRoleLocalService;

	private final List<Account> _accounts = new ArrayList<>();

	@Inject
	private AccountUserResource _accountUserResource;

	private final List<AccountUser> _accountUsers = new ArrayList<>();

	@Inject
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Inject
	private UserLocalService _userLocalService;

}