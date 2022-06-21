package com.kodlamaio.rentACar.business.responses.invoices;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetInvoiceResponse {
	private int id;
	private int rentalId;
	private int invoiceNumber;
	private String carNumberPlate;
	private String carDescription;
	private int additionalServiceId;
	private String userFirstNmae;
	private LocalDate currentlyDate;
	private double totalPrice;
}
