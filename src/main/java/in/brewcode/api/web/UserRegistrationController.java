package in.brewcode.api.web;

import in.brewcode.api.auth.server.service.ICustomUserDetailsService;
import in.brewcode.api.dto.AuthorDto;
import in.brewcode.api.dto.AuthorLoginDto;
import in.brewcode.api.dto.AuthorRegistrationDto;
import in.brewcode.api.exception.InvalidAccessException;
import in.brewcode.api.exception.UserAlreadyExistsException;
import in.brewcode.api.exception.UserNotFoundException;
import in.brewcode.api.web.common.BaseController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.Preconditions;

@Controller
@RequestMapping("/user")
public class UserRegistrationController extends BaseController {
	@Autowired
	private ICustomUserDetailsService customUserDetailsService;
	
	/**
	 * Should only be called from official admin app
	 * @param authorRegistrationDto
	 * @throws UserAlreadyExistsException
	 */
	@PreAuthorize("#oauth2.hasAnyScope('client', 'admin_app')")
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public void registerUser(
			@RequestBody AuthorRegistrationDto authorRegistrationDto)
			throws UserAlreadyExistsException {

		Preconditions.checkNotNull(authorRegistrationDto);

		customUserDetailsService.registerUser(authorRegistrationDto);

	}
	/**
	 * Gets user's profile. @AuthenticationPrincipal is used
	 * @param username
	 * @param user
	 * @return
	 * @throws InvalidAccessException
	 * @throws UserNotFoundException
	 */
	@PreAuthorize("#oauth2.hasAnyScope() and hasRole('WRITE')")
	@RequestMapping(value="/{username}", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<AuthorRegistrationDto> getUserProfile(@PathVariable("username") String username, 	@AuthenticationPrincipal User user) throws InvalidAccessException, UserNotFoundException{
		Preconditions.checkArgument(username!=null || username!="");
		if(!user.getUsername().equals(username)){
			throw new InvalidAccessException("This is user specific. Invalid access");
		}
		
			AuthorRegistrationDto authorRegistrationDto = customUserDetailsService.getUserProfile(username);
			return new ResponseEntity<AuthorRegistrationDto>(authorRegistrationDto, HttpStatus.OK);
		
		
	}
	
	
	
	
	/**
	 * Should not go in deployment
	 * @return
	 */
	@RequestMapping(value="/json", method=RequestMethod.GET)
	@ResponseBody
	public AuthorRegistrationDto getJsonOfUser() {
		AuthorRegistrationDto ard = new AuthorRegistrationDto();
		AuthorLoginDto ald = new AuthorLoginDto();
		AuthorDto ad = new AuthorDto();
		ald.setAuthorDto(ad);
		ard.setAuthorLoginDto(ald);
		return ard;
	}
	
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> uploadMedia(
			@RequestParam( name="file", required=true, value="file") MultipartFile mf, HttpServletRequest request)
			throws IllegalStateException, IOException {
		String fileName = mf.getOriginalFilename();
		String appPath=//request.getServletPath();
		ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/");
		String dirPath = appPath+"/resources/";
		
		String dest = dirPath+fileName;
		if(!new File(dirPath).exists()){
			new File(dirPath).mkdir();
		}
		/*
		File desti =new File(dirPath+fileName);
				mf.transferTo(desti);
		*/
		Files.copy(mf.getInputStream(),Paths.get(dest));
		return new ResponseEntity<String>("Successful upload to " + dest,
				new HttpHeaders(), HttpStatus.OK);

	}

}
