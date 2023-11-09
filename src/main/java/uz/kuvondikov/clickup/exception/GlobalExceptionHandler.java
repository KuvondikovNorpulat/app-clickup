package uz.kuvondikov.clickup.exception;

import com.fasterxml.jackson.core.JsonParseException;
import jakarta.validation.ConstraintViolationException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uz.kuvondikov.clickup.dto.response.AppErrorDTO;
import uz.kuvondikov.clickup.dto.response.DataDTO;

import java.sql.SQLException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatusCode status,
                                                                  @NonNull WebRequest request) {
        StringBuilder message = new StringBuilder();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            message.append("Cause -> ").append(fieldError.getDefaultMessage()).append("\n");
        }
        AppErrorDTO appErrorDTO = new AppErrorDTO(HttpStatus.BAD_REQUEST, message.toString());
        return new ResponseEntity<>(new DataDTO<>(appErrorDTO), headers, status);
    }

    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<DataDTO<AppErrorDTO>> handle400(BadRequestException e, WebRequest webRequest) {
        DataDTO<AppErrorDTO> objectDataDTO = new DataDTO<>();
        objectDataDTO.setError(new AppErrorDTO(HttpStatus.NOT_FOUND, e.getMessage(), webRequest));
        return new ResponseEntity<>(objectDataDTO, HttpStatus.OK);
    }


    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<DataDTO<AppErrorDTO>> handle404(NotFoundException e, WebRequest webRequest) {
        DataDTO<AppErrorDTO> objectDataDTO = new DataDTO<>();
        objectDataDTO.setError(new AppErrorDTO(HttpStatus.NOT_FOUND, e.getMessage(), webRequest));
        return new ResponseEntity<>(objectDataDTO, HttpStatus.OK);
    }

    @ExceptionHandler(value = {FileIOException.class, ConstraintViolationException.class, JsonParseException.class, SQLException.class, RuntimeException.class})
    public ResponseEntity<DataDTO<Object>> handle500(RuntimeException e, WebRequest webRequest) {
        DataDTO<Object> objectDataDTO = new DataDTO<>();
        objectDataDTO.setError(new AppErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR, ExceptionUtils.getRootCauseMessage(e), webRequest));
        return new ResponseEntity<>(objectDataDTO, HttpStatus.OK);
    }

}
