package br.com.ddreer.magicplaylistapi.config;

import br.com.ddreer.magicplaylistapi.enums.StatusEnum;
import br.com.ddreer.magicplaylistapi.exception.BusinessException;
import br.com.ddreer.magicplaylistapi.model.MessageErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<MessageErrorDTO> handleBusinessException(BusinessException e) {
        MessageErrorDTO errorDTO = new MessageErrorDTO(e.getMessage(), StatusEnum.WARNING);
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<MessageErrorDTO> handleException(BusinessException e) {
        MessageErrorDTO errorDTO = new MessageErrorDTO(e.getLocalizedMessage(), StatusEnum.ERROR);
        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
