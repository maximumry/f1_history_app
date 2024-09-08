package com.f1.f1history.controller.OAuth;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class OAuthController {
	
	@GetMapping("/")
	public String index(@AuthenticationPrincipal OidcUser user, Model model) {
		model.addAttribute("username", user.getFullName());
		return "/auth/google";
	}

}
