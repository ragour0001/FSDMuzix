package com.stackroute.orchestrationService.service;

import com.stackroute.orchestrationService.domain.User;
import com.stackroute.orchestrationService.exception.UserAlreadyExistsException;

public interface OrchestrationService {

  User registerUser(User user) throws UserAlreadyExistsException;
}
