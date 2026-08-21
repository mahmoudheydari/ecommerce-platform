package com.naderaria.identity.service;

import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

@Component
public class LogoutHandler extends SecurityContextLogoutHandler {
}
