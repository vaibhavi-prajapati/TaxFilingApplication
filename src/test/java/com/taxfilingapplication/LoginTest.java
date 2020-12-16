package com.taxfilingapplication;

import com.taxfiling.dao.TaxFilingLoginDAOImpl;
import com.taxfiling.dao.TaxFilingLoginDAO;

import junit.framework.TestCase;

public class LoginTest extends TestCase {

	TaxFilingLoginDAO tfld;

	protected void setUp() throws Exception {
		tfld = new TaxFilingLoginDAOImpl();
	}

	protected void tearDown() throws Exception {
		tfld = null;
	}

	public void testLoginCustomer() {
		String customerId = "1";
		String customerPass = "vh";
		boolean loginFlag = tfld.loginCustomer(customerId, customerPass);
		assert loginFlag == true : "Logged In";
	}

	public void testLoginRepresentative() {
		String representativeId = "1";
		String representativePass = "r";
		boolean loginFlag = tfld.loginRepresentative(representativeId, representativePass);
		assert loginFlag == true : "Logged In";
	}

	public void testLoginEmployer() {
		String employerId = "1";
		String employerPass = "e";
		boolean loginFlag = tfld.loginEmployer(employerId, employerPass);
		assert loginFlag == true : "Logged In";
	}

	public void testLoginAdmin() {
		String adminId = "admin";
		String adminPass = "admin";
		boolean loginFlag = tfld.loginAdmin(adminId, adminPass);
		assert loginFlag == true : "Logged In";
	}

}
