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

package com.liferay.portal.search.internal.facet;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.BaseFacet;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.search.generic.BooleanClauseImpl;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.facet.Facet;

/**
 * @author André de Oliveira
 */
public class FacetImpl extends BaseFacet implements Facet {

	public FacetImpl(String fieldName, SearchContext searchContext) {
		super(searchContext);

		setFieldName(fieldName);
	}

	@Override
	public void select(String... selections) {
		_selections = selections;
	}

	@Override
	public void setAggregationName(String aggregationName) {
		FacetConfiguration facetConfiguration = getFacetConfiguration();

		JSONObject dataJSONObject = facetConfiguration.getData();

		dataJSONObject.put("aggregationName", aggregationName);
	}

	@Override
	protected BooleanClause<Filter> doGetFacetFilterBooleanClause() {
		if (ArrayUtil.isEmpty(_selections)) {
			return null;
		}

		TermsFilter termsFilter = new TermsFilter(getFieldName());

		termsFilter.addValues(_selections);

		return new BooleanClauseImpl<>(termsFilter, BooleanClauseOccur.MUST);
	}

	private String[] _selections;

}