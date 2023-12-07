package com.example.backend.repositories.research;

import com.example.backend.models.Challenge;
import com.example.backend.models.Research;

import java.util.List;

public interface ResearchRepository {
        List<Research> getResearches();
        Research getResearch(long id);
        void addResearch(Research research);
        void updateResearch(Research research);
        void deleteResearch(long id);

}
