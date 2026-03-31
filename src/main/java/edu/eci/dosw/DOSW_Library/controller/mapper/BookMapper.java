package edu.eci.dosw.DOSW_Library.controller.mapper;

import edu.eci.dosw.DOSW_Library.controller.dto.BookDTO;
import edu.eci.dosw.DOSW_Library.persistence.relational.entity.BookEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookMapper {

    public BookDTO toDTO(BookEntity entity) {
        if (entity == null) return null;
        return BookDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .autor(entity.getAutor())
                .isbn(entity.getIsbn())
                .totalCopies(entity.getTotalCopies())
                .availableCopies(entity.getAvailableCopies())
                .build();
    }

    public BookEntity toEntity(BookDTO dto) {
        if (dto == null) return null;
        return BookEntity.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .autor(dto.getAutor())
                .isbn(dto.getIsbn())
                .totalCopies(dto.getTotalCopies())
                .availableCopies(dto.getAvailableCopies())
                .build();
    }

    public List<BookDTO> toDTOList(List<BookEntity> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}