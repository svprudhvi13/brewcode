package in.brewcode.api.service.impl;

import static in.brewcode.api.service.common.ServiceUtils.convertToAuthorDto;
import static in.brewcode.api.service.common.ServiceUtils.convertToAuthorEntity;
import static in.brewcode.api.service.common.ServiceUtils.convertToAuthorWithRoleDto;
import static in.brewcode.api.service.common.ServiceUtils.convertToPrivilegeDto;
import static in.brewcode.api.service.common.ServiceUtils.convertToPrivilegeEntity;
import static in.brewcode.api.service.common.ServiceUtils.convertToRoleDto;
import static in.brewcode.api.service.common.ServiceUtils.convertToRoleEntity;
import in.brewcode.api.dto.AuthorDto;
import in.brewcode.api.dto.AuthorWithRoleDto;
import in.brewcode.api.dto.PrivilegeDto;
import in.brewcode.api.dto.RoleDto;
import in.brewcode.api.exception.PrivilegeAlreadyExistsException;
import in.brewcode.api.exception.PrivilegeNotFoundException;
import in.brewcode.api.exception.RoleAlreadyExistsException;
import in.brewcode.api.exception.RoleInUseException;
import in.brewcode.api.exception.RoleNotFoundException;
import in.brewcode.api.exception.UserAlreadyExistsException;
import in.brewcode.api.exception.UserNotFoundException;
import in.brewcode.api.persistence.dao.IAdminAuthorDao;
import in.brewcode.api.persistence.dao.IPrivilegeDao;
import in.brewcode.api.persistence.dao.IRoleDao;
import in.brewcode.api.persistence.entity.Author;
import in.brewcode.api.persistence.entity.Privilege;
import in.brewcode.api.persistence.entity.Role;
import in.brewcode.api.service.IAdminService;
import in.brewcode.api.service.common.ServiceUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;

@Service
@Transactional
public class AdminService implements IAdminService {

	private static Logger log = Logger.getLogger(AdminService.class);
	@Autowired
	private IAdminAuthorDao adminAuthorDao;

	@Autowired
	private IRoleDao roleDao;

	@Autowired
	private IPrivilegeDao privilegeDao;

	protected PagingAndSortingRepository<Author, Long> getAdminAuthorDao() {

		return adminAuthorDao;
	}

	public void addPrivilege(PrivilegeDto privilegeDto)
			throws PrivilegeAlreadyExistsException {
		Preconditions.checkArgument(
				null != privilegeDto && privilegeDto.getPrivilegeName() != null
						&& !privilegeDto.getPrivilegeName().equals(""),
				"Privilege cannot be null or empty.");
		Privilege privilege = new Privilege();
		if (privilegeDao.findByPrivilegeNameIgnoreCase(privilegeDto
				.getPrivilegeName()) == null) {
			privilegeDao.saveAndFlush(convertToPrivilegeEntity(privilegeDto,
					privilege));
		} else {
			throw new PrivilegeAlreadyExistsException(
					"Cannot create a duplicate privilege.");
		}
	}

	public void addRole(RoleDto roleDto) throws PrivilegeNotFoundException,
			RoleAlreadyExistsException {
		Preconditions.checkArgument(null != roleDto
				&& roleDto.getRoleName() != null
				&& !roleDto.getRoleName().equals(""),
				" Role cannot be empty. Invalid role name");

		if (roleDao.findByRoleNameIgnoreCase(roleDto.getRoleName()) == null) {
			Role role = convertToRoleEntity(roleDto, new Role());
			Set<Privilege> rolePrivileges = null;
			if (roleDto.getPrivileges() != null) {
				rolePrivileges =new HashSet<Privilege>();
				for (PrivilegeDto pd : roleDto.getPrivileges()) {
					Privilege privilege = privilegeDao
							.findByPrivilegeNameIgnoreCase(pd
									.getPrivilegeName());
					if (privilege != null) {
						rolePrivileges.add(privilege);
					} else {
						throw new PrivilegeNotFoundException(
								"Incorrect Set of Privileges");
					}
				}
				role.setRolePrivileges(rolePrivileges);
			}
			roleDao.saveAndFlush(role);
		} else {
			throw new RoleAlreadyExistsException("Cannot add this role again");
		}
	}

