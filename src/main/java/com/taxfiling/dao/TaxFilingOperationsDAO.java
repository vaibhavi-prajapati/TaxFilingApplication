package com.taxfiling.dao;

import java.util.List;

import com.taxfiling.entity.Admin;
import com.taxfiling.entity.Customer;
import com.taxfiling.entity.Notice;
import com.taxfiling.entity.Representative;
import com.taxfiling.entity.TaxForm;

public interface TaxFilingOperationsDAO {

	Customer getCustomerById(String customerId);
	Representative getRepresentativeById(String representativeId);
	Customer getCustomerByTaxFormId(String tid);

	int addTaxDetailsByCustomer(TaxForm objTaxForm);
	int addTaxDetailsForEmployee(TaxForm objTaxForm);
	int addTaxDetailsByNewCustomer(TaxForm objTaxForm);
	int fileReturn(TaxForm t);

	TaxForm getTaxFormByPan(String pan);
	List<TaxForm> getTaxForms();
	public List<TaxForm> getTaxFormsForAdmin();

	int addNotice(Notice n);
	List<Notice> viewNoticesOfCustomer(Customer c);
	List<Notice> viewNoticesOfRepresenative(Representative r);
	List<Notice> viewNoticesOfAdmin(Admin a);
}
