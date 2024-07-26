package by.angelica.pet_projects.mail_service.controller.filter;

import by.angelica.pet_projects.mail_service.core.exceptions.FieldsIncorrectException;
import jakarta.persistence.OptimisticLockException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final static Logger logger = LogManager.getLogger();

    @ExceptionHandler
    public ResponseEntity<Map<String,Object>> handleIncorrectFields(FieldsIncorrectException e) {

        logger.log(Level.WARN, "Пользователь передал некорректные данные в поля",e);

        Map<String, Object> errors = new HashMap<>();
        errors.put("field", e.getField());
        errors.put("message", e.getMessage());

        Map<String, Object> response = new HashMap<>();

        response.put("logref", "structured_error");
        response.put("errors", errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String,Object>> handleIllegal(IllegalArgumentException e) {

        logger.log(Level.WARN, "Пользователь передал некорректные данные",e);

        Map<String, Object> response = new HashMap<>();

        response.put("logref", "error");
        response.put("message", "Запрос содержит некорректные данные. Измените запрос и отправьте его ещё раз");

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String,Object>> handleOptimisticLock(OptimisticLockException e) {

        logger.log(Level.WARN, "Пользователь передал некорректные данные. Ошибка версий",e);

        Map<String, Object> errors = new HashMap<>();
        errors.put("field", "dt_update");
        errors.put("message", e.getMessage());

        Map<String, Object> response = new HashMap<>();

        response.put("logref", "structured_error");
        response.put("errors", errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String,Object>> handleObjectOptimisticLock(ObjectOptimisticLockingFailureException e) {

        logger.log(Level.WARN, "Ошибка при обновлении данных. Ошибка версий",e);

        Map<String, Object> errors = new HashMap<>();
        errors.put("field", "dt_update");
        errors.put("message", "Ошибка при обновлении данных. Необходимо проверить дату последнего обновления");

        Map<String, Object> response = new HashMap<>();

        response.put("logref", "structured_error");
        response.put("errors", errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String,Object>> handleException(Exception e) {

        logger.log(Level.ERROR, "Ошибка на сервере",e);

        Map<String, Object> response = new HashMap<>();

        response.put("logref", "error");
        response.put("message", "Сервер не смог корректно обработать запрос. Пожалуйста обратитесь к администратору");

        return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
