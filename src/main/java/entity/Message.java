package entity;

import annotation.Column;
import annotation.Id;
import annotation.Table;

import java.util.Date;

@Table(tableName = "message")
public class Message {
    @Id(idName = "m_id")
    @Column(columnName = "m_id")
    private int id;
    @Column(columnName = "m_from_id")
    private String fromId;
    @Column(columnName = "m_to_id")
    private String toId;
    @Column(columnName = "m_message")
    private String message;
    @Column(columnName = "m_time")
    private Date time;

    public Message() {
    }

    public Message(int id, String fromId, String toId, String message, Date time) {
        this.id = id;
        this.fromId = fromId;
        this.toId = toId;
        this.message = message;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", fromId='" + fromId + '\'' +
                ", toId='" + toId + '\'' +
                ", message='" + message + '\'' +
                ", time=" + time +
                '}';
    }
}
