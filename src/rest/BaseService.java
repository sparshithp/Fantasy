package rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import dao.DAOFactory;
import exceptions.CheckedException;
import exceptions.ServiceException;
import exceptions.UnknownUserException;
import logging.Log;
import models.User;

public class BaseService {
	protected void handleException(Exception e) {
		Log.error(e);
		if (e instanceof CheckedException) {
			throw (CheckedException) e;
		} else {
			throw new ServiceException(e);
		}
	}

	protected Response ok() {
		return Response.status(Status.OK).build();
	}

	protected Response ok(Object obj) {
		return Response.status(Status.OK).entity(obj).build();
	}
	
	protected Response created(Object obj) {
		return Response.status(Status.CREATED).entity(obj).build();
	}

	protected User loadExistingUser(String userName) {
		User user = DAOFactory.getInstance().getUserDAO().findByName(userName);
		if (user == null) {
			throw new UnknownUserException(userName);
		}

		return user;
	}
}
