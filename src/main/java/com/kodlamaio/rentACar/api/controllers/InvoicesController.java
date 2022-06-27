package com.kodlamaio.rentACar.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.business.abstracts.InvoiceCustomerService;
import com.kodlamaio.rentACar.business.requests.invoices.CreateInvoiceRequest;
import com.kodlamaio.rentACar.business.requests.invoices.DeleteInvoiceRequest;
import com.kodlamaio.rentACar.business.requests.invoices.UpdateInvoiceRequest;
import com.kodlamaio.rentACar.business.responses.invoices.GetAllInvoicesResponse;
import com.kodlamaio.rentACar.business.responses.invoices.GetInvoiceResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.entities.concretes.AdditionalItem;

@RestController
@RequestMapping("/api/invoices")
public class InvoicesController {
	
	
	private InvoiceCustomerService invoiceService;
	
	@Autowired
	public InvoicesController(InvoiceCustomerService invoiceService) {
		this.invoiceService = invoiceService;
	}

	@PostMapping("/add")
	public Result add(@RequestBody CreateInvoiceRequest createInvoiceRequest) {
		return this.invoiceService.add(createInvoiceRequest);
	}
	
	@PostMapping("/delete")
	public Result delete(@RequestBody DeleteInvoiceRequest deleteInvoiceRequest) {
		return this.invoiceService.delete(deleteInvoiceRequest);
	}
	
	@PostMapping("/update")
	public Result update(@RequestBody UpdateInvoiceRequest updateInvoiceRequest) {
		return this.invoiceService.update(updateInvoiceRequest);
	}
	
	@GetMapping("/getById")
	public DataResult<GetInvoiceResponse> getById(int id){
		return this.invoiceService.getById(id);
	}
	
	@GetMapping("getAll")
	public DataResult<List<GetAllInvoicesResponse>> getAll(){
		return this.invoiceService.getAll();
	}
	
	@GetMapping("/getAllAdditionalItems")
	public DataResult<List<AdditionalItem>> getAllAdditionalItems(@RequestParam int rentalId) {
		return this.invoiceService.getAllAdditionalItems(rentalId);
	}
}
