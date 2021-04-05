package net.thumbtack.school.elections.server;

import com.google.gson.*;
import net.thumbtack.school.elections.server.dto.response.ErrorDtoResponse;
import net.thumbtack.school.elections.server.dto.response.SuccessDtoRespons;
import net.thumbtack.school.elections.server.model.Candidate;
import net.thumbtack.school.elections.server.model.Proposal;
import net.thumbtack.school.elections.server.model.Voter;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.MapIterator;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;

import java.util.ListIterator;

public class ServerUtils {
    private static Gson gson;

    public static Gson getGson() {
        gson = new GsonBuilder()
                .enableComplexMapKeySerialization()
                //.serializeNulls()
                //.excludeFieldsWithModifiers(Modifier.TRANSIENT)
                .registerTypeAdapter(MultiValuedMap.class,
                        (JsonDeserializer<MultiValuedMap<Candidate, Proposal>>) (src, type, context) -> {
                            JsonObject jsonObject = src.getAsJsonObject();
                            MultiValuedMap<Candidate, Proposal> election = new HashSetValuedHashMap<>();
                            return election;
                            /*JsonObject jsonObject = jsonElement.getAsJsonObject();
                            MultiValuedMap<Candidate, Proposal> prop = new HashSetValuedHashMap<Candidate, Proposal>();
                            JsonArray author = jsonObject.getAsJsonArray("author");*/
                })
                .registerTypeAdapter(MultiValuedMap.class,
                        (JsonSerializer<MultiValuedMap<Candidate, Voter>>) (src, type, context) -> {
                            return null;
                        })

                .registerTypeAdapter(BidiMap.class,
                        (JsonDeserializer<BidiMap<String, Voter>>) (src, type, context) -> {
                            JsonObject jsonObject = src.getAsJsonObject();
                            BidiMap<String, Voter> online = new DualHashBidiMap<>();

                            return online;//context.deserialize(src, BidiMap.class);
                        })
                .registerTypeAdapter(BidiMap.class,
                        (JsonSerializer<BidiMap<String, Voter>>) (src, type, context) -> {
                            JsonObject object = new JsonObject();
                            MapIterator<String, Voter> mi = src.mapIterator();
                            while (mi.hasNext()) {
                                mi.next();
                                JsonObject object1 = new JsonObject();
                                object.add(mi.getKey(), object1);
                                JsonObject object2 = new JsonObject();
                                object2.addProperty("login", mi.getValue().getUser().getLogin());
                                object1.add("user", object2);

                                JsonArray array = new JsonArray();
                                JsonObject object3 = null;
                                ListIterator<Proposal> li = mi.getValue().getSelfProposals().listIterator();
                                while (li.hasNext()) {
                                    object3 = new JsonObject();
                                    Proposal p = li.next();
                                    object3.addProperty("text", p.getText());
                                    object3.addProperty("author", p.getAuthor());
                                    object3.addProperty("averageRating", p.getAverageRating());
                                    object3.addProperty("id", p.getId());
                                    array.add(object3);
                                }
                                object1.add("selfProposals", array);

                                object1.addProperty("voice", mi.getValue().getVoice());
                            }
                            return object;
                        })
                .setPrettyPrinting()
                .create();
        return gson;
    }

    public static String makeErrorResponse(ServerException e) {
        gson = new Gson();
        return gson.toJson(new ErrorDtoResponse(e.getServerExceptionErrorCode().getErrorCode()),
                ErrorDtoResponse.class);
    }

    public static String makeSuccessResponse() {
        gson = new Gson();
        return gson.toJson(new SuccessDtoRespons(), SuccessDtoRespons.class);
    }

    public static String makeErrorDateResponse() {
        gson = new Gson();
        return gson.toJson(new ErrorDtoResponse(ServerExceptionErrorCode.OUT_OF_ELECTIONDATE.getErrorCode()),
                ErrorDtoResponse.class);
    }
}
