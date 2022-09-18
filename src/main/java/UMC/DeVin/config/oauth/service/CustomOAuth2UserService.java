package UMC.DeVin.config.oauth.service;

import UMC.DeVin.config.oauth.entity.ProviderType;
import UMC.DeVin.config.oauth.entity.UserPrincipal;
import UMC.DeVin.config.oauth.info.OAuth2UserInfoFactory;
import UMC.DeVin.member.Member;
import UMC.DeVin.member.repository.MemberRepository;
import UMC.DeVin.config.oauth.exception.OAuthProviderMissMatchException;
import UMC.DeVin.config.oauth.info.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);

        try {
            return this.process(userRequest, user);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User user) {
        ProviderType providerType = ProviderType.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());

        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, user.getAttributes());
        Optional<Member> savedUser = memberRepository.findByEmail(userInfo.getEmail());

        Member newMember = null;

        if (savedUser.isPresent()) {
            if (providerType != savedUser.get().getDivision()) {
                throw new OAuthProviderMissMatchException(
                        "Looks like you're signed up with " + providerType +
                                " account. Please use your " + savedUser.get().getDivision() + " account to login."
                );
            }
            updateUser(savedUser.get(), userInfo);
            return UserPrincipal.create(savedUser.get(), user.getAttributes());
        } else {
            newMember = createMember(userInfo, providerType);
            return UserPrincipal.create(newMember, user.getAttributes());
        }


    }

    private Member createMember(OAuth2UserInfo userInfo, ProviderType providerType) {
        LocalDateTime now = LocalDateTime.now();
        Member member = Member.createMember(userInfo.getName(), userInfo.getEmail(), userInfo.getImageUrl(), ProviderType.GOOGLE);

        return memberRepository.saveAndFlush(member);
    }

    private Member updateUser(Member member, OAuth2UserInfo userInfo) {

        if (userInfo.getImageUrl() != null && !member.getProfileImageUrl().equals(userInfo.getImageUrl())) {
            member.updateProfileImg(userInfo.getImageUrl());
        }

        return member;
    }
}
