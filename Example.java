package edu.purdue.cs.cs180.channel;

/**
 * A sample server application, both server and clients has to implement the
 * MessageListener interface.
 */
class Server implements MessageListener {
	/**
	 * Creates a server channel on the current machine, and assign port 8888 to
	 * it.
	 */
	TCPChannel channel = new TCPChannel(8888);

	/**
	 * Constructor.
	 */
	public Server() {
		// inform the channel that when new messages are received forward them
		// to the current server object.
		channel.setMessageListener(this);
	}

	/**
	 * Implement the message received method from the MessageListener interface
	 * Will be invoked (called) whenever a message is received on the channel
	 * "channel"
	 * 
	 * @param message
	 *            the info received
	 * @param clientID
	 *            an id that identifies where it came from.
	 */
	@Override
	public void messageReceived(String message, int clientID) {
		System.out.println(message);
		// simple reply that message received, send it to the same client it
		// came from.
		try {
			channel.sendMessage(message + ": Received", clientID);
		} catch (ChannelException e) {
			e.printStackTrace();
		}
	}
}

/**
 * A sample client.
 */
class Client implements MessageListener {
	/**
	 * Unlike the server channel, the client channel also need to specify the
	 * machine address where the server is (using an overloaded constructor) for
	 * TCPChannel.
	 */
	TCPChannel channel = null;

	/**
	 * Constructor.
	 */
	public Client() {
		try {
			this.channel = new TCPChannel("localhost", 8888);
		} catch (ChannelException e) {
			e.printStackTrace();
		}
		// inform the channel that when new messages are received forward them
		// to the current client object.
		this.channel.setMessageListener(this);
	}

	@Override
	public void messageReceived(String message, int channelID) {
		// simply print the message.
		System.out.println(message);
	}

	public void sendMessage(String message) {
		// send a message, since we did not specify a client ID, then the
		// message will be sent to the server.
		try {
			channel.sendMessage(message);
		} catch (ChannelException e) {
			e.printStackTrace();
		}
	}

}

/**
 * A sample controller, that has one server and 2 clients.
 */
public class Example {

	public static void main(String[] args) throws ChannelException {
		// create the objects.
		Server server = new Server();
		Client client1 = new Client();
		Client client2 = new Client();

		// send a message form a clients.
		client1.sendMessage("Hi from 1");
		client2.sendMessage("Hi from 2");

		// wait for the reply.
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// close the client and server channels.
		client1.channel.close();
		client2.channel.close();
		server.channel.close();

	}
}