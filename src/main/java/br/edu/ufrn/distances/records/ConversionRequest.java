package br.edu.ufrn.distances.records;

import br.edu.ufrn.distances.enums.Unit;

public record ConversionRequest(Unit from, Unit to, double value) {}
