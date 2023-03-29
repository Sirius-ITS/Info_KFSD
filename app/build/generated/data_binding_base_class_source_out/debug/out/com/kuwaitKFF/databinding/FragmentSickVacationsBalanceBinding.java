// Generated by data binding compiler. Do not edit!
package com.kuwaitKFF.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.ViewStubProxy;
import com.kuwaitKFF.R;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class FragmentSickVacationsBalanceBinding extends ViewDataBinding {
  @NonNull
  public final ViewFlipper mainFlipper;

  @NonNull
  public final AppCompatTextView sickHealthCentersValue;

  @NonNull
  public final AppCompatTextView sickHospitalsValue;

  @NonNull
  public final AppCompatTextView sickQuarantineValue;

  @NonNull
  public final RelativeLayout spinnerParentLayout;

  @NonNull
  public final TableLayout tableLayout;

  @NonNull
  public final TextView textView1;

  @NonNull
  public final TextView titleText;

  @NonNull
  public final LinearLayout topLinear;

  @NonNull
  public final ViewStubProxy vsHeader;

  @NonNull
  public final Spinner yearSpinner;

  protected FragmentSickVacationsBalanceBinding(Object _bindingComponent, View _root,
      int _localFieldCount, ViewFlipper mainFlipper, AppCompatTextView sickHealthCentersValue,
      AppCompatTextView sickHospitalsValue, AppCompatTextView sickQuarantineValue,
      RelativeLayout spinnerParentLayout, TableLayout tableLayout, TextView textView1,
      TextView titleText, LinearLayout topLinear, ViewStubProxy vsHeader, Spinner yearSpinner) {
    super(_bindingComponent, _root, _localFieldCount);
    this.mainFlipper = mainFlipper;
    this.sickHealthCentersValue = sickHealthCentersValue;
    this.sickHospitalsValue = sickHospitalsValue;
    this.sickQuarantineValue = sickQuarantineValue;
    this.spinnerParentLayout = spinnerParentLayout;
    this.tableLayout = tableLayout;
    this.textView1 = textView1;
    this.titleText = titleText;
    this.topLinear = topLinear;
    this.vsHeader = vsHeader;
    this.yearSpinner = yearSpinner;
  }

  @NonNull
  public static FragmentSickVacationsBalanceBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_sick_vacations_balance, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static FragmentSickVacationsBalanceBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<FragmentSickVacationsBalanceBinding>inflateInternal(inflater, R.layout.fragment_sick_vacations_balance, root, attachToRoot, component);
  }

  @NonNull
  public static FragmentSickVacationsBalanceBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_sick_vacations_balance, null, false, component)
   */
  @NonNull
  @Deprecated
  public static FragmentSickVacationsBalanceBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<FragmentSickVacationsBalanceBinding>inflateInternal(inflater, R.layout.fragment_sick_vacations_balance, null, false, component);
  }

  public static FragmentSickVacationsBalanceBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.bind(view, component)
   */
  @Deprecated
  public static FragmentSickVacationsBalanceBinding bind(@NonNull View view,
      @Nullable Object component) {
    return (FragmentSickVacationsBalanceBinding)bind(component, view, R.layout.fragment_sick_vacations_balance);
  }
}