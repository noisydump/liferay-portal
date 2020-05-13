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

import ClayAutocomplete from '@clayui/autocomplete';
import ClayIcon from '@clayui/icon';
import React, {useCallback, useEffect, useRef, useState} from 'react';

import {
	addClickOutsideListener,
	handleClickOutside,
	removeClickOutsideListener,
} from '../filter/util/filterEvents.es';
import {Autocomplete} from './Autocomplete.es';

const AutocompleteMultiSelect = ({
	items,
	fieldId = 'id',
	fieldName = 'name',
	id = '',
	placeholder = Liferay.Language.get('select-or-type-an-option'),
	onChange,
	selectedItems = [],
}) => {
	const [active, setActive] = useState(false);
	const [currentIndex, setCurrentIndex] = useState(-1);
	const [filteredItems, setFilteredItems] = useState(items);
	const [highlighted, setHighlighted] = useState(false);
	const [search, setSearch] = useState('');

	const wrapperRef = useRef();

	const className = `${
		highlighted ? 'is-active' : ''
	} align-items-start form-control form-control-tag-group multi-select-wrapper`;

	const handleBlur = () => {
		setActive(false);
		setHighlighted(false);
		setCurrentIndex(-1);
		setSearch('');
	};

	const handleChange = useCallback(
		(newSelectedItems) => {
			if (onChange) {
				onChange(newSelectedItems);
			}
		},
		[onChange]
	);

	const handleFocus = () => {
		setHighlighted(true);
		setActive(true);
	};

	const handleKeyDown = useCallback(
		({keyCode}) => {
			const keyArrowDown = 40;
			const keyArrowUp = 38;
			const keyEnter = 13;
			const keyTab = 9;

			const item = filteredItems[currentIndex];

			if (keyCode === keyTab) {
				handleBlur();
			}

			if (keyCode === keyEnter && item) {
				handleSelect(item);
			}

			if (keyCode === keyArrowUp && currentIndex > 0) {
				setCurrentIndex(currentIndex - 1);
			}

			if (
				keyCode === keyArrowDown &&
				currentIndex < filteredItems.length - 1
			) {
				setCurrentIndex(currentIndex + 1);
			}
		},
		[currentIndex, filteredItems, handleSelect]
	);

	const handleRemove = useCallback(
		(id) => {
			const newSelectedItems = selectedItems.filter(
				(item) => id !== item[fieldId]
			);

			handleChange(newSelectedItems);
		},
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[fieldId, selectedItems]
	);

	const handleSelect = useCallback(
		(item) => {
			const newSelectedItems = [...selectedItems, item];

			handleChange(newSelectedItems);
		},
		[handleChange, selectedItems]
	);

	useEffect(() => {
		const term = search.toLowerCase().trim();

		setFilteredItems(
			items
				.filter(
					(item) =>
						!selectedItems.find(
							(selectedItem) =>
								item[fieldId] === selectedItem[fieldId]
						)
				)
				.filter(
					({[fieldName]: name}) =>
						name.toLowerCase().indexOf(term) > -1
				)
		);
	}, [fieldId, fieldName, items, search, selectedItems]);

	useEffect(() => {
		const listener = handleClickOutside((event) => {
			const listenerCallback = handleClickOutside(
				handleBlur,
				document.getElementById(`dropDownList${id}`)
			);

			listenerCallback(event);
		}, wrapperRef.current);

		addClickOutsideListener(listener);

		return () => {
			removeClickOutsideListener(listener);
		};
	}, [id, wrapperRef]);

	return (
		<ClayAutocomplete>
			<div className={className} ref={wrapperRef}>
				<div className="col-11 d-flex flex-wrap p-0">
					{selectedItems.map(
						({[fieldId]: id, [fieldName]: name}, index) => (
							<AutocompleteMultiSelect.Item
								key={index}
								name={name}
								onRemove={() => handleRemove(id)}
							/>
						)
					)}

					<input
						className="form-control-inset"
						data-testid="multiSelectInput"
						onChange={({target}) => setSearch(target.value)}
						onFocus={handleFocus}
						onKeyDown={handleKeyDown}
						placeholder={!selectedItems.length ? placeholder : ''}
						type="text"
					/>
				</div>

				<div
					className="col-1 drop-icon mt-1 text-right"
					onClick={handleFocus}
				>
					<ClayIcon symbol="caret-double" />
				</div>

				<Autocomplete.DropDown
					active={active}
					activeItem={currentIndex}
					id={id}
					items={filteredItems.map((item) => ({
						...item,
						name: item[fieldName],
					}))}
					match={search}
					onSelect={handleSelect}
					setActive={setActive}
					setActiveItem={setCurrentIndex}
				/>
			</div>
		</ClayAutocomplete>
	);
};

const Item = ({key, name, onRemove}) => {
	return (
		<span
			className="label label-dismissible label-secondary"
			data-testid="multiSelectItem"
			key={key}
		>
			<span className="label-item label-item-expand">{name}</span>

			<span className="label-item label-item-after">
				<button
					aria-label="Close"
					className="close"
					data-testid="multiSelectItemRemove"
					onClick={onRemove}
					type="button"
				>
					<ClayIcon symbol="times" />
				</button>
			</span>
		</span>
	);
};

AutocompleteMultiSelect.Item = Item;

export {AutocompleteMultiSelect};
