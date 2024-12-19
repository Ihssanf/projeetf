package jmrs.service.impl;

import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import jmrs.entity.User;
import jmrs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl implements UserService {

	@Autowired
	private jmrs.repository.UserRepository userRepository;

	@Override
	public User checkUserAndPassword(String name, String password) {
		if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(password)) {
			User user = userRepository.findByName(name);
			if (user != null && user.getStatus()) {
				String dbPassword = user.getPassword();
				String inputShaPassword = sha256Hex(password);
				if (dbPassword.equals(inputShaPassword)) {
					return user;
				}
			}
		}
		return null;
	}


	private String sha256Hex(String input) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
			StringBuilder hexString = new StringBuilder();
			for (byte b : hash) {
				hexString.append(String.format("%02x", b));
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
}