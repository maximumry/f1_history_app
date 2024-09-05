package com.f1.f1history.security;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomPrincipal implements Serializable {
	
	private String id;
	private String name;

}
