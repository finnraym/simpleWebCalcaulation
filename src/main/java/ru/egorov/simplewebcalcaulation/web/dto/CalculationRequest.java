package ru.egorov.simplewebcalcaulation.web.dto;

import java.util.List;

public record CalculationRequest(String inputFile, String outputFile, List<Integer> nums) implements Request {

}
