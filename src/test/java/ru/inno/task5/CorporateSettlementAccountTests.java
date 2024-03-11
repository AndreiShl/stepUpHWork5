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
import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest()
@ContextConfiguration(classes = {AppConfig.class})
@AutoConfigureMockMvc
@Sql("/create-db.sql")
@Sql("/fill-db.sql")
class CorporateSettlementAccountTests {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void currencyCode() throws Exception {

        String accountRequestSt = "{\"instanceId\":134,\"registryTypeCode\":\"03.012.002_47533_ComSoLd\"" +
                ",\"accountType\":\"Клиентский\",\"currencyCode\":\"TOO_LONG_VALUE\",\"branchCode\":\"0022\"" +
                ",\"priorityCode\":\"00\",\"mdmCode\":\"15\",\"clientCode\":null,\"counter\":null" +
                ",\"salesCode\":null,\"trainRegion\":\"РЖД\"}";

        mockMvc.perform(
                post("/corporate-settlement-account/create")
                        .content(accountRequestSt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                )
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(containsString("Код валюты должен быть 3 символа")))
        ;
    }

    @Test
    @DisplayName("Пустой instanceId")
    public void instanceId() throws Exception {

        String accountRequestSt = "{\"instanceId\":null,\"registryTypeCode\":\"03.012.002_47533_ComSoLd\"" +
                ",\"accountType\":\"Клиентский\",\"currencyCode\":\"800\",\"branchCode\":\"0022\"" +
                ",\"priorityCode\":\"00\",\"mdmCode\":\"15\",\"clientCode\":null,\"counter\":null" +
                ",\"salesCode\":null,\"trainRegion\":\"РЖД\"}";

        mockMvc.perform(
                        post("/corporate-settlement-account/create")
                                .content(accountRequestSt)
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                )
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(containsString("Имя обязательного параметра instanceId не заполнено")))
        ;
    }

    @Test
    @DisplayName("Проверка таблицы ПР (таблица tpp_product_register) на дубли")
    public void tpp_product_register_double()throws Exception {

        String accountRequestSt = "{\"instanceId\":101,\"registryTypeCode\":\"03.012.002_47533_ComSoLd\"" +
                ",\"accountType\":\"Клиентский\",\"currencyCode\":\"800\",\"branchCode\":\"0022\"" +
                ",\"priorityCode\":\"00\",\"mdmCode\":\"15\",\"clientCode\":null,\"counter\":null" +
                ",\"salesCode\":null,\"trainRegion\":\"РЖД\"}";

        mockMvc.perform(
                post("/corporate-settlement-account/create")
                        .content(accountRequestSt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
        );

        mockMvc.perform(
                        post("/corporate-settlement-account/create")
                                .content(accountRequestSt)
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                )
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(matchesPattern("Параметр registryTypeCode тип регистра 03.012.002_47533_ComSoLd уже существует для ЭП с ИД  101.")))
        ;
    }

    @Test
    @DisplayName("Проверка существования registryTypeCode ")
    public void existRegistryTypeCode() throws Exception {

        String accountRequestSt = "{\"instanceId\":102,\"registryTypeCode\":\"03.012.002_47533_ComSoLd\"" +
                ",\"accountType\":\"TestКлиентский\",\"currencyCode\":\"800\",\"branchCode\":\"0022\"" +
                ",\"priorityCode\":\"00\",\"mdmCode\":\"15\",\"clientCode\":null,\"counter\":null" +
                ",\"salesCode\":null,\"trainRegion\":\"РЖД\"}";

        mockMvc.perform(
                        post("/corporate-settlement-account/create")
                                .content(accountRequestSt)
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                )
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(containsString("Код Продукта TestКлиентский не найдено в Каталоге продуктов <public.tpp_ref_product_register_type> для данного типа Регистра")))
        ;
    }

    @Test
    @DisplayName("Успешное создание tppProductRegister")
    public void successfulCreate() throws Exception {

        String accountRequestSt = "{\"instanceId\":115,\"registryTypeCode\":\"03.012.002_47533_ComSoLd\"" +
                ",\"accountType\":\"Клиентский\",\"currencyCode\":\"800\",\"branchCode\":\"0022\"" +
                ",\"priorityCode\":\"00\",\"mdmCode\":\"15\",\"clientCode\":null,\"counter\":null" +
                ",\"salesCode\":null,\"trainRegion\":\"РЖД\"}";

        mockMvc.perform(
                        post("/corporate-settlement-account/create")
                                .content(accountRequestSt)
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(matchesPattern("\\d*")))
        ;
    }
}
