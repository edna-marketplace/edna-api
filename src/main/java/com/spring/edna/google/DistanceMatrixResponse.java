package com.spring.edna.google;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DistanceMatrixResponse {
    private String status;
    private String[] originAddresses;
    private String[] destinationAddresses;
    private Row[] rows;

    @Data
    public static class Row {
        private Element[] elements;
    }

    @Data
    public static class Element {
        private String status;
        private Distance distance;
        private Duration duration;
    }

    @Data
    public static class Distance {
        private String text;
        private long value;
    }

    @Data
    public static class Duration {
        private String text;
        private long value;
    }
}
