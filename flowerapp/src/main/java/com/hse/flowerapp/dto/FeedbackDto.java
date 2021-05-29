package com.hse.flowerapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hse.flowerapp.domain.Feedback;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeedbackDto {

    Long id;
    String settedEmail;
    String fromEmail;
    String name;
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

    public static FeedbackDto convertToDto(Feedback feedback) {
        FeedbackDto feedbackDto = new FeedbackDto();

        feedbackDto.setId(feedback.getId());
        feedbackDto.setSettedEmail(feedback.getSettedEmail());
        feedbackDto.setFromEmail(feedback.getFromEmail());
        feedbackDto.setName(feedback.getName());
        feedbackDto.setText(feedback.getText());

        return feedbackDto;
    }
}
