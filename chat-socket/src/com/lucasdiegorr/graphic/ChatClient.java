package com.lucasdiegorr.graphic;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import com.lucasdiegorr.client.Client;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextField;
import javax.swing.JTextArea;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.swing.ScrollPaneConstants;

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
	private JTextField textField;
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
		
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
			}
			@Override
			public void windowClosing(WindowEvent arg0) {
				try {
					client.sendMessage("DESCONECTAR");
					new Thread(client).stop();
					client.getWriter().close();
					client.getReader().close();
					client.getSocket().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		frame.setBounds(100, 100, 450, 360);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Chat");
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
		btnEnviar.setBounds(335, 287, 89, 23);
		frame.getContentPane().add(btnEnviar);
		
		textToSend = new JTextField();
		textToSend.setBounds(10, 288, 315, 23);
		frame.getContentPane().add(textToSend);
		textToSend.setColumns(10);
		
		
		textAreaChat = new JTextArea();
		textAreaChat.setEditable(false);
		textAreaChat.setVisible(true);
		textAreaChat.setCaretPosition(textAreaChat.getDocument().getLength()); 
		textAreaChat.setBounds(10, 11, 414, 202);
		
		JScrollPane scrollPane = new JScrollPane(textAreaChat);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);;
		scrollPane.setBounds(10, 69, 414, 202);
		frame.getContentPane().add(scrollPane);
		
		textField = new JTextField();
		textField.setBounds(10, 30, 216, 28);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton bntConnect = new JButton("Conectar");
		bntConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		bntConnect.setBounds(236, 30, 89, 23);
		frame.getContentPane().add(bntConnect);
		
		JButton bntDisconnect = new JButton("Sair");
		bntDisconnect.setBounds(335, 30, 89, 23);
		frame.getContentPane().add(bntDisconnect);
	}

	public JTextArea getTextAreaChat() {
		return textAreaChat;
	}
}
