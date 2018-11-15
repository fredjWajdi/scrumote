package com.adach.scrumote.service.internal;

import com.adach.scrumote.entity.Role;
import com.adach.scrumote.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.MANDATORY, isolation = Isolation.READ_COMMITTED)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoleInternalService {

  private static final String STANDARD_USER_ROLE_NAME = "STANDARD";

  private final RoleRepository repository;

  @Secured({"ROLE_ANONYMOUS", "swagger"})
  public Role findStandardUserRole() {
    return repository
        .findByName(STANDARD_USER_ROLE_NAME)
        .orElseThrow(() -> new RuntimeException("No standard role in DB.")); //TODO inny wyjatek
  }
}