	public void updateRoleName(String oldRoleName, String newRoleName)
			throws RoleNotFoundException, RoleAlreadyExistsException {
		Preconditions.checkArgument(!(oldRoleName == null || oldRoleName == ""
				|| newRoleName == null || newRoleName == ""),
				" Old / New Role name cannot be empty");
		;
		Role role = roleDao.findByRoleNameIgnoreCase(oldRoleName);
		if (roleDao.findByRoleNameIgnoreCase(newRoleName) != null
				&& !(oldRoleName.equalsIgnoreCase(newRoleName))) {
			throw new RoleAlreadyExistsException(
					"Cannot update role name. Already a role with name "
							+ newRoleName + " exists");
		}
		if (role != null) {
			role.setRoleName(newRoleName);
			roleDao.saveAndFlush(role);
		} else {
			throw new RoleNotFoundException("Invalid role name, " + oldRoleName);
		}
	}

	public void updatePrivilegesOfRole(RoleDto roleDto)
			throws RoleNotFoundException, PrivilegeNotFoundException,
			RoleAlreadyExistsException {
		Preconditions.checkArgument(null != roleDto,
				"RoleDto input can't be null");
		Role role = roleDao.findByRoleNameIgnoreCase(roleDto.getRoleName());
		// If update is not possible, create new role.
		if (role == null) {
			// If update is not possible, create new role.
			// throw new
			// RoleNotFoundException("Role doesn't exists. So, can't update");
			// Now, instead of updating, a new role is created
			addRole(roleDto);
		} else {
			role = convertToRoleEntity(roleDto, role);
			Set<Privilege> rolePrivileges = null;
			if (roleDto.getPrivileges() != null) {
				rolePrivileges = new HashSet<Privilege>();
				for (PrivilegeDto pd : roleDto.getPrivileges()) {
					Privilege privilege = privilegeDao
							.findByPrivilegeNameIgnoreCase(pd
									.getPrivilegeName());
					if (privilege != null) {
						rolePrivileges.add(privilege);
					} else {
						throw new PrivilegeNotFoundException(
								"Incorrect Set of Privileges");
					}
				}
				role.setRolePrivileges(rolePrivileges);
			}
			roleDao.save(role);

		}

	}

	public void deleteRole(String roleName) throws RoleNotFoundException,
			RoleInUseException {
		Preconditions.checkArgument(null != roleName && !roleName.equals(""));
		Role role = roleDao.findByRoleNameIgnoreCase(roleName);
		if (role != null) {
			// Delete role only if it is not in use i.e., not assigned to any
			// user
			List<AuthorDto> authorsWithThisRole = findAuthorsByRoleName(roleName);
			if (authorsWithThisRole == null)
				roleDao.deleteByRoleNameIgnoreCase(roleName);
			else
				throw new RoleInUseException(
						"Cannot delete role, active users have this role");
		} else {
			throw new RoleNotFoundException("Cannot delete role. " + roleName
					+ " is invalid input");
		}
	}

	public void deletePrivilege(String privilegeName)
			throws PrivilegeNotFoundException {
		Preconditions.checkArgument(
				null != privilegeName && !privilegeName.equals(""),
				"Can't delete. Privilege shouldn't be null or empty");
		Privilege privilege = privilegeDao
				.findByPrivilegeNameIgnoreCase(privilegeName);
		if (privilege != null) {
			Set<Role> roles = privilege.getRoles();
			// Used internal iterator with Java 8 lamda expression
			if (roles != null && roles.size() > 0) {
				roles.forEach(role -> {
					role.getRolePrivileges().remove(privilege);
					roleDao.saveAndFlush(role);
				});
			}
			/*
			 * for(Role r : roles){ r.getRolePrivileges().remove(privilege);
			 * roleDao.saveAndFlush(r); }
			 */
			privilegeDao.deleteByPrivilegeNameIgnoreCase(privilege
					.getPrivilegeName());

		} else {
			throw new PrivilegeNotFoundException(privilegeName
					+ " privilege doesn't exist");
		}

	}

