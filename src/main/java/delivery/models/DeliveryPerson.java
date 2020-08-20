package delivery.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeliveryPerson {
    String name;
    String id;
    Boolean isAvailable;
}
