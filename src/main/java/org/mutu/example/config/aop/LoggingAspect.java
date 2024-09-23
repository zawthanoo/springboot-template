package org.mutu.example.config.aop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.mutu.example.config.dto.LogMessage;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Zaw Than Oo
 * @since 01-DEC-2018 <br/>
 *        It is a intercepter class. Which is used to log for all DAO and
 *        Service classes.<br/>
 *        A method before start, this class automatically will log method name
 *        and parameter as JSON format.<br/>
 *        A method before end, this class automatically will log method name and
 *        result as JSON format.<br/>
 *        Note : A method before end will not be hit if there is occurred an
 *        exception.<br/>
 * @update 28-05-2024 <br/>
 * 		 Update for log4j json layout to send Kafa and elasticsearch       
 */
@Aspect
@Component
public class LoggingAspect extends PointCutConfig {

	private Logger logger = LogManager.getLogger(LoggingAspect.class);

	@Before("publicMethodInsideServiceBean() || publicMethodInsideDaoBean()")
	public void beforeServiceDao(JoinPoint joinPoint) {
		String logMessage = String.format("%s.%s() method has been started.", joinPoint.getSignature().getDeclaringType(), joinPoint.getSignature().getName());
		ObjectMapper mapper = new ObjectMapper();
		try {
			String parameter = mapper.writeValueAsString(joinPoint.getArgs());
			logger.debug(logMessage + " Parameter => " + parameter);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}

	// only hit without throwing exception
	@AfterReturning(pointcut = "publicMethodInsideServiceBean() || publicMethodInsideDaoBean()", returning = "result")
	public void afterReturningServiceDao(JoinPoint joinPoint, Object result) {
		String logMessage = String.format("%s.%s() method has been finished.", joinPoint.getSignature().getDeclaringType(), joinPoint.getSignature().getName());
		ObjectMapper mapper = new ObjectMapper();
		try {
			String returnResult = mapper.writeValueAsString(result);
			logger.debug(logMessage + " Return Result => " + returnResult);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
}