	public void createAuthor(AuthorDto authorDto)
			throws UserAlreadyExistsException {
		Preconditions.checkArgument(null != authorDto,
				"AuthorDto cannot be null");
		Author checkAuthor = adminAuthorDao.findByAuthorUserName(authorDto
				.getAuthorUserName());
		if (checkAuthor == null) {

			Author author = convertToAuthorEntity(authorDto, new Author());

			getAdminAuthorDao().save(author);
		} else {
			final String err = "Cannot enter duplicate";
			log.error(err);
			throw new UserAlreadyExistsException(err);
		}
	}

	public void lockAuthor(String userName) throws UserNotFoundException {
		Preconditions.checkArgument(userName != null && !userName.equals(""));
		if (adminAuthorDao.findByAuthorUserName(userName) != null) {
			adminAuthorDao.lockAuthor(userName);
		} else {
			throw new UserNotFoundException(userName + " not found");
		}
	}

	public void unlockAuthor(String userName) throws UserNotFoundException {
		Preconditions.checkArgument(userName != null && !userName.equals(""));
		if (adminAuthorDao.findByAuthorUserName(userName) != null) {
			adminAuthorDao.unlockAuthor(userName);
		} else {
			throw new UserNotFoundException(userName + " not found");
		}
	}

	public AuthorDto findAuthorByUserName(String userName) {
		AuthorDto authorDto = null;
		Author author = adminAuthorDao.findByAuthorUserName(userName);

		Preconditions.checkArgument(null != author);
		authorDto = convertToAuthorDto(author);

		return authorDto;
	}

