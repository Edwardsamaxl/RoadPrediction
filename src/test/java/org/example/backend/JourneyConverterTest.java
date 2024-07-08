package org.example.backend;

import org.example.backend.Entity.Journey;
import org.example.backend.Entity.Route;
import org.example.backend.utils.JourneyConverter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JourneyConverterTest {

    @Test
    void testConvertToJourney() {
        Route route = new Route();
        route.setStart("北京市天安门");
        route.setEnd("北京市故宫");

        Journey journey = JourneyConverter.convertToJourney(route);

        assertNotNull(journey);
        assertTrue(journey.getStartLatitude() != 0);
        assertTrue(journey.getStartLongitude() != 0);
        assertTrue(journey.getEndLatitude() != 0);
        assertTrue(journey.getEndLongitude() != 0);
    }
}
