package ru.egorov.simplewebcalcaulation.web.dto;

public record MultAndAddRequest(String inputFile, String outputFile, Integer firstNum, Integer secondNum, Integer thirdNum) implements Request {
}
