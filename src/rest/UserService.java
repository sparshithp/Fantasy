package rest;

import javax.crypto.SecretKey;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dao.DAOFactory;
import dao.IUserDAO;
import exceptions.UnauthorizedUserException;
import exceptions.ValidationException;
import logging.Log;
import models.User;



/**
 * This class contains the implementation of the RESTful API calls made with
 * respect to users.
 * 
 */

@Path("/user")
public class UserService extends BaseService {
	/**
	 * This method checks the validity of the user name and if it is valid, adds
	 * it to the database
	 * 
	 * @param user
	 *            - An object of type User
	 * @return - An object of type Response with the status of the request
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/signup")
	public Response addUser(User user) {
		Log.enter(user);
		User resp = new User();

		try {
			IUserDAO dao = DAOFactory.getInstance().getUserDAO();
			User existingUser = dao.findByName(user.getUserName());

			// Validation to check that user name should be unique
			// in the system. If a new users tries to register with
			// an existing userName, notify that to the user.
			if (existingUser != null) {
				Log.trace("User name provided already exists. Validating if it is same password ...");
				if (!validateUserPassword(user.getPassword(), existingUser)) {
					Log.warn("Password is different for the existing user name.");
					throw new ValidationException("User name already taken");
				} else {
					Log.debug("Yay!! Password is same for the existing user name.");

					resp.setUserName(existingUser.getUserName());
					return ok(resp);
				}
			}

			dao.save(user);
		} catch (Exception e) {
			handleException(e);
		} finally {
			Log.exit();
		}

		return created(user);
	}

	/**
	 * This method is used to login a user.
	 * 
	 * @param user
	 *            - User information to login
	 * 
	 * @return - Status 200 when successful login. Else other status.
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/{userName}/authenticate")
	public Response loginUser(@PathParam("userName") String userName,
			User user) {
		Log.enter(userName, user);

		try {
			User po = loadExistingUser(userName);
			if (!validateUserPassword(user.getPassword(), po)) {
				throw new UnauthorizedUserException(userName);
			}
		} catch (Exception e) {
			handleException(e);
		} finally {
			Log.exit();
		}

		return ok();
	}

	/**
	 * This method will validate the user's password based on what information
	 * is sent from the UI, versus the information retrieved for that user from
	 * the database.
	 * 
	 * @param password
	 *            - Encrypted Password
	 * @param user
	 *            - User info from DB
	 * 
	 * @return - Flag specifying YES or NO
	 */
	private boolean validateUserPassword(String password, User user) {
		//Decrypt pass word and return true if exists

		return false;
	}

	/**
	 * All all information related to a particular userName.
	 * 
	 * @param userName
	 *            - User Name
	 * 
	 * @return - Details of the User
	 */
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/{userName}")
	public User loadUser(@PathParam("userName") String userName) {
		Log.enter(userName);

		User user = null;
		try {
			user = loadExistingUser(userName);
		} catch (Exception e) {
			handleException(e);
		} finally {
			Log.exit(user);
		}

		return user;
	}

}
