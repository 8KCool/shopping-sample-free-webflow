package net.hka.onlineshopping.controller.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import net.hka.shoppingbackend.exception.ProductNotFoundException;

/**
 * Advice controller that is available for all other controllers and requests. 
 * This controller handles the thrown exceptions from anywhere in the application 
 * and returns the user friendly error message and render this message in the error page.
 * */
@ControllerAdvice
public class GlobalDefaultExceptionHandler {

	
	@ExceptionHandler(NoHandlerFoundException.class)
	public ModelAndView handlerNoHandlerFoundException() {
		
		ModelAndView mv = new ModelAndView("error");
		
		mv.addObject("errorTitle", "The page is not constructed!");
		
		mv.addObject("errorDescription", "The page you are looking for is not available now!");
		
		mv.addObject("title", "404 Error Page");
		
		return mv;
	}
	
	
	@ExceptionHandler(ProductNotFoundException.class)
	public ModelAndView handlerProductNotFoundException() {
		
		ModelAndView mv = new ModelAndView("error");
		
		mv.addObject("errorTitle", "Product not available!");
		
		mv.addObject("errorDescription", "The product you are looking for is not available right now!");
		
		mv.addObject("title", "Product Unavailable");
		
		return mv;
	}
		
	
	@ExceptionHandler(Exception.class)
	public ModelAndView handlerException(Exception ex) {
		
		ModelAndView mv = new ModelAndView("error");
		
		mv.addObject("errorTitle", "Contact Your Administrator!!");
		
		
		/* only for debugging your application*/
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		
		ex.printStackTrace(pw);
						
		mv.addObject("errorDescription", sw.toString());
		
		mv.addObject("title", "Error");
		
		return mv;
	}
			
	
}
