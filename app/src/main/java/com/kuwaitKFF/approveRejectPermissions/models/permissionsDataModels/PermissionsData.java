package com.kuwaitKFF.approveRejectPermissions.models.permissionsDataModels;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class PermissionsData {

	@SerializedName("permissionRequestView")
	private List<EmpPermissionRequestViewItem> empPermissionRequestView;

	public void setEmpPermissionRequestView(List<EmpPermissionRequestViewItem> empPermissionRequestView){
		this.empPermissionRequestView = empPermissionRequestView;
	}

	public List<EmpPermissionRequestViewItem> getEmpPermissionRequestView(){
		return empPermissionRequestView;
	}
}