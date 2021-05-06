package com.avanadebrasil.keycloak.orahash;

import com.avanadebrasil.keycloak.database.HashQueryDataBase;
import org.keycloak.credential.hash.PasswordHashProvider;
import org.keycloak.models.PasswordPolicy;
import org.keycloak.models.credential.PasswordCredentialModel;

public class OrahashPasswordProvider implements PasswordHashProvider {

    private final int defaultIterations;
    private final String providerId;

    private HashQueryDataBase hashQueryDataBase;

    public OrahashPasswordProvider(String providerId, int defaultIterations) {
        this.providerId = providerId;
        this.defaultIterations = defaultIterations;
    }

    @Override
    public boolean policyCheck(PasswordPolicy policy, PasswordCredentialModel credential) {
        int policyHashIterations = policy.getHashIterations();
        if (policyHashIterations == -1) {
            policyHashIterations = defaultIterations;
        }

        return credential.getPasswordCredentialData().getHashIterations() == policyHashIterations
                && providerId.equals(credential.getPasswordCredentialData().getAlgorithm());
    }

    @Override
    public PasswordCredentialModel encodedCredential(String rawPassword, int iterations) {
        String encodedPassword = encode(rawPassword, iterations);

        return PasswordCredentialModel.createFromValues(providerId, new byte[0], iterations, encodedPassword);
    }

    @Override
    public String encode(String rawPassword, int iterations) {
        int cost;
        if (iterations == -1) {
            cost = defaultIterations;
        } else {
            cost = iterations;
        }
        hashQueryDataBase = new HashQueryDataBase();
        String passwordHash = hashQueryDataBase.returnHashPassword(rawPassword);
        System.out.println("Password: " + rawPassword);
        System.out.println("Password Hash: " + passwordHash);
        return passwordHash;
    }

    @Override
    public void close() {

    }

    @Override
    public boolean verify(String rawPassword, PasswordCredentialModel credential) {
    	System.out.println("This password enter: " + rawPassword);
        final String hash = credential.getPasswordSecretData().getValue();
        System.out.println("credencial hash: " + hash);
        hashQueryDataBase = new HashQueryDataBase();
        return hash.equals(hashQueryDataBase.returnHashPassword(rawPassword));
    }
}
