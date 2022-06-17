package com.kodlamaio.rentACar.core.utilities.adapters.concretes;

import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.core.utilities.adapters.abstracts.FindeksService;

@Service
public class FindeksAdapter implements FindeksService{
	
	Findeks findeks = new Findeks();
	@Override
	public int checkPerson(String identity) {
		int score = findeks.calculatePersonScore(identity);
		return score;
	}
	 
}
