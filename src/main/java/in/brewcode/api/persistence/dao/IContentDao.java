package in.brewcode.api.persistence.dao;

import in.brewcode.api.persistence.entity.Content;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface IContentDao extends PagingAndSortingRepository<Content, Long>{

	//List<Content> getArticleContents(Long id);
}
