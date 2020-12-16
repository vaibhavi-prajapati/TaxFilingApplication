package com.taxfiling.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.taxfiling.entity.Admin;
import com.taxfiling.entity.Customer;
import com.taxfiling.entity.Employer;
import com.taxfiling.entity.Notice;
import com.taxfiling.entity.Representative;
import com.taxfiling.entity.TaxForm;
import com.taxfiling.service.TaxFilingService;
import com.taxfiling.service.TaxFilingServiceInterface;

public class TaxFilingController implements TaxFilingControllerInterface {

	private static TaxFilingController tfc = new TaxFilingController();
	private static Scanner sc = new Scanner(System.in);
	private static TaxFilingServiceInterface tfs = new TaxFilingService();

	public void registerActor() {

		System.out.println("Register as:");
		System.out.print("1.Customer\t2.Employer\t3.Representative\nEnter choice:");
		int choice = Integer.parseInt(sc.nextLine());
		switch (choice) {
		case 1:
			tfc.registerCustomer();
			break;
		case 2:
			tfc.registerEmployer();
			break;
		case 3:
			tfc.registerRepresentative();
			break;
		default:
			System.out.println("Invalid Choice");
		}
	}

	private void registerCustomer() {
		Customer cs = new Customer();
		Employer e = null;
		System.out.print("Enter your name: ");
		String name = sc.nextLine();
		System.out.print("Enter your email: ");
		String email = sc.nextLine();
		System.out.print("Enter your password: ");
		String password = sc.nextLine();
		System.out.print("Enter your address: ");
		String address = sc.nextLine();
		System.out.print("Enter your PAN: ");
		String pan = sc.nextLine();
		System.out.print("Enter your contact no: ");
		String con = sc.nextLine();
		System.out.print("Enter your account no: ");
		String acc = sc.nextLine();
		System.out.print("Enter your marital status(single/married): ");
		String mar = sc.nextLine();
		System.out.print("Are you employee?(Y/N): ");

		if (sc.nextLine().equalsIgnoreCase("Y")) {
			System.out.print("What is your organization name?: ");
			String organization = sc.nextLine().toLowerCase();
			e = tfs.getEmployerByOrganization(organization);
			cs.setIsEmployee(true);
		}

		System.out.print("Enter your Date of Birth in dd-mm-yyyy format: ");
		String date = sc.nextLine();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate date2 = LocalDate.parse(date, formatter);

		String queAndAns[] = tfc.getQuestionAndAnswer();

		cs.setName(name);
		cs.setEmail(email);
		cs.setPassword(password);
		cs.setAddress(address);
		cs.setPan(pan);
		cs.setContactNo(con);
		cs.setAccountNo(acc);
		cs.setMaritalStatus(mar);
		cs.setSecurityQuestion(queAndAns[0]);
		cs.setSecurityAnswer(queAndAns[1]);
		cs.setDateOfBirth(date2);
		cs.setEmployer(e);
		int i = tfs.registerCustomer(cs);
		if (i > 0) {
			System.out.println("Registration successful");
			System.out.println("For security reasons please go and Login");
		} else {
			System.out.println("Registration process failed, Try after some time");
		}
	}

	private int registerEmployer() {
		Employer ep = new Employer();
		System.out.print("Enter your email: ");
		String email = sc.nextLine();
		System.out.print("enter your password: ");
		String pass = sc.nextLine();
		System.out.print("enter your contact no: ");
		String con = sc.nextLine();
		System.out.print("enter your organization: ");
		String org = sc.nextLine();

		String queAndAns[] = tfc.getQuestionAndAnswer();

		Admin a = tfs.getAdminObject();

		ep.setEmail(email);
		ep.setPassword(pass);
		ep.setContactNo(con);
		ep.setOrganization(org);
		ep.setSecurityQuestion(queAndAns[0]);
		ep.setSecurityAnswer(queAndAns[1]);
		ep.setAdmin_e(a);

		int i = tfs.registerEmployer(ep);
		if (i > 0) {
			System.out.println("Registration successful");
			System.out.println("For security reason please go and Login");
		} else {
			System.out.println("Registration process failed, Try after some time");
		}
		return 1;

	}

	private void registerRepresentative() {
		Representative rp = new Representative();

		System.out.print("Enter your name: ");
		String orgName = sc.nextLine();
		System.out.print("Enter your email: ");
		String email = sc.nextLine();
		System.out.print("Enter your password: ");
		String pass = sc.nextLine();
		System.out.print("Enter your contact no: ");
		String con = sc.nextLine();

		String queAndAns[] = tfc.getQuestionAndAnswer();

		Admin a = tfs.getAdminObject();

		rp.setName(orgName);
		rp.setEmail(email);
		rp.setPassword(pass);
		rp.setContactNo(con);
		rp.setAdmin_r(a);
		rp.setSecurityQuestion(queAndAns[0]);
		rp.setSecurityAnswer(queAndAns[1]);

		int i = tfs.registerRepresentative(rp);
		if (i > 0) {
			System.out.println("Registration successful");
			System.out.println("For security reasons go and Login");
		} else {
			System.out.println("Registration process failed, Try after some time");
		}
	}

	// *******************************************************************************************************
	// below method is for Security Question and Answer collection
	// *******************************************************************************************************

	private String[] getQuestionAndAnswer() {
		List<String> list = new ArrayList<String>();
		System.out.println("Choose which security question would u like to answer");
		list.add("1.what is your nickname?");
		list.add("2.what is place of birth?");
		list.add("3.What is your fathers name?");
		String queAndAns[] = new String[2];
		for (String s : list) {
			System.out.println(s);
		}
		System.out.println("Enter your choice");
		int queChoice = Integer.parseInt(sc.nextLine());
		String question = list.get((queChoice - 1));
		queAndAns[0] = question;
		System.out.println("Give answer for above question");
		queAndAns[1] = sc.nextLine();

		return queAndAns;
	}

