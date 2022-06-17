package com.kodlamaio.rentACar.core.utilities.adapters.concretes;

public class Findeks {
	int calculatePersonScore(String identity) {
		int max = 1900;
		int min = 650;
		int score = (int) Math.floor(Math.random() * (max - min + 1) + min);
		System.out.println("Findeks Score :" + score);
		return score;
	}
}