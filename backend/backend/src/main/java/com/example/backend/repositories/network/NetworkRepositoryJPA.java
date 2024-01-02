package com.example.backend.repositories.network;

import com.example.backend.models.Network;
import com.example.backend.repositories.AbstractEntityRepositoryJpa;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository("NETWORK.JPA")
@Primary
public class NetworkRepositoryJPA extends AbstractEntityRepositoryJpa<Network> {
public NetworkRepositoryJPA() {
        super(Network.class);
    }

    @Override
    public List<Network> findAll() {
        return super.findAll();
    }

    @Override
    public List<Network> findByQuery(String jpqlName, Object... params) {
        return super.findByQuery(jpqlName, params);
    }

    @Override
    public Network findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Network save(Network entity) {
        return super.save(entity);
    }

    @Override
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }
}