	public void loginActor() throws NumberFormatException, IOException {

		String repeatLoginPage = "n";
		do {
			System.out.println("Log in as");
			System.out.print("1.Customer\t2.Employer\t3.Representative\t4.Admin\nEnter choice: ");
			int choice = Integer.parseInt(sc.nextLine());
			switch (choice) {
			case 1:
				System.out.print("Enter your id: ");
				String customerId = sc.nextLine();
				System.out.print("Enter your password: ");
				String customerPass = sc.nextLine();
				boolean loginCFlag = tfs.loginCustomer(customerId, customerPass);
				int custChoice = 0;
				if (loginCFlag) {
					while (custChoice != 5) {
						System.out.print(
								"1.Edit Profile\t2.Add Tax Details\t3.File Return\t4.View Notices\t5.Logout\nEnter choice: ");
						custChoice = Integer.parseInt(sc.nextLine());

						switch (custChoice) {
						case 1:
							tfc.manageCustomer(customerId);
							break;
						case 2:
							Customer c = tfs.getCustomerById(customerId);
							tfc.addTaxDetails(c);
							break;
						case 3:
							Customer c2 = tfs.getCustomerById(customerId);
							tfc.fileReturn(c2);
							break;
						case 4:
							Customer c3 = tfs.getCustomerById(customerId);
							List<Notice> notices = tfs.viewNoticesOfCustomer(c3);
							if (!notices.isEmpty()) {
								System.out.println("You have received following notices:");
								for (Notice n : notices) {
									Admin aa = n.getAdmin_n();
									if (aa == null)
										System.out.println("Notice from Representative:" + n.getNoticeBody());
									else
										System.out.println("Notice from Admin: " + n.getNoticeBody());
								}
							} else
								System.out.println("No notices received yet!");
							break;
						default:
							System.out.println("You are logged out. Have a nice day!");
							break;
						}
					}
				} else {
					System.out.println("Wrong credentials. Try again!");
				}
				break;

			case 2:
				System.out.print("Enter your id: ");
				String employerId = sc.nextLine();
				System.out.print("Enter your password: ");
				String employerPass = sc.nextLine();
				boolean loginEFlag = tfs.loginEmployer(employerId, employerPass);
				if (loginEFlag) {
					while (true) {
						System.out.print("\n1.Edit Profile\t2.Add Tax Details for Employee\t3.Logout\nEnter choice: ");
						int empChoice = Integer.parseInt(sc.nextLine());
						if (empChoice == 1)
							tfc.manageEmployer(employerId);
						else if (empChoice == 2) {
							tfc.addTaxDetailsForEmployee();
						} else {
							System.out.println("You are logged out. Have a nice day!");
							break;
						}
					}
				} else {
					System.out.println("Wrong credentials. Try again!");
				}
				break;
			case 3:
				System.out.print("Enter your id: ");
				String representativeId = sc.nextLine();
				System.out.print("Enter your password: ");
				String representativePass = sc.nextLine();
				boolean loginRFlag = tfs.loginRepresentative(representativeId, representativePass);
				if (loginRFlag) {
					while (true) {
						System.out.print(
								"1.Edit Profile\t2.Verify TaxForms\t3.View Sent Notices\t4.Logout\nEnter choice: ");
						int repChoice = Integer.parseInt(sc.nextLine());
						if (repChoice == 1)
							tfc.manageRepresentative(representativeId);
						else if (repChoice == 2) {
							Representative r = tfs.getRepresentativeById(representativeId);
							tfc.verifyTaxForms(r);
						} else if (repChoice == 3) {

							Representative r = tfs.getRepresentativeById(representativeId);
							List<Notice> notices = tfs.viewNoticesOfRepresentative(r);
							if (!notices.isEmpty()) {
								System.out.println("You have sent Notices to Following Customer Id's:");
								for (Notice n : notices) {
									Customer c = n.getCustomer();
									System.out.println("Customer " + c.getCustomerId() + ": " + n.getNoticeBody());
								}
							} else {
								System.out.println("No notices sent yet.");
							}
						} else {
							System.out.println("You are logged out. Have a nice day!");
							break;
						}
					}
				} else {
					System.out.println("Wrong credentials. Try again!");
				}
				break;
			case 4:
				System.out.print("Enter admin id: ");
				String adminId = sc.nextLine();
				System.out.print("Enter admin password: ");
				String adminPass = sc.nextLine();
				boolean loginAFlag = tfs.loginAdmin(adminId, adminPass);
				if (loginAFlag) {
					while (true) {
						System.out.print(
								"1.Edit Profile\t2.Remove Actors\t3.View Return Files\t4.View Notices\t5.Logout\nEnter choice: ");
						int adminChoice = Integer.parseInt(sc.nextLine());
						if (adminChoice == 1)
							tfc.manageAdmin(adminId);
						else if (adminChoice == 2)
							tfc.removeActors();
						else if (adminChoice == 3) {
							Admin a = tfs.getAdminObject();
							tfc.verifyTaxForms(a);
						} else if (adminChoice == 4) {
							Admin a = tfs.getAdminObject();
							List<Notice> notices = tfs.viewNoticesOfAdmin(a);
							if (!notices.isEmpty()) {
								System.out.println("You have sent Notices to Following Customer Id's:");
								for (Notice n : notices) {
									Customer c = n.getCustomer();
									System.out.println("Customer " + c.getCustomerId() + ": " + n.getNoticeBody());
								}
							} else {
								System.out.println("No notices sent yet.");
							}
						} else {
							System.out.println("You are logged out. Have a nice day");
							break;
						}
					}
				} else {
					System.out.println("Wrong credentials. Try again!");
				}
			default:
				System.out.println("Incorrct choice. Try again!");
				break;
			}
			System.out.print("\nDo you want to go on login page(y/n)?: ");
			repeatLoginPage = sc.nextLine();
		} while (repeatLoginPage.equalsIgnoreCase("y"));
	}

