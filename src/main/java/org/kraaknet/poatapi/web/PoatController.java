package org.kraaknet.poatapi.web;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kraaknet.poatapi.service.PoatService;
import org.kraaknet.poatapi.web.dto.PowerOfAttorneyReferenceDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/power-of-attorney/", produces = MediaType.APPLICATION_JSON_VALUE)
public class PoatController {

    @NonNull
    private final PoatService service;


    @GetMapping
    public List<PowerOfAttorneyReferenceDTO> getPowerOfAttorneyReferences() {
        log.debug("getPowerOfAttorneyReferences()");
        return service.getAllPowerOfAttorneyReferences();
    }
}
