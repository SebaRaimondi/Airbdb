package ar.edu.info.unlp.bd2.etapa2.service.impl;

import ar.edu.info.unlp.bd2.etapa2.repository.AirBdbRepository;
import ar.edu.info.unlp.bd2.etapa2.service.AirBdbService;
import ar.edu.info.unlp.bd2.etapa2.model.*;
import ar.edu.info.unlp.bd2.etapa2.exceptions.*;

public class AirBdbServiceImpl implements AirBdbService {
    private AirBdbRepository repository;

    public AirBdbServiceImpl(AirBdbRepository repository) {
        this.repository = repository;
    }

    /* creates a new user and returns it. Throws RepeatedUsernameException if the chosen username is already taken */
    public User createUser(String username, String name) throws RepeatedUsernameException {
        username = username.toLowerCase();
        if (! this.repository.uniqueUsername(username)) throw new RepeatedUsernameException();
        User user = new User (username, name);
        return repository.createUser(user);
    }
}
