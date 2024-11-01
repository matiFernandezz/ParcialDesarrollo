package com.example.parcial.controllers;

import com.example.parcial.dto.DnaRequest;
import com.example.parcial.dto.DnaResponse;
import com.example.parcial.services.DnaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mutant")
public class DnaController {
    private final DnaService dnaService;

    public DnaController (DnaService dnaService) {
        this.dnaService = dnaService;
    }

    @PostMapping
    @RequestMapping("")
    public ResponseEntity<DnaResponse> analizarMutante (@Valid @RequestBody DnaRequest dnaRequest){
        boolean isMutant = dnaService.analyzeDna(dnaRequest.getDna());
        DnaResponse dnaResponse = new DnaResponse(isMutant);
        if (isMutant){
            return ResponseEntity.ok(dnaResponse);
        }else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(dnaResponse);
        }
    }
}
