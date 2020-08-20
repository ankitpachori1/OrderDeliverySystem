package delivery.models;

import lombok.Data;

@Data
public class DeliveryRequest {
    String deliveryPersonId;
    String orderId;
    String status;
}
