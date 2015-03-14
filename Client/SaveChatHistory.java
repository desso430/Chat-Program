package Client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Queue;

import Message.Message;

public class SaveChatHistory implements Runnable {
	private static final String dash = " - ";
	private Queue<Message> chatHistory;

	public SaveChatHistory(Queue<Message> chat) {
		this.chatHistory = chat;
	}
	
	@Override
	public void run() {
	   saveHistory();	
	}
	
	private void saveHistory() {
		try {
			String between = chatHistory.peek().getFrom() + dash + chatHistory.peek().getTo();
			File fileHistory = new File("ChatHistory" + File.separator + between + ".txt");
			PrintWriter pr = new PrintWriter(fileHistory);
			
			pr.write(" Chat between:  " + between + "       Started in: " + getTimestamp(chatHistory.peek()) + "\r\n\r\n");
			while(!chatHistory.isEmpty()) {
				pr.write(" " + chatHistory.poll().toString());
				pr.write("\r\n");
			}
			
			pr.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private String getTimestamp(Message message) {
		String date = message.getData().getDayOfMonth() + " " +  message.getData().getMonth() + " ";
		String time = message.getTime().getHour() + ":" + message.getTime().getMinute();
		
		  return date + time ;
	}
}
