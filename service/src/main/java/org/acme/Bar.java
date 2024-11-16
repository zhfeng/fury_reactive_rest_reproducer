package org.acme;

import io.quarkiverse.fury.FurySerialization;

import static org.acme.GreetingResource.BAR_CLASS_ID;

@FurySerialization(serializer = BarSerializer.class, classId = BAR_CLASS_ID)

public record Bar(int f1, String f2) {
}
