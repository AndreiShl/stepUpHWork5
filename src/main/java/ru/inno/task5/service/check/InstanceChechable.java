package ru.inno.task5.service.check;

import org.springframework.http.ResponseEntity;
import ru.inno.task5.service.InstanceRequest;

public interface InstanceChechable {
    ResponseEntity<?> check(ResponseEntity<?> response, InstanceRequest request);
}
