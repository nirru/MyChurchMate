package com.mcm.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mcm.SplashActivity;

public class InsertTable {

	SQLiteDatabase database;

	public InsertTable(SQLiteDatabase database) {
		this.database = database;
	}

	public void addRowforMemberTable(String memberID, String clientID,
			String firstName, String surName, String emailIDString,
			String passWordString) {
		Log.e("NAME", "" + memberID + "  " + clientID + " " + firstName + " "
				+ surName + " " + emailIDString + " " + passWordString);

		ContentValues contentValue = new ContentValues();

		contentValue.put(AppConstant.MCM_MEMBER_MEMEBER_ID, memberID);
		contentValue.put(AppConstant.MCM_MEMBER_CLIENT_ID, clientID);
		contentValue.put(AppConstant.MCM_MEMBER_FIRST_NAME, firstName);
		contentValue.put(AppConstant.MCM_MEMBER_LAST_NAME, surName);
		contentValue.put(AppConstant.MCM_MEMBER_EMAIL_ID, emailIDString);
		contentValue.put(AppConstant.MCM_MEMBER_PASSWORD, passWordString);
		// Log.e("Cotent value", "" + contentValue);

		try {
			SplashActivity.databaseHelper.insertInToTable(database,
					AppConstant.MEMBER_TABLE_NAME, contentValue);
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addRowforClientTable(int clientID, int memberID,
			String firstName, String lastName, String churhList, String email,
			String password) {
		// Log.e("clientID", "" + clientID + "  " + "CHURCHLIST" + "  " +
		// churhList);
		ContentValues contentValue = new ContentValues();
		contentValue.put(AppConstant.MCM_CLIENT_CLIENT_ID, "" + clientID);
		contentValue.put(AppConstant.MCM_CLIENT_MEMBER_ID, "" + memberID);
		contentValue.put(AppConstant.MCM_CLIENT_FIRST_NAME, "" + firstName);
		contentValue.put(AppConstant.MCM_CLIENT_LAST_NAME, "" + lastName);
		contentValue.put(AppConstant.MCM_CLIENT_CLIENT_CHURCH, churhList);
		contentValue.put(AppConstant.MCM_CLIENT_CLIENT_EMAIL, "" + email);
		contentValue.put(AppConstant.MCM_CLIENT_CLIENT_PASSWORD, "" + password);
		// Log.e("Cotent value", "" + contentValue);
		try {
			SplashActivity.databaseHelper.insertInToTable(database,
					AppConstant.CLIENT_TABLE_NAME, contentValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addRowforChurchMenuTable(int clientID, int memberID,
			int menuAppId, String church_Menu_Client_Name, String appMenuName,
			int display_Order_Id, String theme_Image_Url_Path) {
		Log.e("clientID", "" + clientID);
		ContentValues contentValue = new ContentValues();
		contentValue.put(AppConstant.MEMBER_APP_MENU_CLIENT_ID, "" + clientID);
		contentValue.put(AppConstant.MEMBER_APP_MENU_MEMBER_ID, "" + memberID);
		contentValue.put(AppConstant.MEMBER_APP_MENU_ID, "" + menuAppId);
		contentValue.put(AppConstant.MEMBER_APP_MENU_CLIENT_NAME, ""
				+ church_Menu_Client_Name);
		contentValue.put(AppConstant.MEMBER_APP_MENU_NAME, appMenuName);
		contentValue.put(AppConstant.MEMBER_APP_MENU_DISPLAY_ORDER_ID, ""
				+ display_Order_Id);
		contentValue.put(AppConstant.MEMBER_APP_MENU_THEME_IMAGE_LOCAL_PATH, ""
				+ theme_Image_Url_Path);
		// Log.e("Cotent value", "" + contentValue);
		try {
			SplashActivity.databaseHelper.insertInToTable(database,
					AppConstant.MEMBER_APP_MENU_TABLE_NAME, contentValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addRowforEventTable(int eventID, int clientId,
			String EventDateTime, String EventDisplayStartDate,
			String EventDisplayEndDate, String EventTitle,
			String EventShortDesc, String EventLongDesc,
			boolean event_upcoming, String location, boolean reminder_Flag) {
		// Log.e("clientID", "" + clientID + "  " + "CHURCHLIST" + "  " +
		// churhList);
		ContentValues contentValue = new ContentValues();
		contentValue.put(AppConstant.EVENT_EVENT_ID, "" + eventID);
		contentValue.put(AppConstant.EVENT_CLIENT_ID, "" + clientId);
		contentValue.put(AppConstant.EVENT_DATE_TIME, "" + EventDateTime);
		contentValue.put(AppConstant.EVENT_DISPALY_START_DATE, ""
				+ EventDisplayStartDate);
		contentValue.put(AppConstant.EVENT_DISPALY_END_DATE,
				EventDisplayEndDate);
		contentValue.put(AppConstant.EVENT_TITLE, "" + EventTitle);
		contentValue.put(AppConstant.EVENT_SHORT_DESC, "" + EventShortDesc);
		contentValue.put(AppConstant.EVENT_LONG_DESC, "" + EventLongDesc);
		contentValue.put(AppConstant.EVENT_UPCOMING_EVENT, "" + event_upcoming);
		contentValue.put(AppConstant.EVENT_LOCATION, "" + location);
		contentValue.put(AppConstant.EVENT_SET_REMINDER_FLAG, ""
				+ reminder_Flag);
		// Log.e("Cotent value", "" + contentValue);
		try {
			SplashActivity.databaseHelper.insertInToTable(database,
					AppConstant.EVENT_TABLE_NAME, contentValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateRowforEventTable(int eventID, int clientId,
			String EventDateTime, String EventDisplayStartDate,
			String EventDisplayEndDate, String EventTitle,
			String EventShortDesc, String EventLongDesc, boolean event_upcoming) {
		// Log.e("clientID", "" + clientID + "  " + "CHURCHLIST" + "  " +
		// churhList);
		ContentValues contentValue = new ContentValues();
		contentValue.put(AppConstant.EVENT_EVENT_ID, "" + eventID);
		contentValue.put(AppConstant.EVENT_CLIENT_ID, "" + clientId);
		contentValue.put(AppConstant.EVENT_DATE_TIME, "" + EventDateTime);
		contentValue.put(AppConstant.EVENT_DISPALY_START_DATE, ""
				+ EventDisplayStartDate);
		contentValue.put(AppConstant.EVENT_DISPALY_END_DATE,
				EventDisplayEndDate);
		contentValue.put(AppConstant.EVENT_TITLE, "" + "KRISHNA");
		contentValue.put(AppConstant.EVENT_SHORT_DESC, "" + EventShortDesc);
		contentValue.put(AppConstant.EVENT_LONG_DESC, "" + EventLongDesc);
		contentValue.put(AppConstant.EVENT_UPCOMING_EVENT, "" + event_upcoming);
		// Log.e("Cotent value", "" + contentValue);
		try {
			String whereClause = AppConstant.EVENT_CLIENT_ID + " ='" + clientId
					+ "'";
			SplashActivity.databaseHelper.updateTableItem(database,
					AppConstant.EVENT_TABLE_NAME, contentValue, whereClause);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update_memberTable(String memberID, String clientID,
			String firstName, String surName, String emailIDString,
			String passWordString) {

		Log.e("TAG MEMBER CLIENT ID", " " + clientID);
		ContentValues contentValue = new ContentValues();
		contentValue.put(AppConstant.MCM_MEMBER_MEMEBER_ID, memberID);
		contentValue.put(AppConstant.MCM_MEMBER_CLIENT_ID, clientID);
		contentValue.put(AppConstant.MCM_MEMBER_FIRST_NAME, firstName);
		contentValue.put(AppConstant.MCM_MEMBER_LAST_NAME, surName);
		contentValue.put(AppConstant.MCM_MEMBER_EMAIL_ID, emailIDString);
		contentValue.put(AppConstant.MCM_MEMBER_PASSWORD, passWordString);
		try {
			// String whereClause = AppConstant.MCM_MEMBER_PASSWORD + "= " +
			// passWordString;
			SplashActivity.databaseHelper.updateTableItem(database,
					AppConstant.MEMBER_TABLE_NAME, contentValue, null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public void insertValueInRemindertable(int clientID,
			String event_name, String clientEmail,long reminderID, boolean isSet) {

		Log.e("TAG MEMBER CLIENT ID", " " + clientID);
		ContentValues contentValue = new ContentValues();
		contentValue.put(AppConstant.REMINDER_CLIENT_ID, clientID);
		contentValue.put(AppConstant.REMINDER_NAME, event_name);
		contentValue.put(AppConstant.REMINDER_CLIENT_EMAIL, clientEmail);
		contentValue.put(AppConstant.REMINDER_ID,  reminderID);
		contentValue.put(AppConstant.REMINDER_IS_SET,  isSet);
		try {
			// String whereClause = AppConstant.MCM_MEMBER_PASSWORD + "= " +
			// passWordString;
			SplashActivity.databaseHelper.insertInToTable(database,
					AppConstant.CHECK_REMINDER_TABLE, contentValue);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public void updateClientTable(int clientID, int memberID, String firstName,
			String lastName, String churhList, String email, String password) {
		Log.e("TAG CLIENT clientID", "" + clientID);
		ContentValues contentValue = new ContentValues();
		contentValue.put(AppConstant.MCM_CLIENT_CLIENT_ID, "" + clientID);
		contentValue.put(AppConstant.MCM_CLIENT_MEMBER_ID, "" + memberID);
		contentValue.put(AppConstant.MCM_CLIENT_FIRST_NAME, "" + firstName);
		contentValue.put(AppConstant.MCM_CLIENT_LAST_NAME, "" + lastName);
		contentValue.put(AppConstant.MCM_CLIENT_CLIENT_CHURCH, churhList);
		contentValue.put(AppConstant.MCM_CLIENT_CLIENT_EMAIL, "" + email);
		contentValue.put(AppConstant.MCM_CLIENT_CLIENT_PASSWORD, "" + password);
		// Log.e("Cotent value", "" + contentValue);
		try {
			String whereClause = AppConstant.MCM_CLIENT_CLIENT_PASSWORD + "= "
					+ password;
			SplashActivity.databaseHelper.updateTableItem(database,
					AppConstant.CLIENT_TABLE_NAME, contentValue, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addRowforNotificationTable(int push_id, int client_id,
			String notif_date, String notif_title, String notif_details,
			String notif_status, String notif_set_reminder,
			String notif_recuiring_reminder_days, String notif_survey_flag) {
		// Log.e("clientID", "" + clientID + "  " + "CHURCHLIST" + "  " +
		// churhList);
		ContentValues contentValue = new ContentValues();
		contentValue.put(AppConstant.NCTN_PUSH_NOTIFICATION_ID, "" + push_id);
		contentValue.put(AppConstant.NCTN_CLIENT_ID, "" + client_id);
		contentValue.put(AppConstant.NCTN_DATE, "" + notif_date);
		contentValue.put(AppConstant.NCTN_TITLE, "" + notif_title);
		contentValue.put(AppConstant.NCTN_DETAILS, notif_details);
		contentValue.put(AppConstant.NCTN_STATUS, "" + notif_status);
		contentValue
				.put(AppConstant.NCTN_SET_REMINDER, "" + notif_set_reminder);
		contentValue.put(AppConstant.NCTN_RECURRING_REMINDER_DAYS, ""
				+ notif_recuiring_reminder_days);
		contentValue.put(AppConstant.NCTN_SURVEY_FLAG, "" + notif_survey_flag);
		// Log.e("Cotent value", "" + contentValue);
		try {
			SplashActivity.databaseHelper.insertInToTable(database,
					AppConstant.NOTIFICATION_TABLE_NAME, contentValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteAllTable() {
		database.delete(AppConstant.MEMBER_TABLE_NAME, null, null);
		database.delete(AppConstant.CLIENT_TABLE_NAME, null, null);
		database.delete(AppConstant.MEMBER_APP_MENU_TABLE_NAME, null, null);
		database.delete(AppConstant.EVENT_TABLE_NAME, null, null);
	}

	public void deleteTable() {
		database.delete(AppConstant.MEMBER_TABLE_NAME, null, null);
		database.delete(AppConstant.CLIENT_TABLE_NAME, null, null);
	}

	public void deleteEventsTableByClientId() {
		database.delete(AppConstant.EVENT_TABLE_NAME, null, null);
	}
}
