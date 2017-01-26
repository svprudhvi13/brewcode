package in.brewcode.api.web;


import in.brewcode.api.dto.ArticleDto;
import in.brewcode.api.dto.ContentDto;
import in.brewcode.api.service.IArticleAndContentService;
import in.brewcode.api.web.common.BaseController;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.google.common.base.Preconditions;

@Controller
@RequestMapping(value="/author/article")
public class ArticleController extends BaseController {

	private Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private IArticleAndContentService articleAndContentService;
	
	
	/**
	 * Lazy fetch, article contents and author
	 * @return
	 */
	@PreAuthorize("hasRole('USER')")
	@RequestMapping( method=RequestMethod.GET)
	@ResponseBody
	public  List<ArticleDto> getAllArticles() {
		List<ArticleDto> listArticles  = null;
		listArticles= articleAndContentService.getAllArticles();
		return listArticles;
		
	}
	
	/**
	 * Article is nloaded with contents and author dtos
	 * @param id
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET )
	@ResponseBody
	public ArticleDto getArticleByIdWithContents(@PathVariable("id") long id, HttpServletResponse response){
		ArticleDto articleDto = null;
		
		articleDto=articleAndContentService.getFullArticleById(id);
		logger.debug(articleDto.toString()+"	"+ this.toString());
		return articleDto;
	}
	@RequestMapping(value = "/content/{id}", method = RequestMethod.GET )
	@ResponseBody
	public ContentDto getContent(@PathVariable("id") long id, HttpServletResponse response){
		ContentDto contentDto = null;
		
		contentDto=articleAndContentService.getContentById(id);
		return contentDto;
	}

	@RequestMapping(value = "/content/", method = RequestMethod.PUT )
	@ResponseStatus(HttpStatus.CREATED)
	public void addContent(@RequestBody ContentDto contentDto, HttpServletResponse response){
		Preconditions.checkNotNull(contentDto);
		
	articleAndContentService.saveContent(contentDto, contentDto.getArticleDtoId());	
	
	}
	
	@RequestMapping(value="", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void createArticle(ArticleDto articleDto){
		Preconditions.checkNotNull(articleDto);
		
		articleAndContentService.saveArticle(articleDto);
		
	}
	
	
}
