package skuns.chat.auxiliary;

import java.sql.Time;
import java.util.Date;
import java.io.Serializable;


public class Message implements Serializable {
	private String login;
	private String message;
	private String[] users;
	private String status;
	private String ip_adress;
	private Date time;

	public Message(String login, String message, String status, String ip_adress) {
		this.login = login;
		this.message = message;
		this.status = status;
		this.ip_adress = ip_adress;
		this.time = java.util.Calendar.getInstance().getTime();
	}

	public String getLogin() {
		return this.login;
	}

	public String getMessage() {
		return this.message;
	}

	public String[] getUsers() {
		return this.users;
	}

	public String getStatus() {
		return status;
	}

	public String getIpAdress() {
		return ip_adress;
	}

	public String getDate() {
		Time tm = new Time(this.time.getTime());
		return tm.toString();
	}
}
