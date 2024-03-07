package ca.sheridancollege.simran33.beans;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Cart {

	private String name;
	private String size;
	private String crust;
	private int quantity;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
	private float price;
	
	
}
