package com.example.backend.repositories;

import com.example.backend.BackendApplication;
import com.example.backend.models.Paragraph;
import com.example.backend.models.Network;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = BackendApplication.class)
@Import(DataLoader.class)
public class NetworkRepositoryTest {

    @Autowired
    private EntityRepository<Network> networkEntityRepository;
    @BeforeAll
    public static void classLevelSetup() {
        System.out.println("initialise static values and expensive data structures");
    }

    @Autowired
    CommandLineRunner dataLoader;

    private List<Network> someNetworks;

    @BeforeEach
    public void setup() throws Exception {
        this.dataLoader.run(null);
        this.someNetworks = this.networkEntityRepository.findAll();
    }

    @Test
    public void testFindAll() {
        assertTrue(this.someNetworks.size() > 0);
    }

    @Test
    public void findNetworkByTheme() {
        Network network = new Network("Test Network", "Test Network Description", null, Network.Theme.ENERGY_TRANSITION);
        List<Network> networks = this.networkEntityRepository.findMultipleByProperty("theme", Network.Theme.ENERGY_TRANSITION);
        assertTrue(networks.size() > 0);
        assertTrue(networks.get(0).getTheme().equals(Network.Theme.ENERGY_TRANSITION));
    }






}
