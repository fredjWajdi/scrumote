package com.adach.scrumote.dto.simple;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class SystemFeatureSimpleDto extends AbstractSimpleDto {

  @NotNull
  @Size(max = 32)
  private String code;

  private boolean enabled;
}