	public void verifyTaxForms(Admin a) {
		List<TaxForm> tfList = tfs.getTaxFormsForAdmin();
		if (!tfList.isEmpty()) {
			for (TaxForm ct : tfList) {
				System.out.println("Tax Form Id: " + ct.getTaxformId());
				System.out.print("\tBasic Salary: " + ct.getTotalIncomeSalary());
				System.out.print("\tOther Income: " + ct.getOtherIncome());
				System.out.print("\tInterest Income: " + ct.getInterestIncome());
				System.out.println("\tRental Income: " + ct.getRentalIncome());
				System.out.print("\tPPF: " + ct.getPpf());
				System.out.print("\tMedical Insurance: " + ct.getMedicalInsurance());
				System.out.print("\tEducation Loan: " + ct.getEducaionLoan());
				System.out.print("\tHouse Loan: " + ct.getHouseLoan());
				System.out.print("\tNPS: " + ct.getNps());
				System.out.print("\tSavings Interest: " + ct.getSavingsInterest());
				System.out.print("\ttds: " + ct.getTds());
				System.out.println("\tTotal Tax: " + ct.getPayableTax());
				if (ct.getPayableTax() - ct.getTds() > 0)
					System.out.println("\tTotal Payable tax: " + (ct.getPayableTax() - ct.getTds()));
				else if ((ct.getPayableTax() - ct.getTds()) < 0)
					System.out.println("\tRefund amount to customer: " + (ct.getPayableTax() - ct.getTds() * (-1)));
				else
					System.out.println("\tNo tax to be paid");
			}
			System.out.print("Enter Tax Form Id to verify: ");
			String tid = sc.nextLine();
			System.out.println("Enter 1 to Approve Tax form");
			System.out.print("Enter 2 to Reject Tax form: ");
			String choice = sc.nextLine();
			if (choice.contentEquals("1")) {
				Notice n = new Notice();
				Customer c = tfs.getCustomerByTaxFormId(tid);
				n.setNoticeBody("Your Application is Approved.");
				n.setCustomer(c);
				n.setAdmin_n(a);
				int i = tfs.addNotice(n);
				if (i > 0)
					System.out.println("Approval notice is sent to customer.");
				else
					System.out.println("An error occured!");
			} else if (choice.contentEquals("2")) {
				Notice n = new Notice();
				Customer c = tfs.getCustomerByTaxFormId(tid);
				n.setNoticeBody("Your Application is Rejected.");
				n.setCustomer(c);
				n.setAdmin_n(a);
				int i = tfs.addNotice(n);
				if (i > 0)
					System.out.println("Rejection notice is sent to customer.");
				else
					System.out.println("An error occured!");
			} else {
				System.out.println("Wrong choice. Try again!");
			}
		} else
			System.out.println("No Taxforms yet filed.");
	}

	public void verifyTaxForms(Representative r) {
		List<TaxForm> tfList = tfs.getTaxForms();
		if (!tfList.isEmpty()) {
			for (TaxForm ct : tfList) {
				System.out.println("Tax Form Id: " + ct.getTaxformId());
				System.out.print("\tBasic Salary: " + ct.getTotalIncomeSalary());
				System.out.print("\tOther Income: " + ct.getOtherIncome());
				System.out.print("\tInterest Income: " + ct.getInterestIncome());
				System.out.println("\tRental Income: " + ct.getRentalIncome());
				System.out.print("\tPPF: " + ct.getPpf());
				System.out.print("\tMedical Insurance: " + ct.getMedicalInsurance());
				System.out.print("\tEducation Loan: " + ct.getEducaionLoan());
				System.out.print("\tHouse Loan: " + ct.getHouseLoan());
				System.out.print("\tNPS: " + ct.getNps());
				System.out.print("\tSavings Interest: " + ct.getSavingsInterest());
				System.out.print("\ttds: " + ct.getTds());
				System.out.println("\tTotal Tax: " + ct.getPayableTax());
				if (ct.getPayableTax() - ct.getTds() > 0)
					System.out.println("\tTotal Payable tax: " + (ct.getPayableTax() - ct.getTds()));
				else if ((ct.getPayableTax() - ct.getTds()) < 0)
					System.out.println("\tRefund amount to customer: " + (ct.getPayableTax() - ct.getTds() * (-1)));
				else
					System.out.println("\tNo tax to be paid");
			}
			System.out.print("Enter Tax Form Id to verify: ");
			String tid = sc.nextLine();
			System.out.println("Enter 1 to Approve Tax form");
			System.out.print("Enter 2 to Reject Tax form: ");
			String choice = sc.nextLine();
			if (choice.contentEquals("1")) {
				Notice n = new Notice();
				Customer c = tfs.getCustomerByTaxFormId(tid);
				TaxForm tf = tfs.getTaxFromByPan(c.getPan());
				double tax = tf.getPayableTax() - tf.getTds();
				if (tax > 0)
					n.setNoticeBody("Your Application is verified. Total tax to be paid is " + tax);
				else if (tax < 0)
					n.setNoticeBody(
							"Your Application is verified. You are eligible for a refund of Rs." + (tax * (-1)));
				else
					n.setNoticeBody("Your Application is verified. You dont have to pay tax");
				n.setCustomer(c);
				n.setRepresentative_n(r);
				int i = tfs.addNotice(n);
				if (i > 0)
					System.out.println("Verified notice is sent to customer.");
				else
					System.out.println("An error occured!");
			} else if (choice.contentEquals("2")) {
				Notice n = new Notice();
				Customer c = tfs.getCustomerByTaxFormId(tid);
				n.setNoticeBody("Your Details are Incorrect .Your Application is Rejected.");
				n.setCustomer(c);
				n.setRepresentative_n(r);
				int i = tfs.addNotice(n);
				if (i > 0)
					System.out.println("Rejection notice is sent to customer.");
				else
					System.out.println("An error occured!");
			} else {
				System.out.println("Wrong choice. Try again!");
			}
		} else
			System.out.println("No Taxforms yet to verify.");
	}

