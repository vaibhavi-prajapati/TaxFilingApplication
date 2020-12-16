package com.taxfiling.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * @author Vaibhavi
 *
 */
@Entity
public class Representative {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long representativeId;
	private String name;
	private String email;
	private String password;
	private String securityQuestion;
	private String securityAnswer;
	private String contactNo;

	// @OneToMany(mappedBy = "representative")
	// private List<Customer> customers;

	@ManyToOne
	@JoinColumn(name = "email_r")
	private Admin admin_r;

	@OneToMany(mappedBy = "representative_n")
	private List<Notice> notices;

	@OneToMany(mappedBy = "representative_t")
	private List<TaxForm> taxForms;

	public long getRepresentativeId() {
		return representativeId;
	}

	public void setRepresentativeId(long representativeId) {
		this.representativeId = representativeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSecurityQuestion() {
		return securityQuestion;
	}

	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}

	public String getSecurityAnswer() {
		return securityAnswer;
	}

	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public Admin getAdmin_r() {
		return admin_r;
	}

	public void setAdmin_r(Admin admin_r) {
		this.admin_r = admin_r;
	}

	public List<Notice> getNotices() {
		return notices;
	}

	public void setNotices(List<Notice> notices) {
		this.notices = notices;
	}

	public List<TaxForm> getTaxForms() {
		return taxForms;
	}

	public void setTaxForms(List<TaxForm> taxForms) {
		this.taxForms = taxForms;
	}
}
