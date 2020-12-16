package com.taxfiling.service;

import java.util.List;

import com.taxfiling.entity.Admin;
import com.taxfiling.entity.Customer;
import com.taxfiling.entity.Employer;
import com.taxfiling.entity.Notice;
import com.taxfiling.entity.Representative;
import com.taxfiling.entity.TaxForm;

public interface TaxFilingServiceInterface {

	int registerEmployer(Employer ep);
	int registerRepresentative(Representative rp);
	int registerCustomer(Customer cs);
	
	boolean loginCustomer(String customerId, String customerPass);
	boolean loginRepresentative(String representativeId, String representativePass);
	boolean loginEmployer(String employerId, String employerPass);
	boolean loginAdmin(String adminId, String adminPass);
	
	int forgotPassword(int i, long forgotId, String answer);
	
	Customer getCustomerById(String customerId);
	Customer getCustomerByTaxFormId(String tid);
	Representative getRepresentativeById(String representativeId);
	Employer getEmployerByOrganization(String organization);
	Admin getAdminObject();

	int addTaxDetailsForEmployee(TaxForm objTaxForm);
	int addTaxDetailsByCustomer(TaxForm objTextForm);
	TaxForm getTaxFromByPan(String pan);
	
	List<TaxForm> getTaxForms();
	List<Notice> viewNoticesOfCustomer(Customer c);
	List<Notice> viewNoticesOfRepresentative(Representative r);
	List<Notice> viewNoticesOfAdmin(Admin a);
	
	int fileReturn(TaxForm t);
	int addNotice(Notice n);
	List<TaxForm> getTaxFormsForAdmin();
	int addTaxDetailsByNewCustomer(TaxForm objTaxForm);
	
	int manageCustomerProfile(String columnName,String id,String newValue);
	int manageIsEmployeeStatusProfile(String columnName, String customerId, String newEmpStatus, String newOrgName);
	int manageEmployerProfile(String columnName, String employerId, String newName);
	int manageRepresentativeProfile(String columnName, String representativeId, String newName);
	int manageAdminProfile(String columnName, String adminId, String newPass);
	
	
	int removeCustomer(long cId);
	int removeRepresentative(long rId);
	
	List<Customer> displayAllCustomers();
	List<Representative> displayAllRepresentatives();
	int removeEmployer(long eId);
	List<Employer> displayAllEmployers();
	
	Customer displayCustomerInfo(long customerId1);
	Representative displayRepresentativeInfo(long representativeId);
	Employer displayEmployerInfo(long employerId);
	Admin displayAdminInfo(String id);
}
