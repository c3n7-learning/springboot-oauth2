package tech.c3n7.keycloak.MyRemoteUserStorageProvider;

import org.keycloak.component.ComponentModel;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.credential.UserCredentialStore;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.SubjectCredentialManager;
import org.keycloak.models.UserModel;
import org.keycloak.models.credential.PasswordCredentialModel;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.adapter.AbstractUserAdapter;
import org.keycloak.storage.user.UserLookupProvider;;


public class RemoteUserStorageProvider implements UserStorageProvider, UserLookupProvider, CredentialInputValidator {
    private KeycloakSession session;
    private ComponentModel model;

    private UsersApiService usersApiService;

    public RemoteUserStorageProvider(KeycloakSession keycloakSession, ComponentModel componentModel, UsersApiService usersApiService) {
        this.session = keycloakSession;
        this.model = componentModel;
        this.usersApiService = usersApiService;
    }

    @Override
    public UserModel getUserById(String s, RealmModel realmModel) {
        return null;
    }

    @Override
    public UserModel getUserByUsername(String s, RealmModel realmModel) {
        User user = usersApiService.getUserDetails(s);
        UserModel returnValue = null;

        if (user != null) {
            returnValue = createUserModel(s, realmModel);
        }

        return null;
    }

    private UserModel createUserModel(String s, RealmModel realmModel) {
        return new AbstractUserAdapter(session, realmModel, model) {
            @Override
            public String getUsername() {
                return s;
            }

            @Override
            public SubjectCredentialManager credentialManager() {
                return null;
            }
        };
    }

    @Override
    public UserModel getUserByEmail(String s, RealmModel realmModel) {
        return null;
    }

    @Override
    public boolean supportsCredentialType(String s) {
        // If this returns true, then the credential type(e.g. password) is supported
        return PasswordCredentialModel.TYPE.equals(s);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isConfiguredFor(RealmModel realmModel, UserModel userModel, String s) {
        if (!supportsCredentialType(s)) {
            return false;
        }
        return !getCredentialStore().getStoredCredentialsByType(realmModel, userModel, s).isEmpty();
    }

    @SuppressWarnings("deprecation")
    private UserCredentialStore getCredentialStore() {
        return session.userCredentialManager();
    }

    @Override
    public boolean isValid(RealmModel realmModel, UserModel userModel, CredentialInput credentialInput) {
        VerifyPasswordResponse response = usersApiService.verifyUserPassword(userModel.getUsername(), credentialInput.getChallengeResponse());

        return response != null || response.getResult();
    }

    @Override
    public void close() {

    }
}
