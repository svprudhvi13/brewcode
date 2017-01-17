package in.brewcode.api.persistence.dao;

import in.brewcode.api.persistence.dao.common.IOperationsDao;
import in.brewcode.api.persistence.entity.Content;

import java.util.List;

public interface IContentDao extends IOperationsDao<Content>{

	List<Content> getArticleContents(Long id);
}
