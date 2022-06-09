package com.kodlamaio.rentACar.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.BrandService;
import com.kodlamaio.rentACar.business.requests.brands.CreateBrandRequest;
import com.kodlamaio.rentACar.business.requests.brands.DeleteBrandRequest;
import com.kodlamaio.rentACar.business.requests.brands.UpdateBrandRequest;
import com.kodlamaio.rentACar.business.responses.brands.GetBrandResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.BrandRepository;
import com.kodlamaio.rentACar.entities.concretes.Brand;

@Service
public class BrandManager implements BrandService {
	private BrandRepository brandRepository;
	
	@Autowired //git constructrın parametresine bak onun nesnesini new leyip bana ver.
	public BrandManager(BrandRepository brandRepository) {
		this.brandRepository = brandRepository;
	}

	@Override
	public Result add(CreateBrandRequest createBrandRequest) {
		
		//mapping
		Brand brand = new Brand();
		brand.setName(createBrandRequest.getName());
		this.brandRepository.save(brand);
		return new SuccessResult("BRAND.ADDED");
	}

	@Override
	public DataResult<List<Brand>> getAll() {
		return new SuccessDataResult<List<Brand>>(this.brandRepository.findAll());
	}
	
	@Override
	public DataResult<Brand> getById(GetBrandResponse getBrandResponse) {		
		return new SuccessDataResult<Brand>(this.brandRepository.findById(getBrandResponse.getId()));
	}

	@Override
	public Result delete(DeleteBrandRequest deleteBrandRequest) {
		brandRepository.deleteById((deleteBrandRequest.getId()));
		return new SuccessResult("BRAND.DELETED");
	}

	@Override
	public Result update(UpdateBrandRequest updateBrandRequest) {
		Brand brand = brandRepository.findById(updateBrandRequest.getId());
		brand.setName(updateBrandRequest.getName());
		this.brandRepository.save(brand);
		return new SuccessResult("BRAND.UPDATED");
	}
	
}
