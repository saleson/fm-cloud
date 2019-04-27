package com.fm.client.event;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
//@JsonIgnoreProperties({"source"})
public class BusEvent extends RemoteApplicationEvent {


    private static final long serialVersionUID = 6796291922470619977L;


    public BusEvent() {
    }

    public BusEvent(Object source, String originService, String destinationService) {
        super(source, originService, destinationService);
    }
}
