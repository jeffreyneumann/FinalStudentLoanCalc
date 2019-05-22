package app.controller;

import app.StudentCalc;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.formula.functions.FinanceLib;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class LoanCalcViewController implements Initializable   {

	private StudentCalc SC = null;
	
	
	//These convert the text fields into easier to use doubles
	private double loanAmount;
		
	private double interestRate;
		
	private double additionalPayment;
		
	private double termLoan;
	
	private double totalPayment;
	
	private double totalInterest;
	
	private static DecimalFormat df = new DecimalFormat("0.00");
	
	@FXML
	private TextField LoanAmount;
	
	@FXML
	private TextField InterestRate;
	
	@FXML
	private TextField NbrOfYears;
	
	@FXML
	private Label lblTotalPayments;
	
	@FXML
	private Label lblTotalInterest;
	
	@FXML
	private DatePicker PaymentStartDate;
	
	@FXML
	private TextField AdditonalPayment;
	
	
	
	
	List<Integer> number = new ArrayList<>();
	List<Double> payment = new ArrayList<>();
	List<Double> addPayment = new ArrayList<>();
	List<Double> interest = new ArrayList<>();
	List<Double> principle = new ArrayList<>();
	List<Double> balance = new ArrayList<>();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	public void setMainApp(StudentCalc sc) {
		this.SC = sc;
	}
	
	
	/**
	 * btnCalcLoan - Fire this event when the button clicks
	 * 
	 * @version 1.0
	 * @param event
	 */
	@FXML
	private void btnCalcLoan(ActionEvent event) {

		number.clear();
		payment.clear();
		addPayment.clear();
		interest.clear();
		principle.clear();
		balance.clear();
		
		//System.out.println("Amount: " + LoanAmount.getText());
		double dLoanAmount = Double.parseDouble(LoanAmount.getText());
		//System.out.println("Amount: " + dLoanAmount);	
		
		
		
		LocalDate localDate = PaymentStartDate.getValue();
	 
		//System.out.println(localDate);
		
		loanAmount = Double.parseDouble(LoanAmount.getText());
		
		interestRate = Double.parseDouble(InterestRate.getText());
		
		additionalPayment = Double.parseDouble(AdditonalPayment.getText());
		
		termLoan = Double.parseDouble(NbrOfYears.getText());
		
		
		/*
		System.out.println(loanAmount);
		System.out.println(interestRate);
		System.out.println(termLoan);
		System.out.println(additionalPayment);
		*/
		
		/*
		
		//
		//balance.add(loanAmount);
		
		//This fills in the "number" catagory
		for(int i = 1; i <= (termLoan * 12); i++) {
			number.add(i);
		}
		
		//This fills in the "payment due" category
		double monthPay = -1 * FinanceLib.pmt((interestRate / 12), (termLoan * 12), loanAmount, 0, false);
		for(int i = 1; i <= (termLoan * 12); i++) {
			payment.add(monthPay);
		}
		
		//This fills in the "additional payment" category
		for(int i = 1; i <= (termLoan * 12); i++) {
			addPayment.add(additionalPayment);
		}
		
		//This fills in the "interest" category
		
		for(int i = 1; i <= (termLoan * 12) - 1; i++) {
			interest.add((interestRate / 12) * balance.get(i - 2));
		}
		
		//this fills in the "principle" category
		for(int i = 1; i <= (termLoan * 12); i++) {
			principle.add(payment.get(i - 1) - interest.get(i - 1) + addPayment.get(i - 1));
		}
		
		//this fills in the "balance" category
		balance.add(loanAmount);
		for(int i = 1; i <= (termLoan * 12) - 1; i++) {
			balance.add(balance.get(i - 2) - principle.get(i - 1));
		}
		
		*/
		
		//this is to combine all the categories into a parameterized table
		double monthPay = -1 * FinanceLib.pmt((interestRate / 12), (termLoan * 12), loanAmount, 0, false);
		
		balance.add(loanAmount);
		for(int i = 1; i <= (termLoan * 12); i++) {
			number.add(i);
			//System.out.println("A");
			payment.add((double)Math.round(monthPay * 100) / 100);
			//System.out.println("B");
			addPayment.add((double)Math.round(additionalPayment * 100) / 100);
			//System.out.println("C");
			interest.add((double)Math.round((interestRate / 12) * balance.get(i - 1) * 100) / 100);
			//System.out.println("D");
			principle.add((double)Math.round((payment.get(i - 1) - interest.get(i - 1) + addPayment.get(i - 1)) * 100) / 100);
			//System.out.println("E");
			balance.add((double)Math.round((balance.get(i - 1) - principle.get(i - 1)) * 100) / 100);
			//System.out.println("F");
		}
		
		double sumInterest = 0;
		for(int i = 0; i < interest.size(); i++) {
		    if(interest.get(i) >= 0) {
		    	sumInterest += interest.get(i);
		    }
		    //sumInterest = Math.round(sumInterest * 100) / 100;
		}
		//System.out.println(sumInterest);
		
		double sumPrinciple = 0;
		for(int i = 0; i < principle.size(); i++) {
			if(interest.get(i) >= 0) {
				sumPrinciple += principle.get(i);
			}
		    //sumPrinciple = Math.round(sumPrinciple * 100) / 100;
		}
		//System.out.println(sumPrinciple);
		
		totalPayment = Double.parseDouble(df.format(sumInterest + sumPrinciple));
		sumInterest = Double.parseDouble(df.format(sumInterest));
		
		//System.out.println(totalPayment);
		//System.out.println(sumInterest);
		//System.out.println(sumPrinciple);
		
		//System.out.println(balance);
		//System.out.println(principle);
		//System.out.println(interest);
		//System.out.println(addPayment);
		//System.out.println(payment);
		//System.out.println(number);
		
		
		lblTotalPayments.setText(String.valueOf(totalPayment));
		lblTotalInterest.setText(String.valueOf(sumInterest));
	}
}
