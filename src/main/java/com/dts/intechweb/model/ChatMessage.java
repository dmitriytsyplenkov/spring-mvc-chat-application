package com.dts.intechweb.model;

import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private User author;
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    private String content;
    private MessageType type;
    public enum MessageType {
        JOIN, LEAVE, CHAT
    }

}
