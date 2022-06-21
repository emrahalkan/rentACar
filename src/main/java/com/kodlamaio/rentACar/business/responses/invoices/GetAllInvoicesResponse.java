package com.kodlamaio.rentACar.business.responses.invoices;

import java.time.LocalDate;
import java.util.List;

import com.kodlamaio.rentACar.entities.concretes.AdditionalItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllInvoicesResponse {
	private int id;
	private int rentalId;
	private int invoiceNumber;
	private String carNumberPlate;
	private String carDescription;
	private int additionalServiceId;
	private String userFirstNmae;
	private LocalDate currentlyDate;
	private double totalPrice;
	private List<AdditionalItem> additionalItems;
}
