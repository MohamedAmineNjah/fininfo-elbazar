package org.fininfo.elbazar.repository;

import java.util.List;
import java.util.Optional;

import org.fininfo.elbazar.domain.Adresse;
import org.fininfo.elbazar.domain.Client;
import org.fininfo.elbazar.service.dto.AdresseDTO;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the Adresse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdresseRepository extends JpaRepository<Adresse, Long>, JpaSpecificationExecutor<Adresse> {
    List<Adresse> findByClient(Client client);

    @Query(value = "SELECT a FROM Adresse a WHERE a.principale = true and a.client =?1 ")
    Optional<Adresse> findByPrincipal(Client idclient);

    @Query(value = "select adr.* from adresse adr inner join jhi_user usr on adr.client_id= usr.id where usr.login = ?1", nativeQuery = true)
    List<Adresse> findByEmail(String email);
}