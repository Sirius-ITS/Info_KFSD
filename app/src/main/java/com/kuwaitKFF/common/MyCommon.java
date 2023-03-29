package com.kuwaitKFF.common;

import java.io.IOException;
import java.io.StringReader;
import java.util.Locale;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.widget.EditText;

import com.kuwaitKFF.login.LoginResponseBean;

@SuppressLint("SimpleDateFormat")
public class MyCommon {

//	public static final String WS_BASE_URL="http://62.215.204.224:7001/KFSDMobRS/resources/restws/";


    //	TEST SERVER
//    public static final String WS_BASE_URL = "http://192.168.164.66:7003/KFSDMobRS/resources/restws/";
//    public static final String WS_BASE_URL_NEW = "http://192.168.164.66:7003/KFF/resources/rest/";
//    public static final String WS_BASE_URL_LEAVE_REQUEST = "http://192.168.164.66:7003/KFF/resources/leaveRequest/";
//    public static final String WS_BASE_URL_PERMISSION_REQUEST = "http://192.168.164.66:7003/KFF/resources/permissionRequest/";

//	PRODUCTION SERVER
	public static final String WS_BASE_URL="https://mobile.kff.gov.kw/KFSDMobRS/resources/restws/";
	public static final String WS_BASE_URL_NEW = "https://mobile.kff.gov.kw/KFF/resources/rest/";
	public static final String WS_BASE_URL_LEAVE_REQUEST = "https://mobile.kff.gov.kw/KFF/resources/leaveRequest/";
    public static final String WS_BASE_URL_PERMISSION_REQUEST = "https://mobile.kff.gov.kw/KFF/resources/permissionRequest/";

    public static final String LANGUAGE_ENG = "en_US";
    public static final String LANGUAGE_AR = "ar";
    public static final String DEFAULT_STRING = "";

    public static final String VALUE_ENG = "EN";
    public static final String VALUE_AR = "AR";
    public static boolean refresh = true;

    public static LoginResponseBean sLoginResponseBean;

