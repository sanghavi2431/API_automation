package factory;

import requestModel.CreateCartRequest;

public final class CreateCartRequestFactory {

    private CreateCartRequestFactory() {
    }

    public static CreateCartRequest createCart(String regionId) {

        CreateCartRequest request = new CreateCartRequest();
        request.setRegionId(regionId);

        return request;
    }
}
