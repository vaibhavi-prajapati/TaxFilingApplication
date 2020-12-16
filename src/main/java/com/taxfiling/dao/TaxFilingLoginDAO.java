package com.taxfiling.dao;

public interface TaxFilingLoginDAO {

	boolean loginCustomer(String customerId, String customerPass);

	boolean loginRepresentative(String representativeId, String representativePass);

	boolean loginEmployer(String employerId, String employerPass);

	boolean loginAdmin(String adminId, String adminPass);
}
