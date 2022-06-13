package com.kodlamaio.rentACar.business.responses.maintenances;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetMaintenanceResponse {
	private int id;
	private Date dateSent;
	private Date dateReturned;
	private int carId;
}
