package tech.c3n7.keycloak.MyRemoteUserStorageProvider;

public class VerifyPasswordResponse {
    private boolean result;

    public VerifyPasswordResponse(boolean result) {
        this.result = result;
    }

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
