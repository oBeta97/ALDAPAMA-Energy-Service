package gruppo4.ALDAPAMA.enums;

import gruppo4.ALDAPAMA.exceptions.BadRequestException;

public enum TipoSede {
    LEGALE, OPERATIVA;

    public static TipoSede stringToEnum(String tiposede) {
        switch (tiposede.toUpperCase()) {
            case "LEGALE":
                return TipoSede.LEGALE;
            case "OPERATIVA":
                return TipoSede.OPERATIVA;
            default:
                if (tiposede.toUpperCase().contains(TipoSede.LEGALE.toString())) {
                    return TipoSede.LEGALE;
                } else if (tiposede.toUpperCase().contains(TipoSede.OPERATIVA.toString())) {
                    return TipoSede.OPERATIVA;
                } else {
                    throw new BadRequestException("Tipologia di sede non valida");
                }
        }
    }
}
