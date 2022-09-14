package tech.c3n7.keycloak.MyRemoteUserStorageProvider;

import org.keycloak.component.ComponentModel;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.user.UserLookupProvider;;


public class RemoteUserStorageProvider implements UserStorageProvider, UserLookupProvider, CredentialInputValidator {
    private KeycloakSession session;
    private ComponentModel model;

    public RemoteUserStorageProvider(KeycloakSession keycloakSession, ComponentModel componentModel) {
        this.session = session;
        this.model = model;
    }

    @Override
    public UserModel getUserById(String s, RealmModel realmModel) {
        return null;
    }

    @Override
    public UserModel getUserByUsername(String s, RealmModel realmModel) {
        return null;
    }

    @Override
    public UserModel getUserByEmail(String s, RealmModel realmModel) {
        return null;
    }

    @Override
    public boolean supportsCredentialType(String s) {
        return false;
    }

    @Override
    public boolean isConfiguredFor(RealmModel realmModel, UserModel userModel, String s) {
        return false;
    }

    @Override
    public boolean isValid(RealmModel realmModel, UserModel userModel, CredentialInput credentialInput) {
        return false;
    }

    @Override
    public void close() {

    }
}