    public static final String WS_METHOD_TASK = "except";
    public static final String WS_METHOD_PERMISSION = "permReq";
    public static final String WS_METHOD_PERMISSION_INQUIRY = "permissions";
    public static final String WS_METHOD_LEAVE_INQUIRY = "leaves";
    public static final String WS_METHOD_ATTENDANCE = "attendanceReq";
    public static final String WS_METHOD_VACATION = "totalLeaves";
    public static final String WS_METHOD_VACATION_TYPES = "vacationTypes";
    public static final String WS_METHOD_VACATION_BALANCE = "leaveBalance";
    public static final String WS_METHOD_SICK_VACATION_BALANCE = "sickLeave";
    public static final String WS_METHOD_MONTHLY_REPORT_INQUIRY = "monthlyDetail";
    public static final String WS_METHOD_DECISION = "decisionReq";
    public static final String WS_METHOD_CIRCULAR = "circularRequest";
    public static final String WS_METHOD_CHANGE_PASSWORD = "changePassword";
    public static final String WS_METHOD_EMPLIST = "mgrStaff";
    public static final String WS_METHOD_CLEARANCE = "valueClearance";
    public static final String WS_METHOD_SECTOR = "requestSector";
    public static final String WwebSiteURLS_METHOD_CORRESPONDENCE_COUNTER = "corresCount";
    public static final String WS_METHOD_LOGIN = "Login";
    public static final String WS_METHOD_CREDIT_SECTOR = "sectorOrg";
    public static final String WS_METHOD_REMARK_SECTOR = "requestSectorNote";
    public static final String WS_METHOD_MAIL = "valueExternalMail";
    public static final String WS_METHOD_WEBSITE = "webSiteURL";
    public static final String WS_METHOD_CORRESPONDENCE_COUNTER = "corresCount";
    public static final String WS_METHOD_SOCIAL_MEDIA = "socialMediaReq";
    public static final String WS_METHOD_PHONE_EMAILS = "phoneEmail";
    public static final String WS_METHOD_WHO_WE_ARE = "whoWeAre";
    public static final String WS_METHOD_NEWS = "newsReq";
    public static final String WS_METHOD_ADVERTISEMENT = "advertList";
    public static final String WS_METHOD_PERMISSION_TYPE = "permReqType";
    public static final String WS_METHOD_LEAVE_REQUEST_TYPE = "leaveReqType";
    public static final String WS_METHOD_REQUEST_TYPE = "reqType";
    public static final String WS_METHOD_LEAVE_TYPE = "leaveType";
    public static final String WS_METHOD_EMP_CIVIL_ID = "empCivilId";
    public static final String WS_METHOD_YEAR = "year";
    public static final String WS_METHOD_MONTH = "month";
    public static final String WS_METHOD_FROM_DATE = "fromDate";
    public static final String WS_METHOD_TO_DATE = "toDate";
    public static final String WS_METHOD_CONTACT_TYPE = "contactUsType";
    public static final String WS_METHOD_ADD_CONTACT_TYPE = "addContactType";
    public static final String WS_METHOD_PERMISSION_INSERT = "insertPermission";
    public static final String WS_METHOD_LEAVE_INSERT = "insertLeaves";
    public static final String WS_METHOD_LOCATION_LIST = "locationList";
    public static final String WS_METHOD_LOCATION_POINT = "locationListPoint";
    public static final String WS_METHOD_EMP_ENTITY = "empEntity";
    public static final String WS_METHOD_CORRES_ENTITY = "empCorresEntity";
    public static final String WS_METHOD_CMS_DOC = "cmsDocument";
    public static final String WS_METHOD_CMS_REL = "cmsRelated";
    public static final String WS_METHOD_CMS_REL_CORRES = "empRelCorres";
    public static final String WS_METHOD_MOCI = "Moci";
    public static final String WS_METHOD_PAI = "Pai";
    public static final String WS_METHOD_MOCI_ERROR = "MobileAppError";

    public static final String WS_PARA_START_DATE = "startDate";
    public static final String WS_PARA_END_DATE = "endDate";
    public static final String WS_PARA_CIVILID = "civilId";
    public static final String WS_PARA_ENTITYID = "entityId";
    public static final String WS_PARA_LANG = "lang";
    public static final String WS_PARA_New_Pass = "newPwd";
    public static final String WS_PARA_Old_Pass = "oldPwd";
    public static final String WS_PARA_EMPID = "empId";
    public static final String WS_PARA_SERIALID = "serialNo";
    public static final String WS_PARA_FILENO = "fileNo";
    public static final String WS_PARA_UserId = "userCd";
    public static final String WS_PARA_Pass = "pwd";
    public static final String WS_PARA_SERIAL = "serialNo";
    public static final String WS_PARA_REL_SERIAL = "relSerial";

    public static final String WS_PARA_EMAIL = "appMail";
    public static final String WS_PARA_REP_CIVIL_NO = "delegId";
    public static final String WS_PARA_REP_NAME = "delegName";
    public static final String WS_PARA_CELL_NO = "cellNo";
    public static final String WS_PARA_TRX_NO = "appNo";
    public static final String WS_PARA_ULANG = "uLang";

    public static void setPortraitOrientation(Activity a) {
        a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }


    public static String getStringFromView(EditText editText) {
        return editText.getText().toString().trim();
    }

    public static String getJsonFromWebServiceResponse(String webServiceResponse) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(new StringReader(webServiceResponse));
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.TEXT) {
                    return xpp.getText();
                }
                eventType = xpp.next();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return "";
    }


    public static void saveSelectedLanguage(Context context, String language) {
        Resources res = context.getResources();

        // Change locale settings in the application.
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.locale = new Locale(language);
        res.updateConfiguration(conf, dm);
    }
}	
