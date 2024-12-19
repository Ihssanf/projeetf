package jmrs.session;


import jmrs.enums.Role;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component("userInfo")
@Scope(value = "session",  proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserInfo {
	private String name;
	private Integer userId;
	private jmrs.enums.Role role;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public jmrs.enums.Role getRole() {
		return role;
	}

	public void setRole(jmrs.enums.Role role) {
		this.role = role;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}