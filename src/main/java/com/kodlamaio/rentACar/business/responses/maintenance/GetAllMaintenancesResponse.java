package com.kodlamaio.rentACar.business.responses.maintenance;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllMaintenancesResponse {
	private int id;
	private Date dateSent;
	private Date dateReturned;
	private int carId;
}
