package jmrs.auth;


import jmrs.controller.BaseController;
import jmrs.entity.User;
import jmrs.enums.Role;
import jmrs.service.UserService;
import jmrs.session.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component("customAuthenticationProvider")
public class CustomAuthenticationProvider extends jmrs.controller.BaseController implements AuthenticationProvider {

	@Autowired
	private ApplicationContext context;

	@Autowired
	private jmrs.service.UserService userService;

	@Autowired
	private UserInfo userInfo;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String name = authentication.getName();
		String password = (String) authentication.getCredentials();

		if (StringUtils.isEmpty(name) || StringUtils.isEmpty(password)) {
			addMessage("ERR_LOGIN");
			throw new UsernameNotFoundException("email / password is empty !!! ");
		}

		User user = userService.checkUserAndPassword(name, password);

		if (user == null) {
			addMessage("ERR_LOGIN");
			throw new UsernameNotFoundException("email / password is empty !!! ");
		}
		String roleName = user.getRole().name();

		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		grantedAuthorities.add(new SimpleGrantedAuthority(roleName));

		userInfo.setName(name);
		userInfo.setRole(user.getRole());
		userInfo.setUserId(user.getUserId());

		return new UsernamePasswordAuthenticationToken(name, user, grantedAuthorities);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}