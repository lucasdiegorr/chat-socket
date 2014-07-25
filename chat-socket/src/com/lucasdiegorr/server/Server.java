package com.lucasdiegorr.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Lucas Diego
 *
 */
public class Server {

	private ServerSocket server;
	private List<Socket> listClient = new ArrayList<>();

	public Server() {

		try {
			server = new ServerSocket(5000);
			System.out.println("Server online");
			while (true) {
				Socket client = server.accept();
				listClient.add(client);
				new Thread(new ThreadServer(this, client)).start();
				System.out.println("Client adicionado: " + client.getPort());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		new Server();
	}
	
	public List<Socket> getListClient() {
		return listClient;
	}

	public void remove(Socket client) {
		for (Socket remove : listClient) {
			if (remove.equals(client)) {
				try {
					client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				listClient.remove(client);
				System.out.println("O cliente foi removido.");
			}
		}
	}
}
