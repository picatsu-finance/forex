package com.picatsu.financeforex.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "ForexModel")
@Builder
public class ForexModel {
    private String code;
    private String name;
}
