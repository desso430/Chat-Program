package Message;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5169217555757945956L;
	private String from;
	private String to;
	private String content;
	private LocalDate localDate;
	private LocalTime localTime;

	public Message(String from, String to, String content) {
		this.from = from;
		this.to = to;
		this.content = content;
		this.localDate = LocalDate.now();
		this.localTime = LocalTime.now();
	}

	public String getFrom() {
		return from;
	}
	public String getTo() {
		return to;
	}
	public String getContent() {
		return content;
	}
	public LocalDate getData() {
		return localDate;
	}
	public LocalTime getTime() {
		return localTime;
	}
	
	@Override
	public String toString() {
		String date = localDate.getDayOfMonth() + " " +  localDate.getMonth() + " ";
		String time = localTime.getHour() + ":" + localTime.getMinute();
		
	  return "(" + date + time + ") " + from + " : " + content;
	}
}
