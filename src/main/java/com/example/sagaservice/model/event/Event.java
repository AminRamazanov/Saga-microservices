package com.example.sagaservice.model.event;

import java.io.Serializable;

public interface Event extends Serializable {
    String getEventId();
}