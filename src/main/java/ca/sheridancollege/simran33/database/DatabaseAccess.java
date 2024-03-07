package ca.sheridancollege.simran33.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ca.sheridancollege.simran33.beans.Cart;
import ca.sheridancollege.simran33.beans.Pizza;
import ca.sheridancollege.simran33.beans.User;


@Component
@Repository
public class DatabaseAccess {

	@Autowired
	protected NamedParameterJdbcTemplate jdbc;
	
	public void insertUser(User user) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "INSERT INTO users(username, password, email, userRole) VALUES (:username, :password, :email, :userRole)";
		namedParameters.addValue("username", user.getUsername());
		namedParameters.addValue("password", user.getPassword());
		namedParameters.addValue("email", user.getEmail());
		namedParameters.addValue("userRole", "USER");
		int rowsAffected = jdbc.update(query, namedParameters);
		if (rowsAffected > 0) {
		System.out.println("User inserted into database");
		}
	}
	
	

	public List<Pizza> getPizzaList() {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT * FROM pizza";
		return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Pizza>(Pizza.class));
	}
	
	public List<Pizza> getPizzaDetails(String name) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT * FROM pizza where name = :name";
		 namedParameters.addValue("name", name);
		 return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Pizza>(Pizza.class));
	}
	
	public void insertPizzaInCart(Cart cart) {
	    MapSqlParameterSource namedParameters = new MapSqlParameterSource();
	    String query = "INSERT INTO cart(name, size, crust, quantity, price) VALUES (:name, :size, :crust, :quantity, :price)";

	    namedParameters.addValue("name", cart.getName());
	    namedParameters.addValue("size", cart.getSize());
	    namedParameters.addValue("crust", cart.getCrust());
	    namedParameters.addValue("quantity", cart.getQuantity());
	    namedParameters.addValue("price", cart.getPrice());

	    int rowsAffected = jdbc.update(query, namedParameters);
	    if (rowsAffected > 0) {
	        System.out.println("Pizza inserted into the database cart");
	    }
	}

	public List<Cart> getCartList() {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT * FROM cart";
		return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Cart>(Cart.class));
	}
	
	public Pizza getPizzaByName(String name) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT * FROM pizza WHERE name = :name";
		namedParameters.addValue("name", name);
		List<Pizza> result = jdbc.query(query, namedParameters, new BeanPropertyRowMapper<>(Pizza.class));
		return result.isEmpty() ? null : result.get(0);
	}
	
	public Cart getCartByName(String name) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT * FROM cart WHERE name = :name";
		namedParameters.addValue("name", name);
		List<Cart> result = jdbc.query(query, namedParameters, new BeanPropertyRowMapper<>(Cart.class));
		return result.isEmpty() ? null : result.get(0);
	}
	
	public void deleteCart(String name) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "DELETE FROM cart where name = :name";
		namedParameters.addValue("name", name);
		jdbc.update(query, namedParameters);
	}
	
	public void deletePizza(String name) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "DELETE FROM pizza where name = :name";
		namedParameters.addValue("name", name);
		jdbc.update(query, namedParameters);
	}
	
	public void addPizza(Pizza pizza) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "INSERT INTO pizza(name, size, crust, price, description) VALUES (:name, :size, :crust, :price, :description)";
		namedParameters.addValue("name", pizza.getName());
		namedParameters.addValue("size", pizza.getSize());
		namedParameters.addValue("crust", pizza.getCrust());
		namedParameters.addValue("price", pizza.getPrice());
		namedParameters.addValue("description", pizza.getDescription());
		
		int rowsAffected = jdbc.update(query, namedParameters);
		if (rowsAffected > 0) {
		System.out.println("Pizza inserted into database");
		}
	}
	public User findUserAccount(String username) {
		MapSqlParameterSource namedParameters = new
		MapSqlParameterSource();
		String query = "SELECT * FROM users where username = :username";
		namedParameters.addValue("username", username);
		try {
		return jdbc.queryForObject(query, namedParameters, new
		BeanPropertyRowMapper<>(User.class));
		} catch (EmptyResultDataAccessException erdae) {
		return null;
		}
		}
	
	public List<String> getRolesByUsername(String username) {
		MapSqlParameterSource namedParameters = new
		MapSqlParameterSource();
		String query = "SELECT userRole FROM  users where username = :username";
		namedParameters.addValue("username", username);
		return jdbc.queryForList(query, namedParameters, String.class);
		}
		
	
	
	
}
