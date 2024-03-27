package cards.logic.dto.mappers;

import cards.logic.dto.CardDto;
import cards.logic.model.Card;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CardMapper {

  CardMapper INSTANCE = Mappers.getMapper(CardMapper.class);

  @Mapping(target = "status", ignore = true)
  Card cardDtoToCard(CardDto cardDto);

  CardDto cardToCardDto(Card card);

  @Mapping(target = "id", ignore = true)
  void updateCardFromDto(CardDto cardDto, @MappingTarget Card card);
}

