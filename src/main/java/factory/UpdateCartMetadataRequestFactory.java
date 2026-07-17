package factory;

import java.util.Map;
import java.util.Objects;

import requestModel.Metadata;
import requestModel.UpdateCartMetadataRequest;
import requestModel.VehicleDetails;

public final class UpdateCartMetadataRequestFactory {

    private UpdateCartMetadataRequestFactory() {
    }

    public static UpdateCartMetadataRequest create(Map<String, String> data) {

        VehicleDetails vehicleDetails = VehicleDetails.builder()
                .id(Objects.requireNonNull(data.get("user_id"), "Vehicle ID is required").trim())
                .phone(Objects.requireNonNull(data.get("mobileNo"), "Phone is required").trim())
                .userName(Objects.requireNonNull(data.get("user_name"), "User name is required").trim())
                .vehicleNumber(Objects.requireNonNull(data.get("vehicle_number"), "Vehicle number is required").trim())
                .vehicleType(Objects.requireNonNull(data.get("vehicle_type"), "Vehicle type is required").trim())
                .build();

        Metadata metadata = Metadata.builder()
                .vehicleDetails(vehicleDetails)
                .build();

        return UpdateCartMetadataRequest.builder()
                .metadata(metadata)
                .build();
    }
}