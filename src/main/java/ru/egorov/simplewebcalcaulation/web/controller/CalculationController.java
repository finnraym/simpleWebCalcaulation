package ru.egorov.simplewebcalcaulation.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.egorov.simplewebcalcaulation.exception.ArithmeticException;
import ru.egorov.simplewebcalcaulation.service.CalculationService;
import ru.egorov.simplewebcalcaulation.util.ReadFileService;
import ru.egorov.simplewebcalcaulation.util.WriteFileService;
import ru.egorov.simplewebcalcaulation.web.dto.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CalculationController {

    private final CalculationService calculationService;
    private final ReadFileService readFileService;
    private final WriteFileService writeFileService;

    @PostMapping("/add")
    public int add(@RequestBody CalculationRequest request) throws IOException {
        int result = calculationService.addCalculation(parseRequest(request));
        writeToFile(request, result);

        return result;
    }

    @PostMapping("/mult")
    public int mult(@RequestBody CalculationRequest request) throws IOException {
        int result = calculationService.multCalculation(parseRequest(request));
        writeToFile(request, result);

        return result;
    }

    @PostMapping("/mult_and_add")
    public int multAndAdd(@RequestBody MultAndAddRequest request) throws IOException {
        Integer num1 = request.firstNum();
        Integer num2 = request.secondNum();
        Integer num3 = request.thirdNum();
        if (request.inputFile() != null) {
            List<Integer> nums = readFileService.readFile(request.inputFile());
            if (nums.size() != 3) {
                throw new ArithmeticException("Чисел для расчета должно быть 3.");
            }
            num1 = nums.get(0);
            num2 = nums.get(1);
            num3 = nums.get(2);
        }

        if (num1 == null || num2 == null || num3 == null) {
            throw new ArithmeticException("Невозможно провести расчет так одно или несколько из чисел null.");
        }

        int result = calculationService.multAndAddCalc(num1, num2, num3);

        writeToFile(request, result);

        return result;
    }

    @PostMapping("/divide")
    public int divide(@RequestBody DividedRequest request) throws IOException {
        Integer firstNum = request.firstNum();
        Integer secondNum = request.secondNum();

        if (request.inputFile() != null) {
            List<Integer> nums = readFileService.readFile(request.inputFile());
            if (nums.size() != 2) {
                throw new ArithmeticException("Должно быть 2 числа.");
            }
            firstNum = nums.get(0);
            secondNum = nums.get(1);
        }

        if (firstNum == null || secondNum == null || secondNum == 0) {
            throw new ArithmeticException("Делитель пустой или равен 0.");
        }

        int result = calculationService.divide(firstNum, secondNum);

        writeToFile(request, result);

        return result;
    }

    private List<Integer> parseRequest(CalculationRequest request) throws IOException {
        List<Integer> nums = request.nums();
        if (request.inputFile() != null) {
            nums = readFileService.readFile(request.inputFile());
        }

        if (nums == null || nums.size() < 2) {
            throw new ArithmeticException("Невозможно провести расчет т.к нехватает чисел.");
        }

        return nums;
    }

    private void writeToFile(Request request, int result) throws IOException {
        if (request.outputFile() != null) {
            writeFileService.writeFile(request.outputFile(), result);
        }
    }


    @ExceptionHandler(ArithmeticException.class)
    public ResponseEntity<String> handleArithmeticException(ArithmeticException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("Ошибка записи или чтения в файл: " + e.getMessage());
    }
}
