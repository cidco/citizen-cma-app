package com.cma.pgrssystem.util;

public interface PgrsConstants {

	int SLIDING_MENU_ID_STATUS = 1;
	int SLIDING_MENU_ID_COMPLAINT = 2;
	int SLIDING_MENU_ID_HISTORY = 3;
	int SLIDING_MENU_ID_PROFILE = 4;

	String SLIDING_MENU_LABEL_STATUS = "Complaint Status";
	String SLIDING_MENU_LABEL_COMPLAINT = "Register a Complaint";
	String SLIDING_MENU_LABEL_HISTORY = "History";
	String SLIDING_MENU_LABEL_PROFILE = "My Profile";
	
	String CURRENT_FRAGMENT_TAG = "Current_Fragment_Tag";
	
	String PHONE_COMPLAINTS_CALL = "1913";
	String PHONE_COMPLAINTS_SMS = "9789951111";
	
	String STATUS_RESOLVED = "RESOLVED";
	String STATUS_PENDING = "PENDING";
	String STATUS_CANCELLED = "CANCELLED";
	String STATUS_INPROGRESS = "INPROGRESS";
	String STATUS_NOT_SUBMITTED = "NOT_SUBMITTED";
	
	String EMPTY_TEXT = "";
	
	String INTENT_PARAM_COMPLAINT_NUMBER = "comp_no";
	String INTENT_PARAM_COMPLAINT_NUMBER_LIST = "comp_no_list";

}