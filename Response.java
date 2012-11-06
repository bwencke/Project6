import javax.swing.JFrame;

import edu.purdue.cs.cs180.channel.Channel;
import edu.purdue.cs.cs180.channel.ChannelException;
import edu.purdue.cs.cs180.channel.MessageListener;
import edu.purdue.cs.cs180.channel.TCPChannel;

public class Response extends JFrame implements MessageListener {
	private Channel channel = null;
	
	/**
	 * constructs a new Request object
	 * 
	 * @param host a String that represents the hostname of the server
	 * 
	 * @param port an integer that represents the port that the server is running on
	 */
	public Response(String host, int port) {
		initChannel(host, port);
		teamSubmit();
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
			channel.setMessageListener(this); // have this Request object listen for messages (messageReceived())
		} catch (ChannelException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * submits the location request to the server
	 */
	private void teamSubmit() {
		// send the request to the server
		try {
			channel.sendMessage("Response:Billy's Amazing Team"); // send message with location
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
		System.out.println(arg0);
	}
	
	public static void main(String[] args) {
		new Response(args[0], Integer.parseInt(args[1])); // create new Request object with the values entered by the user
	}
}

