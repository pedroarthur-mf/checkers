package main;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import handler.Controller;
import handler.MyMouseListener;
import model.Player;
import view.BoardPanel;

/**
 * Client Application -> ClientApp
 * 
 * @author Keerthikan
 * 
 *         ClientApp
 */
public class ClientApp extends JFrame {

	private static final /*@ spec_public @*/ long serialVersionUID = 1L;
	//@ public constraint serialVersionUID == \old(serialVersionUID);

	private /*@ spec_public @*/ String address;
	private /*@ spec_public @*/ int port;

	// Model
	private /*@ spec_public @*/ Player player;

	// View
	private /*@ spec_public nullable @*/ BoardPanel boardPanel;

	// Network properties
	private /*@ spec_public nullable @*/ Socket connection;
	private /*@ spec_public nullable @*/ DataInputStream fromServer;
	private /*@ spec_public nullable @*/ DataOutputStream toServer;
	
	//@ public invariant this.player.getName() != null && 0 < this.player.getName().length();

	// Constructor
	public ClientApp() {
		try {
			PropertyManager pm = PropertyManager.getInstance();
			address = pm.getAddress();
			port = pm.getPort();

			String name = (String) JOptionPane.showInputDialog(null, "Enter your name to Connect", "Connect to Server",
					JOptionPane.OK_CANCEL_OPTION);

			if (name != null && name.length() > 0) {
				player = new Player(name);
				connect();
			} else {
				JOptionPane.showMessageDialog(null, "Please enter valid name", "Error", JOptionPane.ERROR_MESSAGE,
						null);
				System.exit(0);
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Please enter valid IPv4-Address", "Error", JOptionPane.ERROR_MESSAGE,
					null);
			System.exit(0);
		}

	}
	
	//@ assignable this.connection, this.fromServer, this.toServer, this.player;
	private void connect() {

		try {
			connection = new Socket(address, port);

			// Should error occurs, handle it in a seperate thread (under try)
			fromServer = new DataInputStream(connection.getInputStream());
			toServer = new DataOutputStream(connection.getOutputStream());

			// First player get 1 and second player get 2
			player.setPlayerID(fromServer.readInt());

			Controller task = new Controller(player, fromServer, toServer);
			setup(task);

			new Thread(task).start();
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "Internal Server Error - Check your IPv4-Address", "Error",
					JOptionPane.ERROR_MESSAGE, null);
			System.exit(0);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Couldn't connect to Server. Please try again", "Error",
					JOptionPane.ERROR_MESSAGE, null);
			System.exit(0);
		}
	}
	
	/*@ requires c != null;
	  @ assignable this.boardPanel;
	  @*/
	private void setup(Controller c) {
		MyMouseListener listener = new MyMouseListener();
		listener.setController(c);

		boardPanel = new BoardPanel(listener);
		c.setBoardPanel(boardPanel);
		add(boardPanel);
	}
}
