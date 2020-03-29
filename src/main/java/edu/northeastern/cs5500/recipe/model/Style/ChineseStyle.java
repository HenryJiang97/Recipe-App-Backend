package edu.northeastern.cs5500.recipe.model.Style;

import edu.northeastern.cs5500.recipe.model.Style.EasternStyle;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ChineseStyle extends EasternStyle {
    private String name;

    /** @return true if this ingredient is valid */
    @JsonIgnore
    public boolean isValid() {
        return name != null && !name.isEmpty();
    }
}
