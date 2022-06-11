package com.kodlamaio.rentACar.entities.concretes;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="cars")
public class Car {
	@Id()
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	
	@Column(name="id")
	private int id;
	
	@Column(name="description")
	private String description;
	
	@Column(name="dailyPrice")
	private double dailyPrice;
	
	@ManyToOne
	@JoinColumn(name = "brand_id")
	private Brand brand;
	
	@ManyToOne
	@JoinColumn(name = "color_id")
	private Color color;
	
	@Column(name="numberPlate")
	private String numberPlate;
	
	@Column(name="kilometer")
	private int kilometer;
	
	@Column(name="state")
	private int state;
	
	@OneToMany(mappedBy = "car")
	List<Maintenance> maintenances;
	
	
//	public void setBrandId(Brand brand) {
//		this.brand.setId(brand.getId());
//	}
	
	
}
