package com.tus.java.oop1.rms.data.record;

import java.util.Set;

public record MenuItemRecord(String name, Double price, Long branchId, Set<String> ingredients) {}