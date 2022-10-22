package root.templates;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import root.Main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public final class TemplateManager {
    private final JsonAdapter<Set<Template>> jsonAdapter;

    public TemplateManager(Moshi moshi) {
        jsonAdapter = moshi.adapter(Types.newParameterizedType(Set.class, Template.class));
    }
    public Set<Template> readTemplates() {
        try {
            return jsonAdapter.fromJson(Files.readString(STORAGE_TEMPLATES));
        } catch (IOException e) {
            e.printStackTrace(System.err);
            return new HashSet<>();
        }
    }

    public void addOrUpdateTemplate(Template template) {
        try {
            Set<Template> toWrite = readTemplates();
            toWrite.add(template);
            Files.writeString(STORAGE_TEMPLATES, jsonAdapter.toJson(toWrite));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteTemplate(String templateName) {
        try {
            Set<Template> toWrite = readTemplates();
            toWrite.removeIf(template -> template.name.contentEquals(templateName));
            Files.writeString(STORAGE_TEMPLATES, jsonAdapter.toJson(toWrite));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static final Path STORAGE_TEMPLATES = Main.STORAGE_ROOT.resolve("templates.json");
}
