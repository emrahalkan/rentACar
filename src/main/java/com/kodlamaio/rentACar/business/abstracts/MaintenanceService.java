package com.kodlamaio.rentACar.business.abstracts;

import java.util.List;

import com.kodlamaio.rentACar.business.requests.maintenances.CreateMaintenanceRequest;
import com.kodlamaio.rentACar.business.requests.maintenances.DeleteMaintenanceRequest;
import com.kodlamaio.rentACar.business.requests.maintenances.UpdateMaintenanceRequest;
import com.kodlamaio.rentACar.business.responses.maintenances.GetAllMaintenancesResponse;
import com.kodlamaio.rentACar.business.responses.maintenances.GetMaintenanceResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.entities.concretes.Maintenance;

public interface MaintenanceService {
	Result add(CreateMaintenanceRequest createMaintenanceRequest);
	Result delete(DeleteMaintenanceRequest deleteMaintenanceRequest);
	Result update(UpdateMaintenanceRequest updateMaintenanceRequest);
	Result updateState(int id);
	DataResult<GetMaintenanceResponse> getById(int id);
	DataResult<List<GetAllMaintenancesResponse>> getAll();
	public Maintenance findMaintenanceById(int id);
}
