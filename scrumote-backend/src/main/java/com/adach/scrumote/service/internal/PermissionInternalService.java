package com.adach.scrumote.service.internal;

import com.adach.scrumote.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PermissionInternalService {

  private final PermissionRepository repository;
}
