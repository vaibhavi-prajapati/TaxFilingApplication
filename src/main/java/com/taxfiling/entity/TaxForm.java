package com.taxfiling.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * @author Vaibhavi
 *
 */
@Entity
public class TaxForm {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long taxformId;
	private String pan;
	private double totalIncomeSalary;
	private double hra;
	private double otherIncome;
	private double interestIncome;
	private double rentalIncome;
	private double ppf;
	private double medicalInsurance;
	private double educaionLoan;
	private double houseLoan;
	private double nps;
	private double savingsInterest;
	private double tds;
	private double payableTax;
	private String extraInfo;
	@Column(columnDefinition = " varchar(255) default 'pending'")
	private String verifiedStatus;

	@OneToOne(mappedBy = "taxForm")
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = "representativeId_t")
	private Representative representative_t;

	@ManyToOne
	@JoinColumn(name = "email_t")
	private Admin admin_t;

	public long getTaxformId() {
		return taxformId;
	}

	public void setTaxformId(long taxformId) {
		this.taxformId = taxformId;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public double getTotalIncomeSalary() {
		return totalIncomeSalary;
	}

	public void setTotalIncomeSalary(double totalIncomeSalary) {
		this.totalIncomeSalary = totalIncomeSalary;
	}

	public double getHra() {
		return hra;
	}

	public void setHra(double hra) {
		this.hra = hra;
	}

	public double getOtherIncome() {
		return otherIncome;
	}

	public void setOtherIncome(double otherIncome) {
		this.otherIncome = otherIncome;
	}

	public double getInterestIncome() {
		return interestIncome;
	}

	public void setInterestIncome(double interestIncome) {
		this.interestIncome = interestIncome;
	}

	public double getRentalIncome() {
		return rentalIncome;
	}

	public void setRentalIncome(double rentalIncome) {
		this.rentalIncome = rentalIncome;
	}

	public double getPpf() {
		return ppf;
	}

	public void setPpf(double ppf) {
		this.ppf = ppf;
	}

	public double getMedicalInsurance() {
		return medicalInsurance;
	}

	public void setMedicalInsurance(double medicalInsurance) {
		this.medicalInsurance = medicalInsurance;
	}

	public double getEducaionLoan() {
		return educaionLoan;
	}

	public void setEducaionLoan(double educaionLoan) {
		this.educaionLoan = educaionLoan;
	}

	public double getHouseLoan() {
		return houseLoan;
	}

	public void setHouseLoan(double houseLoan) {
		this.houseLoan = houseLoan;
	}

	public double getNps() {
		return nps;
	}

	public void setNps(double nps) {
		this.nps = nps;
	}

	public double getSavingsInterest() {
		return savingsInterest;
	}

	public void setSavingsInterest(double savingsInterest) {
		this.savingsInterest = savingsInterest;
	}

	public double getTds() {
		return tds;
	}

	public void setTds(double tds) {
		this.tds = tds;
	}

	public double getPayableTax() {
		return payableTax;
	}

	public void setPayableTax(double payableTax) {
		this.payableTax = payableTax;
	}

	public String getExtraInfo() {
		return extraInfo;
	}

	public void setExtraInfo(String extraInfo) {
		this.extraInfo = extraInfo;
	}

	public String getVerifiedStatus() {
		return verifiedStatus;
	}

	public void setVerifiedStatus(String verifiedStatus) {
		this.verifiedStatus = verifiedStatus;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Representative getRepresentative_t() {
		return representative_t;
	}

	public void setRepresentative_t(Representative representative_t) {
		this.representative_t = representative_t;
	}

	public Admin getAdmin_t() {
		return admin_t;
	}

	public void setAdmin_t(Admin admin_t) {
		this.admin_t = admin_t;
	}
}
