package com.mcm.database;

public class AppConstant {

	// Database details
	public static String DB_NAME = "MCMMEMBER_DB";
	public final String DB_VERSION = "1";

	/*---------------------------------------TableName-----------------------------------------------------------------*/
	public static String MEMBER_TABLE_NAME = "Member_Table";
	public static String CLIENT_TABLE_NAME = "Client_Table";
	public static final String MEMBER_APP_MENU_TABLE_NAME = "menu_Table";
	public static final String EVENT_TABLE_NAME = "event_Table";
	public static final String NOTIFICATION_TABLE_NAME = "notification_Table";
	public static final String CHECK_REMINDER_TABLE = "check_reminder_table";
	// Column Name of MemberTableInfo
	public static String MCM_MEMBER_MEMEBER_ID = "member_ID";
	public static String MCM_MEMBER_CLIENT_ID = "client_ID";
	public static String MCM_MEMBER_FIRST_NAME = "first_name";
	public static String MCM_MEMBER_LAST_NAME = "sur_name";
	public static String MCM_MEMBER_EMAIL_ID = "email";
	public static String MCM_MEMBER_PASSWORD = "password";

	// Column name of Client Table Info

	public static String MCM_CLIENT_CLIENT_ID = "client_ID";
	public static String MCM_CLIENT_MEMBER_ID = "member_ID";
	public static String MCM_CLIENT_FIRST_NAME = "first_name";
	public static String MCM_CLIENT_LAST_NAME = "last_name";
	public static String MCM_CLIENT_CLIENT_CHURCH = "client_church";
	public static String MCM_CLIENT_CLIENT_EMAIL = "client_email";
	public static String MCM_CLIENT_CLIENT_PASSWORD = "client_password";

	/*---------------------------------------Member App Menu Database Constant---------------------------------------------------*/

	public static final String MEMBER_APP_MENU_CLIENT_ID = "menu_Client_Id";
	public static final String MEMBER_APP_MENU_CLIENT_NAME = "menu_Client_Name";
	public static final String MEMBER_APP_MENU_MEMBER_ID = "menu_Member_Id";
	public static final String MEMBER_APP_MENU_ID = "menu_Id";
	public static final String MEMBER_APP_MENU_NAME = "menu_Name";
	public static final String MEMBER_APP_MENU_DISPLAY_ORDER_ID = "menu_Display_Order_Id";
	public static final String MEMBER_APP_MENU_THEME_IMAGE_LOCAL_PATH = "menu_Theme_Image_Local_Path";

	/*---------------------------------------Events Database Constant---------------------------------------------------*/
	public static final String EVENT_EVENT_ID = "event_Id";
	public static final String EVENT_CLIENT_ID = "event_Client_Id";
	public static final String EVENT_DATE_TIME = "event_Date_Time";
	public static final String EVENT_DISPALY_START_DATE = "event_display_start_date";
	public static final String EVENT_DISPALY_END_DATE = "event_display_end_date";
	public static final String EVENT_TITLE = "event_title";
	public static final String EVENT_SHORT_DESC = "event_short_desc";
	public static final String EVENT_LONG_DESC = "event_long_desc";
	public static final String EVENT_UPCOMING_EVENT = "upcoming_events";
	public static final String EVENT_LOCATION = "EventLocation";
	public static final String EVENT_SET_REMINDER_FLAG = "SetReminder";

	/*---------------------------------------NOTIFICATION Database Constant---------------------------------------------------*/

	public static final String NCTN_PUSH_NOTIFICATION_ID = "pushNotificationId";
	public static final String NCTN_CLIENT_ID = "clientId";
	public static final String NCTN_DATE = "notificationDate";
	public static final String NCTN_TITLE = "notificationTitle";
	public static final String NCTN_DETAILS = "notificationDetails";
	public static final String NCTN_STATUS = "notificationStaus";
	public static final String NCTN_SET_REMINDER = "setReminder";
	public static final String NCTN_RECURRING_REMINDER_DAYS = "recurringReminderDays";
	public static final String NCTN_SURVEY_FLAG = "surveyFlag";
	public static final String NCTN_RECORD_CREATED = "record_Created";
	public static final String NCTN_RECORD_UPDATED = "record_Updated";
	public static final String NCTN_LAST_UPDATED_LOGIN_USER_ID = "lastUpdated_LoginUserID";
	public static final String NCTN_LAST_UPDATED_WINDOW_USER = "lastUpdated_WindowsUser";

	/*---------------------------------------CHECKREMINDER Database Constant---------------------------------------------------*/
	public static String REMINDER_CLIENT_ID = "reminder_client_ID";
	public static String REMINDER_CLIENT_EMAIL = "reminder_client_EMAIL";
	public static String REMINDER_NAME = "reminder_name";
	public static String REMINDER_IS_SET = "is_reminder_set";
	public static String REMINDER_ID = "reminder_id";

	// Field Constant to pass in intent
	public static final String CHECK_TABLE = "check_table";

}
