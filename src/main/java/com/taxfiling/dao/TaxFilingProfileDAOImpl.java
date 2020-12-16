package com.taxfiling.dao;

import java.util.ArrayList;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.taxfiling.entity.Admin;
import com.taxfiling.entity.Customer;
import com.taxfiling.entity.Employer;
import com.taxfiling.entity.Representative;

public class TaxFilingProfileDAOImpl implements TaxFilingProfileDAO {

	final static Logger logger = Logger.getLogger(TaxFilingProfileDAOImpl.class);

	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-pu");
	private static Scanner sc = new Scanner(System.in);

	public int manageCustomerProfile(String columnName, String id, String newValue) {
		int i = 0;
		long customerId = Long.parseLong(id);
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		if (columnName.equals("isemployee")) {
			Customer cs = em.find(Customer.class, customerId);
			cs.setIsEmployee(false);
			cs.setEmployer(null);

		} else {
			i = em.createQuery(
					"update com.taxfiling.entity.Customer c set c." + columnName + "=:new where c.customerId=:id1")
					.setParameter("new", newValue).setParameter("id1", customerId).executeUpdate();
		}
		em.getTransaction().commit();
		return i;

	}

	public int manageEmployerProfile(String columnName, String id, String newValue) {
		int i = 0;
		long employerId = Long.parseLong(id);
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		i = em.createQuery(
				"update com.taxfiling.entity.Employer e set e." + columnName + "=:new where e.employerId=:id1")
				.setParameter("new", newValue).setParameter("id1", employerId).executeUpdate();

		em.getTransaction().commit();
		return i;
	}

	public int manageRepresentativeProfile(String columnName, String id, String newValue) {
		int i = 0;
		long representativeId = Long.parseLong(id);
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		i = em.createQuery("update com.taxfiling.entity.Representative r set r." + columnName
				+ "=:new where r.representativeId=:id1").setParameter("new", newValue)
				.setParameter("id1", representativeId).executeUpdate();

		em.getTransaction().commit();
		return i;
	}

	public int manageAdminProfile(String columnName, String adminId, String newValue) {
		int i = 0;

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		i = em.createQuery("update com.taxfiling.entity.Admin a set a." + columnName + "=:new where a.email=:id1")
				.setParameter("new", newValue).setParameter("id1", adminId).executeUpdate();

		em.getTransaction().commit();
		return i;
	}

	@SuppressWarnings("unchecked")
	public int manageIsEmployeeStatusProfile(String columnName, String id, String newEmpStatus, String newOrgName) {
		long customerId = Long.parseLong(id);
		int i = 0;
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		boolean newEmpBoolean = false;
		if (newEmpStatus.equalsIgnoreCase("y"))
			newEmpBoolean = true;
		i = em.createQuery("update com.taxfiling.entity.Customer c set c.isEmployee=:new where c.customerId=:id1")
				.setParameter("new", newEmpBoolean).setParameter("id1", customerId).executeUpdate();

		if (newEmpBoolean) {
			ArrayList<Employer> eList = (ArrayList<Employer>) em
					.createQuery("select e from Employer e where e.organization=:id1").setParameter("id1", newOrgName)
					.getResultList();
			Customer cs = em.find(Customer.class, customerId);
			// em.getTransaction().commit();

			int random = (int) (Math.random() * eList.size());
			Employer e = eList.get(random);
			cs.setEmployer(e);
			cs.setIsEmployee(true);
		}
		em.getTransaction().commit();
		return i;
	}

