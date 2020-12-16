package com.taxfilingapplication;

import com.taxfiling.dao.TaxFilingProfileDAOImpl;
import com.taxfiling.dao.TaxFilingProfileDAO;
import com.taxfiling.entity.Admin;
import com.taxfiling.entity.Customer;
import com.taxfiling.entity.Employer;
import com.taxfiling.entity.Representative;

import junit.framework.TestCase;

public class ProfileTest extends TestCase {

	TaxFilingProfileDAO tfpd;

	protected void setUp() throws Exception {
		super.setUp();
		tfpd = new TaxFilingProfileDAOImpl();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		tfpd = null;
	}

	public void testManageCustomerProfile() {
		int i = tfpd.manageCustomerProfile("name", "1", "sk");
		assert i > 0 : "Customer Profile Not Updated";
	}

	public void testManageEmployerProfile() {
		int i = tfpd.manageEmployerProfile("organization", "3", "Capgemini");
		assert i > 0 : "Employer Profile Not Updated";
	}

	public void testManageRepresentativeProfile() {
		int i = tfpd.manageRepresentativeProfile("name", "2", "Chartered Accountant");
		assert i > 0 : "Representative Profile Not Updated";
	}

	public void testManageAdminProfile() {
		int i = tfpd.manageAdminProfile("password", "admin@cg.com", "root");
		assert i > 0 : "Admin Profile Not Updated";
	}

	public void testManageIsEmployeeStatusProfile() {

		int i = tfpd.manageIsEmployeeStatusProfile("isemployee", "1", "f", "cg");
		assert i > 0 : "Employee status Not Updated";
	}

	public void testForgotPassword() {

		int i = tfpd.forgotPassword(1, 3, "cds");
		assert i > 0 : "Password Not Updated";
	}

	public void testDisplayAdminInfo() {
		Admin a1 = new Admin();
		a1.setEmail("admin@cg.com");
		a1.setPassword("root");
		Admin a2 = tfpd.displayAdminInfo("admin@cg.com");
		assert a1.getEmail().equals(a2.getEmail()) : "Admin Information Not Displayed";
	}

	public void testDisplayEmployerInfo() {
		Employer e1 = new Employer();
		e1.setEmail("tcs#tcs.com");
		e1.setEmployerId(2);
		e1.setOrganization("TCS");
		e1.setContactNo("15486965");
		Employer e2 = tfpd.displayEmployerInfo(2);
		assert e2.getOrganization().equals(e2.getOrganization());
	}

	public void testDisplayRepresentativeInfo() {
		Representative r1 = new Representative();
		r1.setName("Chartered Accountant");
		r1.setEmail("ca@cg.com");
		r1.setContactNo("54654");
		r1.setRepresentativeId(2);
		Representative r2 = tfpd.displayRepresentativeInfo(2);
		assert r1.getName().equals(r2.getName()) : "Representative Information Not Displayed";
	}

	public void testDisplayCustomerInfo() {
		Customer c1 = new Customer();
		c1.setName("sdsds");
		c1.setEmail("sdsdas");
		c1.setPan("ssda");
		c1.setCustomerId(2);
		Customer c2 = tfpd.displayCustomerInfo(2);
		assert c1.getPan().equals(c2.getPan()) : "Customer Information Not Displayed";
	}

}
