import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import edu.purdue.cs.cs180.channel.*;
/**
 * Project 6 -- SafeWalk 1.0
 * provides a GUI for users to request a ride from one of 5 location around campus,
 * another GUI for responders to use so that they know where to pick up the person who needs a ride,
 * and uses a server for efficient requester and responder communication and allocation
 * 
 * @author cservaas
 * 
 * @recitation RM5 (Julian Stephen)
 * 
 * @date November 4, 2012
 *
 */
public class Response extends JFrame implements MessageListener {
	private Channel channel = null;
	
	// the GUI elements
	
	private JButton readyButton;
	private JLabel statusLabel;
	
	/**
	 * constructs a new Response object
	 * 
	 * @param host a String that represents the hostname of the server
	 * 
	 * @param port an integer that represents the port that the server is running on
	 */
	public Response(String host, int port) {
		
		// create submit button
		readyButton = new JButton("Ready");
		readyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				readySubmit(); // submit the location when clicked
			}
		});
		
		// create status label
		statusLabel = new JLabel("");
		
		// create the GUI design by using panels
		// CONTROLS
		Container controlsPane = new JPanel();
		controlsPane.setLayout(new FlowLayout()); // organized the elements horizontally
		controlsPane.add(readyButton); // add the submit button
		
		// LABEL
		Container labelPane = new JPanel();
		labelPane.setLayout(new FlowLayout()); // center the label
		labelPane.add(statusLabel); // add the label
		
		// add everything to the window
		Container contentPane = this.getContentPane(); // get the window's content pane
		contentPane.setLayout(new GridLayout(2,1)); // organize the panels vertically
		contentPane.add(controlsPane); // add the panel with the controls first
		contentPane.add(labelPane); // then add the panel with the label beneath
		
		// set window properties
		this.setTitle("Response");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		// pack and show!
		this.pack();
		this.setVisible(true);
		
		// initialize the communication channel using the host and port provided by the user
		initChannel(host, port);
	}
	
	/**
	 * initializes the communication channel using the host and port provided by the user
	 * 
	 * @param host the hostname of the server
	 * @param port the port number that the server is listening to
	 */
	private void initChannel(String host, int port) {
		try {
			channel = new TCPChannel(host, port); // create new TCPChannel object with specified values
			channel.setMessageListener(this); // have this Response object listen for messages (messageReceived())
		} catch (ChannelException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * submits the location Response to the server
	 */
	private void readySubmit() {
			readyButton.setEnabled(false); // disable the ready button
			
			// send the Readiness to the server
			try {
				channel.sendMessage("Response:" + "Help Team X"); // send message with location
			} catch (ChannelException e) {
				e.printStackTrace();
			}
		}

	@Override
	/**
	 * listens for a message and sets the label text when one is received
	 * also checks to see if the message is an assigned message
	 */
	public void messageReceived(String arg0, int arg1) {
		statusLabel.setText(arg0);
		this.pack(); // adjusts the window.
		
	}
	
	public static void main(String[] args) {
		new Response(args[0], Integer.parseInt(args[1])); // create new Response object with the values entered by the user
	}
}
