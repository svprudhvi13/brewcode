package in.brewcode.api.web;

import in.brewcode.api.dto.AuthorDto;
import in.brewcode.api.service.IAdminService;
import in.brewcode.api.web.common.BaseController;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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

private static Logger logger = Logger.getLogger(AdminController.class);	

@Autowired
private ApplicationEventPublisher eventPublisher;
@Autowired
private IAdminService adminService;

@RequestMapping(value="/createauthor", method=RequestMethod.POST)
@ResponseStatus(value=HttpStatus.CREATED)
@ResponseBody
public void createAuthor(@RequestBody AuthorDto authorDto, HttpServletResponse response){
	
	Preconditions.checkNotNull(authorDto);
	adminService.createAuthor(authorDto);
	logger.debug("Author created");
	
}


@RequestMapping(value="/updateauthor/", method=RequestMethod.POST)
@ResponseStatus(value=HttpStatus.CREATED)
@ResponseBody
public void updateAuthor(@RequestBody AuthorDto authorDto, HttpServletResponse response){
	
	Preconditions.checkNotNull(authorDto);
	Preconditions.checkArgument((authorDto.getAuthorId()<0));
	adminService.updateAuthor(authorDto);
	logger.debug("Author updated");
	

}


@RequestMapping(value="/getAuthor/{id}", method=RequestMethod.GET)
@ResponseBody
public AuthorDto findAuthorById(@PathVariable(value="id")final Long id, HttpServletResponse response ){
	AuthorDto authorDto = null;
		authorDto = adminService.findAuthorById(id);
	return authorDto;
}

@RequestMapping(value="getAuthors", method=RequestMethod.GET)
public List<AuthorDto> findAllAuthors(){
List<AuthorDto> listAuthors = null;

	listAuthors = adminService.findAllAuthors();

	return listAuthors;
	
}


}