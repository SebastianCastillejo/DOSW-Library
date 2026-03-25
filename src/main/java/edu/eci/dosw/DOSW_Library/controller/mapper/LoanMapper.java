package edu.eci.dosw.DOSW_Library.controller.mapper;

import edu.eci.dosw.DOSW_Library.controller.dto.LoanDTO;
import edu.eci.dosw.DOSW_Library.persistence.entity.LoanEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LoanMapper {

    private final BookMapper bookMapper;
    private final UserMapper userMapper;

    public LoanMapper(BookMapper bookMapper, UserMapper userMapper) {
        this.bookMapper = bookMapper;
        this.userMapper = userMapper;
    }

    public LoanDTO toDTO(LoanEntity entity) {
        if (entity == null) return null;
        return LoanDTO.builder()
                .id(entity.getId())
                .book(bookMapper.toDTO(entity.getBook()))
                .user(userMapper.toDTO(entity.getUser()))
                .loanDate(entity.getLoanDate())
                .returnDate(entity.getReturnDate())
                .status(entity.getStatus())
                .build();
    }

    public List<LoanDTO> toDTOList(List<LoanEntity> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}