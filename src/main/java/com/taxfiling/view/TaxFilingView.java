package com.taxfiling.view;

import java.io.IOException;
import java.util.Scanner;

import com.taxfiling.controller.TaxFilingController;
import com.taxfiling.controller.TaxFilingControllerInterface;

public class TaxFilingView {
	public static void main(String[] args) throws NumberFormatException, IOException {
		System.out.println("Welcome to Tax Filing Application");
		Scanner sc = new Scanner(System.in);
		TaxFilingControllerInterface tfc = new TaxFilingController();
		int choice = 0;
		while (choice != 4) {
			System.out.print("1.Register\t2.Login\t\t3.Forgot Password\t4.Exit Application\nEnter your choice: ");
			choice = sc.nextInt();
			switch (choice) {
			case 1:
				tfc.registerActor();
				break;
			case 2:
				tfc.loginActor();
				break;
			case 3:
				tfc.forgotPassword();
				break;
			case 4:
				System.out.println("Thank you for using the application");
				sc.close();
				break;
			default:
				System.out.println("Invalid choice, please try again!!");
				break;
			}
		}
	}
}