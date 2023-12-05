package com.example.backend.repositories.UserEvent;


import com.example.backend.models.Event;
import com.example.backend.models.User;
import com.example.backend.models.UserEvent;

import java.util.List;

public interface UserEventRepository {
    List<UserEvent> getUserEvents();
    UserEvent getUserEvent(long id);
    void addUserEvent(UserEvent userEvent);
    void updateUserEvent(UserEvent userEvent);
    void deleteUserEvent(long id);
    UserEvent getUserEventByUserAndEvent(User user, Event event);
    int countUserEventsByEventId(long eventId);


}
