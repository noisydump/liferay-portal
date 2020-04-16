<%--
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
--%>

<%@ include file="/change_lists/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

CTCollection ctCollection = (CTCollection)request.getAttribute("ctCollection");

String actionName = "/change_lists/edit_ct_collection";
long ctCollectionId = CTConstants.CT_COLLECTION_ID_PRODUCTION;
String description = StringPool.BLANK;
String name = StringPool.BLANK;
String saveButtonLabel = "create";

boolean revert = ParamUtil.getBoolean(request, "revert");

if (revert) {
	actionName = "/change_lists/undo_ct_collection";
	ctCollectionId = ctCollection.getCtCollectionId();
	name = StringBundler.concat(LanguageUtil.get(request, "revert"), " \"", ctCollection.getName(), "\"");
	saveButtonLabel = "revert-and-create-publication";

	renderResponse.setTitle(LanguageUtil.get(resourceBundle, "revert"));
}
else if (ctCollection != null) {
	ctCollectionId = ctCollection.getCtCollectionId();
	description = ctCollection.getDescription();
	name = ctCollection.getName();
	saveButtonLabel = "save";

	renderResponse.setTitle(StringBundler.concat(LanguageUtil.format(resourceBundle, "edit-x", new Object[] {ctCollection.getName()})));
}
else {
	renderResponse.setTitle(LanguageUtil.get(request, "create-new-publication"));
}

portletDisplay.setURLBack(redirect);
portletDisplay.setShowBackIcon(true);
%>

<liferay-portlet:actionURL name="<%= actionName %>" var="actionURL">
	<liferay-portlet:param name="mvcRenderCommandName" value="/change_lists/view" />
	<liferay-portlet:param name="redirect" value="<%= redirect %>" />
</liferay-portlet:actionURL>

<liferay-ui:error exception="<%= CTCollectionDescriptionException.class %>" message="the-publication-description-is-too-long" />
<liferay-ui:error exception="<%= CTCollectionNameException.class %>" message="the-publication-name-is-too-long" />

<div class="custom-sheet sheet sheet-lg">
	<aui:form action='<%= actionURL + "&etag=0&strip=0" %>' cssClass="lfr-export-dialog" method="post" name="editChangeListFm">
		<aui:input name="ctCollectionId" type="hidden" value="<%= ctCollectionId %>" />

		<c:if test="<%= revert %>">
			<p class="sheet-text"><liferay-ui:message key="reverting-creates-a-new-publication-with-the-reverted-changes" /></p>

			<div class="sheet-section">
				<h3 class="sheet-subtitle">
					<liferay-ui:message key="publication-with-reverted-changes" />
				</h3>
		</c:if>

			<aui:input label="name" name="name" placeholder="publication-name-placeholder" value="<%= name %>">
				<aui:validator name="maxLength"><%= ModelHintsUtil.getMaxLength(CTCollection.class.getName(), "name") %></aui:validator>
				<aui:validator name="required" />
			</aui:input>

			<aui:input label="description" name="description" placeholder="publication-description-placeholder" type="textarea" value="<%= description %>">
				<aui:validator name="maxLength"><%= ModelHintsUtil.getMaxLength(CTCollection.class.getName(), "description") %></aui:validator>
			</aui:input>

		<c:if test="<%= revert %>">
			</div>
		</c:if>

		<aui:button-row>
			<aui:button id="saveButton" type="submit" value="<%= LanguageUtil.get(request, saveButtonLabel) %>" />

			<aui:button href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:form>
</div>