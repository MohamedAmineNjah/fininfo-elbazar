package org.fininfo.elbazar.repository;

import java.util.Optional;

import org.fininfo.elbazar.domain.Produit;
import org.fininfo.elbazar.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Stock entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StockRepository extends JpaRepository<Stock, Long>, JpaSpecificationExecutor<Stock> {
	
	Optional<Stock> findOneByrefProduit(Produit refProduit);
}
