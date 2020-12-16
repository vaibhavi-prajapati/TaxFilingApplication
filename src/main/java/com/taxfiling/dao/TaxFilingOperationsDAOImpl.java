package com.taxfiling.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.taxfiling.entity.Admin;
import com.taxfiling.entity.Customer;
import com.taxfiling.entity.Notice;
import com.taxfiling.entity.Representative;
import com.taxfiling.entity.TaxForm;

import org.apache.log4j.Logger;

public class TaxFilingOperationsDAOImpl implements TaxFilingOperationsDAO {

	final static Logger logger = Logger.getLogger(TaxFilingProfileDAOImpl.class);

	public Customer getCustomerById(String customerId) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-pu");
		EntityManager em = emf.createEntityManager();
		Customer c = em.find(Customer.class, Long.parseLong(customerId));
		em.close();
		return c;
	}

	public TaxForm getTaxFormByPan(String pan) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-pu");
		EntityManager em = emf.createEntityManager();
		Query query1 = em.createQuery("SELECT t FROM TaxForm t where t.pan=:val").setParameter("val", pan);
		TaxForm tf = (TaxForm) query1.getSingleResult();
		em.close();
		return tf;
	}

	public int addTaxDetailsByCustomer(TaxForm objTaxForm) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-pu");
		EntityManager em = emf.createEntityManager();
		EntityTransaction t1 = em.getTransaction();
		t1.begin();
		em.createQuery(
				"update TaxForm t set t.totalIncomeSalary=:tot, t.otherIncome=:o,t.interestIncome=:ii,t.rentalIncome=:ri,t.ppf=:ppf,t.medicalInsurance=:med,t.educaionLoan=:edu,t.nps=:nps,t.savingsInterest=:sav,t.verifiedStatus=:ver, t.payableTax=:tax where t.taxformId=:id1")
				.setParameter("tot", objTaxForm.getTotalIncomeSalary()).setParameter("o", objTaxForm.getOtherIncome())
				.setParameter("ii", objTaxForm.getInterestIncome()).setParameter("ri", objTaxForm.getRentalIncome())
				.setParameter("ppf", objTaxForm.getPpf()).setParameter("med", objTaxForm.getMedicalInsurance())
				.setParameter("edu", objTaxForm.getEducaionLoan()).setParameter("nps", objTaxForm.getNps())
				.setParameter("sav", objTaxForm.getSavingsInterest()).setParameter("ver", "pending")
				.setParameter("tax", objTaxForm.getPayableTax()).setParameter("id1", objTaxForm.getTaxformId())
				.executeUpdate();
		Query query1 = em.createQuery("SELECT c FROM TaxForm t INNER JOIN Customer c on c.pan=t.pan where c.pan=:val")
				.setParameter("val", objTaxForm.getPan());
		Customer c = (Customer) query1.getSingleResult();
		int i1 = em.createQuery("update Customer t set t.taxForm=:tot  where t.customerId=:id1")
				.setParameter("tot", objTaxForm).setParameter("id1", c.getCustomerId()).executeUpdate();
		t1.commit();
		em.close();
		return i1;
	}

	public int fileReturn(TaxForm t) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-pu");
		EntityManager em = emf.createEntityManager();
		EntityTransaction t1 = em.getTransaction();
		t1.begin();
		int i = 0;
		i = em.createQuery("update TaxForm t set t.verifiedStatus=:tot  where t.taxformId=:id1")
				.setParameter("tot", "approvePending").setParameter("id1", t.getTaxformId()).executeUpdate();
		t1.commit();
		em.close();
		return i;
	}

	public int addTaxDetailsForEmployee(TaxForm objTaxForm) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-pu");
		EntityManager em = emf.createEntityManager();
		EntityTransaction t1 = em.getTransaction();
		t1.begin();
		em.persist(objTaxForm);
		t1.commit();
		em.close();
		return 1;
	}

	public Representative getRepresentativeById(String representativeId) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-pu");
		EntityManager em = emf.createEntityManager();
		Representative r = em.find(Representative.class, Long.parseLong(representativeId));
		em.close();
		return r;
	}

	@SuppressWarnings("unchecked")
	public List<TaxForm> getTaxForms() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-pu");
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery("select t from TaxForm t where t.verifiedStatus='pending'");
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<TaxForm> getTaxFormsForAdmin() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-pu");
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery("select t from TaxForm t where t.verifiedStatus='approvePending'");
		return query.getResultList();
	}

	public Customer getCustomerByTaxFormId(String tid) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-pu");
		EntityManager em = emf.createEntityManager();
		Customer c = (Customer) em.createQuery("SELECT c FROM Customer c where c.taxForm.taxformId=:val")
				.setParameter("val", Long.parseLong(tid)).getSingleResult();
		em.close();
		return c;
	}

	public int addNotice(Notice n) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-pu");
		EntityManager em = emf.createEntityManager();
		EntityTransaction t1 = em.getTransaction();
		t1.begin();
		em.persist(n);
		TaxForm t = em.find(TaxForm.class, n.getCustomer().getTaxForm().getTaxformId());
		int i = 0;
		if (n.getNoticeBody().equals("Your Details are Incorrect .Your Application is Rejected."))
			i = em.createQuery("update TaxForm t set t.verifiedStatus=:tot  where t.taxformId=:id1")
					.setParameter("tot", "rejected_r").setParameter("id1", t.getTaxformId()).executeUpdate();
		else if (n.getNoticeBody().contains("Your Application is verified"))
			i = em.createQuery("update TaxForm t set t.verifiedStatus=:tot  where t.taxformId=:id1")
					.setParameter("tot", "verified").setParameter("id1", t.getTaxformId()).executeUpdate();
		else if (n.getNoticeBody().equals("Your Application is Rejected."))
			i = em.createQuery("update TaxForm t set t.verifiedStatus=:tot  where t.taxformId=:id1")
					.setParameter("tot", "rejected_a").setParameter("id1", t.getTaxformId()).executeUpdate();
		else if (n.getNoticeBody().equals("Your Application is Approved."))
			i = em.createQuery("update TaxForm t set t.verifiedStatus=:tot  where t.taxformId=:id1")
					.setParameter("tot", "approved").setParameter("id1", t.getTaxformId()).executeUpdate();
		t1.commit();
		em.close();
		return i;
	}

	@SuppressWarnings("unchecked")
	public List<Notice> viewNoticesOfCustomer(Customer c) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-pu");
		EntityManager em = emf.createEntityManager();
		Query query3 = em.createQuery("select t from Notice t INNER JOIN t.customer c where c.customerId=:id1 ");
		query3.setParameter("id1", c.getCustomerId());
		return query3.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Notice> viewNoticesOfRepresenative(Representative r) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-pu");
		EntityManager em = emf.createEntityManager();
		Query query3 = em
				.createQuery("select t from Notice t INNER JOIN t.representative_n c where c.representativeId=:id1 ");
		query3.setParameter("id1", r.getRepresentativeId());
		return query3.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Notice> viewNoticesOfAdmin(Admin a) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-pu");
		EntityManager em = emf.createEntityManager();
		Query query3 = em.createQuery("select n from Notice n where n.admin_n=:id1").setParameter("id1", a);
		return query3.getResultList();
	}

	public int addTaxDetailsByNewCustomer(TaxForm objTaxForm) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-pu");
		EntityManager em = emf.createEntityManager();
		EntityTransaction t1 = em.getTransaction();
		t1.begin();
		em.persist(objTaxForm);
		Query query1 = em.createQuery("SELECT c FROM TaxForm t INNER JOIN Customer c on c.pan=t.pan where c.pan=:val")
				.setParameter("val", objTaxForm.getPan());
		Customer c = (Customer) query1.getSingleResult();
		int i1 = em.createQuery("update Customer t set t.taxForm=:tot  where t.customerId=:id1")
				.setParameter("tot", objTaxForm).setParameter("id1", c.getCustomerId()).executeUpdate();
		t1.commit();
		em.close();
		return i1;
	}

}
