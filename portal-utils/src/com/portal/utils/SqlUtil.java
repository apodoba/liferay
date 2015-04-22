package com.portal.utils;

public class SqlUtil {
	
	public static final String FIELD_STATISTIC_ID = "statistic_id";
	public static final String FIELD_USER_ID = "user_id";
	public static final String FIELD_VALUE = "value";
	public static final String FIELD_MONTH = "month";
	public static final String FIELD_YEAR = "year";
	public static final String FIELD_STATISTIC_PRICE = "price";
	public static final String FIELD_STATISTIC_SERVICE = "service_id";
	public static final String FIELD_HISTORY_ID = "history_id";
	public static final String FIELD_HISTORY_DATE = "date";
	public static final String FIELD_HISTORY_OPERATION = "operation";
	
	public static final String FIELD_BALANCE_ID = "balance_id";
	
	public static final String SELECT_ALL_STATISTIC = "SELECT * FROM COUNTER_STATISTIC ";
	public static final String SELECT_ALL_STATISTIC_BY_USER = "SELECT * FROM COUNTER_STATISTIC WHERE USER_ID=? ";
	public static final String SELECT_STATISTIC_ORDER = "ORDER BY MONTH, YEAR DESC ";
	public static final String SELECT_STATISTIC_BY_PERIOD_AND_SERVICE = "SELECT * FROM COUNTER_STATISTIC WHERE USER_ID=? AND MONTH=? AND YEAR=? AND SERVICE_ID=? ";
	
	public static final String INSERT_STATISTIC = "INSERT INTO COUNTER_STATISTIC (VALUE, SERVICE_ID, MONTH, YEAR, USER_ID) VALUES (?, ?, ?, ?, ?)";
	public static final String UPDATE_STATISTIC_VALUE = "UPDATE COUNTER_STATISTIC SET VALUE=? WHERE USER_ID=? AND STATISTIC_ID=? ";
	public static final String UPDATE_STATISTIC_PRICE = "UPDATE COUNTER_STATISTIC SET PRICE=? WHERE SERVICE_ID=? AND MONTH=? AND YEAR=? ";
	
	public static final String INSERT_PAYMENT = "INSERT INTO COUNTER_PAYMENT (USER_ID, MONTH, YEAR, VALUE) VALUES (?, ?, ?, ?) ";	
	
	public static final String SELECT_USER_BALANCE = "SELECT * FROM COUNTER_BALANCE WHERE USER_ID=? ";
	public static final String INSERT_BALANCE = "INSERT INTO COUNTER_BALANCE (USER_ID, VALUE) VALUES (?, ?) ";	
	public static final String UPDATE_BALANCE = "UPDATE COUNTER_BALANCE SET VALUE=? WHERE USER_ID=? ";	
	
	public static final String INSERT_BALANCE_HISTORY = "INSERT INTO COUNTER_BALANCE_HISTORY (USER_ID, VALUE, DATE, OPERATION) VALUES (?, ?, ?, ?) ";
	
	public static final String SELECT_STATISTIC_PRICE = "SELECT SUM(PRICE) FROM COUNTER_STATISTIC WHERE USER_ID=? AND MONTH=? AND YEAR=? GROUP BY user_id ";
	
	public static final String SELECT_ALL_PAYMENT = "SELECT SUM(VALUE) FROM COUNTER_PAYMENT WHERE USER_ID=? AND MONTH=? AND YEAR=? GROUP BY USER_ID, MONTH, YEAR ";
	
	public static final String SELECT_BALANCE_HISTORY = "SELECT * FROM COUNTER_BALANCE_HISTORY WHERE USER_ID=? ";
}
