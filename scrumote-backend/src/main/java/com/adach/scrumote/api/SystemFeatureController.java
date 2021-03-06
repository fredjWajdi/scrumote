package com.adach.scrumote.api;

import com.adach.scrumote.configuration.api.PrefixedRestController;
import com.adach.scrumote.dto.simple.SystemFeatureSimpleDto;
import com.adach.scrumote.service.external.SystemFeatureExternalService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@PrefixedRestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SystemFeatureController extends AbstractController {

  private final SystemFeatureExternalService systemFeatureExternalService;

  @GetMapping("/system-features/{code}")
  public SystemFeatureSimpleDto getSystemFeature(@PathVariable String code) {
    return systemFeatureExternalService.getSystemFeature(code);
  }

  @PreAuthorize("hasAnyAuthority('getAllSystemFeatures')")
  @GetMapping("/system-features")
  public List<SystemFeatureSimpleDto> getAllSystemFeatures() {
    return systemFeatureExternalService.getAllSystemFeatures();
  }

  @PreAuthorize("hasAnyAuthority('updateSystemFeature')")
  @PutMapping("/system-features/{featureId}")
  public ResponseEntity<?> updateSystemFeature(@PathVariable Long featureId,
      @RequestBody @Valid SystemFeatureSimpleDto dto,
      @RequestHeader(value = VERSION_HEADER) String versionHeader) {
    Long version = extractVersion(versionHeader);
    systemFeatureExternalService.updateSystemFeature(featureId, version, dto);
    return ResponseEntity.noContent().build();
  }
}
