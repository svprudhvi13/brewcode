package in.brewcode.api.web;

import in.brewcode.api.dto.AuthorDto;
import in.brewcode.api.service.IAdminService;
import in.brewcode.api.web.common.BaseController;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.google.common.base.Preconditions;

@Controller(value="adminController")
@RequestMapping(value="/admin")
public class AdminController extends BaseController {

@Autowired
private IAdminService adminService;

@RequestMapping(value="/createauthor", method=RequestMethod.POST)
@ResponseStatus(value=HttpStatus.CREATED)
@ResponseBody
public void createAuthor(@RequestBody AuthorDto authorDto, HttpServletResponse response){
	
	Preconditions.checkNotNull(authorDto);
	adminService.createAuthor(authorDto);
	System.out.println("created Author");
}

@RequestMapping(value="/getAuthor/{id}", method=RequestMethod.GET)
@ResponseBody
public AuthorDto findAuthorById(@PathVariable(value="id")final Long id, HttpServletResponse response ){
	AuthorDto authorDto = null;
		authorDto = adminService.findAuthorById(id);
	return authorDto;
}
}