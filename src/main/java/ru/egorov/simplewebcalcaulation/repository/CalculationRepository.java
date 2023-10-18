package ru.egorov.simplewebcalcaulation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.egorov.simplewebcalcaulation.model.CalculationModel;

public interface CalculationRepository extends JpaRepository<CalculationModel, Long> {
}
