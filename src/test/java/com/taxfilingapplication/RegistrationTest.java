package com.taxfilingapplication;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.taxfiling.dao.TaxFilingRegistrationDAOImpl;
import com.taxfiling.dao.TaxFilingRegistrationDAO;
import com.taxfiling.entity.Admin;
import com.taxfiling.entity.Customer;
import com.taxfiling.entity.Employer;
import com.taxfiling.entity.Representative;

import junit.framework.TestCase;

public class RegistrationTest extends TestCase {

	TaxFilingRegistrationDAO tfrd;

	protected void setUp() throws Exception {
		tfrd = new TaxFilingRegistrationDAOImpl();
	}

	protected void tearDown() throws Exception {
		tfrd = null;
	}

	public void testRegisterCustomer() {
		Customer c = new Customer();
		c.setName("cust2");
		c.setEmail("cust2@gmail.com");
		c.setPassword("password");
		c.setAddress("mumbai");
		c.setPan("ABC123");
		c.setContactNo("9876543210");
		c.setAccountNo("ASDF12345");
		c.setMaritalStatus("single");
		c.setIsEmployee(false);
		String date = "08-07-1998";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate date2 = LocalDate.parse(date, formatter);
		c.setDateOfBirth(date2);
		c.setSecurityQuestion("1.what is your nickname?");
		c.setSecurityAnswer("custu");
		int cTest = tfrd.registerCustomer(c);
		assert cTest > 0 : "Registration succesfull";
	}

	public void testRegisterRepresentative() {
		Representative r = new Representative();
		r.setName("rep2");
		r.setEmail("rep2@gmail.com");
		r.setPassword("password");
		r.setContactNo("1234567890");
		r.setSecurityQuestion("2.what is place of birth?");
		r.setSecurityAnswer("Pune");
		Admin a = tfrd.getAdminObject();
		r.setAdmin_r(a);
		int rTest = tfrd.registerRepresentative(r);
		assert rTest > 0 : "Registration sucessfull";
	}

	public void testRegisterEmployer() {
		Employer e = new Employer();
		e.setEmail("emp2@gmail.com");
		e.setPassword("password");
		e.setContactNo("9874561230");
		e.setOrganization("TCS");
		e.setSecurityQuestion("2.what is place of birth?");
		e.setSecurityAnswer("Nagpur");
		Admin a = tfrd.getAdminObject();
		e.setAdmin_e(a);
		int eTest = tfrd.registerEmployer(e);
		assert eTest > 0 : "Registration sucessfull";
	}

}