	public void addTaxDetailsForEmployee() {
		TaxForm objTaxForm = new TaxForm();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Enter details of your Employee below.");

			System.out.print("Enter your Employee's PAN: ");
			objTaxForm.setPan(br.readLine());

			System.out.print("Enter your Employee's date of birth in dd-mm-yyyy format: ");
			objTaxForm.setExtraInfo(br.readLine());

			System.out.print("Enter Income Salary without deduction: ");
			objTaxForm.setTotalIncomeSalary(Double.parseDouble(br.readLine()));

			System.out.print("Enter income from other taxable sources: ");
			objTaxForm.setOtherIncome(Double.parseDouble(br.readLine()));

			System.out.print("Enter Employee's income from interest: ");
			objTaxForm.setInterestIncome(Double.parseDouble(br.readLine()));

			System.out.print("Enter rental income received on let-out properties: ");
			objTaxForm.setRentalIncome(Double.parseDouble(br.readLine()));

			System.out.print("Enter amount invested in PPF, ELSS, LIC: ");
			objTaxForm.setPpf(Double.parseDouble(br.readLine()));

			System.out.print("Enter medical insurance premium: ");
			objTaxForm.setMedicalInsurance(Double.parseDouble(br.readLine()));

			System.out.print("Enter amount of Interest on Edcational loan if any: ");
			objTaxForm.setEducaionLoan(Double.parseDouble(br.readLine()));

			System.out.print("Enter amount contributed NPS(80 CCD): ");
			objTaxForm.setNps(Double.parseDouble(br.readLine()));

			System.out.print("Enter interest received from bank accounts: ");
			objTaxForm.setSavingsInterest(Double.parseDouble(br.readLine()));

			System.out.print("Enter interest paid on Home Loan: ");
			objTaxForm.setHouseLoan(Double.parseDouble(br.readLine()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		LocalDate today = LocalDate.now(); // Today's date
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate date2 = LocalDate.parse(objTaxForm.getExtraInfo(), formatter);
		Period p = Period.between(date2, today);
		int age = p.getYears();

		// Gross Total Income
		double gti = objTaxForm.getTotalIncomeSalary() + objTaxForm.getOtherIncome() + objTaxForm.getInterestIncome()
				+ objTaxForm.getRentalIncome();

		// Section 80C
		double d1;
		if (objTaxForm.getPpf() > 150000)
			d1 = 150000;
		else
			d1 = objTaxForm.getPpf();

		// Section 80D
		double d2;
		if (age > 60) {
			if (objTaxForm.getMedicalInsurance() > 50000)
				d2 = 50000;
			else
				d2 = objTaxForm.getMedicalInsurance();
		} else {
			if (objTaxForm.getMedicalInsurance() > 25000)
				d2 = 25000;
			else
				d2 = objTaxForm.getMedicalInsurance();
		}

		// Section 80CCD NPS+APY
		double d3;
		if (objTaxForm.getNps() > 50000)
			d3 = 50000;
		else
			d3 = objTaxForm.getNps();

		// Section 80TTA
		double d4;
		if (objTaxForm.getSavingsInterest() > 10000)
			d4 = 10000;
		else
			d4 = objTaxForm.getSavingsInterest();

		// Section 80EEA
		double d5;
		if (objTaxForm.getHouseLoan() > 200000)
			d5 = 200000;
		else
			d5 = objTaxForm.getHouseLoan();

		// Total Deductions
		double td = d1 + d2 + d3 + d4 + d5;

		// Total taxable income after deductions
		double ti = gti - td;

		// Tax amount
		double tax1 = 0;
		if (age <= 60) {
			if (ti <= 250000)
				tax1 = 0;
			else if (ti > 250000 && ti <= 500000)
				tax1 = 5 / 100.0 * ti;
			else if (ti > 500000 && ti <= 1000000)
				tax1 = 12500 + 0.2 * ti;
			else if (ti > 1000000)
				tax1 = 112500 + (30 / 100.0 * ti);
		} else if (age > 60 && age <= 80) {
			if (ti <= 300000)
				tax1 = 0;
			else if (ti > 300000 && ti <= 500000)
				tax1 = 5 / 100.0 * ti;
			else if (ti > 500000 && ti <= 1000000)
				tax1 = 10000 + (20 / 100.0 * ti);
			else if (ti > 1000000)
				tax1 = 110000 + (30 / 100.0 * ti);
		} else if (age > 80) {
			if (ti <= 500000)
				tax1 = 0;
			else if (ti > 500000 && ti <= 1000000)
				tax1 = 20 / 100.0 * ti;
			else if (ti > 1000000)
				tax1 = 100000 + (30 / 100.0 * ti);
		}
		objTaxForm.setTds(tax1);
		objTaxForm.setVerifiedStatus("none");

		int i = tfs.addTaxDetailsForEmployee(objTaxForm);
		if (i > 0)
			System.out.println("Tax details of your employee added successfuly.");
		else
			System.err.println("An error occured!");
	}

	public void fileReturn(Customer c) {
		if (c.getTaxForm() != null) {
			TaxForm t = tfs.getTaxFromByPan(c.getPan());
			if (t.getVerifiedStatus().equals("pending")) {
				System.out.println("Your taxForm is yet to be verified by representative. Check after some time.");
			} else if (t.getVerifiedStatus().equals("verified")) {
				int i = tfs.fileReturn(t);
				if (i > 0)
					System.out.println("File has been successfuly returned. Wait till admin approves the same.");
				else
					System.err.println("An error occurred!");
			} else if (t.getVerifiedStatus().equals("approved")) {
				System.out.println("Your file return is already approved.");
			} else if (t.getVerifiedStatus().equals("none")) {
				System.out.println("Add tax details first!");
			} else if (t.getVerifiedStatus().equals("approvePending")) {
				System.out.println("Your file return is yet to be approved by admin.");
			} else if (t.getVerifiedStatus().equals("rejected_r")) {
				System.out.println("Your file return is rejected by representative.");
			} else if (t.getVerifiedStatus().equals("rejected_a")) {
				System.out.println("Your file return is rejected by admin.");
			}
		} else {
			System.out.println("Add tax details first!");
		}
	}

	public void addTaxDetails(Customer c) {

		TaxForm objTaxForm;
		if (c.getIsEmployee())
			objTaxForm = tfs.getTaxFromByPan(c.getPan());
		else {
			objTaxForm = new TaxForm();
			objTaxForm.setPan(c.getPan());
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter your details below.");
		try {
			System.out.print("Enter Income Salary without deduction: ");
			objTaxForm.setTotalIncomeSalary(Double.parseDouble(br.readLine()));

			System.out.print("Enter income from other taxable sources: ");
			objTaxForm.setOtherIncome(Double.parseDouble(br.readLine()));

			System.out.print("Enter income from interest: ");
			objTaxForm.setInterestIncome(Double.parseDouble(br.readLine()));

			System.out.print("Enter rental income received on let-out properties: ");
			objTaxForm.setRentalIncome(Double.parseDouble(br.readLine()));

			System.out.print("Enter amount invested in PPF, ELSS, LIC: ");
			objTaxForm.setPpf(Double.parseDouble(br.readLine()));

			System.out.print("Enter medical insurance premium: ");
			objTaxForm.setMedicalInsurance(Double.parseDouble(br.readLine()));

			System.out.print("Enter amount of Interest on Edcational loan if any: ");
			objTaxForm.setEducaionLoan(Double.parseDouble(br.readLine()));

			System.out.print("Enter amount contributed NPS(80 CCD): ");
			objTaxForm.setNps(Double.parseDouble(br.readLine()));

			System.out.print("Enter interest received from bank accounts: ");
			objTaxForm.setSavingsInterest(Double.parseDouble(br.readLine()));

			System.out.print("Enter interest paid on Home Loan: ");
			objTaxForm.setHouseLoan(Double.parseDouble(br.readLine()));
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		LocalDate today = LocalDate.now(); // Today's date
		Period p = Period.between(c.getDateOfBirth(), today);
		int age = p.getYears();

		// Gross Total Income
		double gti = objTaxForm.getTotalIncomeSalary() + objTaxForm.getOtherIncome() + objTaxForm.getInterestIncome()
				+ objTaxForm.getRentalIncome();

		// Section 80C
		double d1;
		if (objTaxForm.getPpf() > 150000)
			d1 = 150000;
		else
			d1 = objTaxForm.getPpf();

		// Section 80D
		double d2;
		if (age > 60) {
			if (objTaxForm.getMedicalInsurance() > 50000)
				d2 = 50000;
			else
				d2 = objTaxForm.getMedicalInsurance();

		} else {
			if (objTaxForm.getMedicalInsurance() > 25000)
				d2 = 25000;
			else
				d2 = objTaxForm.getMedicalInsurance();
		}

		// Section 80CCD NPS+APY
		double d3;
		if (objTaxForm.getNps() > 50000)
			d3 = 50000;
		else
			d3 = objTaxForm.getNps();

		// Section 80TTA
		double d4;
		if (objTaxForm.getSavingsInterest() > 10000)
			d4 = 10000;
		else
			d4 = objTaxForm.getSavingsInterest();

		// Section 80EEA
		double d5;
		if (objTaxForm.getHouseLoan() > 200000)
			d5 = 200000;
		else
			d5 = objTaxForm.getHouseLoan();

		// Total Deductions
		double td = d1 + d2 + d3 + d4 + d5;

		// Total taxable income after deductions
		double ti = gti - td;

		// Tax amount
		double tax1 = 0;
		if (age <= 60) {
			if (ti <= 250000)
				tax1 = 0;
			else if (ti > 250000 && ti <= 500000)
				tax1 = 5 / 100.0 * ti;
			else if (ti > 500000 && ti <= 1000000)
				tax1 = (double) (12500 + 0.2 * ti);
			else if (ti > 1000000)
				tax1 = 112500 + (30 / 100.0 * ti);

		} else if (age > 60 && age <= 80) {
			if (ti <= 300000)
				tax1 = 0;
			else if (ti > 300000 && ti <= 500000)
				tax1 = 5 / 100.0 * ti;
			else if (ti > 500000 && ti <= 1000000)
				tax1 = 10000 + (20 / 100.0 * ti);
			else if (ti > 1000000)
				tax1 = 110000 + (30 / 100.0 * ti);

		} else if (age > 80) {
			if (ti <= 500000)
				tax1 = 0;
			else if (ti > 500000 && ti <= 1000000)
				tax1 = 20 / 100.0 * ti;
			else if (ti > 1000000)
				tax1 = 100000 + (30 / 100.0 * ti);

		}
		objTaxForm.setPayableTax(tax1);
		objTaxForm.setVerifiedStatus("pending");

		int i;
		if (c.getIsEmployee())
			i = tfs.addTaxDetailsByCustomer(objTaxForm);
		else
			i = tfs.addTaxDetailsByNewCustomer(objTaxForm);

		if (i > 0)
			System.out.println(
					"Details added succesfully. Wait till a reprentative sends notice and then you can file returns.");
		else
			System.err.println("An error occured!");
	}

	// *******************************************************************************************************
	// below method is Forgot Password for actors except Admin
	// *******************************************************************************************************

	public void forgotPassword() {

		System.out.println("Are you 1.Customer/Employee   2.Representative   3.Employer");
		int forgotChoice = Integer.parseInt(sc.nextLine());
		// int queChoice=0;
		switch (forgotChoice) {

		case 1:
			System.out.println("Enter your id");
			long forgotCId = Long.parseLong(sc.nextLine());
			tfc.displayQuestions();
			System.out.println("Give ans for Question");
			String cAnswer = sc.nextLine();
			int cResult = tfs.forgotPassword(1, forgotCId, cAnswer);
			tfc.displayPassSuccessOrNot(cResult);
			break;

		case 2:
			System.out.println("Enter your id");
			long forgotRId = Long.parseLong(sc.nextLine());
			tfc.displayQuestions();
			System.out.println("Give ans for Question");
			String rAnswer = sc.nextLine();
			int rResult = tfs.forgotPassword(2, forgotRId, rAnswer);
			tfc.displayPassSuccessOrNot(rResult);
			break;

		case 3:
			System.out.println("Enter your id");
			long forgotEId = Long.parseLong(sc.nextLine());
			tfc.displayQuestions();
			System.out.println("Give ans for Question");
			String eAnswer = sc.nextLine();
			int eResult = tfs.forgotPassword(3, forgotEId, eAnswer);
			tfc.displayPassSuccessOrNot(eResult);
			break;

		default:
			System.out.println("Invalid Choice");
		}
	}

	// *******************************************************************************************************
	// method for password reset successful or not
	// *******************************************************************************************************

	public void displayPassSuccessOrNot(int result) {
		if (result == 1)
			System.out.println("Your password has been reset successfully! please go and login");
		else
			System.out.println("Your Question/Answer does not match, Please try again");
	}

	// *******************************************************************************************************
	// method for display questions
	// *******************************************************************************************************

	public void displayQuestions() {
		System.out.println("Choose which security question you had given while registration");
		System.out.println("1.what is your nickname?");
		System.out.println("2.what is place of birth?");
		System.out.println("3.What is your fathers name?");
	}

	@SuppressWarnings("unused")
	public void manageCustomer(String customerId) {

		System.out.println("Your inforamtion is:");
		tfc.displayCustomerInfo(customerId);
		String columnName = "";
		int i = 0;

		String ans = "y";
		do {
			System.out.println("\nWhat do you  want to edit:");
			System.out.println(
					"1.Name  2.Email  3.Password  4.Pan Number  5.Contact Number  6. Bank Account Number  7.Marital Status  8.Address  9.Is Employee?");
			System.out.println("\nEnter your choice:");
			int choice = Integer.parseInt(sc.nextLine());

			switch (choice) {

			case 1:
				columnName = "name";
				System.out.println("Enter new Name");
				String newName = sc.nextLine();
				i = tfs.manageCustomerProfile(columnName, customerId, newName);
				System.out.println("\nYour updated infomration is:");
				tfc.displayCustomerInfo(customerId);
				break;

			case 2:
				columnName = "email";
				System.out.println("Enter new Email");
				String newEmail = sc.nextLine();
				i = tfs.manageCustomerProfile(columnName, customerId, newEmail);
				System.out.println("\nYour updated infomration is:");
				tfc.displayCustomerInfo(customerId);
				break;

			case 3:
				columnName = "password";
				System.out.println("Enter new password");
				String newPass = sc.nextLine();
				i = tfs.manageCustomerProfile(columnName, customerId, newPass);
				System.out.println("\nYour updated infomration is:");
				tfc.displayCustomerInfo(customerId);
				break;

			case 4:
				columnName = "pan";
				System.out.println("Enter new pancard Number");
				String newPan = sc.nextLine();
				i = tfs.manageCustomerProfile(columnName, customerId, newPan);
				System.out.println("\nYour updated infomration is:");
				tfc.displayCustomerInfo(customerId);
				break;

			case 5:
				columnName = "contactNo";
				System.out.println("Enter new Contact Number");
				String newContact = sc.nextLine();
				i = tfs.manageCustomerProfile(columnName, customerId, newContact);
				System.out.println("\nYour updated infomration is:");
				tfc.displayCustomerInfo(customerId);
				break;

			case 6:
				columnName = "accountNo";
				System.out.println("Enter new Bank Account Number");
				String newAccNo = sc.nextLine();
				i = tfs.manageCustomerProfile(columnName, customerId, newAccNo);
				System.out.println("\nYour updated infomration is:");
				tfc.displayCustomerInfo(customerId);
				break;

			case 7:
				columnName = "maritalStatus";
				System.out.println("Enter new Marital Status(single/married)");
				String newMStatus = sc.nextLine();
				i = tfs.manageCustomerProfile(columnName, customerId, newMStatus);
				System.out.println("\nYour updated infomration is:");
				tfc.displayCustomerInfo(customerId);
				break;

			case 8:
				columnName = "address";
				System.out.println("Enter new Address");
				String newAddress = sc.nextLine();
				i = tfs.manageCustomerProfile(columnName, customerId, newAddress);
				System.out.println("\nYour updated infomration is:");
				tfc.displayCustomerInfo(customerId);
				break;

			case 9:
				columnName = "isemployee";
				System.out.println("Enter new isEmployee Status(y/n)");
				String newEmpStatus = sc.nextLine();
				String orgName = "";
				if (newEmpStatus.equalsIgnoreCase("y")) {
					System.out.println("Enter your organization name");
					String newOrgName = sc.nextLine();
					i = tfs.manageIsEmployeeStatusProfile(columnName, customerId, newEmpStatus, newOrgName);
				} else {
					i = tfs.manageCustomerProfile(columnName, customerId, newEmpStatus);
				}

				System.out.println("\nYour updated infomration is:");
				tfc.displayCustomerInfo(customerId);
				break;

			}

			System.out.println("\nDo want to continue update(y/n)??");
			ans = sc.nextLine();
		} while (ans.equalsIgnoreCase("y"));
	}

	@SuppressWarnings("unused")
	public void manageEmployer(String employerId) {
		System.out.println("Your inforamtion is:");
		tfc.displayEmployerInfo(employerId);
		String columnName = "";
		int i = 0;

		String ans = "y";
		do {
			System.out.println("\nWhat do you  want to edit:");
			System.out.println("1.Organization Name  2.Email  3.Password  4.Contact Number");
			System.out.println("\nEnter your choice:");
			int choice = Integer.parseInt(sc.nextLine());

			switch (choice) {

			case 1:
				columnName = "name";
				System.out.println("Enter new Organization Name");
				String newOrgName = sc.nextLine();
				i = tfs.manageEmployerProfile(columnName, employerId, newOrgName);
				System.out.println("\nYour updated infomration is:");
				tfc.displayEmployerInfo(employerId);
				break;

			case 2:
				columnName = "email";
				System.out.println("Enter new Email");
				String newEmail = sc.nextLine();
				i = tfs.manageEmployerProfile(columnName, employerId, newEmail);
				System.out.println("\nYour updated infomration is:");
				tfc.displayEmployerInfo(employerId);
				break;

			case 3:
				columnName = "password";
				System.out.println("Enter new password");
				String newPass = sc.nextLine();
				i = tfs.manageEmployerProfile(columnName, employerId, newPass);
				System.out.println("\nYour updated infomration is:");
				tfc.displayEmployerInfo(employerId);
				break;

			case 4:
				columnName = "contactNo";
				System.out.println("Enter new Contact Number");
				String newContactNo = sc.nextLine();
				i = tfs.manageEmployerProfile(columnName, employerId, newContactNo);
				System.out.println("\nYour updated infomration is:");
				tfc.displayEmployerInfo(employerId);
				break;

			}

			System.out.println("\nDo want to continue update(y/n)??");
			ans = sc.nextLine();
		} while (ans.equalsIgnoreCase("y"));

	}

	@SuppressWarnings("unused")
	public void manageRepresentative(String representativeId) {
		System.out.println("Your inforamtion is:");
		tfc.displayRepresentativeInfo(representativeId);
		String columnName = "";
		int i = 0;

		String ans = "y";
		do {
			System.out.println("\nWhat do you  want to edit:");
			System.out.println("1.Name  2.Email  3.Password  4.Contact Number");
			System.out.println("\nEnter your choice:");
			int choice = Integer.parseInt(sc.nextLine());

			switch (choice) {

			case 1:
				columnName = "name";
				System.out.println("Enter new Name");
				String newName = sc.nextLine();
				i = tfs.manageRepresentativeProfile(columnName, representativeId, newName);
				System.out.println("\nYour updated infomration is:");
				tfc.displayRepresentativeInfo(representativeId);
				break;

			case 2:
				columnName = "email";
				System.out.println("Enter new Email");
				String newEmail = sc.nextLine();
				i = tfs.manageRepresentativeProfile(columnName, representativeId, newEmail);
				System.out.println("\nYour updated infomration is:");
				tfc.displayRepresentativeInfo(representativeId);
				break;

			case 3:
				columnName = "password";
				System.out.println("Enter new password");
				String newPass = sc.nextLine();
				i = tfs.manageRepresentativeProfile(columnName, representativeId, newPass);
				System.out.println("\nYour updated infomration is:");
				tfc.displayRepresentativeInfo(representativeId);
				break;

			case 4:
				columnName = "contactNo";
				System.out.println("Enter new Contact Number");
				String newContactNo = sc.nextLine();
				i = tfs.manageRepresentativeProfile(columnName, representativeId, newContactNo);
				System.out.println("\nYour updated infomration is:");
				tfc.displayRepresentativeInfo(representativeId);
				break;

			}

			System.out.println("\nDo want to continue update(y/n)??");
			ans = sc.nextLine();
		} while (ans.equalsIgnoreCase("y"));
	}

	@SuppressWarnings("unused")
	public void manageAdmin(String adminId) {

		System.out.println("Your inforamtion is:");
		tfc.displayAdminInfo(adminId);
		String columnName = "";
		int i = 0;

		String ans = "y";
		do {
			System.out.println("\nWhat do you  want to edit:");
			System.out.println("1.Password");
			System.out.println("\nEnter your choice:");
			int choice = Integer.parseInt(sc.nextLine());

			switch (choice) {

			case 1:
				columnName = "password";
				System.out.println("Enter new password");
				String newPass = sc.nextLine();
				i = tfs.manageAdminProfile(columnName, adminId, newPass);
				System.out.println("\nYour updated infomration is:");
				tfc.displayAdminInfo(adminId);
				break;

			}

			System.out.println("\nDo want to continue update(y/n)??");
			ans = sc.nextLine();
		} while (ans.equalsIgnoreCase("y"));

	}

	@SuppressWarnings("unused")
	public void removeActors() {
		int i = 0;
		String ans = "y";
		String removeAns = "y";
		do {
			do {
				System.out.println(
						"See all information about\n1.Customers  2.Representatives   3.Employers\nEnter your choice:");
				int choiceActor = Integer.parseInt(sc.nextLine());

				switch (choiceActor) {
				case 1:
					tfc.displayAllCustomer();
					break;
				case 2:
					tfc.displayAllRepresentative();
					break;
				case 3:
					tfc.displayAllEmployer();
					break;

				default:
					System.out.println("Invalid choice");
					System.out.println("Please try again!!");
				}
				System.out.println("\nDo you want to see details of other Actors(y/n)?");
				ans = sc.nextLine();
			} while (ans.equalsIgnoreCase("y"));

			System.out.println(
					"From which Actor do you want to delete?\n1.Customers  2.Representatives   3.Employers\nEnter your choice:");
			int choiceRemoveActor = Integer.parseInt(sc.nextLine());

			switch (choiceRemoveActor) {

			case 1:
				System.out.println("Enter CustomerId you want to delete");
				long cId = Long.parseLong(sc.nextLine());
				i = tfs.removeCustomer(cId);

				System.out.println("\nDo you want to see all Customers again(y/n)?");
				String againC_Ans = sc.nextLine();
				if (againC_Ans.equalsIgnoreCase("y"))
					tfc.displayAllCustomer();
				break;

			case 2:
				System.out.println("Enter RepresentativeId you want to delete");
				long rId = Long.parseLong(sc.nextLine());
				i = tfs.removeRepresentative(rId);
				System.out.println("Do you want to see all Representatives again(y/n)?");
				String againR_Ans = sc.nextLine();
				if (againR_Ans.equalsIgnoreCase("y"))
					tfc.displayAllRepresentative();
				break;

			case 3:
				System.out.println("Enter EmployerId you want to delete");
				long eId = Long.parseLong(sc.nextLine());
				i = tfs.removeEmployer(eId);

				System.out.println("Do you want to see all Employers again(y/n)?");
				String againE_Ans = sc.nextLine();
				if (againE_Ans.equalsIgnoreCase("y"))
					tfc.displayAllEmployer();
				break;

			default:
				System.out.println("Invalid choice, Try again!!");

			}
			System.out.println("\nDo you want to continue Remove operation(y/n)?");
			removeAns = sc.nextLine();
		} while (removeAns.equalsIgnoreCase("y"));
		System.out.println("Thank You");

	}

	public void displayAllEmployer() {
		List<Employer> eList = tfs.displayAllEmployers();
		for (Employer e : eList)
			System.out.println("Id= " + e.getEmployerId() + " Organization= " + e.getOrganization() + " email= "
					+ e.getEmail() + " password= " + e.getPassword() + " ContactNo= " + e.getContactNo());

	}

	public void displayAllRepresentative() {
		List<Representative> rList = tfs.displayAllRepresentatives();

		for (Representative r : rList)
			System.out.println("Id= " + r.getRepresentativeId() + " name= " + r.getName() + " email= " + r.getEmail()
					+ " contactNo= " + r.getContactNo());

	}

	public void displayAllCustomer() {
		List<Customer> cList = tfs.displayAllCustomers();
		for (Customer c : cList)
			System.out.println("Id= " + c.getCustomerId() + " name= " + c.getName() + " email= " + c.getEmail()
					+ " panNo= " + c.getPan() + " contactNo= " + c.getContactNo() + " " + "accountNo= "
					+ c.getAccountNo() + " maritalStatus= " + c.getMaritalStatus() + " address= " + c.getAddress()
					+ " isEmployee= " + c.getIsEmployee());
	}

	public void displayAdminInfo(String adminId) {

		Admin a = tfs.displayAdminInfo(adminId);

		System.out.println("Password= " + a.getPassword());

	}

	public void displayRepresentativeInfo(String representativeId) {
		long id = Long.parseLong(representativeId);
		Representative r = tfs.displayRepresentativeInfo(id);

		System.out.println("Id= " + r.getRepresentativeId() + " Name= " + r.getName() + " Email= " + r.getEmail()
				+ " Password= " + r.getPassword() + " Contact Number= " + r.getContactNo());

	}

	public void displayEmployerInfo(String employerId) {
		long id = Long.parseLong(employerId);
		Employer e = tfs.displayEmployerInfo(id);

		System.out.println("Id= " + e.getEmployerId() + " Organization= " + e.getOrganization() + " Email= "
				+ e.getEmail() + " Password= " + e.getPassword() + " Contact Number= " + e.getContactNo());

	}

	public void displayCustomerInfo(String customerId) {
		long id = Long.parseLong(customerId);
		Customer c = tfs.displayCustomerInfo(id);

		System.out.println("Id= " + c.getCustomerId() + " Name= " + c.getName() + " Email= " + c.getEmail()
				+ " Password= " + c.getPassword() + " PanNo= " + c.getPan() + " Contact Number= " + c.getContactNo()
				+ " " + "Bank Account Number= " + c.getAccountNo() + " Marital Status= " + c.getMaritalStatus()
				+ " Address= " + c.getAddress() + " isEmployee Status= " + c.getIsEmployee());

	}

}
