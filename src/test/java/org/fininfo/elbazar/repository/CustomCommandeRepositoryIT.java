package org.fininfo.elbazar.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.fininfo.elbazar.ElbazarApp;
import org.fininfo.elbazar.domain.Commande;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for {@link CustomAuditEventRepository}.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ElbazarApp.class)
@Transactional

public class CustomCommandeRepositoryIT {

    @Autowired
    private CommandeRepository commandeRepository;
    
    @Test
    public void getClientByCommandeIdTest() {
    	Long idc = (long) 1204;
    	List<Commande> objects = commandeRepository.findAll();
    	System.out.println("Select OK"+objects.size());

    }
    
    	
}
