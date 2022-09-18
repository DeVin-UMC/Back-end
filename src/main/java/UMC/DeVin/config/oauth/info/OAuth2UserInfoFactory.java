package UMC.DeVin.config.oauth.info;

import UMC.DeVin.config.oauth.entity.ProviderType;
import UMC.DeVin.config.oauth.info.impl.GoogleOAuth2UserInfo;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(ProviderType providerType, Map<String, Object> attributes) {
        switch (providerType) {
            case GOOGLE: return new GoogleOAuth2UserInfo(attributes);
            // TODO : 플랫폼 확장
            /*case FACEBOOK: return new FacebookOAuth2UserInfo(attributes);
            case NAVER: return new NaverOAuth2UserInfo(attributes);
            case KAKAO: return new KakaoOAuth2UserInfo(attributes);*/
            default: throw new IllegalArgumentException("Invalid Provider Type.");
        }
    }
}
