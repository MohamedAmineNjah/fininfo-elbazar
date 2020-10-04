package org.fininfo.elbazar.web.rest;

import org.fininfo.elbazar.domain.Slides;
import org.fininfo.elbazar.repository.SlidesRepository;
import org.fininfo.elbazar.repository.search.SlidesSearchRepository;
import org.fininfo.elbazar.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link org.fininfo.elbazar.domain.Slides}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SlidesResource {

    private final Logger log = LoggerFactory.getLogger(SlidesResource.class);

    private static final String ENTITY_NAME = "slides";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SlidesRepository slidesRepository;

    private final SlidesSearchRepository slidesSearchRepository;

    public SlidesResource(SlidesRepository slidesRepository, SlidesSearchRepository slidesSearchRepository) {
        this.slidesRepository = slidesRepository;
        this.slidesSearchRepository = slidesSearchRepository;
    }

    /**
     * {@code POST  /slides} : Create a new slides.
     *
     * @param slides the slides to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new slides, or with status {@code 400 (Bad Request)} if the slides has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/slides")
    public ResponseEntity<Slides> createSlides(@Valid @RequestBody Slides slides) throws URISyntaxException {
        log.debug("REST request to save Slides : {}", slides);
        if (slides.getId() != null) {
            throw new BadRequestAlertException("A new slides cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Slides result = slidesRepository.save(slides);
        slidesSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/slides/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /slides} : Updates an existing slides.
     *
     * @param slides the slides to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated slides,
     * or with status {@code 400 (Bad Request)} if the slides is not valid,
     * or with status {@code 500 (Internal Server Error)} if the slides couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/slides")
    public ResponseEntity<Slides> updateSlides(@Valid @RequestBody Slides slides) throws URISyntaxException {
        log.debug("REST request to update Slides : {}", slides);
        if (slides.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Slides result = slidesRepository.save(slides);
        slidesSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, slides.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /slides} : get all the slides.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of slides in body.
     */
    @GetMapping("/slides")
    public List<Slides> getAllSlides() {
        log.debug("REST request to get all Slides");
        return slidesRepository.findAll();
    }

    /**
     * {@code GET  /slides/:id} : get the "id" slides.
     *
     * @param id the id of the slides to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the slides, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/slides/{id}")
    public ResponseEntity<Slides> getSlides(@PathVariable Long id) {
        log.debug("REST request to get Slides : {}", id);
        Optional<Slides> slides = slidesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(slides);
    }

    /**
     * {@code DELETE  /slides/:id} : delete the "id" slides.
     *
     * @param id the id of the slides to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/slides/{id}")
    public ResponseEntity<Void> deleteSlides(@PathVariable Long id) {
        log.debug("REST request to delete Slides : {}", id);

        slidesRepository.deleteById(id);
        slidesSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/slides?query=:query} : search for the slides corresponding
     * to the query.
     *
     * @param query the query of the slides search.
     * @return the result of the search.
     */
    @GetMapping("/_search/slides")
    public List<Slides> searchSlides(@RequestParam String query) {
        log.debug("REST request to search Slides for query {}", query);
        return StreamSupport
            .stream(slidesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }

        /**************************** FININFO CODE : START ************************************ */

        // Get All "Slides" in Carousel
        @GetMapping("/slides/carousel")
        public List<Slides> findAllByCarousel() {
            List<Slides> produits = slidesRepository.findAllByCarousel();
            return produits; 
        }

        // Get All "Slides" in Carousel
        @GetMapping("/slides/partners")
        public List<Slides> findAllByPartners() {
            List<Slides> produits = slidesRepository.findAllByPartners();
            return produits; 
        }

        /***************************** FININFO CODE : END *************************************/
}
