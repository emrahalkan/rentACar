package com.kodlamaio.rentACar.business.abstracts;

import java.util.List;

import com.kodlamaio.rentACar.business.requests.invoices.CreateInvoiceRequest;
import com.kodlamaio.rentACar.business.requests.invoices.DeleteInvoiceRequest;
import com.kodlamaio.rentACar.business.responses.invoices.GetAllInvoicesResponse;
import com.kodlamaio.rentACar.business.responses.invoices.GetInvoiceResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.entities.concretes.AdditionalItem;
import com.kodlamaio.rentACar.entities.concretes.Invoice;
import com.kodlamaio.rentACar.entities.concretes.Rental;

public interface InvoiceService {
	Result add(CreateInvoiceRequest createInvoiceRequest);
	Result delete(DeleteInvoiceRequest deleteInvoiceRequest);
	DataResult<GetInvoiceResponse> getById(int id);
	DataResult<List<GetAllInvoicesResponse>> getAll();
	DataResult<List<AdditionalItem>> getAllAdditionalItems(int additionalItemId);
	DataResult<Rental> getJustRental(int rentalId);
	
	Invoice findInvoiceById(int id);
	Invoice findInvoiceByInvoiceNumber(int invoiceNumber);
}
