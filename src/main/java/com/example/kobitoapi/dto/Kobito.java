package com.example.kobitoapi.dto;

import lombok.Data;

import java.awt.*;
import java.time.LocalDateTime;

@Data
public class Kobito {
    private long id;
    private String name;
    private String description;
    private String imageUrl;
    private LocalDateTime createdAt;
}
