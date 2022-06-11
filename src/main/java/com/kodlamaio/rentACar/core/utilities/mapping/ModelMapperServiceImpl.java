package com.kodlamaio.rentACar.core.utilities.mapping;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

@Service //IoC otomatik yap ondan yararlan

//Bean bellekte oluşturulmuş insteance demek
public class ModelMapperServiceImpl implements ModelMapperService {
	private ModelMapper  modelMapper;
	
	public ModelMapperServiceImpl(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	@Override
	public ModelMapper forResponse() {
		this.modelMapper.getConfiguration().setAmbiguityIgnored(true).
		setMatchingStrategy(MatchingStrategies.LOOSE);
		return this.modelMapper;
	}

	@Override
	public ModelMapper forRequest() {
		this.modelMapper.getConfiguration().setAmbiguityIgnored(true).
		setMatchingStrategy(MatchingStrategies.STANDARD);		
		return this.modelMapper;
	}
	
}
