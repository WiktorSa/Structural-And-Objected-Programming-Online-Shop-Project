package client;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public abstract class RegisteredClient extends Client implements Serializable {
	private static final long serialVersionUID = 1L;
	protected String password;
	
	public RegisteredClient() {
		super();
		this.password = "";
	}
	
	public RegisteredClient(Client client){
		super(client);
		password = "";
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void saveClient() {
		try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("Client_"+email+".ser"))){
			outputStream.writeObject(this);
		} catch (FileNotFoundException e) {
			System.exit(-1);
		} catch (IOException e) {
			System.exit(-1);
		}
	}
}
