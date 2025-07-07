package br.edu.ufrn.distancies.records;

import br.edu.ufrn.distancies.enums.Unit;

public record ConversionRequest(Unit from, Unit to, double value) {}
