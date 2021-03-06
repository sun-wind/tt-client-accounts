
package testtask.accounts.serializator;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 *
 * @author Olga Grazhdanova <dvl.java@gmail.com> at Jan 27, 2018
 */
public class JsonBigDecimalSerializer extends JsonSerializer<BigDecimal> {

    private final DecimalFormat format = (DecimalFormat) DecimalFormat.getInstance();

    public JsonBigDecimalSerializer() {
         format.setGroupingUsed(false);
         DecimalFormatSymbols separator = new DecimalFormatSymbols();
         separator.setDecimalSeparator('.');
         format.setDecimalFormatSymbols(separator);
    }

    @Override
    public void serialize(BigDecimal val, JsonGenerator jg, SerializerProvider sp) throws IOException, JsonProcessingException {
        jg.writeNumber(format.format(val));
    }

}
