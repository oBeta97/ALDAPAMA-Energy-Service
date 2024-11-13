package gruppo4.ALDAPAMA.enums;


import gruppo4.ALDAPAMA.exceptions.BadRequestException;

public enum Ruolo {
    ADMIN, USER;

    public static Ruolo stringToEnum(String ruolo) {
        switch (ruolo.toUpperCase()) {
            case "ADMIN":
                return Ruolo.ADMIN;
            case "USER":
                return Ruolo.USER;
            default:
                if (ruolo.toUpperCase().contains(Ruolo.ADMIN.toString())) {
                    return Ruolo.ADMIN;
                } else if (ruolo.toUpperCase().contains(Ruolo.USER.toString())) {
                    return Ruolo.USER;
                } else {
                    throw new BadRequestException("Tipologia di sede non valida");
                }
        }
    }
}
