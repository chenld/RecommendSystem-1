package com.itzzq.j2ee.recommandSystem.services;

import com.itzzq.j2ee.recommandSystem.domain.UserEntity;

/**
 * Provides processing service to set user authentication session
 * 
 * @author itzzq
 */
public interface UserAuthenticationProviderService {

        /**
         * Process user authentication
         * 
         * @param user
         * @return
         */
        boolean processUserAuthentication(UserEntity user);
}