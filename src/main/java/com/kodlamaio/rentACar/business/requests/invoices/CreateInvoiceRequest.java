package com.kodlamaio.rentACar.business.requests.invoices;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateInvoiceRequest {
	private int invoiceNumber;
	private int rentalId;
}
