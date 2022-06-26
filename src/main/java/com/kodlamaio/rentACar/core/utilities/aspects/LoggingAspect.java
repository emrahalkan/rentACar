package com.kodlamaio.rentACar.core.utilities.aspects;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.io.JsonEOFException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Aspect
@Component // classdan nesne üretme işini spring e bırakıyoruz
public class LoggingAspect {
	//List<StringBuilder> builderList = new ArrayList<StringBuilder>();

	// @Before("execution(*
	// com.kodlamaio.rentACar.business.concretes.BrandManager.*(..))")
	@Before("execution(* com.kodlamaio.rentACar.business.concretes.*.*(..))")
	public void beforeLog(JoinPoint joinPoint) throws JsonEOFException, IOException {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		StringBuilder builder = new StringBuilder();
		ObjectMapper mapper = new ObjectMapper();
		
//		builder.append("date :" + LocalDate.now());
		builder.append(",\n{");
		builder.append(("\n" + "\"date\":") + mapper.writeValueAsString(LocalDate.now().getYear() + "-"+LocalDate.now().getMonthValue() + "-" + LocalDate.now().getDayOfMonth()));
		//builder.append(String.format("date: %s", LocalDate.now()));
		//builder.append(String.format("className %s" , joinPoint.getTarget().getClass().getSimpleName()));
		builder.append("\n" + "\"className\":" + mapper.writeValueAsString(joinPoint.getTarget().getClass().getSimpleName()));
		//builder.append("\"className\":" +  joinPoint.getTarget().getClass().getSimpleName());
		builder.append("\n" +  "\"methodName\":"  + mapper.writeValueAsString(signature.getMethod().getName()));

	

		if (signature.getMethod().getName() != "getAll") {
			builder.append("\n" + "\"parameters:\":" + mapper.writeValueAsString(joinPoint.getArgs())); // java reflection
		
		} else {
			builder.append("\n" + "\"parameter:\":" + "null");
			
		}
		builder.append("\n" +"}");
		//builderList.add(builder);
		File file = new File("C:\\logs\\operations.json");
//		try {
//		
//			ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
//			writer.writeValue(Paths.get("C:\\logs\\operations.json").toFile(), builderList);
//		} catch (Exception exception) {
//			System.out.println(exception.getMessage());
//		}
				
		try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file,true)) ) {
			bufferedWriter.write(builder.toString());
		} catch (Exception e) {
			// TODO: handle exception
		}


	


//
//		File file = new File("C:\\logs\\txt.json");
//		FileWriter fileWriter = new FileWriter(file, true);
//		fileWriter.write(convertedObject.toString());
//		fileWriter.close();

	//	System.out.println("calisti");



//	
//	@After("pointcut()")
//	public void afterLog() {
//		System.out.println("After brand manager delete");
//	}

//	@Before("execution(* com.kodlamaio.rentACar.business.concretes.BrandManager.getById(int))")
//	public void beforeLog(JoinPoint joinPoint) {
//		MethodSignature signature = (MethodSignature)joinPoint.getSignature();
//		System.out.println("Before brand manager getbyid");
//		System.out.println(joinPoint.getArgs()[0]);
//		System.out.println(joinPoint.getSignature().getName());
//		System.out.println(signature.getParameterNames()[0]);
//	}

//	@Around("execution(* com.kodlamaio.rentACar.business.concretes..(..))")
//	public void beforeLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
//	
//		try {
//			System.out.println("before");
//			proceedingJoinPoint.proceed(); //method delagasyonu burası getbyid oldu
//			System.out.println("after returning");
//		} catch (Exception e) {
//			System.out.println("after throwing");
//			e.printStackTrace();
//		}
//		
//		System.out.println("after finally");
//	}

//	@Pointcut("execution(* com.kodlamaio.rentACar.business.concretes.BrandManger.getById(int)) ") //.. hepsi için
//	public void pointcut() {} //kesişim noktası //dummy
	}
}
