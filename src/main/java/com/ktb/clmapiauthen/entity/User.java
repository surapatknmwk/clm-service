package com.ktb.clmapiauthen.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User extends BaseEntity{

    @OneToMany(mappedBy = "user")
    private List<MappingUserRole> mappingUserRoles;

    @OneToMany(mappedBy = "user")
    private List<UserLogPermission> userLogPermissions;

    @OneToMany(mappedBy = "user")
    private List<TransactionLog> transactionLogs;

    private String titleNameThai;
    private String titleNameEng;
    private String empNameThai;
    private String empSurnameThai;
    private String empPosition;
    private String empEmployeeDate;
    private String marStatus;
    private String empType;
    private String empNameEng;
    private String empSurnameEng;
    private String userUpdateDate;
    private String empMail;
    private String empNameThaiOld;
    private String empSurnameThaiOld;
    private String jobLevel;
    private String ktbIntRc;
    private String emplStatus;
    private String emplidTo;
    private String ktbFieldMap;
    private String jobcode;
    private String ktbDescr254_3;
    private String descr100Ger;
    private String location;

}
