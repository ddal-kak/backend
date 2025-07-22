package ddalkak.presigned_url.listener;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import ddalkak.presigned_url.dto.ApiGatewayLambdaResponse;
import ddalkak.presigned_url.dto.FileMetaData;
import ddalkak.presigned_url.service.PresignedURLGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class LambdaEventListener implements Function<APIGatewayProxyRequestEvent, ApiGatewayLambdaResponse> {
    private final PresignedURLGenerator presignedURLGenerator;
    private static final String FULL_FILE_NAME = "fileName";

    @Override
    public ApiGatewayLambdaResponse apply(APIGatewayProxyRequestEvent event) {
        Map<String, String> params = event.getQueryStringParameters();
        return ApiGatewayLambdaResponse.from(
                presignedURLGenerator.generateUploadUrl(FileMetaData.from(params.get(FULL_FILE_NAME)))
        );
    }
}
