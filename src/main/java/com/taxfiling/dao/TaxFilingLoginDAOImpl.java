package com.taxfiling.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.taxfiling.entity.Admin;
import com.taxfiling.entity.Customer;
import com.taxfiling.entity.Employer;
import com.taxfiling.entity.Representative;

import org.apache.log4j.Logger;

public class TaxFilingLoginDAOImpl implements TaxFilingLoginDAO {

	final static Logger logger = Logger.getLogger(TaxFilingProfileDAOImpl.class);

	// *******************************************************************************************************
	// below are Login methods for all actors
	// *******************************************************************************************************

	public boolean loginCustomer(String customerId, String customerPass) {

		long loginCustomerId = Long.parseLong(customerId);
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-pu");
		EntityManager em = emf.createEntityManager();
		boolean loginCFlag = false;
		em.getTransaction().begin();
		Customer c = (Customer) em.createQuery("select c from Customer c where c.customerId=:id1")
				.setParameter("id1", loginCustomerId).getSingleResult();
		if (c.getCustomerId() == loginCustomerId && c.getPassword().equals(customerPass)) {
			loginCFlag = true;
		}

		return loginCFlag;
	}

	public boolean loginRepresentative(String representativeId, String representativePass) {

		long loginRepresentativeId = Long.parseLong(representativeId);
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-pu");
		EntityManager em = emf.createEntityManager();
		boolean loginEFlag = false;
		em.getTransaction().begin();
		Representative r = (Representative) em
				.createQuery("select r from Representative r where r.representativeId=:id1")
				.setParameter("id1", loginRepresentativeId).getSingleResult();
		if (r.getRepresentativeId() == loginRepresentativeId && r.getPassword().equals(representativePass)) {
			loginEFlag = true;
		}

		return loginEFlag;
	}

	public boolean loginEmployer(String employerId, String employerPass) {

		long longinEmployerId = Long.parseLong(employerId);
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-pu");
		EntityManager em = emf.createEntityManager();
		boolean loginEFlag = false;
		em.getTransaction().begin();
		Employer e = (Employer) em.createQuery("select e from Employer e where e.employerId=:id1")
				.setParameter("id1", longinEmployerId).getSingleResult();
		if (e.getEmployerId() == longinEmployerId && e.getPassword().equals(employerPass)) {
			loginEFlag = true;
		}

		return loginEFlag;
	}

	public boolean loginAdmin(String adminId, String adminPass) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-pu");
		EntityManager em = emf.createEntityManager();
		boolean loginEFlag = false;
		em.getTransaction().begin();
		Admin a = (Admin) em.createQuery("select a from Admin a").getSingleResult();
		if (a.getEmail().equals(adminId) && a.getPassword().equals(adminPass)) {
			loginEFlag = true;
		}

		return loginEFlag;
	}

}
