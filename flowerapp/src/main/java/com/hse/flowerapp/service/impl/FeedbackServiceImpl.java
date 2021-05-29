package com.hse.flowerapp.service.impl;

import com.hse.flowerapp.domain.Feedback;
import com.hse.flowerapp.dto.FeedbackDto;
import com.hse.flowerapp.repository.FeedbackRepository;
import com.hse.flowerapp.service.FeedbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;

    @Autowired
    public FeedbackServiceImpl(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }


    @Override
    public FeedbackDto sendFeedback(FeedbackDto feedbackDto) {
        Feedback feedback = convertToFeedback(feedbackDto);
        feedbackRepository.save(feedback);
        feedbackDto.setId(feedback.getId());
        return feedbackDto;
    }

    public static Feedback convertToFeedback(FeedbackDto feedbackDto) {
        Feedback feedback = new Feedback();

        feedback.setName(feedbackDto.getName());
        feedback.setText(feedbackDto.getText());
        feedback.setFromEmail(feedbackDto.getFromEmail());
        feedback.setSettedEmail(feedbackDto.getSettedEmail());

        return feedback;
    }
}
