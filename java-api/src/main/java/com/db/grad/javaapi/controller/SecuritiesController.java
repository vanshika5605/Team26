package com.db.grad.javaapi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.db.grad.javaapi.exception.ResourceNotFoundException;
import com.db.grad.javaapi.model.Securities;
import com.db.grad.javaapi.repository.SecuritiesRepository;
import java.util.*;  

@RestController
@CrossOrigin
@RequestMapping("/api/v2")
public class SecuritiesController {
    @Autowired
    private SecuritiesRepository securitiesRepository;

    @GetMapping("/securities")
    public List < Securities > getAllSecurities() {
        List < Securities > secur = securitiesRepository.findAll();
        Collections.sort(secur, (Securities a1, Securities a2) -> a1.maturitydate.compareTo(a2.maturitydate));
        return secur;
    }

    @GetMapping("/securities/{id}")
    public ResponseEntity < Securities > getSecuritiesById(@PathVariable(value = "id") Long id)
    throws ResourceNotFoundException {
       Securities securities = securitiesRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Security not found for id " + id));
        return ResponseEntity.ok().body(securities);
    }

    @PostMapping("/securities")
    public Securities createSecurities(@Valid @RequestBody Securities securities) {
        return securitiesRepository.saveAndFlush(securities);
    }

    @PutMapping("/securities/{id}")
    public ResponseEntity < Securities > updateSecurity(@PathVariable(value = "id") Long id,
        @Valid @RequestBody Securities securityDetails) throws ResourceNotFoundException {
    	Securities getSecurities = securitiesRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("security not found for this id :: " + id));

    	getSecurities.setIsin(securityDetails.getIsin());
        getSecurities.setCusip(securityDetails.getCusip());
        getSecurities.setIssuer(securityDetails.getIssuer());
        getSecurities.setMaturitydate(securityDetails.getMaturitydate());
        getSecurities.setCoupon(securityDetails.getCoupon());
        getSecurities.setType(securityDetails.getType());
        getSecurities.setFacevalue(securityDetails.getFacevalue());
        getSecurities.setStatus(securityDetails.getStatus());
        

    	
        final Securities updatedSecurities = securitiesRepository.save(getSecurities);
        return ResponseEntity.ok(updatedSecurities);
    }

    // @DeleteMapping("/dogs/{id}")
    // public Map < String, Boolean > deleteDog(@PathVariable(value = "id") Long id)
    // throws Exception {
    // 	Dogs dogs = dogsRepository.findById(id)
    //         .orElseThrow(() -> new ResourceNotFoundException("Dog not found for this id :: " + id));

    // 	dogsRepository.delete(dogs);
    //     Map < String, Boolean > response = new HashMap <>();
    //     response.put("deleted", Boolean.TRUE);
    //     return response;
    // }
}
