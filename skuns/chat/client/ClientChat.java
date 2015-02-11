package skuns.chat.client;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import skuns.chat.auxiliary.Message;
import skuns.chat.Config;

public class ClientChat {

	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private String login;
	private String status;
	private BufferedReader bufReader;


	public ClientChat() {
		login = "";
		status = "work";

		try {
				System.out.println("Client is running.\nLets try to connect on IP "+Config.HOST_IP+" and port "+Config.PORT);
				socket = new Socket(Config.HOST_IP, Config.PORT);
				out = new ObjectOutputStream(socket.getOutputStream());
				in = new ObjectInputStream(socket.getInputStream());

				if(socket != null) {

					Thread t_out = new Thread(new Runnable() {
							public void run() {
								doOutput();
							}
					});

					Thread t_in = new Thread(new Runnable() {
							public void run() {
								doInput();
							}
						});

					t_out.start();
					t_in.start();
			}
		} catch (IOException e) { e.printStackTrace(); }
	}

	private void doInput() {
		System.out.println("Thread input is started");

		String msg;
		BufferedReader input = new BufferedReader(new
			InputStreamReader(System.in));

		try {
				// Читаем логин
				while(true) {
					System.out.println("Enter your login:");
					login = input.readLine();
					if(login != null) {
						out.writeUTF(login);
						out.flush();
						break;
					}
				}

				while( (msg = input.readLine()) != null ) {
					if(msg.equalsIgnoreCase("change status")) {
						this.setStatus();
					} else {
						Message message = new Message(login, msg, status);
						out.writeObject(message);
					}

				}

		} catch(IOException ex) { ex.printStackTrace(); }

	}

	private void doOutput() {
		System.out.println("Thread output is started");
		Message mess;

		try {
				while((mess = (Message) in.readObject()) != null) {
						System.out.println(mess.getLogin() +" ["+ mess.getDate() +"] "+"(status:" + mess.getStatus()+"): "+mess.getMessage());
				}

		} catch (ClassNotFoundException ex) {
				System.err.println("class not found");
				ex.printStackTrace();
		} catch (IOException ex) {
				ex.printStackTrace();
		}
	}

	private void setStatus() {
		int choise;
		do {
			Scanner sc = new Scanner(System.in);

			System.out.println("For choosing status press number:");
			System.out.println("1 Work");
			System.out.println("2 Eat");
			System.out.println("3 Sleep");
			choise = Integer.parseInt(sc.nextLine());

		}  while (choise < 1 || choise > 3);

		switch(choise) {
			case 1:
				status = "Work";
				break;

			case 2:
				status = "Eat";
				break;

			case 3:
				status = "Sleep";
				break;
		}
	}
}