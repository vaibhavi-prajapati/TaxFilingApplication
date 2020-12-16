package com.taxfiling.dao;

import com.taxfiling.entity.Admin;
import com.taxfiling.entity.Customer;
import com.taxfiling.entity.Employer;
import com.taxfiling.entity.Representative;

public interface TaxFilingRegistrationDAO {

	int registerCustomer(Customer cs);
	int registerRepresentative(Representative rp);
	int registerEmployer(Employer ep);
	
	Employer getEmployerByOrganization(String organization);
	Admin getAdminObject();
}
