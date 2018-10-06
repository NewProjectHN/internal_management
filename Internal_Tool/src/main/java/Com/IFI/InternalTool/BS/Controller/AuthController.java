package Com.IFI.InternalTool.BS.Controller;

import Com.IFI.InternalTool.DS.Model.Employee;
import org.slf4j.Logger;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Com.IFI.InternalTool.BS.Service.VacationService;
import Com.IFI.InternalTool.BS.Service.Impl.EmployeeServiceImpl;
import Com.IFI.InternalTool.DS.DAO.EmployeeDAO;
import Com.IFI.InternalTool.DS.DAO.RoleDAO;
import Com.IFI.InternalTool.Payloads.EmailSent;
import Com.IFI.InternalTool.Payloads.LoginRequest;
import Com.IFI.InternalTool.Payloads.LoginResponse;
import Com.IFI.InternalTool.Security.JwtTokenProvider;
import Com.IFI.InternalTool.Security.UserPrincipal;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	EmployeeServiceImpl employeeService;
	@Autowired
	VacationService vacationService;
	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtTokenProvider tokenProvider;
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	@PostMapping("/signin")
	public LoginResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) throws UnsupportedEncodingException, SocketException, UnknownHostException {
		logger.info("Login ... ");
		LoginResponse message = new LoginResponse();
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		// Use for login with domain
//		LdapUserDetailsImpl user = (LdapUserDetailsImpl) authentication.getPrincipal();
		//TODO: Use for testing only
		UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
		String userName = user.getUsername();
		Employee emp = employeeService.getEmployeeByName(userName);
		if(emp == null){
			return null;
		}
		String jwt = tokenProvider.generateToken(Long.toString(emp.getEmployee_id()));
		message.setToken(jwt);
		message.setUsername(emp);
//		if(emp!=null) {
//			final DatagramSocket socket = new DatagramSocket();
//			socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
//			String ip=socket.getLocalAddress().getHostAddress();
//			Date date=new Date();
//			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//			EmailSent emailSent=new EmailSent();
//			emailSent.setSend_to(emp.getEmail());
//			emailSent.setName(emp.getFullname());
//			emailSent.setSubject("Login Notification!");
//			emailSent.setBody("Someone just logged in to your account on: "+ sdf.format(date) +"\nIP address: "+ ip + "\nThis email is intended to inform you about the security of your account.");
//			vacationService.sendEmail(emailSent);
//		}
		
		return message;
	}

}