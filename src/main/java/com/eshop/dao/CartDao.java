package com.eshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.eshop.domain.Cart;
import com.eshop.domain.CartDetails;
import com.eshop.domain.ProductDetails;
import com.eshop.util.DBconnection;

@Configuration
public class CartDao {
	@Autowired
	DBconnection connect;
	Logger logger = LogManager.getLogger(this.getClass());

	public boolean cartToAdd(CartDetails cartDetails) {
		try {
			String query = "exec cartDetailss @ProductId=?,@CartQuantity=?,@CartId=?,@ProductName=?";
			logger.info("product id and name is found");
			Connection connection = connect.connection();
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, cartDetails.getProduct().getId());
			statement.setInt(2, cartDetails.getQuantity());
			statement.setInt(3, cartDetails.getCartId());
			statement.setString(4, cartDetails.getProduct().getName());
			if (statement.executeUpdate() > 0) {
				logger.info("Query Executed");
				logger.info("product is added cart sucessfully");
				return true;
			} else {
				logger.warn("added to cart is failed");
				return false;
			}

		} catch (Exception e) {
			logger.fatal("Exception : " + e.getLocalizedMessage());
			e.printStackTrace();
			return false;
		}
	}

	public List<CartDetails> viewCartProduct(int cartId) throws SQLException {
		List<CartDetails> cartProducts = new ArrayList<CartDetails>();
		try {
			String query = "exec viewCartItems @CartId=?";
			Connection connection = connect.connection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, cartId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				ProductDetails product = new ProductDetails();
				product.setId(rs.getInt("ProductId"));
				product.setName(rs.getString("ProductName"));
				CartDetails cart = new CartDetails();
				Cart carts = new Cart();
				carts.setCartId(rs.getInt("CartId"));
				cart.setCartId(rs.getInt("Product_Cart_Id"));
				cart.setCartdetails(carts);
				cart.setProduct(product);
				cart.setQuantity(rs.getInt("CartQuantity"));
				cartProducts.add(cart);
				logger.info("data found");
			}
			logger.info("data retrieved Successfully");
			if (cartProducts.size() > 0) {
				logger.info("CartList has values");
				return cartProducts;
			} else {
				logger.info("CartList is empty");
				return null;
			}
		} catch (Exception e) {
			logger.fatal("Exception : " + e.getLocalizedMessage());
			return null;
		}

	}

	public ProductDetails getProductName(String productName) throws SQLException {
		try {
			logger.info("getBookByName method Entry");
			Connection connection = connect.connection();
			logger.info("DB connection Establised");
			PreparedStatement prepareStatement = connection
					.prepareStatement("exec getProductDetails @Name=?");
			prepareStatement.setString(1, productName);
			ResultSet result = prepareStatement.executeQuery();
			ProductDetails book = new ProductDetails();
			logger.info("query executed");
			if (result.next()) {
				book.setQuantity(result.getInt("Quantity"));
				book.setPrice(result.getDouble("ProductPrice"));
				logger.info("getproductname executed");
				return book;
			} else {
				logger.info("getproductname is not found");
				logger.info("No product found");
				return null;
			}
		} catch (Exception e) {
			logger.fatal("Exception : " + e.getLocalizedMessage());
			return null;
		}
	}

	public ResultSet checkCartItem(int cartid, int productid) {
		try {
			logger.info("checkCartItem method Entry");
			Connection connection = connect.connection();
			logger.info("DB connection Establised");
			PreparedStatement prepareStatement;
			prepareStatement = connection.prepareStatement("exec checkCartItems @Id=?,@ProductId=?");
			prepareStatement.setInt(1, cartid);
			prepareStatement.setInt(2, productid);
			ResultSet result = prepareStatement.executeQuery();
			logger.info("query executed");
			logger.info("checkCartItem method Exit");
			return result;
		} catch (Exception e) {
			logger.fatal("Exception : " + e.getLocalizedMessage());
			return null;
		}
	}

	public boolean cartCount(CartDetails cartDetails) {
		try {
			boolean checking = checkProduct(cartDetails.getProduct().getId(), cartDetails.getProduct().getName());
			if (checking == false) {

				return false;
			} else {
				ResultSet result = checkCartItem(cartDetails.getCartId(), cartDetails.getProduct().getId());
				if (result.next()) {
					int quantity = result.getInt("CartQuantity") + cartDetails.getQuantity();
					ProductDetails products = getProductName(cartDetails.getProduct().getName());
					if (quantity > products.getQuantity()) {
						System.out.println("every");
						return false;
					} else {
						double totalprice = quantity * products.getPrice();
						Connection connection = connect.connection();
						PreparedStatement prepareStatement = connection
								.prepareStatement("exec updateCartItems @quantity=?,@productsName=?,@cartId=?");
						prepareStatement.setInt(1, quantity);
						prepareStatement.setString(2, cartDetails.getProduct().getName());
						prepareStatement.setInt(3, cartDetails.getCartId());
						if (prepareStatement.executeUpdate() < 1) {
							logger.info("query executed");
							logger.info("cartAddCount method is failed");
							return false;
						} else {
							logger.info("query executed");
							logger.info("cartAddCount method found");
							return true;
						}
					}
				} else {
					return cartToAdd(cartDetails);
				}
			}
		} catch (Exception e) {
			logger.error("Exception : " + e.getLocalizedMessage());
			return false;
		}
	}

	public boolean removeCart(int cartId, String name) {
		try {
			logger.info("Remove the product API");
			Connection connection = connect.connection();
			PreparedStatement preparedStatement = connection.prepareStatement(
					"exec removeCartItems @CArtId=?,@Namee=? ");
			preparedStatement.setInt(1, cartId);
			preparedStatement.setString(2, name);
			if (preparedStatement.executeUpdate() < 1) {
				logger.info("query executed");
				logger.info("product in cart is not removed ");
				return false;
			} else {
				logger.info("query executed");
				logger.info("product in cart is removed ");
				return true;
			}
		} catch (Exception e) {
			logger.error("Exception : " + e.getLocalizedMessage());
			return false;
		}
	}

	public boolean checkProduct(int id, String name) throws SQLException {
		try {
			System.out.println(id);
			System.out.println(name);
			List<ProductDetails> checkProducts = new ArrayList<ProductDetails>();
			String query = "exec products_AllDetails";
			Connection userData = connect.connection();
			PreparedStatement preparestatement = userData.prepareStatement(query);
			ResultSet rs = preparestatement.executeQuery();
			while (rs.next()) {
				ProductDetails products = new ProductDetails();
				products.setId(rs.getInt("ProductId"));
				products.setName(rs.getString("ProductName"));
				checkProducts.add(products);
				logger.info("sjddkji");
				System.out.println("sxju");
			}
			System.out.println(checkProducts);

			for (int y = 0; y < checkProducts.size(); y++) {
				System.out.println(checkProducts.get(y));
				if (checkProducts.get(y).getId() == id && checkProducts.get(y).getName().equalsIgnoreCase(name)) {
					logger.info("product id and name was correct");
					return true;
				} else {
					logger.warn("product id and name was incorrect");
				}
			}
		} catch (Exception e) {
			logger.fatal("Exception : " + e.getLocalizedMessage());
			e.printStackTrace();
			return false;
		}
		return false;

	}

}
