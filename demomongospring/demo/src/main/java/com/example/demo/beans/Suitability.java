package com.example.demo.beans;

import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document
public class Suitability
{
    String type;
    int level;

}
