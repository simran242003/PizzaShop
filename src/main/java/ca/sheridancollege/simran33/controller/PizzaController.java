package ca.sheridancollege.simran33.controller;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ca.sheridancollege.simran33.beans.Cart;
import ca.sheridancollege.simran33.beans.Pizza;
import ca.sheridancollege.simran33.beans.User;
import ca.sheridancollege.simran33.database.DatabaseAccess;



@Controller
public class PizzaController {

	@Autowired
	private DatabaseAccess da;
	
	
	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/menu")
	public String menu() {
		return "menu";
	}
	
	@GetMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	
	@PostMapping("/addUser")
	public String addUser(Model model, @ModelAttribute User user) {
		da.insertUser(user);
		model.addAttribute("user", new User());
		return "/login";
	}
	List<Pizza> pizzaList = new CopyOnWriteArrayList<Pizza>();
	
	@GetMapping("/secure")
	public String secureIndex(Model model, Authentication authentication) {
		model.addAttribute("pizzaList", pizzaList);
		model.addAttribute("pizzaList", da.getPizzaList());
            return "/secure/index";
        
	}
	
	@GetMapping("/secure/details/{name}")
	public String details(Model model, @PathVariable String name) {
		List<Pizza> pizza = da.getPizzaDetails(name);
		model.addAttribute("pizza", pizza.get(0));
		return "/secure/details";
	}
	
	@PostMapping("/secure/addToCart/{name}")
	public String addToCart(@PathVariable String name, @ModelAttribute Cart cart) {
	   
	    Pizza pizza = da.getPizzaByName(name);
	    cart.setName(pizza.getName());
	    cart.setPrice(pizza.getPrice() * cart.getQuantity()); // Update price based on quantity
	    da.insertPizzaInCart(cart);

	    return "secure/itemAdded";
	}


	
	@GetMapping("/secure/cart")
	public String showCart(Model model) {
	    List<Cart> cartList = da.getCartList(); 
	    double totalPrice = calculateTotalPrice(cartList);

	    model.addAttribute("cartList", cartList);
	    model.addAttribute("totalPrice", totalPrice);

	    return "/secure/shoppingCart";
	}
	// Method to calculate total price of the cart
		private double calculateTotalPrice(List<Cart> cartList) {
		    double totalPrice = 0.0;
		    for (Cart cart : cartList) {
		        totalPrice += cart.getPrice();
		   }
		    return totalPrice;
		}
		
		@GetMapping("/secure/deleteCart/{name}")
		public String deleteCartByTitle(Model model, @PathVariable String name) {
		    Cart cart = da.getCartByName(name);
		    da.deleteCart(name);
		    List<Cart> cartList = da.getCartList(); // Retrieve updated cart items
		    double totalPrice = calculateTotalPrice(cartList); // Recalculate total price
		    totalPrice = Double.parseDouble(String.format("%.2f", totalPrice));

		    model.addAttribute("cartList", cartList);
		    model.addAttribute("totalPrice", totalPrice);

		    return "/secure/shoppingCart";
		}
		@PostMapping("/secure/placeOrder")
		public String processOrder(Model model) {

		    model.addAttribute("orderMessage", "Your order has been placed successfully!");
		    return "secure/placeOrder";
		}
		@GetMapping("/deletePizza/{name}")
		public String deletePizzaByName(Model model, @PathVariable String name) {
			Pizza pizza = da.getPizzaByName(name);
			da.deletePizza(name);
			List<Pizza> pizzaList = da.getPizzaList();
			model.addAttribute("pizzaList", da.getPizzaList());
			model.addAttribute("pizza", pizza);
			return "secure/index";
		}
		
		@GetMapping("/addPizza")
		public String addBook(Model model) {
			model.addAttribute("pizza", new Pizza());
			return "secure/addPizza";
		}
		
		@PostMapping("/addPizza")
		public String addBook(Model model, @ModelAttribute Pizza pizza) {
			pizzaList.add(pizza);
			model.addAttribute("book", new Pizza());
			model.addAttribute("pizzaList", pizzaList);

			da.addPizza(pizza);
			model.addAttribute("pizzaList", da.getPizzaList());

			return "secure/index";
		}
	

}