	public int removeCustomer(long cId) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		Customer c = em.find(Customer.class, cId);
		em.remove(c);
		em.getTransaction().commit();
		System.out.println("Customer with " + cId + " is removed from system");
		return 1;
	}

	public int removeRepresentative(long rId) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		Representative r = em.find(Representative.class, rId);
		em.remove(r);
		em.getTransaction().commit();
		System.out.println("Representative with " + rId + " is removed from system");
		return 1;
	}

	public int removeEmployer(long eId) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		Employer e = em.find(Employer.class, eId);
		em.remove(e);
		em.getTransaction().commit();
		System.out.println("Employer with " + eId + " is removed from system");
		return 1;
	}

	// *******************************************************************************************************
	// method for Password Reset using security Question and it's answers
	// *******************************************************************************************************

	public int forgotPassword(int userChoice, long forgotId, String answer) {

		int result = 0;

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		switch (userChoice) {

		case 1:
			Customer c = (Customer) em.createQuery("select c from Customer c where c.customerId=:id1")
					.setParameter("id1", forgotId).getSingleResult();

			if (c.getSecurityAnswer().equalsIgnoreCase(answer)) {
				System.out.println("Enter new Password");
				String newCPassword = sc.nextLine();
				result = em
						.createQuery(
								"update com.taxfiling.entity.Customer c set c.password=:new where c.customerId=:id1")
						.setParameter("new", newCPassword).setParameter("id1", forgotId).executeUpdate();
				em.getTransaction().commit();
			}
			break;

		case 2:
			Representative r = (Representative) em
					.createQuery("select r from Representative r where r.representativeId=:id1")
					.setParameter("id1", forgotId).getSingleResult();

			if (r.getSecurityAnswer().equalsIgnoreCase(answer)) {
				System.out.println("Enter new Password");
				String newRPassword = sc.nextLine();
				result = em.createQuery(
						"update com.taxfiling.entity.Representative r set r.password=:new where r.representativeId=:id1")
						.setParameter("new", newRPassword).setParameter("id1", forgotId).executeUpdate();
				em.getTransaction().commit();
			}
			break;

		case 3:
			Employer e = (Employer) em.createQuery("select e from Employer e where e.employerId=:id1")
					.setParameter("id1", forgotId).getSingleResult();

			if (e.getSecurityAnswer().equalsIgnoreCase(answer)) {
				System.out.println("Enter new Password");
				String newEPassword = sc.nextLine();
				result = em
						.createQuery(
								"update com.taxfiling.entity.Employer e set e.password=:new where e.employerId=:id1")
						.setParameter("new", newEPassword).setParameter("id1", forgotId).executeUpdate();
				em.getTransaction().commit();
			}
			break;

		}
		return result;

	}

	// *******************************************************************************************************
	// below methods are used by admin to display all actors while removal
	// *******************************************************************************************************

	@SuppressWarnings("unchecked")
	public ArrayList<Employer> displayAllEmployers() {

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		ArrayList<Employer> eList = (ArrayList<Employer>) em.createQuery("from Employer").getResultList();
		em.getTransaction().commit();

		return eList;

	}

	@SuppressWarnings("unchecked")
	public ArrayList<Representative> displayAllRepresentatives() {

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		ArrayList<Representative> rList = (ArrayList<Representative>) em.createQuery("from Representative")
				.getResultList();

		em.getTransaction().commit();

		return rList;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Customer> displayAllCustomers() {

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		ArrayList<Customer> cList = (ArrayList<Customer>) em.createQuery("from Customer").getResultList();

		em.getTransaction().commit();
		return cList;

	}

	// *******************************************************************************************************
	// below methods are used for displaying information before and after edit
	// *******************************************************************************************************

	public Admin displayAdminInfo(String id) {

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Query q = em.createQuery("select a from Admin a where a.email=:id1");

		q.setParameter("id1", id);
		Admin a = (Admin) q.getSingleResult();

		em.getTransaction().commit();
		return a;

	}

	public Employer displayEmployerInfo(long employerId) {

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Query q = em.createQuery("select e from Employer e where e.employerId=:employerId");

		q.setParameter("employerId", employerId);
		Employer e = (Employer) q.getSingleResult();
		em.getTransaction().commit();

		return e;
	}

	public Representative displayRepresentativeInfo(long representativeId) {

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Query q = em.createQuery("select r from Representative r where r.representativeId=:representativeId");

		q.setParameter("representativeId", representativeId);
		Representative r = (Representative) q.getSingleResult();

		em.getTransaction().commit();

		return r;

	}

	public Customer displayCustomerInfo(long customerId) {

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Query q = em.createQuery("select c from Customer c where c.customerId=:customerId");

		q.setParameter("customerId", customerId);
		Customer c = (Customer) q.getSingleResult();

		em.getTransaction().commit();
		return c;

	}

}
