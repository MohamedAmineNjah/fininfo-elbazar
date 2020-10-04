package org.fininfo.elbazar.repository;

import java.util.List;

import org.fininfo.elbazar.domain.Slides;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Slides entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SlidesRepository extends JpaRepository<Slides, Long> {

    // Get "Slides" Carousel
    @Query(value = "SELECT s FROM Slides s where s.type = 'Carousel' order by s.nom")
    List<Slides> findAllByCarousel();

    // Get "Slides" Partners
    @Query(value = "SELECT s FROM Slides s where s.type = 'Partners' order by s.nom")
    List<Slides> findAllByPartners();
}