package com.app.model.board;

import com.app.model.DaoModel;

import javax.persistence.*;

@Entity
@Table(name = "board_message")
public class BoardMessage extends DaoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BoardMessageIdGenerator")
    @SequenceGenerator(name="BoardMessageIdGenerator", sequenceName = "board_message_id_sequence", allocationSize=1)
    private Long id;

    private String type;

    private String message;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
