package com.adach.scrumote.dto.simple;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CardSimpleDto extends AbstractSimpleDto {

  @NotNull
  private Long deckId;

  @NotNull
  private Integer level;

  @NotNull
  private String value;
}