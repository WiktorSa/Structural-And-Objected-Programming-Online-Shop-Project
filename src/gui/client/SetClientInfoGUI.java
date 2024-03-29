package gui.client;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import client.HelperFunctionsClient;
import client.RegisteredClient;
import gui.shop.MainGUI;

public class SetClientInfoGUI {
	private MainGUI main;
	private JPanel jPanel;
	private JTextField firstNameJTextField;
	private JTextField lastNameJTextField;
	private JTextField emailJTextField;
	private JTextField phoneNumberJTextField;
	private JButton confirmDataButton;
	private JButton goBackButton;
	
	public JPanel getjPanel() {
		return jPanel;
	}
	
	public SetClientInfoGUI(MainGUI main) {
		this.main = main;
		
		jPanel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(jPanel, BoxLayout.Y_AXIS);
		jPanel.setLayout(boxLayout);
		jPanel.add(Box.createVerticalGlue());
		
		JLabel titleJLabel = new JLabel("Wpisz swoje podstawowe dane", SwingConstants.CENTER);
		titleJLabel.setFont(new Font(titleJLabel.getFont().getName(), Font.BOLD, 40));
		titleJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		titleJLabel.setBorder(new EmptyBorder(5,10,15,10)); //top,left,bottom,right
		jPanel.add(titleJLabel);
		
		JLabel firstNameJLabel = new JLabel("Wpisz swoje imie", SwingConstants.CENTER);
		firstNameJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		firstNameJLabel.setBorder(new EmptyBorder(15, 0, 5, 0));
		jPanel.add(firstNameJLabel);
		
		firstNameJTextField = new JTextField(main.getClient().getFirstName()); 
		firstNameJTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
		firstNameJTextField.setHorizontalAlignment(JTextField.CENTER);
		firstNameJTextField.setMaximumSize(new Dimension(250, 90));
		jPanel.add(firstNameJTextField);
		
		JLabel lastNameJLabel = new JLabel("Wpisz swoje nazwisko", SwingConstants.CENTER);
		lastNameJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		lastNameJLabel.setBorder(new EmptyBorder(15, 0, 5, 0));
		jPanel.add(lastNameJLabel);
		
		lastNameJTextField = new JTextField(main.getClient().getLastName());
		lastNameJTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
		lastNameJTextField.setHorizontalAlignment(JTextField.CENTER);
		lastNameJTextField.setMaximumSize(new Dimension(250, 90));
		jPanel.add(lastNameJTextField);
		
		JLabel adresEmailJLabel = new JLabel("Wpisz swoj adres email", SwingConstants.CENTER);
		adresEmailJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		adresEmailJLabel.setBorder(new EmptyBorder(15, 0, 5, 0));
		jPanel.add(adresEmailJLabel);
		
		emailJTextField = new JTextField(main.getClient().getEmail()); 
		emailJTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
		emailJTextField.setHorizontalAlignment(JTextField.CENTER);
		emailJTextField.setMaximumSize(new Dimension(250, 90));
		if (main.getClient() instanceof RegisteredClient) {
			emailJTextField.setEditable(false); 
		}
		jPanel.add(emailJTextField);
		
		JLabel phoneNumberEmailJLabel = new JLabel("Wpisz swoj numer telefonu", SwingConstants.CENTER);
		phoneNumberEmailJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		phoneNumberEmailJLabel.setBorder(new EmptyBorder(15, 0, 5, 0));
		jPanel.add(phoneNumberEmailJLabel);
		
		phoneNumberJTextField = new JTextField(main.getClient().getPhoneNumber()); 
		phoneNumberJTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
		phoneNumberJTextField.setHorizontalAlignment(JTextField.CENTER);
		phoneNumberJTextField.setMaximumSize(new Dimension(250, 90));
		jPanel.add(phoneNumberJTextField);
		
		jPanel.add(Box.createRigidArea(new Dimension(5, 15)));
		
		JPanel buttonPanel = new JPanel();
		
		goBackButton = new JButton(new ImageIcon("Ikony/goBack.png"));
		goBackButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		goBackButton.addActionListener(new GoBack());
		buttonPanel.add(goBackButton);
		
		confirmDataButton = new JButton(new ImageIcon("Ikony/forward.png"));
		confirmDataButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		confirmDataButton.addActionListener(new ConfirmData());
		buttonPanel.add(confirmDataButton);
	
		jPanel.add(buttonPanel);
		jPanel.add(Box.createVerticalGlue());
		this.main.setButtonCursor(jPanel);
	
	}
	
	class ConfirmData implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			String firstName = firstNameJTextField.getText();
			String lastName = lastNameJTextField.getText();
			String email = emailJTextField.getText();
			String phoneNumber = phoneNumberJTextField.getText();
			
			if (HelperFunctionsClient.isCorrectName(firstName) && HelperFunctionsClient.isCorrectName(lastName) 
					&& HelperFunctionsClient.isCorrectEmail(email) && HelperFunctionsClient.isCorrectPhoneNumber(phoneNumber)) {
				
				main.getClient().setFirstName(firstName);
				main.getClient().setLastName(lastName);
				main.getClient().setEmail(email);
				main.getClient().setPhoneNumber(phoneNumber);
				
				if(JOptionPane.showConfirmDialog(null, "Czy to sa twoje dane: " + main.getClient().toString(), "Potwierdz dane", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					if (main.getClient() instanceof RegisteredClient) {
						((RegisteredClient) main.getClient()).saveClient();
					}
					
					
					main.changeLayoutToDeliverySelectingCategory();
				}
			}
			
			else {
					if(!HelperFunctionsClient.isCorrectName(firstName)) {
						JOptionPane.showMessageDialog(new JFrame(), "Niepoprawne imie, na pewno jest z duzej litery?");
					}
					else if(!HelperFunctionsClient.isCorrectName(lastName)) {
						JOptionPane.showMessageDialog(new JFrame(), "Niepoprawne nazwisko, na pewno jest z duzej litery?");
					}
					else if(!HelperFunctionsClient.isCorrectEmail(email)) {
						JOptionPane.showMessageDialog(new JFrame(), "Niepoprawny email, pamietaj o @ i .");
					}
					else if(!HelperFunctionsClient.isCorrectPhoneNumber(phoneNumber)) {
						JOptionPane.showMessageDialog(new JFrame(), "Niepoprawny numer telefonu, czy na pewno ma 9 cyfr bez spacji?");
					}
				}
		}
	}
	
	class GoBack implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			main.changeLayoutToBasket();
		}
	}
}
