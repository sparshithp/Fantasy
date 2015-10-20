package dao;

import java.util.List;

import models.User;

/**
 * Interface specifying the contract that all implementations will implement to
 * provide persistence of User information in the system.
 * 
 */
public interface IUserDAO {
	/**
	 * This method will save the information of the user into the database.
	 * 
	 * @param userPO
	 *            - User information to be saved.
	 */
	void save(User user);

	/**
	 * This method will load all the users in the
	 * database.
	 * 
	 * @return - List of all users.
	 */
	List<User> loadUsers();

	/**
	 * This method with search for a user by his userName in the database. The
	 * search performed is a case insensitive search to allow case mismatch
	 * situations.
	 * 
	 * @param userName
	 *            - User name to search for.
	 * 
	 * @return - UserPO with the user information if a match is found.
	 */
	User findByName(String userName);

}
