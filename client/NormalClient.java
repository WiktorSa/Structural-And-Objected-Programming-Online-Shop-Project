package client;

import java.io.Serializable;
import java.util.Scanner;

import waysofdelivery.Dostawa;
import waysofpayments.Blik;
import waysofpayments.Card;
import waysofpayments.Paypal;
import waysofpayments.WaysOfPayments;

// Klasa zaimplementowana przez Wiktora Sadowego uzywajac kodu Szymona Sawczuka
public class NormalClient extends RegisteredClient implements Serializable
{
	private static final long serialVersionUID = 1L;

	public NormalClient() 
	{
		super();
	}
	
	public NormalClient(Client client) 
	{
		super(client);
	}

	public boolean setDeliveryInfo() 
	{
		return ((Dostawa) wayOfDelivery).provideDeliveryInformations(this);
	}

	@SuppressWarnings("resource")
	public void chooseWayOfPayment() 
	{
		WaysOfPayments[] waysOfPayments = {new Blik(), new Card(), new Paypal()};
		boolean wasWayOfPaymentChosen = false;
		Scanner scanner = new Scanner(System.in);
		int chosenWayOfPayment = 0;
		
		while (!wasWayOfPaymentChosen)
		{
			for (int i=0; i<waysOfPayments.length; i++)
			{
				System.out.println(i+1 + ". " + waysOfPayments[i].getName());
			}
			System.out.println("Wybierz metode zaplaty");
			
			try 
			{
				chosenWayOfPayment = scanner.nextInt();
				scanner.nextLine();
				
				if (chosenWayOfPayment>0 && chosenWayOfPayment<=waysOfPayments.length) {
					System.out.println("Wybrano metode zaplaty");
					wasWayOfPaymentChosen = true;
				}
				else {
					System.out.println("Nie ma takiej opcji zaplaty");
				}
			} 
			
			catch (java.util.InputMismatchException e) 
			{
				System.out.println("Nie ma takiej opcji zaplaty");
			}
		}
		
		this.wayOfPayment = waysOfPayments[chosenWayOfPayment-1];
	}

	public boolean initiatePay() 
	{
		wayOfPayment.pay(this);
		return wayOfPayment.isPaymentDone();
	}

	public String getTransactionInfo() 
	{
		String transactionInfo = "Informacje o transakcji\n";
		transactionInfo += getBasket().toString();
		transactionInfo = transactionInfo + "Cena: " + String.format("%.2f", basket.getPrice() + wayOfDelivery.getPrice()) + " (z wliczona dostawa)\n";
		
		if (getWasDeliveryChosen()){
			transactionInfo += ((Dostawa) wayOfDelivery).deliveryInfo() + "\n";
			transactionInfo += "Metoda dostawy zostala zatwierdzona oraz ";
			
			if (wayOfPayment.isPaymentDone()){
				transactionInfo += "transakcja zostala oplacona\nTransakcja zostala wykonana przez zarejestrowanego uzytkownika";
			}
			
			else {
				transactionInfo += "nie dokonano platnosci za produkty";
			}
		}
		
		else {
			transactionInfo += "Nie zatwierdzono metody dostawy";
		}

		return transactionInfo;
	}
}
