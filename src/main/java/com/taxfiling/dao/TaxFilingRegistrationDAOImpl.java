package com.taxfiling.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.taxfiling.entity.Admin;
import com.taxfiling.entity.Customer;
import com.taxfiling.entity.Employer;
import com.taxfiling.entity.Representative;

import org.apache.log4j.Logger;

public class TaxFilingRegistrationDAOImpl implements TaxFilingRegistrationDAO {

	final static Logger logger = Logger.getLogger(TaxFilingProfileDAOImpl.class);

	// *******************************************************************************************************
	// below are Register methods for actors except Admin
	// *******************************************************************************************************

	public int registerCustomer(Customer cs) {
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-pu");
			EntityManager em = emf.createEntityManager();
			EntityTransaction t = em.getTransaction();
			t.begin();
			Admin a = (Admin) em.createQuery("select a from Admin a").getSingleResult();
			cs.setAdmin_c(a);
			// em.merge(cs);
			em.persist(cs);
			t.commit();
			em.close();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int registerRepresentative(Representative rp) {
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-pu");
			EntityManager em = emf.createEntityManager();
			EntityTransaction t = em.getTransaction();
			t.begin();
			em.persist(rp);
			t.commit();
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	public int registerEmployer(Employer ep) {
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-pu");
			EntityManager em = emf.createEntityManager();
			EntityTransaction t = em.getTransaction();
			t.begin();
			em.persist(ep);
			t.commit();
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	public Employer getEmployerByOrganization(String organization) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-pu");
		EntityManager em = emf.createEntityManager();
		Employer e = (Employer) em.createQuery("select e from Employer e where e.organization=:id1")
				.setParameter("id1", organization).getSingleResult();
		em.close();
		return e;
	}

	public Admin getAdminObject() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-pu");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		Admin a = (Admin) em.createQuery("select a from Admin a").getSingleResult();
		em.getTransaction().commit();
		em.close();
		return a;
	}
}
