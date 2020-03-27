package net.hka.onlineshopping.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Logs all CRUD operations on database
 * 
 * @author Hany Kamal
 * */
@Aspect
@Component
public class DaoCRUDAspect {
	
	private static final Logger logger = LoggerFactory.getLogger(DaoCRUDAspect.class);
	
	/*
	 * This method logs all create, update, remove operations that are executed 
	 * on the database side by watching the DAO classes in the back end project
	 * */
	@Before("execution(* net.hka.shoppingbackend.dao.*.add*(..)) " + 
			"|| execution(* net.hka.shoppingbackend.dao.*.update*(..)) " + 
			"|| execution(* net.hka.shoppingbackend.dao.*.remove*(..))")  				//point-cut expression
    public void logBeforeCUD(JoinPoint joinPoint) {
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] lArgs = joinPoint.getArgs();
		Object dto = lArgs[0];		
		String attributes = (dto != null)? dto.toString() : "No attributes are detected for this case!!!";			
		logger.info("The follwoing: " + className + "." + methodName + " method with attributes: " + attributes + " did changes on the database at ");       
    }
	
	
	/*
	 * This method logs all reading/fetching data operations that are executed 
	 * on the database side by watching the DAO classes in the back end project
	 * */
	@Before("execution(* net.hka.shoppingbackend.dao.*.list*(..)) " + 
			"|| execution(* net.hka.shoppingbackend.dao.*.get*(..))")  					//point-cut expression
	public void logBeforeRead(JoinPoint joinPoint) {
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		logger.info("The follwoing: " + className + "." + methodName + " method fetched data from the database at ");		
	}

}
