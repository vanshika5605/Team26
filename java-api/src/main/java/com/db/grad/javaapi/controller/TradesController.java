package com.db.grad.javaapi.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import java.text.SimpleDateFormat; 
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

import com.db.grad.javaapi.repository.BooksRepository;
import com.db.grad.javaapi.repository.CounterpartiesRepository;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v2")
public class TradesController {
	@Autowired
	private TradesRepository tradesRepository;
	
	@Autowired
	private SecuritiesRepository securitiesRepository;

	@Autowired
	private BooksRepository booksRepository;
	@Autowired
	private CounterpartiesRepository counterpartiesRepository;


	  @GetMapping("/alltrades")
    public List < Trades > getAllTrades() {
        List < Trades > trade = tradesRepository.findAll();
         return trade;
    }
	
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
	@PostMapping("/trades/{id}/{bid}/{sid}/{cid}/{quantity}/{status}/{price}/{buysell}/{tradedate}/{settlementdate}")
	public Trades createTrade(@PathVariable(value = "id") Long id,@PathVariable(value = "bid") Long bid,@PathVariable(value = "sid") Long sid,@PathVariable(value = "cid") Long cid,
	@PathVariable(value = "quantity") int quantity,@PathVariable(value = "status") String status,
	@PathVariable(value = "price") Long price,@PathVariable(value = "buysell") String buysell,@PathVariable(value = "tradedate") String tradedate,
	@PathVariable(value = "settlementdate")String settlementdate) throws ResourceNotFoundException,Exception{


		Trades trade = new Trades();
		trade.id=id;
		Date d1=new SimpleDateFormat("yyyy-MM-dd").parse(tradedate);
		Date d2=new SimpleDateFormat("yyyy-MM-dd").parse(settlementdate);
		trade.security = securitiesRepository.findById(sid)
            .orElseThrow(() -> new ResourceNotFoundException("security not found for this id :: " + sid));
		
		trade.quantity=quantity;
		trade.status=status;
		trade.price=price;
		trade.buysell=buysell;
		trade.tradedate=d1;
		trade.settlementdate=d2;

		trade.book = booksRepository.findById(bid)
            .orElseThrow(() -> new ResourceNotFoundException("security not found for this id :: " + bid));
		
		trade.counterparty = counterpartiesRepository.findById(cid)
            .orElseThrow(() -> new ResourceNotFoundException("security not found for this id :: " + cid));
		
		






		
		return tradesRepository.saveAndFlush(trade);

		// return trade;
	}

	// @PostMapping("/trades/{bid}/{cid}/{sid}")
	// public Trades createTrade(@Valid @RequestBody Trades trades, @PathVariable(value="bid") Long bid, 
	// 		@PathVariable(value="cid") Long cid, @PathVariable(value="sid") Long sid) {
	// 	// trades.setSecurity(securitiesRepository.findById(sid));
	// 	System.ou
	// 	return tradesRepository.saveAndFlush(trades);
	// }
	
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
