package com.kodlamaio.rentACar.entities.concretes;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="maintenances")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","cars"})

public class Maintenance {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="dateSent")
	private Date dateSent;
	
	@Column(name="dateReturned")
	private Date dateReturned;
	
	@ManyToOne
	@JoinColumn(name="car_id")
	private Car car;
}
