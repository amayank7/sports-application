package com.sportsapp.newsservice.dto.request;

import com.sportsapp.newsservice.enums.NewsReferenceType;
import lombok.Data;

@Data
public class CreateNewsRequestDto {
    private String title;
    private String description;
    private NewsReferenceType referenceType;
    private Long referenceId;
}
