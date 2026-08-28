package com.naderaria.identity.application.service;

import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

@Component
public class LogoutHandler extends SecurityContextLogoutHandler {
}
