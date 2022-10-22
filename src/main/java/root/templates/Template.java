package root.templates;

public final class Template {
    public final String name;
    public final String merchant;
    public final String currency;
    public final Boolean isReimbursable;
    public final Boolean isBillable;
    public final String category;
    public final String descriptionPrefix;

    public Template(
            String name,
            String merchant,
            String currency,
            Boolean isReimbursable,
            Boolean isBillable,
            String category, String descriptionPrefix
    ) {
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null");
        }
        this.name = name;
        this.merchant = merchant;
        this.currency = currency;
        this.isReimbursable = isReimbursable;
        this.isBillable = isBillable;
        this.category = category;
        this.descriptionPrefix = descriptionPrefix;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Template)) {
            return false;
        }
        return name.contentEquals(((Template) obj).name);
    }
}
