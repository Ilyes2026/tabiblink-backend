package com.tabiblink.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequest {

    private String ancienMotDePasse;
    private String nouveauMotDePasse;
}