package ru.inno.task5.service;

import java.util.List;

public record InstanceResponceBody(String instanceId, List<Integer> registerId, List<String> supplementaryAgreementId) {
}
