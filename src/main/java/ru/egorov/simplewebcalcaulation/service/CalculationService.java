package ru.egorov.simplewebcalcaulation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.egorov.simplewebcalcaulation.model.CalculationModel;
import ru.egorov.simplewebcalcaulation.repository.CalculationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CalculationService {

    private final CalculationRepository repository;
    public int addCalculation(List<Integer> nums) {
        int sum = nums.stream().mapToInt(Integer::intValue).sum();

        CalculationModel model = new CalculationModel("add", sum);
        repository.save(model);

        return sum;
    }

    public int multCalculation(List<Integer> nums) {
        Integer result = nums.stream().reduce((n1, n2) -> n1 * n2).orElse(0);
        CalculationModel model = new CalculationModel("mult", result);
        repository.save(model);

        return result;
    }

    public int multAndAddCalc(int num1, int num2, int num3) {
        int result = num1 * num2 + num3;
        CalculationModel model = new CalculationModel("mult_and_add", result);
        repository.save(model);
        return result;
    }

    public int divide(int num1, int num2) {
        int result = num1 / num2;
        CalculationModel model = new CalculationModel("divide", result);
        repository.save(model);

        return result;
    }
}
