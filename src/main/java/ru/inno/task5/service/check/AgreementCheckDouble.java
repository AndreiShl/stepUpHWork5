package ru.inno.task5.service.check;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.inno.task5.repository.AgreementRepo;
import ru.inno.task5.service.InstanceArrangement;
import ru.inno.task5.service.InstanceRequest;

@Component
public class AgreementCheckDouble implements InstanceChechable {
    @Autowired
    private AgreementRepo agreementRepo;

    @Override
    public ResponseEntity<?> check(ResponseEntity<?> response, InstanceRequest instanceRequest) {
        if (response != null) return response;
        for (InstanceArrangement instanceArrangement : instanceRequest.instanceArrangements()) {
            if (agreementRepo.findFirstByNumber(instanceArrangement.number()) != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        String.format("Параметр № Дополнительного соглашения (сделки)" +
                                        " Number %s уже существует для ЭП с ИД  %s.",
                                instanceArrangement.number(),
                                instanceRequest.instanceId()));
            }
        }
        return null;
    }
}
