package com.lucasdiegorr.graphic;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import com.lucasdiegorr.client.Client;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextField;
import javax.swing.JTextArea;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * @author Lucas Diego
 *
 */
public class ChatClient {

	private JFrame frame;
	private static Client client;
	private JTextField textToSend;
	private JTextArea textAreaChat;
	private String nickName;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatClient window = new ChatClient();
					window.frame.setVisible(true);
					client = new Client(window);
					new Thread(client).start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ChatClient() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		nickName = JOptionPane.showInputDialog(null, "Qual o seu nick?");
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				try {
					client.getSocket().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Chat - " + nickName);
		frame.getContentPane().setLayout(null);
		
		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				client.sendMessage(nickName + " : " + textToSend.getText() + "\n");
				textAreaChat.append("eu: " + textToSend.getText() + "\n");
				textToSend.setText(null);
				textToSend.requestFocus();
			}
		});
		btnEnviar.setBounds(335, 228, 89, 23);
		frame.getContentPane().add(btnEnviar);
		
		textToSend = new JTextField();
		textToSend.setBounds(10, 229, 315, 23);
		frame.getContentPane().add(textToSend);
		textToSend.setColumns(10);
		
		textAreaChat = new JTextArea();
		textAreaChat.setEditable(false);
		textAreaChat.setBounds(10, 11, 414, 202);
		frame.getContentPane().add(textAreaChat);
	}

	public JTextArea getTextAreaChat() {
		return textAreaChat;
	}
}
