package com.kuwaitKFF.databinding;
import com.kuwaitKFF.R;
import com.kuwaitKFF.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentMonthlyReportInquiryBindingImpl extends FragmentMonthlyReportInquiryBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.vsHeader, 1);
        sViewsWithIds.put(R.id.topLinear, 2);
        sViewsWithIds.put(R.id.titleText, 3);
        sViewsWithIds.put(R.id.textView1, 4);
        sViewsWithIds.put(R.id.yearSpinnerParentLayout, 5);
        sViewsWithIds.put(R.id.yearSpinner, 6);
        sViewsWithIds.put(R.id.textViewDate, 7);
        sViewsWithIds.put(R.id.monthSpinnerParentLayout, 8);
        sViewsWithIds.put(R.id.monthSpinner, 9);
        sViewsWithIds.put(R.id.mainFlipper, 10);
        sViewsWithIds.put(R.id.tableLayout, 11);
        sViewsWithIds.put(R.id.noOfAbsentDaysValue, 12);
        sViewsWithIds.put(R.id.absentDaysValue, 13);
        sViewsWithIds.put(R.id.notSigningAttendanceValue, 14);
        sViewsWithIds.put(R.id.notSigningLeaveValue, 15);
        sViewsWithIds.put(R.id.sickHealthCentersValue, 16);
        sViewsWithIds.put(R.id.otherVacationsValue, 17);
        sViewsWithIds.put(R.id.costsValue, 18);
        sViewsWithIds.put(R.id.delayValue, 19);
        sViewsWithIds.put(R.id.absentDeductionValue, 20);
        sViewsWithIds.put(R.id.delayDeductionValue, 21);
        sViewsWithIds.put(R.id.searchButton, 22);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentMonthlyReportInquiryBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 23, sIncludes, sViewsWithIds));
    }
    private FragmentMonthlyReportInquiryBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.appcompat.widget.AppCompatTextView) bindings[13]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[20]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[18]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[21]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[19]
            , (android.widget.ViewFlipper) bindings[10]
            , (android.widget.Spinner) bindings[9]
            , (android.widget.RelativeLayout) bindings[8]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[12]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[14]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[15]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[17]
            , (android.widget.Button) bindings[22]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[16]
            , (android.widget.TableLayout) bindings[11]
            , (android.widget.TextView) bindings[4]
            , (android.widget.TextView) bindings[7]
            , (android.widget.TextView) bindings[3]
            , (android.widget.LinearLayout) bindings[2]
            , new androidx.databinding.ViewStubProxy((android.view.ViewStub) bindings[1])
            , (android.widget.Spinner) bindings[6]
            , (android.widget.RelativeLayout) bindings[5]
            );
        this.mboundView0 = (android.widget.RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.vsHeader.setContainingBinding(this);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x1L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
            return variableSet;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        // batch finished
        if (vsHeader.getBinding() != null) {
            executeBindingsOn(vsHeader.getBinding());
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}