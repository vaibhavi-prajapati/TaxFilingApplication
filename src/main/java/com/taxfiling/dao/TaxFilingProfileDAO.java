package com.taxfiling.dao;

import java.util.List;

import com.taxfiling.entity.Admin;
import com.taxfiling.entity.Customer;
import com.taxfiling.entity.Employer;
import com.taxfiling.entity.Representative;

public interface TaxFilingProfileDAO {

	int forgotPassword(int i, long forgotId, String answer);

	Customer displayCustomerInfo(long customerId1);

	Representative displayRepresentativeInfo(long representativeId);

	Employer displayEmployerInfo(long employerId);

	Admin displayAdminInfo(String id);

	int manageCustomerProfile(String columnName, String id, String newValue);

	int manageIsEmployeeStatusProfile(String columnName, String customerId, String newEmpStatus, String newOrgName);

	int manageEmployerProfile(String columnName, String employerId, String newName);

	int manageRepresentativeProfile(String columnName, String representativeId, String newName);

	int manageAdminProfile(String columnName, String adminId, String newPass);

	int removeCustomer(long cId);

	int removeRepresentative(long rId);

	int removeEmployer(long eId);

	List<Customer> displayAllCustomers();

	List<Representative> displayAllRepresentatives();

	List<Employer> displayAllEmployers();
}
