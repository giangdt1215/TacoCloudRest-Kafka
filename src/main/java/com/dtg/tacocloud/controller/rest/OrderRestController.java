package com.dtg.tacocloud.controller.rest;

import com.dtg.tacocloud.data.jpa.OrderJPARepository;
import com.dtg.tacocloud.messaging.OrderMessagingService;
import com.dtg.tacocloud.model.TacoOrder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/orders", produces = "application/json")
@CrossOrigin(origins = "http://tacocloud:8080")
public class OrderRestController {

	private OrderJPARepository repo;
	private OrderMessagingService orderMessagingService;

	public OrderRestController(OrderJPARepository orderRepo, OrderMessagingService orderMessagingService){
		this.repo = orderRepo;
		this.orderMessagingService = orderMessagingService;
	}

	@GetMapping
	public Iterable<TacoOrder> allOrders(){
		return repo.findAll();
	}
	
	@PostMapping(consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public TacoOrder postOrder(@RequestBody TacoOrder order) {
		orderMessagingService.sendOrder(order);
		return repo.save(order);
	}
	
	@PutMapping(path="/{orderId}", consumes="application/json")
    public TacoOrder putOrder(@PathVariable("orderId") Long orderId,
    		@RequestBody TacoOrder order) {
    	order.setId(orderId);
    	return repo.save(order);
    }
    
    @PatchMapping(path="/{orderId}", consumes = "application/json")
    public TacoOrder patchOrder(@PathVariable("orderId") Long id,
    		@RequestBody TacoOrder patch) {
    	Optional<TacoOrder> optOrder = repo.findById(id);
    	if(optOrder.isPresent()) {
    		TacoOrder order = optOrder.get();
    		
    		if(patch.getDeliveryName() != null) {
    			order.setDeliveryName(patch.getDeliveryName());
    		}
    		
    		if(patch.getDeliveryStreet() != null) {
    			order.setDeliveryStreet(patch.getDeliveryStreet());
    		}
    		
    		if(patch.getDeliveryCity() != null) {
    			order.setDeliveryCity(patch.getDeliveryCity());
    		}
    		
    		if(patch.getDeliveryState() != null) {
    			order.setDeliveryState(patch.getDeliveryState());
    		}
    		
    		if(patch.getDeliveryZip() != null) {
    			order.setDeliveryZip(patch.getDeliveryZip());
    		}
    		
    		if(patch.getCcNumber() != null) {
    			order.setCcNumber(patch.getCcNumber());
    		}
    		
    		if(patch.getCcExpiration() != null) {
    			order.setCcExpiration(patch.getCcExpiration());
    		}
    		
    		if(patch.getCcCVV() != null) {
    			order.setCcCVV(patch.getCcCVV());
    		}
    		
    		return repo.save(order);
    	}
    	
    	return null;
    }
    
    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable("orderId") Long id) {
    	try {
    		repo.deleteById(id);
    	} catch (EmptyResultDataAccessException e) {
    		
    	}
    	
    }
}
