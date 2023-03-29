package com.kuwaitKFF;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.kuwaitKFF.databinding.FragmentApproveDeclinePermissionsBindingArImpl;
import com.kuwaitKFF.databinding.FragmentApproveDeclinePermissionsBindingImpl;
import com.kuwaitKFF.databinding.FragmentApproveDeclineVacationsBindingArImpl;
import com.kuwaitKFF.databinding.FragmentApproveDeclineVacationsBindingImpl;
import com.kuwaitKFF.databinding.FragmentCancelOrBreakVacationBindingArImpl;
import com.kuwaitKFF.databinding.FragmentCancelOrBreakVacationBindingImpl;
import com.kuwaitKFF.databinding.FragmentMonthlyReportInquiryBindingArImpl;
import com.kuwaitKFF.databinding.FragmentMonthlyReportInquiryBindingImpl;
import com.kuwaitKFF.databinding.FragmentSickVacationsBalanceBindingArImpl;
import com.kuwaitKFF.databinding.FragmentSickVacationsBalanceBindingImpl;
import com.kuwaitKFF.databinding.FragmentSubmenuBindingArImpl;
import com.kuwaitKFF.databinding.FragmentSubmenuBindingImpl;
import com.kuwaitKFF.databinding.FragmentVacationsBalanceBindingArImpl;
import com.kuwaitKFF.databinding.FragmentVacationsBalanceBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_FRAGMENTAPPROVEDECLINEPERMISSIONS = 1;

  private static final int LAYOUT_FRAGMENTAPPROVEDECLINEVACATIONS = 2;

  private static final int LAYOUT_FRAGMENTCANCELORBREAKVACATION = 3;

  private static final int LAYOUT_FRAGMENTMONTHLYREPORTINQUIRY = 4;

  private static final int LAYOUT_FRAGMENTSICKVACATIONSBALANCE = 5;

  private static final int LAYOUT_FRAGMENTSUBMENU = 6;

  private static final int LAYOUT_FRAGMENTVACATIONSBALANCE = 7;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(7);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.kuwaitKFF.R.layout.fragment_approve_decline_permissions, LAYOUT_FRAGMENTAPPROVEDECLINEPERMISSIONS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.kuwaitKFF.R.layout.fragment_approve_decline_vacations, LAYOUT_FRAGMENTAPPROVEDECLINEVACATIONS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.kuwaitKFF.R.layout.fragment_cancel_or_break_vacation, LAYOUT_FRAGMENTCANCELORBREAKVACATION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.kuwaitKFF.R.layout.fragment_monthly_report_inquiry, LAYOUT_FRAGMENTMONTHLYREPORTINQUIRY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.kuwaitKFF.R.layout.fragment_sick_vacations_balance, LAYOUT_FRAGMENTSICKVACATIONSBALANCE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.kuwaitKFF.R.layout.fragment_submenu, LAYOUT_FRAGMENTSUBMENU);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.kuwaitKFF.R.layout.fragment_vacations_balance, LAYOUT_FRAGMENTVACATIONSBALANCE);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_FRAGMENTAPPROVEDECLINEPERMISSIONS: {
          if ("layout-ar/fragment_approve_decline_permissions_0".equals(tag)) {
            return new FragmentApproveDeclinePermissionsBindingArImpl(component, view);
          }
          if ("layout/fragment_approve_decline_permissions_0".equals(tag)) {
            return new FragmentApproveDeclinePermissionsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_approve_decline_permissions is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTAPPROVEDECLINEVACATIONS: {
          if ("layout-ar/fragment_approve_decline_vacations_0".equals(tag)) {
            return new FragmentApproveDeclineVacationsBindingArImpl(component, view);
          }
          if ("layout/fragment_approve_decline_vacations_0".equals(tag)) {
            return new FragmentApproveDeclineVacationsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_approve_decline_vacations is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTCANCELORBREAKVACATION: {
          if ("layout/fragment_cancel_or_break_vacation_0".equals(tag)) {
            return new FragmentCancelOrBreakVacationBindingImpl(component, view);
          }
          if ("layout-ar/fragment_cancel_or_break_vacation_0".equals(tag)) {
            return new FragmentCancelOrBreakVacationBindingArImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_cancel_or_break_vacation is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTMONTHLYREPORTINQUIRY: {
          if ("layout/fragment_monthly_report_inquiry_0".equals(tag)) {
            return new FragmentMonthlyReportInquiryBindingImpl(component, view);
          }
          if ("layout-ar/fragment_monthly_report_inquiry_0".equals(tag)) {
            return new FragmentMonthlyReportInquiryBindingArImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_monthly_report_inquiry is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTSICKVACATIONSBALANCE: {
          if ("layout-ar/fragment_sick_vacations_balance_0".equals(tag)) {
            return new FragmentSickVacationsBalanceBindingArImpl(component, view);
          }
          if ("layout/fragment_sick_vacations_balance_0".equals(tag)) {
            return new FragmentSickVacationsBalanceBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_sick_vacations_balance is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTSUBMENU: {
          if ("layout/fragment_submenu_0".equals(tag)) {
            return new FragmentSubmenuBindingImpl(component, view);
          }
          if ("layout-ar/fragment_submenu_0".equals(tag)) {
            return new FragmentSubmenuBindingArImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_submenu is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTVACATIONSBALANCE: {
          if ("layout/fragment_vacations_balance_0".equals(tag)) {
            return new FragmentVacationsBalanceBindingImpl(component, view);
          }
          if ("layout-ar/fragment_vacations_balance_0".equals(tag)) {
            return new FragmentVacationsBalanceBindingArImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_vacations_balance is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(1);

    static {
      sKeys.put(0, "_all");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(14);

    static {
      sKeys.put("layout-ar/fragment_approve_decline_permissions_0", com.kuwaitKFF.R.layout.fragment_approve_decline_permissions);
      sKeys.put("layout/fragment_approve_decline_permissions_0", com.kuwaitKFF.R.layout.fragment_approve_decline_permissions);
      sKeys.put("layout-ar/fragment_approve_decline_vacations_0", com.kuwaitKFF.R.layout.fragment_approve_decline_vacations);
      sKeys.put("layout/fragment_approve_decline_vacations_0", com.kuwaitKFF.R.layout.fragment_approve_decline_vacations);
      sKeys.put("layout/fragment_cancel_or_break_vacation_0", com.kuwaitKFF.R.layout.fragment_cancel_or_break_vacation);
      sKeys.put("layout-ar/fragment_cancel_or_break_vacation_0", com.kuwaitKFF.R.layout.fragment_cancel_or_break_vacation);
      sKeys.put("layout/fragment_monthly_report_inquiry_0", com.kuwaitKFF.R.layout.fragment_monthly_report_inquiry);
      sKeys.put("layout-ar/fragment_monthly_report_inquiry_0", com.kuwaitKFF.R.layout.fragment_monthly_report_inquiry);
      sKeys.put("layout-ar/fragment_sick_vacations_balance_0", com.kuwaitKFF.R.layout.fragment_sick_vacations_balance);
      sKeys.put("layout/fragment_sick_vacations_balance_0", com.kuwaitKFF.R.layout.fragment_sick_vacations_balance);
      sKeys.put("layout/fragment_submenu_0", com.kuwaitKFF.R.layout.fragment_submenu);
      sKeys.put("layout-ar/fragment_submenu_0", com.kuwaitKFF.R.layout.fragment_submenu);
      sKeys.put("layout/fragment_vacations_balance_0", com.kuwaitKFF.R.layout.fragment_vacations_balance);
      sKeys.put("layout-ar/fragment_vacations_balance_0", com.kuwaitKFF.R.layout.fragment_vacations_balance);
    }
  }
}
