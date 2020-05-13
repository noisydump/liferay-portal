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

package com.liferay.change.tracking.internal.reference.portal;

import com.liferay.change.tracking.reference.TableReferenceDefinition;
import com.liferay.change.tracking.reference.builder.TableReferenceInfoBuilder;
import com.liferay.portal.kernel.model.ClassNameTable;
import com.liferay.portal.kernel.model.ResourcePermissionTable;
import com.liferay.portal.kernel.model.RoleTable;
import com.liferay.portal.kernel.model.Team;
import com.liferay.portal.kernel.model.TeamTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.TeamPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class TeamTableReferenceDefinition
	implements TableReferenceDefinition<TeamTable> {

	@Override
	public void defineTableReferences(
		TableReferenceInfoBuilder<TeamTable> tableReferenceInfoBuilder) {

		tableReferenceInfoBuilder.groupedModel(
			TeamTable.INSTANCE
		).nonreferenceColumns(
			TeamTable.INSTANCE.uuid, TeamTable.INSTANCE.name,
			TeamTable.INSTANCE.description, TeamTable.INSTANCE.lastPublishDate
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				ResourcePermissionTable.INSTANCE
			).innerJoinON(
				TeamTable.INSTANCE,
				TeamTable.INSTANCE.companyId.eq(
					ResourcePermissionTable.INSTANCE.companyId
				).and(
					ResourcePermissionTable.INSTANCE.name.eq(
						Team.class.getName())
				).and(
					ResourcePermissionTable.INSTANCE.primKeyId.eq(
						TeamTable.INSTANCE.teamId)
				)
			)
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				RoleTable.INSTANCE
			).innerJoinON(
				TeamTable.INSTANCE,
				TeamTable.INSTANCE.companyId.eq(
					RoleTable.INSTANCE.companyId
				).and(
					TeamTable.INSTANCE.teamId.eq(RoleTable.INSTANCE.classPK)
				)
			).innerJoinON(
				ClassNameTable.INSTANCE,
				ClassNameTable.INSTANCE.classNameId.eq(
					RoleTable.INSTANCE.classNameId
				).and(
					ClassNameTable.INSTANCE.value.eq(Team.class.getName())
				)
			)
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _teamPersistence;
	}

	@Override
	public TeamTable getTable() {
		return TeamTable.INSTANCE;
	}

	@Reference
	private TeamPersistence _teamPersistence;

}