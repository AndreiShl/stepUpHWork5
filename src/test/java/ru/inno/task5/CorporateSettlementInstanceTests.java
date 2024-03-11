package ru.inno.task5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import ru.inno.task5.config.AppConfig;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@ContextConfiguration(classes = {AppConfig.class})
@AutoConfigureMockMvc
@Sql("/create-db.sql")
@Sql("/fill-db.sql")
public class CorporateSettlementInstanceTests {
    @Autowired
    MockMvc mockMvc;

    private String getBaseRequest(){
        return "{\"instanceId\":null,\"productType\":\"03.012.002_47533_ComSoLd\"" +
                ",\"productCode\":\"03.012.002\",\"registerType\":\"registerType\",\"mdmCode\":\"mdmCode\"" +
                ",\"contractNumber\":\"783\",\"contractDate\":\"2024-03-07T18:40:51.771959700Z\",\"priority\":5" +
                ",\"interestRatePenalty\":null,\"minimalBalance\":null,\"thresholdAmount\":null" +
                ",\"accountingDetails\":null,\"rateType\":null,\"taxPercentageRate\":null" +
                ",\"technicalOverdraftLimitAmount\":null,\"contractId\":\"456\",\"branchCode\":\"0021\"" +
                ",\"isoCurrencyCode\":\"777\",\"urgencyCode\":null,\"referenceCode\":null," +
                "\"additionalProperties\":[{\"key\":\"key1\",\"value\":\"value1\",\"name\":\"name1\"},{\"key\":\"key1\",\"value\":\"value1\"" +
                ",\"name\":\"name1\"}]" +
                ",\"instanceArrangements\":[{\"generalAgreementId\":\"545\",\"supplementaryAgreementId\":\"778\"" +
                ",\"arrangementType\":\"НСО\",\"shedulerJobId\":null,\"number\":\"600\"" +
                ",\"openingDate\":\"2024-03-07T18:40:51.772960100Z\",\"closingDate\":null,\"cancelDate\":null" +
                ",\"validityDuration\":null,\"cancellationReason\":null,\"status\":null,\"interestCalculationDate\":null" +
                ",\"interestRate\":null,\"coefficient\":null,\"coefficientAction\":null,\"minimumInterestRate\":null" +
                ",\"minimumInterestRateCoefficient\":null,\"minimumInterestRateCoefficientAction\":null,\"maximalnterestRate\":null" +
                ",\"maximalnterestRateCoefficient\":null,\"maximalnterestRateCoefficientAction\":null},{\"generalAgreementId\":\"546\"" +
                ",\"supplementaryAgreementId\":\"111\",\"arrangementType\":\"НСО\",\"shedulerJobId\":null,\"number\":\"600\"" +
                ",\"openingDate\":\"2024-03-07T18:40:51.772960100Z\",\"closingDate\":null,\"cancelDate\":null" +
                ",\"validityDuration\":null,\"cancellationReason\":null,\"status\":null,\"interestCalculationDate\":null" +
                ",\"interestRate\":null,\"coefficient\":null,\"coefficientAction\":null,\"minimumInterestRate\":null" +
                ",\"minimumInterestRateCoefficient\":null,\"minimumInterestRateCoefficientAction\":null" +
                ",\"maximalnterestRate\":null,\"maximalnterestRateCoefficient\":null,\"maximalnterestRateCoefficientAction\":null}]}";
    }

    @Test
    @DisplayName("Обязательно поле productType")
    public void productType() throws Exception {
        mockMvc.perform(
                        post("/corporate-settlement-instance/create")
                                .content(getBaseRequest().replace("\"productType\":\"03.012.002_47533_ComSoLd\"", "\"productType\":null"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                )
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(containsString("Имя обязательного параметра productType не заполнено")))
        ;
    }

    @Test
    @DisplayName("Обязательно поле contractId")
    public void contractId() throws Exception {
        mockMvc.perform(
                        post("/corporate-settlement-instance/create")
                                .content(getBaseRequest().replace("\"contractId\":\"456\"", "\"contractId\":null"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                )
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(containsString("Имя обязательного параметра contractId не заполнено")))
        ;
    }

    @Test
    @DisplayName("Создание. Проверка таблицы ЭП (tpp_product) на дубли")
    public void tpp_product_double() throws Exception {
        mockMvc.perform(
                post("/corporate-settlement-instance/create")
                        .content(getBaseRequest())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
        );

        mockMvc.perform(
                        post("/corporate-settlement-instance/create")
                                .content(getBaseRequest())
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                )
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(containsString("Параметр ContractNumber № договора 783 уже существует")))
        ;
    }

    @Test
    @DisplayName("успешное создание")
    public void sucessfulCreate() throws Exception {
        mockMvc.perform(
                        post("/corporate-settlement-instance/create")
                                .content(getBaseRequest())
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("{\"instanceId\":\"1\",\"registerId\":[1,2],\"supplementaryAgreementId\":[\"545\",\"546\"]}")))
        ;
    }

    @Test
    @DisplayName("Редактирование. проверка InstanceId")
    public void editCheckInstanceId() throws Exception {
        mockMvc.perform(
                post("/corporate-settlement-instance/create")
                        .content(getBaseRequest())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
        );

        mockMvc.perform(
                        post("/corporate-settlement-instance/create")
                                .content(getBaseRequest().replace("\"instanceId\":null","\"instanceId\":\"555\""))
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                )
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(containsString("Экземпляр продукта с параметром instanceId 555 не найден.")))
        ;
    }
    @Test
    @DisplayName("Редактирование. успех")
    public void editSuccesfull() throws Exception {
        mockMvc.perform(
                post("/corporate-settlement-instance/create")
                        .content(getBaseRequest())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
        );

        mockMvc.perform(
                        post("/corporate-settlement-instance/create")
                                .content(getBaseRequest().replace("\"instanceId\":null","\"instanceId\":\"1\""))
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("{\"instanceId\":\"TppProduct(id=1, productCodeId=null" +
                        ", clientId=null, type=registerType, number=783, priority=5, dateOfConclusion=null, startDateTime=null" +
                        ", endDateTime=null, days=null, penaltyRate=null, nso=null, thresholdAmount=null, requisiteType=null" +
                        ", interestRateType=null, taxRate=null, reasoneClose=null")))
        ;
    }


}
