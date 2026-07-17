package factory;

import java.util.Collections;
import java.util.Map;

import requestModel.DeletePaymentRequest;

/**
 * ============================================================================
 * Factory class for Delete Payment Request.
 * ============================================================================
 */
public final class DeletePaymentRequestFactory {

    private DeletePaymentRequestFactory() {

    }

    /**
     * Creates Delete Payment Request from test data.
     *
     * @param data Test Data
     * @return DeletePaymentRequest
     */
    public static DeletePaymentRequest create(Map<String, String> data) {

        return DeletePaymentRequest.builder()
                .ids(Collections.singletonList(data.get("payment_session_id")))
                .build();
    }

}