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

import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import TranslationManager, {
	TranslationManagerLabel,
	formatIcon,
	formatLabel,
} from '../../../../src/main/resources/META-INF/resources/js/components/translation-manager/TranslationManager.es';

const translatedLanguageIds = {
	ar_SA: 'لقبي',
	de_DE: 'mein titel',
	en_US: 'my title',
	es_ES: 'mi titulo',
	fr_FR: 'mon titre',
	pt_BR: 'meu titulo',
};

const TranslationManagerWrapper = (props) => (
	<TranslationManager
		{...props}
		editingLanguageId={'en_US'}
		translatedLanguageIds={translatedLanguageIds}
	/>
);

const TranslationManagerLabelWrapper = (props) => (
	<TranslationManagerLabel
		{...props}
		translatedLanguageIds={translatedLanguageIds}
	/>
);

describe('TranslationManager', () => {
	afterEach(() => {
		cleanup();
	});

	it('renders TranslationManager passing translated languages', () => {
		render(<TranslationManagerWrapper />);

		expect(document.querySelector('.localizable-dropdown')).toBeTruthy();
		expect(document.querySelector('.localizable-dropdown-ul')).toBeTruthy();

		const button = document.querySelector('button.dropdown-toggle');

		expect(button).toBeTruthy();
		expect(button.getAttribute('symbol')).toEqual('en-US');
		expect(button.querySelector('.btn-section').textContent).toEqual(
			'en-US'
		);
		expect(button.querySelector('svg').classList).toContain(
			'lexicon-icon-en-us'
		);

		const list = document.querySelectorAll('.localizable-dropdown-ul li');

		const labels = [...list].map((li) => ({
			label: li.querySelector('.label-item').textContent,
			languageId: li
				.querySelector('.autofit-section')
				.textContent.replace(/\s/g, ''),
		}));

		expect(list.length).toBe(13);
		expect(labels).toEqual([
			{label: 'translated', languageId: 'ar-SA'},
			{label: 'not-translated', languageId: 'ca-ES'},
			{label: 'translated', languageId: 'de-DE'},
			{label: 'default', languageId: 'en-US'},
			{label: 'translated', languageId: 'es-ES'},
			{label: 'not-translated', languageId: 'fi-FI'},
			{label: 'translated', languageId: 'fr-FR'},
			{label: 'not-translated', languageId: 'hu-HU'},
			{label: 'not-translated', languageId: 'ja-JP'},
			{label: 'not-translated', languageId: 'nl-NL'},
			{label: 'translated', languageId: 'pt-BR'},
			{label: 'not-translated', languageId: 'sv-SE'},
			{label: 'not-translated', languageId: 'zh-CN'},
		]);
	});

	it('change language when click in a dropdown label', () => {
		const onChangeCallback = jest.fn();

		render(
			<TranslationManagerWrapper
				onChangeLanguageId={(id) => onChangeCallback(id)}
			/>
		);

		const list = document.querySelectorAll('.localizable-dropdown-ul li');

		const button = [...list]
			.find(
				(li) =>
					li
						.querySelector('.autofit-section')
						.textContent.replace(/\s/g, '') === 'pt-BR'
			)
			.querySelector('button');

		fireEvent.click(button);

		expect(onChangeCallback.mock.calls.length).toBe(1);
		expect(onChangeCallback.mock.calls[0][0]).toEqual('pt_BR');
	});

	it('renders dropdown menu when clicked', () => {
		render(<TranslationManagerWrapper />);

		const button = document.querySelector('.dropdown-toggle');
		fireEvent.click(button);

		expect(document.body.querySelector('.dropdown-menu')).toBeTruthy();
	});
});

describe('TranslationManagerLabel', () => {
	it('renders TranslationManagerLabel with No Translated label', () => {
		const {getByText} = render(
			<TranslationManagerLabelWrapper languageId={'ca-ES'} />
		);

		expect(document.querySelector('.label.label-warning')).toBeTruthy();
		expect(getByText('not-translated')).toBeTruthy();
	});

	it('renders TranslationManagerLabel with Translated label', () => {
		const {getByText} = render(
			<TranslationManagerLabelWrapper languageId={'pt_BR'} />
		);

		expect(document.querySelector('.label.label-success')).toBeTruthy();
		expect(getByText('translated')).toBeTruthy();
	});

	it('renders TranslationManagerLabel with Default label', () => {
		const {getByText} = render(
			<TranslationManagerLabelWrapper languageId={'en_US'} />
		);

		expect(document.querySelector('.label.label-info')).toBeTruthy();
		expect(getByText('default')).toBeTruthy();
	});
});

describe('TranslationManager utilities', () => {
	it('format string for label', () => {
		expect(formatLabel('en_US')).toEqual('en-US');
		expect(formatLabel('pt_BR')).toEqual('pt-BR');
	});

	it('format string for icon', () => {
		expect(formatIcon('en_US')).toEqual('en-us');
		expect(formatIcon('pt_BR')).toEqual('pt-br');
	});
});