	public void updateAuthor(AuthorDto authorDto) throws UserNotFoundException {
		Preconditions.checkArgument(authorDto != null);
		Author author = adminAuthorDao.findByAuthorUserName(authorDto
				.getAuthorUserName());
		if (author != null)
			getAdminAuthorDao().save(convertToAuthorEntity(authorDto, author));
		else
			throw new UserNotFoundException(
					"User doesn't exist. So, cannot update.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.brewcode.api.service.IAdminService#findAllAuthors()
	 */
	public List<AuthorWithRoleDto> findAllAuthors() {
		List<AuthorWithRoleDto> listAuthorWithRoleDto = null;
		List<Author> listAuthors = (List<Author>) getAdminAuthorDao().findAll();
		Hibernate.initialize(listAuthors);
		if (listAuthors != null) {
			listAuthorWithRoleDto = listAuthors.stream()
					.map(author -> convertToAuthorWithRoleDto(author))
					.collect(Collectors.toList());
			// Used Java 8 streams and collectors

			/*
			 * new ArrayList<AuthorWithRoleDto>(); for (Author a : listAuthors)
			 * { AuthorWithRoleDto ald = convertToAuthorWithRoleDto(a);
			 * listAuthorWithRoleDto.add(ald); }
			 */
		}
		return listAuthorWithRoleDto;
	}

	public AuthorDto findByUserName(String userName)
			throws UserNotFoundException {
		Preconditions.checkArgument(userName != null && !userName.equals(""),
				"Username cannot be null");
		Author author = adminAuthorDao.findByAuthorUserName(userName);
		if (author == null) {
			final String err = "Invalid username.";
			throw new UserNotFoundException(err);
		}

		return convertToAuthorDto(author);
	}

	public void assignRoletoAuthor(String authorUserName, String roleName)
			throws UserNotFoundException, RoleNotFoundException {
		Preconditions.checkArgument(!(authorUserName == null
				|| roleName == null || authorUserName == "" || roleName == ""));
		Author author = adminAuthorDao.findByAuthorUserName(authorUserName);
		if (author == null) {
			throw new UserNotFoundException();
		}
		Role role = roleDao.findByRoleNameIgnoreCase(roleName);
		if (role == null) {
			throw new RoleNotFoundException(roleName + "role not found");
		}

		author.setRole(role);
		adminAuthorDao.save(author);

	}

	public void removeRoleOfAuthor(String authorUserName, String roleName)
			throws UserNotFoundException, RoleNotFoundException {
		Preconditions.checkArgument(
				null != authorUserName && !authorUserName.equalsIgnoreCase(""),
				" Username can't be null or empty");
		Preconditions.checkArgument(
				null != roleName && !roleName.equalsIgnoreCase(""),
				"Role name can't be null or empty");
		Author author = adminAuthorDao.findByAuthorUserName(authorUserName);
		if (author == null) {
			throw new UserNotFoundException("No user exists with username: "
					+ authorUserName);
		}
		Role role = roleDao.findByRoleNameIgnoreCase(roleName);
		if (role == null) {
			throw new RoleNotFoundException(roleName + " not found");
		} else if (author.getRole() != null
				&& author.getRole().getRoleName()
						.equalsIgnoreCase(role.getRoleName())) {
			author.setRole(null);
			adminAuthorDao.save(author);
		} else {
			throw new RoleNotFoundException(" Wrong Input");
		}

	}

	public void deleteAuthor(String authorUserName)
			throws UserNotFoundException {
		Preconditions.checkArgument(
				null != authorUserName && !authorUserName.equals(""),
				"Cannot delete. Username cannot be empty");
		Author author = adminAuthorDao.findByAuthorUserName(authorUserName);
		if (author == null) {
			throw new UserNotFoundException(
					"Cannot delete, no user exists with username: "
							+ authorUserName);
		} else {
			adminAuthorDao.deleteAuthorByUserName(authorUserName);
		}

	}

	public List<RoleDto> getAllRoles() {
		List<Role> roles = roleDao.findAll();
		if (roles == null || roles.size() <= 0) {
			return null;
		} else {
			List<RoleDto> roleDtos = roles.stream()
					.map(ServiceUtils::convertToRoleDto)
					//.map(role -> convertToRoleDto(role))
					.collect(Collectors.toList());
			// Used Java 8 streams and collectors
			/*
			 * new ArrayList<RoleDto>(); for (Role r : roles) {
			 * roleDtos.add(convertToRoleDto(r)); }
			 */
			return roleDtos;
		}

	}

	public List<PrivilegeDto> getAllPrivileges() {

		List<Privilege> privileges = privilegeDao.findAll();
		List<PrivilegeDto> privilegeDtos = null;
		if (privileges == null || privileges.size() <= 0) {
			return privilegeDtos;
		} else {
			privilegeDtos = privileges.stream()
					.map(privilege -> convertToPrivilegeDto(privilege))
					.collect(Collectors.toList());

			// Used internal iterator, Java 8 streams and collectors

			/*
			 * new ArrayList<PrivilegeDto>(); for (Privilege p : privileges) {
			 * privilegeDtos.add(convertToPrivilegeDto(p)); }
			 */
			return privilegeDtos;
		}
	}

	public List<AuthorDto> findAuthorsByRoleName(String rolename)
			throws RoleNotFoundException {

		Preconditions.checkArgument(
				rolename != null && !rolename.equalsIgnoreCase(""),
				"Cannot find users. Role name cannot be empty");
		if (roleDao.findByRoleNameIgnoreCase(rolename) != null) {
			List<Author> authors = adminAuthorDao
					.findAllAuthorsByRole(rolename);
			List<AuthorDto> authorDtos = null;
			if (authors != null && authors.size() > 0) {

				authorDtos = authors.stream()
						.map(author -> convertToAuthorDto(author))
						.collect(Collectors.toList());
				// Used internal iterator, Java 8 streams and collectors
				/*
				 * new ArrayList<AuthorDto>(); for (Author a : authors) {
				 * authorDtos.add(convertToAuthorDto(a)); }
				 */
			}
			return authorDtos;
		} else {
			throw new RoleNotFoundException("Role doesn't exist");
		}

	}

}
