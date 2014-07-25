/**
 * 
 */
package com.lucasdiegorr.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.lucasdiegorr.graphic.ChatClient;

/**
 * @author Lucas Diego
 *
 */
public class Client implements Runnable {

	private Socket socket;
	private DataOutputStream writer;
	private DataInputStream reader;
	private ChatClient window;

	public Client(ChatClient window) {
		try {
			socket = new Socket("127.0.0.1", 5000);
			writer = new DataOutputStream(socket.getOutputStream());
			reader = new DataInputStream(socket.getInputStream());
			this.window = window;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

		while ((!socket.isClosed()) && socket.isConnected()) {
			String fromServer;
			try {
				
				while ((fromServer = reader.readUTF()) != null) {
					System.out.println("Mensagem do servidor: " + fromServer + "\n");
					window.getTextAreaChat().append(fromServer);
					window.getTextAreaChat().setCaretPosition(window.getTextAreaChat().getDocument().getLength());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	public void sendMessage(String string) {
		try {
			writer.writeUTF(string);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Socket getSocket() {
		return socket;
	}

	public DataOutputStream getWriter() {
		return writer;
	}

	public DataInputStream getReader() {
		return reader;
	}
	
}
