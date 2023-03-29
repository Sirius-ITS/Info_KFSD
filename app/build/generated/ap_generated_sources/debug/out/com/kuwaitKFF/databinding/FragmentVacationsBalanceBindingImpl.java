package com.kuwaitKFF.databinding;
import com.kuwaitKFF.R;
import com.kuwaitKFF.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentVacationsBalanceBindingImpl extends FragmentVacationsBalanceBinding  {

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
        sViewsWithIds.put(R.id.leaveTypeSpinner, 4);
        sViewsWithIds.put(R.id.textView1, 5);
        sViewsWithIds.put(R.id.submitButton, 6);
        sViewsWithIds.put(R.id.mainFlipper, 7);
        sViewsWithIds.put(R.id.tableLayout, 8);
        sViewsWithIds.put(R.id.cumulativeValue, 9);
        sViewsWithIds.put(R.id.annualValue, 10);
        sViewsWithIds.put(R.id.frozenBalanceValue, 11);
        sViewsWithIds.put(R.id.formerExcludedValue, 12);
        sViewsWithIds.put(R.id.currentlyExcluded, 13);
        sViewsWithIds.put(R.id.noOfVacationsValue, 14);
        sViewsWithIds.put(R.id.totalDurationValue, 15);
        sViewsWithIds.put(R.id.actualDurationValue, 16);
        sViewsWithIds.put(R.id.cashRedemptionValue, 17);
        sViewsWithIds.put(R.id.residualValue, 18);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentVacationsBalanceBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 19, sIncludes, sViewsWithIds));
    }
    private FragmentVacationsBalanceBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.appcompat.widget.AppCompatTextView) bindings[16]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[10]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[17]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[9]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[13]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[12]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[11]
            , (android.widget.Spinner) bindings[4]
            , (android.widget.ViewFlipper) bindings[7]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[14]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[18]
            , (android.widget.Button) bindings[6]
            , (android.widget.TableLayout) bindings[8]
            , (android.widget.TextView) bindings[5]
            , (android.widget.TextView) bindings[3]
            , (android.widget.LinearLayout) bindings[2]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[15]
            , new androidx.databinding.ViewStubProxy((android.view.ViewStub) bindings[1])
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