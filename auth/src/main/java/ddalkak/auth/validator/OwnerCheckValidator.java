package ddalkak.auth.validator;

import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import ddalkak.auth.common.exception.OwnerMismatchException;
import ddalkak.auth.dto.ApiGatewayLambdaResponse;
import ddalkak.auth.dto.HttpRequestSignature;
import ddalkak.auth.dto.UserContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static ddalkak.auth.enums.MicroServicesConstants.MEMBER_SERVICE;
import static ddalkak.auth.enums.MicroServicesConstants.TICKET_SERVICE;

@Component
public class OwnerCheckValidator implements Validator {

    @Override
    public boolean supports(HttpRequestSignature httpRequestSignature) {
        /**
         * GET /member/{memberId}
         * GET /ticket/{memberId}
         */
        String path = httpRequestSignature.path();
        String httpMethod = httpRequestSignature.httpMethod();

        return isSupportedPath(path, httpMethod);
    }

    @Override
    public ApiGatewayLambdaResponse execute(APIGatewayV2HTTPEvent event, UserContext userContext) {
        /**
         * 1. event => 요청한 memberId 추출
         * 2. UserContext 에서 실제 memberId 추출
         * 3. 동일한지 확인
         */
        Long targetMemberId = extractAuthorizationTargetId(event);
        Long myMemberId = userContext.getMemberId();

        if (targetMemberId == myMemberId) {
            return ApiGatewayLambdaResponse.successOf(myMemberId);
        }
        throw new OwnerMismatchException("you are not owner of this resource");
    }

    private static boolean isSupportedPath(String path, String httpMethod) {
        boolean startCondition = (path.startsWith(MEMBER_SERVICE.getPrefix()) || path.startsWith(TICKET_SERVICE.getPrefix())) && httpMethod.equals("GET");
        boolean hasOnlyOnePathVariable = Arrays.stream(path.split("/"))
                .filter(element -> element.matches("\\d+"))
                .limit(2)
                .count() == 1;
        return startCondition && hasOnlyOnePathVariable;
    }

    private static Long extractAuthorizationTargetId(APIGatewayV2HTTPEvent event) {
        String rawPath = event.getRawPath();
        return Arrays.stream(rawPath.split("/"))
                .filter(element -> element.matches("\\d+")) //숫자만 필터링
                .map(Long::parseLong)
                .findFirst()
                .get();
    }
}
