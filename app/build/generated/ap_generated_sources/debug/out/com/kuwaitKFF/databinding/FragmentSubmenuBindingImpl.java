package com.kuwaitKFF.databinding;
import com.kuwaitKFF.R;
import com.kuwaitKFF.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentSubmenuBindingImpl extends FragmentSubmenuBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.vsHeader, 1);
        sViewsWithIds.put(R.id.relView1sub, 2);
        sViewsWithIds.put(R.id.textView1sub, 3);
        sViewsWithIds.put(R.id.imageView1, 4);
        sViewsWithIds.put(R.id.relView2sub, 5);
        sViewsWithIds.put(R.id.textView2sub, 6);
        sViewsWithIds.put(R.id.imageView2, 7);
        sViewsWithIds.put(R.id.relView3sub, 8);
        sViewsWithIds.put(R.id.textView3sub, 9);
        sViewsWithIds.put(R.id.imageView3, 10);
        sViewsWithIds.put(R.id.relView4sub, 11);
        sViewsWithIds.put(R.id.textView4sub, 12);
        sViewsWithIds.put(R.id.imageView4, 13);
        sViewsWithIds.put(R.id.relView5sub, 14);
        sViewsWithIds.put(R.id.textView5sub, 15);
        sViewsWithIds.put(R.id.imageView5, 16);
        sViewsWithIds.put(R.id.relView6sub, 17);
        sViewsWithIds.put(R.id.textView6sub, 18);
        sViewsWithIds.put(R.id.imageView6, 19);
        sViewsWithIds.put(R.id.relView7sub, 20);
        sViewsWithIds.put(R.id.textView7sub, 21);
        sViewsWithIds.put(R.id.imageView7, 22);
        sViewsWithIds.put(R.id.relView8sub, 23);
        sViewsWithIds.put(R.id.textView8sub, 24);
        sViewsWithIds.put(R.id.imageView8, 25);
        sViewsWithIds.put(R.id.relView9sub, 26);
        sViewsWithIds.put(R.id.textView9sub, 27);
        sViewsWithIds.put(R.id.imageView9, 28);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentSubmenuBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 29, sIncludes, sViewsWithIds));
    }
    private FragmentSubmenuBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.ImageView) bindings[4]
            , (android.widget.ImageView) bindings[7]
            , (android.widget.ImageView) bindings[10]
            , (android.widget.ImageView) bindings[13]
            , (android.widget.ImageView) bindings[16]
            , (android.widget.ImageView) bindings[19]
            , (android.widget.ImageView) bindings[22]
            , (android.widget.ImageView) bindings[25]
            , (android.widget.ImageView) bindings[28]
            , (android.widget.RelativeLayout) bindings[2]
            , (android.widget.RelativeLayout) bindings[5]
            , (android.widget.RelativeLayout) bindings[8]
            , (android.widget.RelativeLayout) bindings[11]
            , (android.widget.RelativeLayout) bindings[14]
            , (android.widget.RelativeLayout) bindings[17]
            , (android.widget.RelativeLayout) bindings[20]
            , (android.widget.RelativeLayout) bindings[23]
            , (android.widget.RelativeLayout) bindings[26]
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[6]
            , (android.widget.TextView) bindings[9]
            , (android.widget.TextView) bindings[12]
            , (android.widget.TextView) bindings[15]
            , (android.widget.TextView) bindings[18]
            , (android.widget.TextView) bindings[21]
            , (android.widget.TextView) bindings[24]
            , (android.widget.TextView) bindings[27]
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