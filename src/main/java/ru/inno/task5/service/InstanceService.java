package ru.inno.task5.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.inno.task5.model.Agreement;
import ru.inno.task5.model.TppProduct;
import ru.inno.task5.model.TppProductRegister;
import ru.inno.task5.model.TppRefProductRegisterType;
import ru.inno.task5.repository.AgreementRepo;
import ru.inno.task5.repository.TppProductRegisterRepo;
import ru.inno.task5.repository.TppProductRepo;
import ru.inno.task5.repository.TppRefProductRegisterTypeRepo;
import ru.inno.task5.service.check.AgreementCheckDouble;
import ru.inno.task5.service.check.TppProductCheckDouble;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class InstanceService {
    @Autowired
    private TppProductCheckDouble tppProductCheckDouble;
    @Autowired
    private AgreementCheckDouble agreementCheckDouble;
    @Autowired
    private TppRefProductRegisterTypeRepo tppRefProductRegisterTypeRepo;
    @Autowired
    private TppProductRepo tppProductRepo;
    @Autowired
    private TppProductRegisterRepo tppProductRegisterRepo;
    @Autowired
    private AgreementRepo agreementRepo;

    @Transactional
    public ResponseEntity<?> process(InstanceRequest request) {
        if (request.instanceId() == null) {
            return create(request);
        } else {
            return update(request);
        }
    }

    private ResponseEntity<?> create(InstanceRequest request) {
        var response = tppProductCheckDouble.check(null, request);
        response = agreementCheckDouble.check(response, request);
        if (response != null) return response;

        var tppRefProductRegisterType = tppRefProductRegisterTypeRepo.getAllByProductClassCodeAndAccountType(request.productCode(), "Клиентский");
        if (tppRefProductRegisterType.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    String.format("КодПродукта %s не найдено в Каталоге продуктов tpp_ref_product_class", request.productCode())
            );
        }
        var registerTypes = tppRefProductRegisterType
                .stream()
                .map(TppRefProductRegisterType::getValue)
                .toList();

        TppProduct product = new TppProduct();
        product.setState("открыт");
        product.setType(request.productType());
        product.setNumber(request.contractNumber());
        product.setType(request.registerType());
        product.setPriority(request.priority().longValue());
        product = tppProductRepo.save(product);
        System.out.println("product.getId() = " + product.getId());

        List<Integer> createdProductRegisterId = new ArrayList<>();
        for (String registrType : registerTypes) {
            TppProductRegister productRegister = new TppProductRegister();
            productRegister.setProductId(product.getId().longValue());
            productRegister.setType(registrType);
            productRegister.setCurrencyCode(request.isoCurrencyCode());
            productRegister = tppProductRegisterRepo.save(productRegister);
            createdProductRegisterId.add(productRegister.getId());
        }
        List<String> supplementaryAgreementId = new ArrayList<>();
        request.instanceArrangements().forEach(instanceArrangement -> supplementaryAgreementId.add(instanceArrangement.generalAgreementId()));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new InstanceResponceBody(
                                product.getId().toString(),
                                createdProductRegisterId,
                                supplementaryAgreementId
                        )
                );
    }

    private ResponseEntity<?> update(InstanceRequest request) {
        var productById = tppProductRepo.findById(request.instanceId());
        if (productById.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(
                            String.format("Экземпляр продукта с параметром instanceId %s не найден.", request.instanceId())
                    );
        }

        var response = agreementCheckDouble.check(null, request);
        if (response != null) return response;

        List<String> createdAgreementId = new ArrayList<>();
        request.instanceArrangements().forEach(
                instanceArrangement -> {
                    Agreement agreement = new Agreement();
                    agreement.setNumber(instanceArrangement.number());
                    agreement.setOpeningDate(instanceArrangement.openingDate() == null ? null
                            : Timestamp.from(instanceArrangement.openingDate()));
                    agreement.setArrangementType(instanceArrangement.arrangementType());
                    agreement.setCancelDate(instanceArrangement.cancelDate() == null ? null
                            : Timestamp.from(instanceArrangement.cancelDate()));
                    agreement.setCancellationReason(instanceArrangement.cancellationReason());
                    agreement.setClosingDate(instanceArrangement.closingDate() == null ? null
                            : Timestamp.from(instanceArrangement.closingDate()));
                    agreement.setCoefficient(instanceArrangement.coefficient());
                    agreement.setCoefficientAction(instanceArrangement.coefficientAction());
                    agreement.setGeneralAgreementId(instanceArrangement.generalAgreementId());
                    agreement.setInterestCalculationDate(instanceArrangement.interestCalculationDate() == null ? null
                            : Timestamp.from(instanceArrangement.interestCalculationDate()));
                    agreement.setInterestRate(instanceArrangement.interestRate());
                    agreement.setMaximalInterestRate(instanceArrangement.maximalnterestRate());
                    agreement.setMaximalInterestRateCoefficient(instanceArrangement.maximalnterestRateCoefficient());
                    agreement.setMaximalInterestRateCoefficientAction(instanceArrangement.maximalnterestRateCoefficientAction());
                    agreement.setMinimumInterestRate(instanceArrangement.minimumInterestRate());
                    agreement.setMinimumInterestRateCoefficient(instanceArrangement.minimumInterestRateCoefficient());
                    agreement.setMinimumInterestRateCoefficientAction(instanceArrangement.minimumInterestRateCoefficientAction());
                    agreement.setNumber(instanceArrangement.number());
                    agreement.setOpeningDate(instanceArrangement.openingDate() == null ? null
                            : Timestamp.from(instanceArrangement.openingDate()));
                    agreement.setProductId(productById.get());
                    agreement.setShedulerJobId(instanceArrangement.shedulerJobId());
                    agreement.setStatus(instanceArrangement.status());
                    agreement.setSupplementaryAgreementId(instanceArrangement.supplementaryAgreementId());
                    agreement.setValidityDuration(instanceArrangement.validityDuration());
                    agreement = agreementRepo.save(agreement);
                    createdAgreementId.add(agreement.getId().toString());
                }
        );
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        new InstanceResponceBody(
                                productById.get().toString(),
                                null,
                                createdAgreementId
                        )
                );
    }
}
