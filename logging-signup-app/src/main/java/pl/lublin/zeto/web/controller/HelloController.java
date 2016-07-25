package pl.lublin.zeto.web.controller;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import pl.lublin.zeto.mapping.*;
import pl.lublin.zeto.util.Util;

/**
 * 
 * @author Michal Rakowski
 *
 */
@Controller
public class HelloController
{
	final static Logger logger = Logger.getLogger(HelloController.class);

	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView showForm(
			@CookieValue(value = "username", defaultValue = "defaultCookieValue") String usernameCookie,
			@CookieValue(value = "passwordHash", defaultValue = "defaultCookieValue") String passwordHashCookie)
	{
		ModelAndView model = new ModelAndView("home", "user", new User());
		
		logger.debug("[cookie] username = "+usernameCookie);
		logger.debug("[cookie] passwordHash = "+passwordHashCookie);
		
		model.addObject("username", usernameCookie);
		model.addObject("passwordHash", passwordHashCookie);
		return model;
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String deleteCookie(HttpServletResponse response)
	{
		response.addCookie(new Cookie("username", ""));
		response.addCookie(new Cookie("passwordHash", ""));
		
		return "hello";
	}
	 
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public String submit(@Validated @ModelAttribute("user") User user, BindingResult result, ModelMap model)
	{
		if (result.hasErrors())
		{
			return "error";
		}
		
		try
		{
			user.setSalt(Util.generateSalt().toString());
		}
		catch (NoSuchAlgorithmException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		user.setRole("user");
		user.setPasswordHash(Util.hashingFunction(user.getPasswordHash()));
		
		logger.debug("---this is about to go to the database:");
		logger.debug(user.getId());
		logger.debug(user.getName());
		logger.debug(user.getEmail());
		logger.debug(user.getPasswordHash());
		logger.debug(user.getSalt());
		logger.debug("---");
		
		Util.insert_data_into_table_Users(user); 
		
		model.addAttribute("name", user.getName());
		model.addAttribute("email", user.getEmail());
		
		return "userView";
	}
	
	
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String signIn(@Validated @ModelAttribute("user") User user, BindingResult result, ModelMap model, HttpServletResponse response)
	{
		if (result.hasErrors())	return "error";
		
		
		
		
		//find the user in Users
		//if found:
		//	if it's role is "admin", open /adminView
		//	else open /employeeView
		//else:
		//redirect back to / with message "login and password don't match"
		
		user.setPasswordHash(Util.hashingFunction(user.getPasswordHash()));
		User userFound = Util.findUserFromDB(user);
		
		if(userFound != null)
		{
			logger.debug("USER FOUND");
			model.addAttribute("name", user.getName());
			model.addAttribute("email", user.getEmail());
			
			response.addCookie(new Cookie("username", user.getName()));
			response.addCookie(new Cookie("passwordHash", user.getPasswordHash()));
			
			if(userFound.getRole().equals("admin"))
			{
				logger.debug("USER IS ADMIN");
				
				
				
				List<LogAndUser> list = Util.listLogsFromDB();
				logger.debug("list  =  ");
				for(LogAndUser logAndUser : list)
		        {
		            logger.debug(
		            		logAndUser.getTimestamp()+" "
		            		+logAndUser.getName()+" "
		            		+logAndUser.getEmail()+" "
		            		+logAndUser.getRole());
		        }
				model.addAttribute("logs", list);
				model.addAttribute("testowa_zmienna", "wartosc testowej zmiennej");
			
				return "adminView";
			}
			else
			{
				return "userView";
			}
		}
		else
		{
			logger.debug("USER NOT FOUND");
			model.addAttribute("error","password and username don't match");
			return "home";
		}
		
	}
}