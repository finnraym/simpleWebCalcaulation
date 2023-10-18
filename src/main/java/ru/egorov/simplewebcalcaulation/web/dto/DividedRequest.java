package ru.egorov.simplewebcalcaulation.web.dto;

public record DividedRequest(String inputFile, String outputFile, Integer firstNum, Integer secondNum) implements Request {
}
