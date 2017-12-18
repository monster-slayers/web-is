package cz.muni.fi.pa165.monsterslayers.frontend.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import cz.muni.fi.pa165.monsterslayers.dto.monstertype.MonsterTypeDTO;
import cz.muni.fi.pa165.monsterslayers.entities.MonsterType;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Deserializer for kill list
 *
 * @author David Kizivat
 */
public class KillListDeserializer extends JsonDeserializer<Map> {
    @Override
    public Map<Long, Integer> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectCodec codec = jsonParser.getCodec();
        JsonNode node = codec.readTree(jsonParser);

        Map<Long, Integer> result = new HashMap<>();

        if (node.isArray()) {
            for (JsonNode n : node) {
                JsonNode key = n.get("key");
                if (key != null) {
                    JsonNode value = n.get("value");
                    result.put(key.asLong(), value.asInt());
                }
            }
        }

        return result;
    }
}
