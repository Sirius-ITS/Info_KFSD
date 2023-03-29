package com.kuwaitKFF.approveRejectPermissions.interfaces;

import com.kuwaitKFF.approveRejectPermissions.models.permissionsDataModels.EmpPermissionRequestViewItem;

public interface AcceptRejectPermissionItemInterface {
    void onItemClick(EmpPermissionRequestViewItem item, int position, int acceptOrReject);
}
