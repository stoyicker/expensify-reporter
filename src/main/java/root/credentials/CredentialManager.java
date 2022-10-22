package root.credentials;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import root.Main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class CredentialManager {
    private final JsonAdapter<Credentials> jsonAdapter;

    public CredentialManager(Moshi moshi) {
        jsonAdapter = moshi.adapter(Credentials.class);
    }
    public Credentials readCredentials() {
        try {
            return jsonAdapter.fromJson(Files.readString(STORAGE_CREDENTIALS));
        } catch (IOException e) {
            e.printStackTrace(System.err);
            return null;
        }
    }

    public void writeCredentials(Credentials credentials) {
        try {
            Files.writeString(STORAGE_CREDENTIALS, jsonAdapter.toJson(credentials));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static final Path STORAGE_CREDENTIALS = Main.STORAGE_ROOT.resolve("credentials.json");
}
