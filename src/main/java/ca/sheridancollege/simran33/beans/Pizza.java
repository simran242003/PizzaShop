package ca.sheridancollege.simran33.beans;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Pizza {
	private String name;
	private String size;
	private String crust;
	private float price;
	private String description;
	
}
