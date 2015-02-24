package Message;

import java.time.LocalDate;
import java.time.LocalTime;

public class Message {
	private String from;
	private String to;
	private String content;
	private LocalDate data;
	private LocalTime time;

	public Message(String from, String to, String content, LocalDate data, LocalTime time) {
		this.from = from;
		this.to = to;
		this.content = content;
		this.data = data;
		this.time = time;
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
		return data;
	}
	public LocalTime getTime() {
		return time;
	}
	
	@Override
	public String toString() {
		return "Message [from=" + from + ", to=" + to + ", content=" + content
				+ ", data=" + data + ", time=" + time + "]";
	}
}
