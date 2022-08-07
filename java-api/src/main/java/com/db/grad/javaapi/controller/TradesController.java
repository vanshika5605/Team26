package com.db.grad.javaapi.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.db.grad.javaapi.exception.ResourceNotFoundException;
import com.db.grad.javaapi.model.Securities;
import com.db.grad.javaapi.model.Trades;
import com.db.grad.javaapi.repository.SecuritiesRepository;
import com.db.grad.javaapi.repository.TradesRepository;

@RestController
@CrossOrigin
@RequestMapping("/api/v2")
public class TradesController {
	@Autowired
	private TradesRepository tradesRepository;
	
	@Autowired
	private SecuritiesRepository securitiesRepository;
	
//	Get trade by ID
	@GetMapping("/trades/{id}")
	public ResponseEntity<Trades> getTradeById(@PathVariable(value="id") Long id) 
	throws ResourceNotFoundException{
		Trades trades = tradesRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Trade not found for id " +id));
		return ResponseEntity.ok().body(trades);
	}
	
//	Retrieve security to which the trade belongs
	@GetMapping("/securities/trade/{id}")
	public ResponseEntity<Securities> getSecurityByTrade(@PathVariable(value="id") Long id)
	throws ResourceNotFoundException{
		Trades trades = tradesRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Trade not found for id " +id));
		Securities security = trades.getSecurity();
		return ResponseEntity.ok().body(security);
	}
	
//	Create and update a trade
	@PostMapping("/trades")
	public Trades createTrade(@Valid @RequestBody Trades trades) {
		return tradesRepository.saveAndFlush(trades);
	}
	
	@PutMapping("/trades/{id}")
    public ResponseEntity < Trades > updateTrade(@PathVariable(value = "id") Long id,
        @Valid @RequestBody Trades tradeDetails) throws ResourceNotFoundException {
    	Trades getTrades = tradesRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Trade not found for this id :: " + id));

    	getTrades.setBook(tradeDetails.getBook());
    	getTrades.setCounterparty(tradeDetails.getCounterparty());
    	getTrades.setSecurity(tradeDetails.getSecurity());
    	getTrades.setQuantity(tradeDetails.getQuantity());
        getTrades.setStatus(tradeDetails.getStatus());
        getTrades.setPrice(tradeDetails.getPrice());     
        getTrades.setBuysell(tradeDetails.getBuysell());
        getTrades.setTradedate(tradeDetails.getTradedate());
        getTrades.setSettlementdate(tradeDetails.getSettlementdate());
    	
        final Trades updatedTrades = tradesRepository.save(getTrades);
        return ResponseEntity.ok(updatedTrades);
    }
}
