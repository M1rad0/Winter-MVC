package mg.itu.framework.utils.validation;

import java.lang.annotation.Annotation;
import mg.itu.framework.annotation.validation.Range;

public class RangeValidator extends Validator {
    public RangeValidator(Object obj, String inputName, Annotation annot) {
        super(obj, inputName, annot);
    }

    @Override
    public boolean validate() {
        Range range = (Range) annot;
        double min = range.minValue();
        double max = range.maxValue();
        setErrorMessage("La valeur de "+inputName+"doit être inclue entre "+min+" et "+max);
        try {
            Double value = convertToDouble(obj);
            return value >= min && value <= max;
        } catch (NumberFormatException e) {
            setErrorMessage("Erreur : Impossible de convertir " + obj + " en nombre.");
            return false;
        }
    }

    private Double convertToDouble(Object obj) {
        if (obj == null) {
            throw new NumberFormatException("L'objet est null.");
        }
        if (obj instanceof Number) {
            return ((Number) obj).doubleValue();
        }
        if (obj instanceof String) {
            return Double.parseDouble((String) obj);
        }
        throw new NumberFormatException("Type non supporté : " + obj.getClass().getSimpleName());
    }
}
