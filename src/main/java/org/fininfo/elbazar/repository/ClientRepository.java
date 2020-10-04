package org.fininfo.elbazar.repository;

import java.util.Optional;

import org.fininfo.elbazar.domain.Client;
import org.fininfo.elbazar.domain.User;
import org.fininfo.elbazar.service.dto.ClientDTO;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Client entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientRepository extends JpaRepository<Client, Long>, JpaSpecificationExecutor<Client> {

	Optional<Client> findOneByUser(User user);

	@Query(value = "SELECT c.pointsFidelite from Client c JOIN User u on c.id = u.id where u.login =?1")
	Optional<Client> findPointFidByLogin(String login);

	@Query(value = "SELECT c from Client c JOIN User u on c.id = u.id where u.login =?1")
	Optional<Client> findClientByLogin(String login);
}