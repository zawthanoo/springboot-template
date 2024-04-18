package org.mutu.example.config.aop;

import org.aspectj.lang.annotation.Pointcut;

/**
 * @author Zaw Than Oo
 * @since 01-DEC-2018 <br/>
 *        It is a point-cut configuration for
 *        <code>ExceptionHandlerAspect</code> and <code>LoggingAspect</code>
 */
public class PointCutConfig {

	@Pointcut("within(@org.springframework.stereotype.Service *)")
	protected void serviceBean() {
	}

	@Pointcut("within(@org.springframework.stereotype.Repository *)")
	protected void daoBean() {
	}

	@Pointcut("execution(* org.mutu.example.zgen.mapper.*.*(..)) || execution(* org.mutu.example.custommapper.*.*(..))")
	protected void mapper() {
	}

	@Pointcut("execution(public * *(..))")
	protected void publicMethod() {
	}

	@Pointcut("publicMethod() && serviceBean()")
	protected void publicMethodInsideServiceBean() {
	}

	@Pointcut("publicMethod() && daoBean()")
	protected void publicMethodInsideDaoBean() {
	}

	@Pointcut("publicMethod() && mapper()")
	protected void publicMethodInsideMapper() {
	}
}
