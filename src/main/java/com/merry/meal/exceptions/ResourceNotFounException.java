package com.merry.meal.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFounException extends RuntimeException {
	String resourceName;
	String resourceFieldName;
	long fieldValue;
	
	
	public ResourceNotFounException(String resourceName, String resourceFieldName, long fieldValue) {
		super(String.format("Unable to find | %s | with provided | %s | : %s", resourceName, resourceFieldName, fieldValue));
		this.resourceName = resourceName;
		this.resourceFieldName = resourceFieldName;
		this.fieldValue = fieldValue;
	}
	
}
