package com.lucasdiegorr.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author Lucas Diego
 *
 */
public class ThreadServer implements Runnable {

	private Server server;
	private Socket client;
	private DataOutputStream writer;
	private DataInputStream reader;
	private int idClient;

	public ThreadServer(Server server, Socket client) {

		this.server = server;
		this.client = client;
		this.idClient = client.getPort();
		try {
			writer = new DataOutputStream(client.getOutputStream());
			reader = new DataInputStream(client.getInputStream());
			System.out.println("A thread est� cuidando do cliente na porta: " + client.getPort());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void run() {

		while (!this.client.isClosed()) {
			try {
				String fromClient;
				while (reader.available() != 0) {
					fromClient = reader.readUTF();
					System.out.println("Mensagem recebida do cliente: " + fromClient);
					sendToAll(fromClient);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			client.close();
			server.getListClient().remove(client);
			sendToAll("O cliente" + this.idClient + " desconectou.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void sendToAll(String fromClient) {
		for (Socket otherClient : this.server.getListClient()) {
			if (otherClient.getPort() != this.idClient) {
				try {
					writer = new DataOutputStream(otherClient.getOutputStream());
					writer.writeUTF(fromClient);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
