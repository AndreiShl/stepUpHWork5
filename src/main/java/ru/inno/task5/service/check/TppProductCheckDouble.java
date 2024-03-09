package ru.inno.task5.service.check;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.inno.task5.repository.TppProductRepo;
import ru.inno.task5.service.InstanceRequest;

@Component
public class TppProductCheckDouble implements InstanceChechable{
    @Autowired private TppProductRepo tppProductRepo;
    @Override
    public ResponseEntity<?> check(ResponseEntity<?> response, InstanceRequest instanceRequest) {
        if (response != null) return response;
        if (tppProductRepo.findFirstByNumber(instanceRequest.contractNumber()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(
                            String.format("Параметр ContractNumber № договора " +
                                            "%s уже существует для ЭП с ИД  %s.",
                                    instanceRequest.contractNumber(),
                                    instanceRequest.instanceId()
                            )
                    );
        }
        return null;
    }
}
