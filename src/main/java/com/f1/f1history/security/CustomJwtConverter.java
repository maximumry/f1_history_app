package com.f1.f1history.security;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.util.Converter;

@Component
public abstract class CustomJwtConverter implements Converter<Jwt, CustomToken>{
	
	private static final String CLAIM_KEY_ID = "sub";
	private static final String CLAIM_KEY_USERNAME = "preferred_username";
	private static final String CLAIM_KEY_ROLES = "roles";
	
	@Override
	public CustomToken convert(Jwt token) {
		//1. principal(id, username)の作成
		String id = token.getClaimAsString(CLAIM_KEY_ID);
		String name = token.getClaimAsString(CLAIM_KEY_USERNAME);
		CustomPrincipal principal = CustomPrincipal.builder().id(id).name(name).build();
		
		//2. authoritiesの作成 AS CustomRoleクラス
		Collection<CustomRole> authorities = Collections.emptyList();
		if(token.getClaims().containsKey(CLAIM_KEY_ROLES)) {
			authorities = token.getClaimAsStringList(CLAIM_KEY_ROLES).stream().map(CustomRole::new)
					.collect(Collectors.toList());
		}
		
		//CustomToken生成
		CustomToken customToken = new CustomToken(authorities, principal);
		//AuthenticationIFで用意されているsetAuthenticated()で更新
		//これをtrueにしないと401エラーで怒られる Custom** -> https://qiita.com/crml1206/items/4df2f2bcdb535f9e2eba
		customToken.setAuthenticated(true);
		return customToken;
	}

}
