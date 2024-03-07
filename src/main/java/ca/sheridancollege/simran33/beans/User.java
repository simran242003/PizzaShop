package ca.sheridancollege.simran33.beans;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
	private String username;
	private String password;
	private String email;
	private String userRole;
}