package com.f1.f1history.security;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class CustomToken extends AbstractAuthenticationToken {

	public CustomToken(Collection<? extends GrantedAuthority> authorities, CustomPrincipal principal) {
        // ここで渡したAuthority(文字列のRole)が、hasRole(),hasAuthority()のチェック対象となる
        super(authorities);
        this.principal = principal;
    }
	
	// @AuthenticationPrincipalで参照されるPrincipalとなる
	private CustomPrincipal principal;

	

	// Authenticationインタフェースで用意されているgetCredentials()を@Override
	public Object getCredentials() {
		return null;
	}

	// Authenticationインタフェースで用意されているgetPrincipal()を@Override
	@Override
	public CustomPrincipal getPrincipal() {
		return principal;
	}

}
