package com.kodlamaio.rentACar.business.concretes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.InvoiceService;
import com.kodlamaio.rentACar.business.abstracts.RentalService;
import com.kodlamaio.rentACar.business.requests.invoices.CreateInvoiceRequest;
import com.kodlamaio.rentACar.business.requests.invoices.DeleteInvoiceRequest;
import com.kodlamaio.rentACar.business.responses.invoices.GetAllInvoicesResponse;
import com.kodlamaio.rentACar.business.responses.invoices.GetInvoiceResponse;
import com.kodlamaio.rentACar.core.utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.AdditionalItemRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.AdditionalServiceRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.InvoiceRepository;
import com.kodlamaio.rentACar.entities.concretes.AdditionalItem;
import com.kodlamaio.rentACar.entities.concretes.AdditionalService;
import com.kodlamaio.rentACar.entities.concretes.Invoice;
import com.kodlamaio.rentACar.entities.concretes.Rental;

@Service
public class InvoiceManager implements InvoiceService {
	
	private InvoiceRepository invoiceRepository;
	private ModelMapperService modelMapperService;
	private AdditionalServiceRepository additionalServiceRepository;
	private RentalService rentalService;
	
	@Autowired
	private AdditionalItemRepository additionalItemRepository;
	
	

	public InvoiceManager(InvoiceRepository invoiceRepository, ModelMapperService modelMapperService,
			AdditionalServiceRepository additionalServiceRepository, RentalService rentalService) {
		this.invoiceRepository = invoiceRepository;
		this.modelMapperService = modelMapperService;
		this.additionalServiceRepository = additionalServiceRepository;
		this.rentalService = rentalService;
	}

	@Override
	public Result add(CreateInvoiceRequest createInvoiceRequest) {
		checkInvoiceNumber(createInvoiceRequest.getInvoiceNumber());
		checkRentalExists(createInvoiceRequest.getRentalId());
		Invoice invoice = this.modelMapperService.forRequest().map(createInvoiceRequest, Invoice.class);
		invoice.setCurrentDate(LocalDate.now());
		invoice.setTotalPrice(calculateTotalPrice(createInvoiceRequest.getRentalId()));
		invoice.setDeleted(0);
		this.invoiceRepository.save(invoice);
		
		return new SuccessResult("INVOICE.ADDED");
	}

	@Override
	public Result delete(DeleteInvoiceRequest deleteInvoiceRequest) {
		checkInvoiceExists(deleteInvoiceRequest.getId());
		Invoice invoice = this.invoiceRepository.findById(deleteInvoiceRequest.getId());
		invoice.setDeleted(1);
		return new SuccessResult("INVOICE.DELETED");
	}

	@Override
	public DataResult<GetInvoiceResponse> getById(int id) {
		checkInvoiceExists(id);
		Invoice invoice = this.invoiceRepository.findById(id);
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
	
	@Override
	public DataResult<List<AdditionalItem>> getAllAdditionalItems(int rentalId) {
		List<AdditionalService> additionalServices = this.additionalServiceRepository.getByRentalId(rentalId);
		List<AdditionalItem> additionalItems = new ArrayList<AdditionalItem>();
		
		for (AdditionalService additionalService : additionalServices) {
			AdditionalItem additionalItem = this.additionalItemRepository.findById(additionalService.getAdditionalItem().getId());
			additionalItems.add(additionalItem);
		}
		return new SuccessDataResult<List<AdditionalItem>>(additionalItems);
	}
	
	@Override
	public DataResult<Rental> getJustRental(int rentalId) {
		this.rentalService.findRentalById(rentalId);
		Rental rental = this.rentalService.findRentalById(rentalId);
		return new SuccessDataResult<Rental>(rental);
	}
	
	private double calculateTotalPrice(int rentalId) {
		Rental rental = this.rentalService.findRentalById(rentalId);
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

	private void checkRentalExists(int rentalId) {
		this.rentalService.findRentalById(rentalId);
	}
	
	private void checkInvoiceNumber(int invoiceNumber) {
		Invoice invoice = this.invoiceRepository.findByInvoiceNumber(invoiceNumber);
		if (invoice != null) {
			throw new BusinessException("THIS.INVOICE.ALREADY.EXISTS");
		}
	}
	
	
	private void checkInvoiceExists(int invoiceId) {
		Invoice invoice = this.invoiceRepository.findById(invoiceId);
		if (invoice == null) {
			throw new BusinessException("THERE.IS.NOT.THIS.INVOICE");
		}
	}

	@Override
	public Invoice findInvoiceById(int id) {
		Invoice invoice = this.invoiceRepository.findById(id);
		if (invoice == null) {
			throw new BusinessException("THERE.IS.NOT.THIS.INVOICE");
		}
		return invoice;
	}

	@Override
	public Invoice findInvoiceByInvoiceNumber(int invoiceNumber) {
		Invoice invoice = this.invoiceRepository.findByInvoiceNumber(invoiceNumber);
		if (invoice == null) {
			throw new BusinessException("THERE.IS.NOT.THIS.INVOICE");
		}
		return invoice;
	}
}
