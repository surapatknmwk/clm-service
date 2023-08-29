package com.ktb.clmapiauthen.models.interfaces.ad;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ADResponse {
    private String username;
	private String uid;
	private String givenName;
	private String sn;
	private String thaiPersonalTitle;
	private String thaiFirstName;
	private String thaiLastName;
	private String titleCode;
	private String title;
	private String rankCode;
	private String rank;
	private String departmentCode;
	private String businessCategory;
	private String locationCode;
	private String postalAddress;
	private String kcsBranchCode;
	private String citizenID;
	private String mail;
	private String ipAddress;
}
