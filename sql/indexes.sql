create index IX_923BD178 on Address (companyId, classNameId, classPK, mailing);
create index IX_9226DBB4 on Address (companyId, classNameId, classPK, primary_);
create index IX_5BC8B0D4 on Address (userId);
create index IX_8FCB620E on Address (uuid_[$COLUMN_LENGTH:75$], companyId);

create index IX_37B0A8A2 on AnnouncementsDelivery (companyId);
create unique index IX_BA4413D5 on AnnouncementsDelivery (userId, type_[$COLUMN_LENGTH:75$]);

create index IX_14F06A6B on AnnouncementsEntry (classNameId, classPK, alert);
create index IX_459BE01B on AnnouncementsEntry (companyId, classNameId, classPK, alert);
create index IX_D49C2E66 on AnnouncementsEntry (userId);
create index IX_F2949120 on AnnouncementsEntry (uuid_[$COLUMN_LENGTH:75$], companyId);

create index IX_EF1F022A on AnnouncementsFlag (companyId);
create index IX_9C7EB9F on AnnouncementsFlag (entryId);
create unique index IX_4539A99C on AnnouncementsFlag (userId, entryId, value);

create index IX_AE8DFA7 on AssetCategory (companyId, externalReferenceCode[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_1757FA92 on AssetCategory (ctCollectionId);
create index IX_62DC0D54 on AssetCategory (groupId, ctCollectionId);
create index IX_3E49A228 on AssetCategory (groupId, name[$COLUMN_LENGTH:255$], vocabularyId, ctCollectionId);
create index IX_5159C90B on AssetCategory (groupId, parentCategoryId, ctCollectionId);
create index IX_852EA801 on AssetCategory (groupId, parentCategoryId, name[$COLUMN_LENGTH:255$], vocabularyId);
create index IX_51264AA0 on AssetCategory (groupId, parentCategoryId, vocabularyId, ctCollectionId);
create index IX_7EF2DB29 on AssetCategory (groupId, vocabularyId, ctCollectionId);
create index IX_8F988466 on AssetCategory (name[$COLUMN_LENGTH:255$], vocabularyId, ctCollectionId);
create index IX_88D822C9 on AssetCategory (parentCategoryId, ctCollectionId);
create index IX_83C2D848 on AssetCategory (parentCategoryId, name[$COLUMN_LENGTH:255$], ctCollectionId);
create unique index IX_DC516B1D on AssetCategory (parentCategoryId, name[$COLUMN_LENGTH:255$], vocabularyId, ctCollectionId);
create index IX_8CEDBFDE on AssetCategory (parentCategoryId, vocabularyId, ctCollectionId);
create index IX_59B2EF86 on AssetCategory (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_A9CC915E on AssetCategory (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_5B65C08 on AssetCategory (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);
create index IX_24AFC3E7 on AssetCategory (vocabularyId, ctCollectionId);

create index IX_38A65B55 on AssetEntries_AssetCategories (companyId);
create index IX_E119938A on AssetEntries_AssetCategories (entryId);

create index IX_112337B8 on AssetEntries_AssetTags (companyId);
create index IX_B2A61B55 on AssetEntries_AssetTags (tagId);

create unique index IX_7BF8337B on AssetEntry (classNameId, classPK, ctCollectionId);
create index IX_25F682BE on AssetEntry (companyId, ctCollectionId);
create index IX_E504D126 on AssetEntry (ctCollectionId);
create index IX_8839F457 on AssetEntry (expirationDate, ctCollectionId);
create index IX_B516ADB0 on AssetEntry (groupId, classNameId, publishDate, expirationDate, ctCollectionId);
create index IX_A62EE954 on AssetEntry (groupId, classNameId, visible, ctCollectionId);
create index IX_683ADC7F on AssetEntry (groupId, classUuid[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_D5B55D40 on AssetEntry (groupId, ctCollectionId);
create index IX_5B55565F on AssetEntry (layoutUuid[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_788964E3 on AssetEntry (publishDate, ctCollectionId);
create index IX_5B6AC3B8 on AssetEntry (visible, ctCollectionId);

create index IX_5D969E8E on AssetLink (ctCollectionId);
create index IX_9BB95D26 on AssetLink (entryId1, ctCollectionId);
create index IX_97B1F7F on AssetLink (entryId1, entryId2, ctCollectionId);
create unique index IX_7FC555F2 on AssetLink (entryId1, entryId2, type_, ctCollectionId);
create index IX_F75CBE6B on AssetLink (entryId1, type_, ctCollectionId);
create index IX_6963BEE7 on AssetLink (entryId2, ctCollectionId);
create index IX_F936118A on AssetLink (entryId2, type_, ctCollectionId);

create index IX_E534924E on AssetTag (ctCollectionId);
create index IX_24286918 on AssetTag (groupId, ctCollectionId);
create unique index IX_AA52E757 on AssetTag (groupId, name[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_7A6CD00D on AssetTag (name[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_71579042 on AssetTag (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_E7450E22 on AssetTag (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_A43FBC4 on AssetTag (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);

create index IX_F75DCEEA on AssetVocabulary (companyId, ctCollectionId);
create index IX_6496D38F on AssetVocabulary (companyId, externalReferenceCode[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_49B3687A on AssetVocabulary (ctCollectionId);
create index IX_4E99C46C on AssetVocabulary (groupId, ctCollectionId);
create unique index IX_AE9F73AB on AssetVocabulary (groupId, name[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_B955B36E on AssetVocabulary (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_2F3D2E76 on AssetVocabulary (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_8F88F9F0 on AssetVocabulary (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);

create unique index IX_E7B95510 on BrowserTracker (userId);

create unique index IX_B27A301F on ClassName_ (value[$COLUMN_LENGTH:200$]);

create index IX_38EFE3FD on Company (logoId);
create index IX_12566EC2 on Company (mx[$COLUMN_LENGTH:200$]);
create index IX_8699D9BD on Company (system_);
create unique index IX_EC00543C on Company (webId[$COLUMN_LENGTH:75$]);

create unique index IX_85C63FD7 on CompanyInfo (companyId);

create index IX_B8C28C53 on Contact_ (accountId);
create index IX_791914FA on Contact_ (classNameId, classPK);
create index IX_66D496A3 on Contact_ (companyId);

create unique index IX_717B97E1 on Country (a2[$COLUMN_LENGTH:75$]);
create unique index IX_717B9BA2 on Country (a3[$COLUMN_LENGTH:75$]);
create index IX_25D734CD on Country (active_);
create unique index IX_19DA007B on Country (name[$COLUMN_LENGTH:75$]);

create index IX_33E8A112 on DLFileEntry (companyId, ctCollectionId);
create index IX_5444C427 on DLFileEntry (companyId, fileEntryTypeId);
create index IX_9B56081C on DLFileEntry (custom1ImageId, ctCollectionId);
create index IX_9D2F5B3B on DLFileEntry (custom2ImageId, ctCollectionId);
create index IX_C0A6F645 on DLFileEntry (fileEntryTypeId, ctCollectionId);
create index IX_F951AC2E on DLFileEntry (folderId, name[$COLUMN_LENGTH:255$], ctCollectionId);
create index IX_60830094 on DLFileEntry (groupId, ctCollectionId);
create index IX_BAF654E5 on DLFileEntry (groupId, fileEntryTypeId);
create index IX_95A2D1F1 on DLFileEntry (groupId, folderId, ctCollectionId);
create index IX_D8883586 on DLFileEntry (groupId, folderId, fileEntryTypeId, ctCollectionId);
create unique index IX_A256938C on DLFileEntry (groupId, folderId, fileName[$COLUMN_LENGTH:255$], ctCollectionId);
create unique index IX_F7878970 on DLFileEntry (groupId, folderId, name[$COLUMN_LENGTH:255$], ctCollectionId);
create unique index IX_EAAB273 on DLFileEntry (groupId, folderId, title[$COLUMN_LENGTH:255$], ctCollectionId);
create index IX_3B20ECE on DLFileEntry (groupId, userId, ctCollectionId);
create index IX_87A8DFAB on DLFileEntry (groupId, userId, folderId, ctCollectionId);
create index IX_863591A1 on DLFileEntry (largeImageId, ctCollectionId);
create index IX_72175754 on DLFileEntry (mimeType[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_6EC7490B on DLFileEntry (repositoryId, ctCollectionId);
create index IX_277C31A8 on DLFileEntry (repositoryId, folderId, ctCollectionId);
create index IX_A8521555 on DLFileEntry (smallImageId, ctCollectionId);
create index IX_854E0F17 on DLFileEntry (smallImageId, largeImageId, custom1ImageId, custom2ImageId, ctCollectionId);
create index IX_1F89A446 on DLFileEntry (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_CF17549E on DLFileEntry (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_373340C8 on DLFileEntry (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);

create unique index IX_B9210CAD on DLFileEntryMetadata (DDMStructureId, fileVersionId, ctCollectionId);
create index IX_8D4F58BC on DLFileEntryMetadata (fileEntryId, ctCollectionId);
create index IX_A158EA62 on DLFileEntryMetadata (fileVersionId, ctCollectionId);
create index IX_EABA15 on DLFileEntryMetadata (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_EAA9CA2F on DLFileEntryMetadata (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);

create index IX_C0561BFA on DLFileEntryType (groupId, ctCollectionId);
create unique index IX_402227BD on DLFileEntryType (groupId, fileEntryTypeKey[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_F147FBA0 on DLFileEntryType (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_17A61184 on DLFileEntryType (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_1773A6A2 on DLFileEntryType (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);

create index IX_2E64D9F9 on DLFileEntryTypes_DLFolders (companyId);
create index IX_6E00A2EC on DLFileEntryTypes_DLFolders (folderId);

create index IX_A46E54B6 on DLFileShortcut (companyId, ctCollectionId);
create index IX_80362F9C on DLFileShortcut (companyId, status, ctCollectionId);
create index IX_8A2EF610 on DLFileShortcut (groupId, folderId, active_, ctCollectionId);
create index IX_CFD4D6F6 on DLFileShortcut (groupId, folderId, active_, status, ctCollectionId);
create index IX_869CA195 on DLFileShortcut (groupId, folderId, ctCollectionId);
create index IX_5CAA7254 on DLFileShortcut (toFileEntryId, ctCollectionId);
create index IX_FE055022 on DLFileShortcut (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_21B07A42 on DLFileShortcut (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_DD2033A4 on DLFileShortcut (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);

create index IX_97782D6C on DLFileVersion (companyId, ctCollectionId);
create index IX_808EF252 on DLFileVersion (companyId, status, ctCollectionId);
create index IX_759EF1C5 on DLFileVersion (fileEntryId, ctCollectionId);
create index IX_C97C4DAB on DLFileVersion (fileEntryId, status, ctCollectionId);
create unique index IX_10E504DF on DLFileVersion (fileEntryId, version[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_3A12DA31 on DLFileVersion (groupId, folderId, status, ctCollectionId);
create index IX_DCA2C64B on DLFileVersion (groupId, folderId, title[$COLUMN_LENGTH:255$], version[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_9E97D7BA on DLFileVersion (mimeType[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_16CE5EAC on DLFileVersion (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_48BF1DF8 on DLFileVersion (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_350F5CAE on DLFileVersion (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);

create index IX_67A46FAA on DLFolder (companyId, ctCollectionId);
create index IX_F1EC1690 on DLFolder (companyId, status, ctCollectionId);
create index IX_9D91952C on DLFolder (groupId, ctCollectionId);
create index IX_4B18B17E on DLFolder (groupId, mountPoint, parentFolderId, ctCollectionId);
create index IX_45D93323 on DLFolder (groupId, mountPoint, parentFolderId, hidden_, ctCollectionId);
create index IX_91065109 on DLFolder (groupId, mountPoint, parentFolderId, hidden_, status, ctCollectionId);
create index IX_CF68C0D3 on DLFolder (groupId, parentFolderId, ctCollectionId);
create index IX_7663654 on DLFolder (groupId, parentFolderId, hidden_, status, ctCollectionId);
create unique index IX_C7E346D2 on DLFolder (groupId, parentFolderId, name[$COLUMN_LENGTH:255$], ctCollectionId);
create index IX_4642F2E0 on DLFolder (parentFolderId, name[$COLUMN_LENGTH:255$], ctCollectionId);
create index IX_BB15D373 on DLFolder (repositoryId, ctCollectionId);
create index IX_F344479E on DLFolder (repositoryId, mountPoint, ctCollectionId);
create index IX_E7CD911A on DLFolder (repositoryId, parentFolderId, ctCollectionId);
create index IX_333CBAAE on DLFolder (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_B7722F36 on DLFolder (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_AA08D130 on DLFolder (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);

create index IX_2A2CB130 on EmailAddress (companyId, classNameId, classPK, primary_);
create index IX_7B43CD8 on EmailAddress (userId);
create index IX_F74AB912 on EmailAddress (uuid_[$COLUMN_LENGTH:75$], companyId);

create unique index IX_FEFC8DA7 on ExpandoColumn (tableId, name[$COLUMN_LENGTH:75$]);

create index IX_49EB3118 on ExpandoRow (classPK);
create unique index IX_81EFBFF5 on ExpandoRow (tableId, classPK);

create unique index IX_37562284 on ExpandoTable (companyId, classNameId, name[$COLUMN_LENGTH:75$]);

create index IX_B29FEF17 on ExpandoValue (classNameId, classPK);
create unique index IX_9DDD21E5 on ExpandoValue (columnId, rowId_);
create index IX_9112A7A0 on ExpandoValue (rowId_);
create index IX_1BD3F4C on ExpandoValue (tableId, classPK);
create unique index IX_D27B03E7 on ExpandoValue (tableId, columnId, classPK);
create index IX_B71E92D5 on ExpandoValue (tableId, rowId_);

create index IX_1827A2E5 on ExportImportConfiguration (companyId);
create index IX_38FA468D on ExportImportConfiguration (groupId, status);
create index IX_47CC6234 on ExportImportConfiguration (groupId, type_, status);

create index IX_EB3A63D9 on Group_ (classNameId, classPK, ctCollectionId);
create index IX_BD3CB13A on Group_ (classNameId, groupId, companyId, parentGroupId);
create index IX_8B5402E5 on Group_ (companyId, active_, ctCollectionId);
create unique index IX_504CABF5 on Group_ (companyId, classNameId, classPK, ctCollectionId);
create index IX_2442742A on Group_ (companyId, classNameId, ctCollectionId);
create unique index IX_B894AE04 on Group_ (companyId, classNameId, liveGroupId, groupKey[$COLUMN_LENGTH:150$], ctCollectionId);
create index IX_B7EBDBB2 on Group_ (companyId, classNameId, parentGroupId, ctCollectionId);
create index IX_286EE120 on Group_ (companyId, ctCollectionId);
create unique index IX_9A7D6AD0 on Group_ (companyId, friendlyURL[$COLUMN_LENGTH:255$], ctCollectionId);
create unique index IX_BE219CF4 on Group_ (companyId, groupKey[$COLUMN_LENGTH:150$], ctCollectionId);
create unique index IX_94AE0C4E on Group_ (companyId, liveGroupId, groupKey[$COLUMN_LENGTH:150$], ctCollectionId);
create index IX_A20523FC on Group_ (companyId, parentGroupId, ctCollectionId);
create index IX_121A14F7 on Group_ (companyId, parentGroupId, site, ctCollectionId);
create index IX_162053E9 on Group_ (companyId, parentGroupId, site, inheritContent, ctCollectionId);
create index IX_4108074A on Group_ (companyId, site, active_, ctCollectionId);
create index IX_CFE2671B on Group_ (companyId, site, ctCollectionId);
create index IX_8060F096 on Group_ (liveGroupId, ctCollectionId);
create index IX_5263ACD8 on Group_ (type_, active_, ctCollectionId);
create index IX_21CBD878 on Group_ (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_BFEBCBAC on Group_ (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_7478D97A on Group_ (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);

create index IX_8BFD4548 on Groups_Orgs (companyId);
create index IX_6BBB7682 on Groups_Orgs (organizationId);

create index IX_557D8550 on Groups_Roles (companyId);
create index IX_3103EF3D on Groups_Roles (roleId);

create index IX_676FC818 on Groups_UserGroups (companyId);
create index IX_3B69160F on Groups_UserGroups (userGroupId);

create index IX_9720F6AB on Image (size_, ctCollectionId);

create index IX_31B45343 on Layout (classNameId, classPK, ctCollectionId);
create index IX_4B906FF6 on Layout (companyId, ctCollectionId);
create index IX_8F868C29 on Layout (companyId, layoutPrototypeUuid[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_FD5AF6EE on Layout (ctCollectionId);
create index IX_34D93878 on Layout (groupId, ctCollectionId);
create index IX_12770E8F on Layout (groupId, masterLayoutPlid, ctCollectionId);
create index IX_7BFE8B01 on Layout (groupId, privateLayout, ctCollectionId);
create unique index IX_B556968F on Layout (groupId, privateLayout, friendlyURL[$COLUMN_LENGTH:255$], ctCollectionId);
create unique index IX_CF5120DA on Layout (groupId, privateLayout, layoutId, ctCollectionId);
create index IX_52D89564 on Layout (groupId, privateLayout, parentLayoutId, ctCollectionId);
create index IX_1E4451FD on Layout (groupId, privateLayout, parentLayoutId, hidden_, ctCollectionId);
create index IX_989E917C on Layout (groupId, privateLayout, parentLayoutId, priority, ctCollectionId);
create index IX_18D0C537 on Layout (groupId, privateLayout, sourcePrototypeLayoutUuid[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_A1FC5430 on Layout (groupId, privateLayout, type_[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_94E0E2D9 on Layout (groupId, type_[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_E7B06BDB on Layout (iconImageId, ctCollectionId);
create index IX_11389031 on Layout (layoutPrototypeUuid[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_7F60B703 on Layout (parentPlid, ctCollectionId);
create index IX_C95F601E on Layout (privateLayout, iconImageId, ctCollectionId);
create index IX_ED8D4D2A on Layout (sourcePrototypeLayoutUuid[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_24AA0CE2 on Layout (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_5AA23582 on Layout (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_52D84D95 on Layout (uuid_[$COLUMN_LENGTH:75$], groupId, privateLayout, ctCollectionId);

create index IX_A705FF94 on LayoutBranch (layoutSetBranchId, plid, master);
create unique index IX_FD57097D on LayoutBranch (layoutSetBranchId, plid, name[$COLUMN_LENGTH:75$]);

create index IX_1C55E26 on LayoutFriendlyURL (companyId, ctCollectionId);
create index IX_7ED3F2A8 on LayoutFriendlyURL (groupId, ctCollectionId);
create index IX_6F5128BF on LayoutFriendlyURL (groupId, privateLayout, friendlyURL[$COLUMN_LENGTH:255$], ctCollectionId);
create unique index IX_E73BB186 on LayoutFriendlyURL (groupId, privateLayout, friendlyURL[$COLUMN_LENGTH:255$], languageId[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_EF247709 on LayoutFriendlyURL (plid, ctCollectionId);
create index IX_CB1E7787 on LayoutFriendlyURL (plid, friendlyURL[$COLUMN_LENGTH:255$], ctCollectionId);
create unique index IX_2069E0D0 on LayoutFriendlyURL (plid, languageId[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_9DE5C8B2 on LayoutFriendlyURL (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_C765BBB2 on LayoutFriendlyURL (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_58E59034 on LayoutFriendlyURL (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);

create index IX_557A639F on LayoutPrototype (companyId, active_);
create index IX_63ED2532 on LayoutPrototype (uuid_[$COLUMN_LENGTH:75$], companyId);

create index IX_43E8286A on LayoutRevision (head, plid);
create index IX_E10AC39 on LayoutRevision (layoutSetBranchId, head, plid);
create index IX_9EC9F954 on LayoutRevision (layoutSetBranchId, head, status);
create index IX_38C5DF14 on LayoutRevision (layoutSetBranchId, layoutBranchId, head, plid);
create index IX_13984800 on LayoutRevision (layoutSetBranchId, layoutBranchId, plid);
create index IX_4A84AF43 on LayoutRevision (layoutSetBranchId, parentLayoutRevisionId, plid);
create index IX_70DA9ECB on LayoutRevision (layoutSetBranchId, plid, status);
create index IX_7FFAE700 on LayoutRevision (layoutSetBranchId, status);
create index IX_8EC3D2BC on LayoutRevision (plid, status);
create index IX_421223B1 on LayoutRevision (status);

create index IX_20615181 on LayoutSet (companyId, layoutSetPrototypeUuid[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_5B990A4A on LayoutSet (groupId, ctCollectionId);
create unique index IX_3F2A9AEF on LayoutSet (groupId, privateLayout, ctCollectionId);
create index IX_55443115 on LayoutSet (layoutSetPrototypeUuid[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_A6EE9D37 on LayoutSet (privateLayout, logoId, ctCollectionId);

create index IX_CCF0DA29 on LayoutSetBranch (groupId, privateLayout, master);
create unique index IX_5FF18552 on LayoutSetBranch (groupId, privateLayout, name[$COLUMN_LENGTH:75$]);

create index IX_9178FC71 on LayoutSetPrototype (companyId, active_);
create index IX_D9FFCA84 on LayoutSetPrototype (uuid_[$COLUMN_LENGTH:75$], companyId);

create index IX_77729718 on ListType (name[$COLUMN_LENGTH:75$], type_[$COLUMN_LENGTH:75$]);
create index IX_2932DD37 on ListType (type_[$COLUMN_LENGTH:75$]);

create index IX_C28C72EC on MembershipRequest (groupId, statusId);
create index IX_35AA8FA6 on MembershipRequest (groupId, userId, statusId);
create index IX_66D70879 on MembershipRequest (userId);

create index IX_4A527DD3 on OrgGroupRole (groupId);
create index IX_AB044D1C on OrgGroupRole (roleId);

create index IX_6AF0D434 on OrgLabor (organizationId);

create index IX_2C1E7914 on Organization_ (companyId, ctCollectionId);
create index IX_38F85A25 on Organization_ (companyId, externalReferenceCode[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_F1E40A53 on Organization_ (companyId, name[$COLUMN_LENGTH:100$], ctCollectionId);
create index IX_7A5E9780 on Organization_ (companyId, parentOrganizationId, ctCollectionId);
create index IX_DC36A7BF on Organization_ (companyId, parentOrganizationId, name[$COLUMN_LENGTH:100$], ctCollectionId);
create index IX_3F532604 on Organization_ (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_206D7DA0 on Organization_ (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);

create index IX_2C1142E on PasswordPolicy (companyId, defaultPolicy);
create unique index IX_3FBFA9F4 on PasswordPolicy (companyId, name[$COLUMN_LENGTH:75$]);
create index IX_E4D7EF87 on PasswordPolicy (uuid_[$COLUMN_LENGTH:75$], companyId);

create unique index IX_C3A17327 on PasswordPolicyRel (classNameId, classPK);
create index IX_CD25266E on PasswordPolicyRel (passwordPolicyId);

create index IX_326F75BD on PasswordTracker (userId);

create index IX_812CE07A on Phone (companyId, classNameId, classPK, primary_);
create index IX_F202B9CE on Phone (userId);
create index IX_B271FA88 on Phone (uuid_[$COLUMN_LENGTH:75$], companyId);

create unique index IX_7171B2E8 on PluginSetting (companyId, pluginId[$COLUMN_LENGTH:75$], pluginType[$COLUMN_LENGTH:75$]);

create index IX_D1F795F1 on PortalPreferences (ownerId, ownerType);

create unique index IX_12B5E51D on Portlet (companyId, portletId[$COLUMN_LENGTH:200$]);

create index IX_96BDD537 on PortletItem (groupId, classNameId);
create index IX_D699243F on PortletItem (groupId, name[$COLUMN_LENGTH:75$], portletId[$COLUMN_LENGTH:200$], classNameId);
create index IX_E922D6C0 on PortletItem (groupId, portletId[$COLUMN_LENGTH:200$], classNameId);

create index IX_F0B8A3A0 on PortletPreferences (companyId, ownerId, ownerType, portletId[$COLUMN_LENGTH:200$], ctCollectionId);
create index IX_31DA3CB8 on PortletPreferences (ownerId, ctCollectionId);
create index IX_451D78CC on PortletPreferences (ownerId, ownerType, plid, ctCollectionId);
create unique index IX_CB778855 on PortletPreferences (ownerId, ownerType, plid, portletId[$COLUMN_LENGTH:200$], ctCollectionId);
create index IX_258CCF40 on PortletPreferences (ownerId, ownerType, portletId[$COLUMN_LENGTH:200$], ctCollectionId);
create index IX_D48717FF on PortletPreferences (ownerType, plid, portletId[$COLUMN_LENGTH:200$], ctCollectionId);
create index IX_D6C3E66A on PortletPreferences (ownerType, portletId[$COLUMN_LENGTH:200$], ctCollectionId);
create index IX_C77A74AD on PortletPreferences (plid, ctCollectionId);
create index IX_67E205D4 on PortletPreferences (plid, portletId[$COLUMN_LENGTH:200$], ctCollectionId);
create index IX_8D0717FF on PortletPreferences (portletId[$COLUMN_LENGTH:200$], ctCollectionId);

create index IX_A1A8CB8B on RatingsEntry (classNameId, classPK, score);
create unique index IX_B47E3C11 on RatingsEntry (userId, classNameId, classPK);
create index IX_9F242DF6 on RatingsEntry (uuid_[$COLUMN_LENGTH:75$], companyId);

create unique index IX_A6E99284 on RatingsStats (classNameId, classPK);
create index IX_5EC6007D on RatingsStats (classNameId, createDate);
create index IX_11A5584A on RatingsStats (classNameId, modifiedDate);

create index IX_B91F79BD on RecentLayoutBranch (groupId);
create index IX_351E86E8 on RecentLayoutBranch (layoutBranchId);
create unique index IX_C27D6369 on RecentLayoutBranch (userId, layoutSetBranchId, plid);

create index IX_8D8A2724 on RecentLayoutRevision (groupId);
create index IX_DA0788DA on RecentLayoutRevision (layoutRevisionId);
create unique index IX_4C600BD0 on RecentLayoutRevision (userId, layoutSetBranchId, plid);

create index IX_711995A5 on RecentLayoutSetBranch (groupId);
create index IX_23FF0700 on RecentLayoutSetBranch (layoutSetBranchId);
create unique index IX_4654D204 on RecentLayoutSetBranch (userId, layoutSetId);

create index IX_2D9A426F on Region (active_);
create index IX_11FB3E42 on Region (countryId, active_);
create unique index IX_A2635F5C on Region (countryId, regionCode[$COLUMN_LENGTH:75$]);

create unique index IX_8BD6BCA7 on Release_ (servletContextName[$COLUMN_LENGTH:75$]);

create unique index IX_60C8634C on Repository (groupId, name[$COLUMN_LENGTH:200$], portletId[$COLUMN_LENGTH:200$]);
create index IX_F543EA4 on Repository (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_11641E26 on Repository (uuid_[$COLUMN_LENGTH:75$], groupId);

create unique index IX_9BDCF489 on RepositoryEntry (repositoryId, mappedId[$COLUMN_LENGTH:255$]);
create index IX_D3B9AF62 on RepositoryEntry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_354AA664 on RepositoryEntry (uuid_[$COLUMN_LENGTH:75$], groupId);

create unique index IX_EDB9986E on ResourceAction (name[$COLUMN_LENGTH:255$], actionId[$COLUMN_LENGTH:75$]);

create index IX_9A838EC7 on ResourcePermission (companyId, name[$COLUMN_LENGTH:255$], scope, primKey[$COLUMN_LENGTH:255$], ctCollectionId);
create unique index IX_A9FF4B2C on ResourcePermission (companyId, name[$COLUMN_LENGTH:255$], scope, primKey[$COLUMN_LENGTH:255$], roleId, ctCollectionId);
create index IX_B60B5751 on ResourcePermission (companyId, name[$COLUMN_LENGTH:255$], scope, primKeyId, roleId, viewActionId, ctCollectionId);
create index IX_829B8423 on ResourcePermission (companyId, name[$COLUMN_LENGTH:255$], scope, roleId, ctCollectionId);
create index IX_490017A2 on ResourcePermission (companyId, primKey[$COLUMN_LENGTH:255$], ctCollectionId);
create index IX_2FABAAC8 on ResourcePermission (companyId, scope, primKey[$COLUMN_LENGTH:255$], ctCollectionId);
create index IX_FABE6981 on ResourcePermission (ctCollectionId);
create index IX_6AD73500 on ResourcePermission (name[$COLUMN_LENGTH:255$], ctCollectionId);
create index IX_3078CBE6 on ResourcePermission (roleId, ctCollectionId);
create index IX_F3870DDF on ResourcePermission (scope, ctCollectionId);

create unique index IX_C0F6BCAC on Role_ (companyId, classNameId, classPK, ctCollectionId);
create unique index IX_D303FC25 on Role_ (companyId, classNameId, classPK, type_, ctCollectionId);
create index IX_4EA65517 on Role_ (companyId, ctCollectionId);
create unique index IX_4CC99816 on Role_ (companyId, name[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_7037255A on Role_ (companyId, type_, ctCollectionId);
create index IX_B482E6EC on Role_ (name[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_18A86359 on Role_ (subtype[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_FFA7B144 on Role_ (type_, ctCollectionId);
create index IX_D8DA3062 on Role_ (type_, subtype[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_411F50A1 on Role_ (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_EFF1D323 on Role_ (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);

create unique index IX_4F0315B8 on ServiceComponent (buildNamespace[$COLUMN_LENGTH:75$], buildNumber);

create index IX_F542E9BC on SocialActivity (activitySetId);
create index IX_D0E9029E on SocialActivity (classNameId, classPK, type_);
create index IX_64B1BC66 on SocialActivity (companyId);
create index IX_FB604DC7 on SocialActivity (groupId, userId, classNameId, classPK, type_, receiverUserId);
create unique index IX_8F32DEC9 on SocialActivity (groupId, userId, createDate, classNameId, classPK, type_, receiverUserId);
create index IX_1F00C374 on SocialActivity (mirrorActivityId, classNameId, classPK);
create index IX_121CA3CB on SocialActivity (receiverUserId);
create index IX_3504B8BC on SocialActivity (userId);

create index IX_83E16F2F on SocialActivityAchievement (groupId, firstInGroup);
create index IX_8F6408F0 on SocialActivityAchievement (groupId, name[$COLUMN_LENGTH:75$]);
create index IX_AABC18E9 on SocialActivityAchievement (groupId, userId, firstInGroup);
create unique index IX_D4390CAA on SocialActivityAchievement (groupId, userId, name[$COLUMN_LENGTH:75$]);

create index IX_A4B9A23B on SocialActivityCounter (classNameId, classPK);
create unique index IX_1B7E3B67 on SocialActivityCounter (groupId, classNameId, classPK, name[$COLUMN_LENGTH:75$], ownerType, endPeriod);
create unique index IX_374B35AE on SocialActivityCounter (groupId, classNameId, classPK, name[$COLUMN_LENGTH:75$], ownerType, startPeriod);
create index IX_926CDD04 on SocialActivityCounter (groupId, classNameId, classPK, ownerType);

create index IX_B15863FA on SocialActivityLimit (classNameId, classPK);
create unique index IX_F1C1A617 on SocialActivityLimit (groupId, userId, classNameId, classPK, activityType, activityCounterName[$COLUMN_LENGTH:75$]);
create index IX_6F9EDE9F on SocialActivityLimit (userId);

create index IX_4460FA14 on SocialActivitySet (classNameId, classPK, type_);
create index IX_9BE30DDF on SocialActivitySet (groupId, userId, classNameId, type_);
create index IX_F71071BD on SocialActivitySet (groupId, userId, type_);
create index IX_62AC101A on SocialActivitySet (userId, classNameId, classPK, type_);

create index IX_384788CD on SocialActivitySetting (groupId, activityType);
create index IX_D984AABA on SocialActivitySetting (groupId, classNameId, activityType, name[$COLUMN_LENGTH:75$]);

create index IX_95135D1C on SocialRelation (companyId, type_);
create index IX_C31A64C6 on SocialRelation (type_);
create index IX_4B52BE89 on SocialRelation (userId1, type_);
create unique index IX_12A92145 on SocialRelation (userId1, userId2, type_);
create index IX_3F9C2FA8 on SocialRelation (userId2, type_);
create index IX_5B30F663 on SocialRelation (uuid_[$COLUMN_LENGTH:75$], companyId);

create index IX_D3425487 on SocialRequest (classNameId, classPK, type_, receiverUserId, status);
create index IX_A90FE5A0 on SocialRequest (companyId);
create index IX_D9380CB7 on SocialRequest (receiverUserId, status);
create unique index IX_36A90CA7 on SocialRequest (userId, classNameId, classPK, type_, receiverUserId);
create index IX_CC86A444 on SocialRequest (userId, classNameId, classPK, type_, status);
create index IX_AB5906A8 on SocialRequest (userId, status);
create index IX_8D42897C on SocialRequest (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_4F973EFE on SocialRequest (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_FFCBB747 on SystemEvent (groupId, classNameId, classPK, type_);
create index IX_A19C89FF on SystemEvent (groupId, systemEventSetKey);

create index IX_622C8165 on Team (groupId, ctCollectionId);
create unique index IX_D424D1E4 on Team (groupId, name[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_14857E95 on Team (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_FC1CD5AF on Team (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_1AAF62D7 on Team (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);

create index IX_1E8DFB2E on Ticket (classNameId, classPK, type_);
create index IX_8BACD0AA on Ticket (companyId, classNameId, classPK, type_);
create index IX_B2468446 on Ticket (key_[$COLUMN_LENGTH:75$]);

create index IX_E8AD6A2C on UserGroup (companyId, ctCollectionId);
create index IX_544FAE0D on UserGroup (companyId, externalReferenceCode[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_3F4FC96B on UserGroup (companyId, name[$COLUMN_LENGTH:255$], ctCollectionId);
create index IX_FFCDFCE5 on UserGroup (companyId, parentUserGroupId, ctCollectionId);
create index IX_9F5F49EC on UserGroup (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_C990BAB8 on UserGroup (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);

create index IX_CF59F0C1 on UserGroupGroupRole (groupId, ctCollectionId);
create index IX_61B91326 on UserGroupGroupRole (groupId, roleId, ctCollectionId);
create index IX_92E36EA on UserGroupGroupRole (roleId, ctCollectionId);
create index IX_663FBB6 on UserGroupGroupRole (userGroupId, ctCollectionId);
create index IX_451514B0 on UserGroupGroupRole (userGroupId, groupId, ctCollectionId);
create unique index IX_B384D815 on UserGroupGroupRole (userGroupId, groupId, roleId, ctCollectionId);

create index IX_813D2FD8 on UserGroupRole (groupId, ctCollectionId);
create index IX_AA134B3D on UserGroupRole (groupId, roleId, ctCollectionId);
create index IX_EEB38F3 on UserGroupRole (roleId, ctCollectionId);
create index IX_21D2A7C8 on UserGroupRole (userId, ctCollectionId);
create index IX_AA91DCDE on UserGroupRole (userId, groupId, ctCollectionId);
create unique index IX_F8059243 on UserGroupRole (userId, groupId, roleId, ctCollectionId);

create index IX_2AC5356C on UserGroups_Teams (companyId);
create index IX_7F187E63 on UserGroups_Teams (userGroupId);

create unique index IX_41A32E0D on UserIdMapper (type_[$COLUMN_LENGTH:75$], externalUserId[$COLUMN_LENGTH:75$]);
create unique index IX_D1C44A6E on UserIdMapper (userId, type_[$COLUMN_LENGTH:75$]);

create unique index IX_8B6E3ACE on UserNotificationDelivery (userId, portletId[$COLUMN_LENGTH:200$], classNameId, notificationType, deliveryType);

create index IX_BF29100B on UserNotificationEvent (type_[$COLUMN_LENGTH:200$]);
create index IX_5CE95F03 on UserNotificationEvent (userId, actionRequired, archived);
create index IX_3DBB361A on UserNotificationEvent (userId, archived);
create index IX_9D34232F on UserNotificationEvent (userId, delivered, actionRequired, archived);
create index IX_BD8BD246 on UserNotificationEvent (userId, delivered, archived);
create index IX_C4EFBD45 on UserNotificationEvent (userId, deliveryType, actionRequired, archived);
create index IX_A87A585C on UserNotificationEvent (userId, deliveryType, archived);
create index IX_4BF3A7AD on UserNotificationEvent (userId, deliveryType, delivered, actionRequired, archived);
create index IX_93012C4 on UserNotificationEvent (userId, deliveryType, delivered, archived);
create index IX_7AFE83D7 on UserNotificationEvent (userId, type_[$COLUMN_LENGTH:200$], deliveryType, delivered, archived);
create index IX_A6BAFDFE on UserNotificationEvent (uuid_[$COLUMN_LENGTH:75$], companyId);

create index IX_29BA1CF5 on UserTracker (companyId);
create index IX_46B0AE8E on UserTracker (sessionId[$COLUMN_LENGTH:200$]);
create index IX_E4EFBA8D on UserTracker (userId);

create index IX_14D8BCC0 on UserTrackerPath (userTrackerId);

create index IX_51338B6A on User_ (companyId, createDate, ctCollectionId);
create index IX_A09EEAB5 on User_ (companyId, createDate, modifiedDate, ctCollectionId);
create index IX_53E4FDAC on User_ (companyId, ctCollectionId);
create index IX_DBE0B8AC on User_ (companyId, defaultUser, ctCollectionId);
create index IX_16583D92 on User_ (companyId, defaultUser, status, ctCollectionId);
create unique index IX_6C9F41D8 on User_ (companyId, emailAddress[$COLUMN_LENGTH:254$], ctCollectionId);
create index IX_210A2A8D on User_ (companyId, externalReferenceCode[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_F0BD8F61 on User_ (companyId, facebookId, ctCollectionId);
create index IX_66712F3F on User_ (companyId, googleUserId[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_79724177 on User_ (companyId, modifiedDate, ctCollectionId);
create index IX_952F78E5 on User_ (companyId, openId[$COLUMN_LENGTH:1024$], ctCollectionId);
create unique index IX_EEC1E477 on User_ (companyId, screenName[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_BC478292 on User_ (companyId, status, ctCollectionId);
create unique index IX_8E3173E6 on User_ (companyId, userId, ctCollectionId);
create unique index IX_C15FB5CF on User_ (contactId, ctCollectionId);
create index IX_E1D5EE24 on User_ (emailAddress[$COLUMN_LENGTH:254$], ctCollectionId);
create index IX_64D54302 on User_ (portraitId, ctCollectionId);
create index IX_68931CD4 on User_ (userId, companyId);
create index IX_B5A2C66C on User_ (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_EA9E0E38 on User_ (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);

create index IX_3499B657 on Users_Groups (companyId);
create index IX_F10B6C6B on Users_Groups (userId);

create index IX_5FBB883C on Users_Orgs (companyId);
create index IX_FB646CA6 on Users_Orgs (userId);

create index IX_F987A0DC on Users_Roles (companyId);
create index IX_C1A01806 on Users_Roles (userId);

create index IX_799F8283 on Users_Teams (companyId);
create index IX_A098EFBF on Users_Teams (userId);

create index IX_BB65040C on Users_UserGroups (companyId);
create index IX_66FF2503 on Users_UserGroups (userGroupId);

create index IX_741D01F2 on VirtualHost (companyId, layoutSetId, ctCollectionId);
create index IX_6A3E4238 on VirtualHost (companyId, layoutSetId, defaultVirtualHost, ctCollectionId);
create unique index IX_76A64FBE on VirtualHost (hostname[$COLUMN_LENGTH:200$], ctCollectionId);

create unique index IX_97DFA146 on WebDAVProps (classNameId, classPK);

create index IX_1AA07A6D on Website (companyId, classNameId, classPK, primary_);
create index IX_F75690BB on Website (userId);
create index IX_712BCD35 on Website (uuid_[$COLUMN_LENGTH:75$], companyId);

create index IX_A4DB1F0F on WorkflowDefinitionLink (companyId, workflowDefinitionName[$COLUMN_LENGTH:75$], workflowDefinitionVersion);
create index IX_705B40EE on WorkflowDefinitionLink (groupId, companyId, classNameId, classPK, typePK);

create index IX_415A7007 on WorkflowInstanceLink (groupId, companyId, classNameId, classPK);