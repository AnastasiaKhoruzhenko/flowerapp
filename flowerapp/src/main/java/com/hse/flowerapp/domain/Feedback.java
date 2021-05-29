package com.hse.flowerapp.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "feedback")
@Data
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "setted_email")
    String settedEmail;

    @Column(name = "from_email")
    String fromEmail;

    @Column(name = "name")
    String name;

    @Column(name = "text")
    String text;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSettedEmail() {
        return settedEmail;
    }

    public void setSettedEmail(String settedEmail) {
        this.settedEmail = settedEmail;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
