package lat.luisdias.pi_v_b.entities;

public enum UserRole {
    ADMIN("Administrador"),
    USER("Usuário"),
    BLOCKED("Bloqueado");

    public final String label;

    UserRole(String label) {
        this.label = label;
    }
}
