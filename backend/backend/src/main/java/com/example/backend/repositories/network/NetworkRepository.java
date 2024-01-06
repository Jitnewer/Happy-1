package com.example.backend.repositories.network;

import com.example.backend.models.Network;

import java.util.List;

public interface NetworkRepository {
    List<Network> getNetwork();
    Network getNetwork(long id);
    void addNetwork(Network network);
    void updateNetwork(Network network);
    void deleteNetwork(long id);

}
