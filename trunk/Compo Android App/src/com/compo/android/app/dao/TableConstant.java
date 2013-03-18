package com.compo.android.app.dao;

import android.provider.BaseColumns;

public class TableConstant {

	private TableConstant() {
	}

	public static class PackTable implements BaseColumns {
		public static final String TABLE_NAME = "pack";
		public static final String COLUMN_NAME = "name";
		public static final String COLUMN_DESCRIPTION = "description";
		public static final String COLUMN_LOCK = "lock";
		public static final String COLUMN_SCORE_LIMIT = "score_limit";
		public static final String COLUMN_CREDIT_LIMIT = "credit_limit";
		public static final String COLUMN_ORDER_NUMBER = "order_number";
	}
}
