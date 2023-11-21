package com.example.backend.repositories.UserEvent;

import com.example.backend.models.Event;
import com.example.backend.models.User;
import com.example.backend.models.UserEvent;
import com.example.backend.repositories.user.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@Qualifier("mock")
public class UserEventRepositoryMock implements UserEventRepository {
    private List<UserEvent> userEvents = new ArrayList<>();


    @Override
    public List<UserEvent> getUserEvents() {
        return null;
    }

    @Override
    public UserEvent getUserEvent(long id) {
        return null;
    }

    @Override
    public void addUserEvent(UserEvent userEvent) {

    }

    @Override
    public void updateUserEvent(UserEvent userEvent) {

    }

    @Override
    public void deleteUserEvent(long id) {

    }

    @Override
    public UserEvent getUserEventByUserAndEvent(User user, Event event) {
        return null;
    }

    @Override
    public int countUserEventsByEventId(long eventId) {
        return 0;
    }
}

