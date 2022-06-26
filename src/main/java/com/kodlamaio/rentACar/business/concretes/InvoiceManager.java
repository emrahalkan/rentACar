package com.kodlamaio.rentACar.business.concretes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.InvoiceService;
import com.kodlamaio.rentACar.business.requests.invoices.CreateInvoiceRequest;
import com.kodlamaio.rentACar.business.requests.invoices.DeleteInvoiceRequest;
import com.kodlamaio.rentACar.business.requests.invoices.UpdateInvoiceRequest;
import com.kodlamaio.rentACar.business.responses.invoices.GetAllInvoicesResponse;
import com.kodlamaio.rentACar.business.responses.invoices.GetInvoiceResponse;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.AdditionalItemRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.AdditionalServiceRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.InvoiceRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.RentalRepository;
import com.kodlamaio.rentACar.entities.concretes.AdditionalItem;
import com.kodlamaio.rentACar.entities.concretes.AdditionalService;
import com.kodlamaio.rentACar.entities.concretes.Invoice;
import com.kodlamaio.rentACar.entities.concretes.Rental;

@Service
public class InvoiceManager implements InvoiceService {
	
	private InvoiceRepository invoiceRepository;
	private ModelMapperService modelMapperService;
	private AdditionalServiceRepository additionalServiceRepository;
	private RentalRepository rentalRepository;
	
	@Autowired
	private AdditionalItemRepository additionalItemRepository;
	
	

	public InvoiceManager(InvoiceRepository invoiceRepository, ModelMapperService modelMapperService,
			AdditionalServiceRepository additionalServiceRepository, RentalRepository rentalRepository) {
		this.invoiceRepository = invoiceRepository;
		this.modelMapperService = modelMapperService;
		this.additionalServiceRepository = additionalServiceRepository;
		this.rentalRepository = rentalRepository;
	}

	@Override
	public Result add(CreateInvoiceRequest createInvoiceRequest) {
		Invoice invoice = this.modelMapperService.forRequest().map(createInvoiceRequest, Invoice.class);
		invoice.setCurrentDate(LocalDate.now());
		invoice.setTotalPrice(calculateTotalPrice(createInvoiceRequest.getRentalId()));
		
		this.invoiceRepository.save(invoice);
		
		return new SuccessResult("INVOICE.ADDED");
	}

	@Override
	public Result delete(DeleteInvoiceRequest deleteInvoiceRequest) {
		this.invoiceRepository.deleteById(deleteInvoiceRequest.getId());
		return new SuccessResult("INVOICE.DELETED");
	}

	@Override
	public Result update(UpdateInvoiceRequest updateInvoiceRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataResult<GetInvoiceResponse> getById(int id) {
		Invoice invoice = this.invoiceRepository.findById(id).get();
		GetInvoiceResponse response = this.modelMapperService.forResponse().map(invoice, GetInvoiceResponse.class);
		return new SuccessDataResult<GetInvoiceResponse>(response, "GET.INVOICE");
	}

	@Override
	public DataResult<List<GetAllInvoicesResponse>> getAll() {
		List<Invoice> getAllInvoicesResponses = this.invoiceRepository.findAll();
		List<GetAllInvoicesResponse> response = getAllInvoicesResponses.stream()
				.map(invoice -> this.modelMapperService.forResponse()
				.map(getAllInvoicesResponses, GetAllInvoicesResponse.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllInvoicesResponse>>(response, "GET.ALL.INVOICES");
	}
	
	private double calculateTotalPrice(int rentalId) {
		Rental rental = this.rentalRepository.findById(rentalId).get();
		double totalPrice = rental.getTotalPrice() + allRentalAdditionalTotalPrice(rentalId);
		return totalPrice;
	}
	
	private double allRentalAdditionalTotalPrice(int id) {
		double totalAdditionalService = 0;
		List<AdditionalService> additionalServices = this.additionalServiceRepository.getByRentalId(id);
		for (AdditionalService additionalService : additionalServices) {
			totalAdditionalService += additionalService.getTotalPrice();
		}
		return totalAdditionalService;
	}

	@Override
	public DataResult<List<AdditionalItem>> getAllAdditionalItems(int rentalId) {
		List<AdditionalService> additionalServices = this.additionalServiceRepository.getByRentalId(rentalId);
		List<AdditionalItem> additionalItems = new ArrayList<AdditionalItem>();
		
		for (AdditionalService additionalService : additionalServices) {
			AdditionalItem additionalItem = this.additionalItemRepository.findById(additionalService.getAdditionalItem().getId()).get();
			additionalItems.add(additionalItem);
		}
		return new SuccessDataResult<List<AdditionalItem>>(additionalItems);
	}
}
