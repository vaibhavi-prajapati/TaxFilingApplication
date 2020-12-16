package com.taxfilingapplication;

import java.util.List;

import com.taxfiling.dao.TaxFilingOperationsDAOImpl;
import com.taxfiling.dao.TaxFilingOperationsDAO;
import com.taxfiling.entity.Customer;
import com.taxfiling.entity.Notice;
import com.taxfiling.entity.Representative;
import com.taxfiling.entity.TaxForm;

import junit.framework.TestCase;

public class OperationTest extends TestCase {

	TaxFilingOperationsDAO tfod;

	protected void setUp() throws Exception {
		super.setUp();
		tfod = new TaxFilingOperationsDAOImpl();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		tfod = null;
	}

	public void testGetCustomerById() {
		String customerid = "1";
		Customer i = tfod.getCustomerById(customerid);
		Customer c = new Customer();
		c.setCustomerId(1);
		c.setName("megha");
		assert i.getName().equals(c.getName()) : "Customerid fetched";
	}

	public void testGetTaxFormByPan() {
		String pan = "4512";
		TaxForm i1 = tfod.getTaxFormByPan(pan);
		TaxForm tf1 = new TaxForm();
		tf1.setPan("4512");
		assert i1.getPan().equals(tf1.getPan()) : "TaxForm fetched";
	}

	public void testAddTaxDetailsByCustomer() {
		TaxForm tf = new TaxForm();
		tf.setTaxformId(1);
		tf.setTotalIncomeSalary(14000);
		tf.setOtherIncome(0);
		tf.setInterestIncome(0);
		tf.setRentalIncome(0);
		tf.setPpf(0);
		tf.setMedicalInsurance(0);
		tf.setEducaionLoan(0);
		tf.setNps(0);
		tf.setSavingsInterest(0);
		tf.setHouseLoan(0);
		tf.setPayableTax(0);
		tf.setVerifiedStatus("approved");
		tf.setPan("4512");
		int i = tfod.addTaxDetailsByCustomer(tf);
		assert i > 0 : "Data added successufully";
	}

	public void testFileReturn() {
		TaxForm tf = new TaxForm();
		tf.setTaxformId(1);
		tf.setVerifiedStatus("approved");
		int i = tfod.fileReturn(tf);
		assert i > 0 : "File returned";
	}

	public void testAddTaxDetailsForEmployee() {
		TaxForm tf = new TaxForm();
		tf.setPan("4512");
		tf.setTotalIncomeSalary(14000);
		tf.setOtherIncome(0);
		tf.setInterestIncome(0);
		tf.setRentalIncome(0);
		tf.setPpf(0);
		tf.setMedicalInsurance(0);
		tf.setEducaionLoan(0);
		tf.setNps(0);
		tf.setSavingsInterest(0);
		tf.setHouseLoan(0);
		tf.setTds(0);
		tf.setVerifiedStatus("approved");
		int i = tfod.addTaxDetailsForEmployee(tf);
		assert i > 0 : "Data of employee added succesfully";
	}

	public void testGetRepresentativeById() {

		String repId = "1";
		Representative r = tfod.getRepresentativeById(repId);
		Representative rt = new Representative();
		rt.setRepresentativeId(1);
		rt.setName("megha");
		assert r.getName().equals(rt.getName()) : "Data fetched";
	}

	public void testGetTaxForms() {

		List<TaxForm> flist = tfod.getTaxForms();
		assert flist != null : "fetched";
	}

	public void testGetTaxFormsForAdmin() {
		List<TaxForm> alist = tfod.getTaxForms();
		assert alist != null : "fetched";
	}

	public void testGetCustomerByTaxFormId() {
		String taxform = "1";
		Customer c = tfod.getCustomerByTaxFormId(taxform);
		TaxForm tf = new TaxForm();
		tf.setTaxformId(1);
		tf.setPan("4512");
		assert c.getPan().equals(c.getPan()) : "fetched";
	}

	public void testViewNoticesOfCustomer() {
		Customer c = new Customer();
		c.setCustomerId(1);
		List<Notice> clist = tfod.viewNoticesOfCustomer(c);
		assert clist != null : "Notice returned";
	}

	public void testViewNoticesOfRepresenative() {
		Representative r = new Representative();

		List<Notice> nlist = tfod.viewNoticesOfRepresenative(r);
		assert nlist != null : "notices viewed";
	}

}
