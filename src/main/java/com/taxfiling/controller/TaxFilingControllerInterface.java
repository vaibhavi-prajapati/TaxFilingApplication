package com.taxfiling.controller;

import java.io.IOException;

import com.taxfiling.entity.Admin;
import com.taxfiling.entity.Customer;
import com.taxfiling.entity.Representative;

public interface TaxFilingControllerInterface {
	public void loginActor() throws IOException;

	public void registerActor();

	public void forgotPassword();

	public void removeActors();

	public void manageCustomer(String customerId);

	public void manageEmployer(String employerId);

	public void manageRepresentative(String representativeId);

	public void manageAdmin(String adminId);

	public void displayAllEmployer();

	public void displayAllRepresentative();

	public void displayAllCustomer();

	public void displayAdminInfo(String adminId);

	public void displayRepresentativeInfo(String representativeId);

	public void displayEmployerInfo(String employerId);

	public void displayCustomerInfo(String customerId);

	public void verifyTaxForms(Admin a);

	public void verifyTaxForms(Representative r);

	public void addTaxDetailsForEmployee();

	public void fileReturn(Customer c);

	public void addTaxDetails(Customer c);
}