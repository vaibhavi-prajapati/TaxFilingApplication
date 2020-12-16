package com.taxfiling.service;

import java.util.List;

import com.taxfiling.dao.TaxFilingLoginDAOImpl;
import com.taxfiling.dao.TaxFilingLoginDAO;
import com.taxfiling.dao.TaxFilingOperationsDAOImpl;
import com.taxfiling.dao.TaxFilingOperationsDAO;
import com.taxfiling.dao.TaxFilingProfileDAOImpl;
import com.taxfiling.dao.TaxFilingProfileDAO;
import com.taxfiling.dao.TaxFilingRegistrationDAOImpl;
import com.taxfiling.dao.TaxFilingRegistrationDAO;
import com.taxfiling.entity.Admin;
import com.taxfiling.entity.Customer;
import com.taxfiling.entity.Employer;
import com.taxfiling.entity.Notice;
import com.taxfiling.entity.Representative;
import com.taxfiling.entity.TaxForm;

public class TaxFilingService implements TaxFilingServiceInterface {

	private static TaxFilingLoginDAO loginDao = new TaxFilingLoginDAOImpl();
	private static TaxFilingProfileDAO profileDao = new TaxFilingProfileDAOImpl();
	private static TaxFilingRegistrationDAO registrationDao = new TaxFilingRegistrationDAOImpl();
	private static TaxFilingOperationsDAO operationsDao = new TaxFilingOperationsDAOImpl();

	public int registerEmployer(Employer ep) {
		return registrationDao.registerEmployer(ep);
	}

	public int registerRepresentative(Representative rp) {
		return registrationDao.registerRepresentative(rp);
	}

	public int registerCustomer(Customer cs) {
		return registrationDao.registerCustomer(cs);
	}

	public boolean loginCustomer(String customerId, String customerPass) {
		return loginDao.loginCustomer(customerId, customerPass);
	}

	public boolean loginRepresentative(String representativeId, String representativePass) {
		return loginDao.loginRepresentative(representativeId, representativePass);
	}

	public boolean loginEmployer(String employerId, String employerPass) {
		return loginDao.loginEmployer(employerId, employerPass);
	}

	public boolean loginAdmin(String adminId, String adminPass) {
		return loginDao.loginAdmin(adminId, adminPass);
	}

	public int forgotPassword(int i, long forgotId, String answer) {
		return profileDao.forgotPassword(i, forgotId, answer);
	}

	public List<Notice> viewNoticesOfCustomer(Customer c) {
		return operationsDao.viewNoticesOfCustomer(c);
	}

	public int fileReturn(TaxForm t) {
		return operationsDao.fileReturn(t);
	}

	public int addTaxDetailsForEmployee(TaxForm objTaxForm) {
		return operationsDao.addTaxDetailsForEmployee(objTaxForm);
	}

	public Employer getEmployerByOrganization(String organization) {
		return registrationDao.getEmployerByOrganization(organization);
	}

	public Admin getAdminObject() {
		return registrationDao.getAdminObject();
	}

	public Customer displayCustomerInfo(long customerId1) {
		return profileDao.displayCustomerInfo(customerId1);
	}

	public Representative displayRepresentativeInfo(long representativeId) {
		return profileDao.displayRepresentativeInfo(representativeId);
	}

	public Employer displayEmployerInfo(long employerId) {
		return profileDao.displayEmployerInfo(employerId);
	}

	public Admin displayAdminInfo(String id) {
		return profileDao.displayAdminInfo(id);
	}

	public Customer getCustomerById(String customerId) {
		return operationsDao.getCustomerById(customerId);

	}

	public TaxForm getTaxFromByPan(String pan) {
		return operationsDao.getTaxFormByPan(pan);
	}

	public int addTaxDetailsByCustomer(TaxForm objTaxForm) {
		return operationsDao.addTaxDetailsByCustomer(objTaxForm);
	}

	public Representative getRepresentativeById(String representativeId) {
		return operationsDao.getRepresentativeById(representativeId);
	}

	public List<TaxForm> getTaxForms() {
		return operationsDao.getTaxForms();
	}

	public Customer getCustomerByTaxFormId(String tid) {
		return operationsDao.getCustomerByTaxFormId(tid);
	}

	public int addNotice(Notice n) {

		return operationsDao.addNotice(n);
	}

	public List<Notice> viewNoticesOfRepresentative(Representative r) {
		return operationsDao.viewNoticesOfRepresenative(r);
	}

	public List<Notice> viewNoticesOfAdmin(Admin a) {
		return operationsDao.viewNoticesOfAdmin(a);
	}

	public List<TaxForm> getTaxFormsForAdmin() {
		return operationsDao.getTaxFormsForAdmin();
	}

	public int addTaxDetailsByNewCustomer(TaxForm objTaxForm) {
		return operationsDao.addTaxDetailsByNewCustomer(objTaxForm);
	}

	public int manageCustomerProfile(String columnName, String id, String newValue) {
		return profileDao.manageCustomerProfile(columnName, id, newValue);
	}

	public int manageIsEmployeeStatusProfile(String columnName, String customerId, String newEmpStatus,
			String newOrgName) {
		return profileDao.manageIsEmployeeStatusProfile(columnName, customerId, newEmpStatus, newOrgName);
	}

	public int manageEmployerProfile(String columnName, String employerId, String newName) {
		return profileDao.manageEmployerProfile(columnName, employerId, newName);
	}

	public int manageRepresentativeProfile(String columnName, String representativeId, String newName) {
		return profileDao.manageRepresentativeProfile(columnName, representativeId, newName);
	}

	public int manageAdminProfile(String columnName, String adminId, String newPass) {
		return profileDao.manageAdminProfile(columnName, adminId, newPass);
	}

	public int removeCustomer(long cId) {
		return profileDao.removeCustomer(cId);
	}

	public List<Customer> displayAllCustomers() {
		return profileDao.displayAllCustomers();
	}

	public int removeRepresentative(long rId) {
		return profileDao.removeRepresentative(rId);
	}

	public List<Representative> displayAllRepresentatives() {
		return profileDao.displayAllRepresentatives();
	}

	public int removeEmployer(long eId) {
		return profileDao.removeEmployer(eId);
	}

	public List<Employer> displayAllEmployers() {
		return profileDao.displayAllEmployers();
	}
}
