package com.example.parcial.entities;

import jakarta.persistence.Entity;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder

@Entity
public class Dna extends Base implements Serializable {
    private String dna;
    private boolean isMutant;
}
