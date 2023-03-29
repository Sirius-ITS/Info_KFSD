package com.kuwaitKFF.approveRejectVacations.interfaces;

import com.kuwaitKFF.approveRejectVacations.models.vacationsDataModels.EmpLeaveRequestViewItem;

public interface AcceptRejectItemInterface {
    void onItemClick(EmpLeaveRequestViewItem item, int position, int acceptOrReject);
}
