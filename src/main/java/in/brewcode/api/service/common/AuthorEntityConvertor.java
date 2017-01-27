package in.brewcode.api.service.common;

import in.brewcode.api.dto.AuthorDto;
import in.brewcode.api.dto.PrivilegeDto;
import in.brewcode.api.dto.RoleDto;
import in.brewcode.api.persistence.entity.Author;
import in.brewcode.api.persistence.entity.Privilege;
import in.brewcode.api.persistence.entity.Role;

import java.util.HashSet;
import java.util.Set;

import com.google.common.base.Preconditions;

public class AuthorEntityConvertor {

	protected AuthorDto convertToArticleAuthorDto(Author articleAuthor) {
		AuthorDto authorDto = null;
		/*
		 * Only author user name is set here
		 */
		if (Preconditions.checkNotNull(articleAuthor != null)) {
			authorDto = new AuthorDto();
			authorDto.setAuthorUserName(articleAuthor.getAuthorUserName());
			authorDto.setAuthorEmail(articleAuthor.getAuthorEmail());

		}

		return authorDto;
	}

	protected Author convertToAuthorEntity(AuthorDto authorDto, Author author) {

		author.setAuthorUserName(authorDto.getAuthorUserName());
		author.setAuthorEmail(authorDto.getAuthorEmail());
		return author;
	}

	protected Role convertToRoleEntity(RoleDto roleDto, Role role) {
		Preconditions.checkArgument(roleDto != null && role != null);
		role.setRoleName(roleDto.getRoleName());
		return role;
	}

	protected RoleDto convertToRoleDto(Role role) {
		Preconditions.checkArgument(role != null);
		RoleDto roleDto = new RoleDto();
		roleDto.setRoleName(role.getRoleName());
		if (role.getRolePrivileges() != null) {
			Set<PrivilegeDto> privilegeSet = new HashSet<PrivilegeDto>();
			for (Privilege priv : role.getRolePrivileges()) {
				privilegeSet.add(convertToPrivilegeDto(priv));
			}
		}

		return roleDto;
	}

	protected Privilege convertToPrivilegeEntity(PrivilegeDto privilegeDto,
			Privilege privilege) {
		privilege.setPrivilegeName(privilegeDto.getPrivilegeName());
		return privilege;
	}

	protected PrivilegeDto convertToPrivilegeDto(Privilege privilege) {
		Preconditions.checkArgument(privilege != null);
		PrivilegeDto privilegeDto = new PrivilegeDto();
		privilegeDto.setPrivilegeName(privilege.getPrivilegeName());
		return privilegeDto;
	}

}
