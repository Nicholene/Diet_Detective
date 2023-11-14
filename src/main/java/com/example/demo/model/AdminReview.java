package com.example.demo.model;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
public class AdminReview {
    private Boolean adminApproved;
}
