package model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import model.request.AddItem.Data;

public class ResponseItem {
    @JsonProperty("id")
    public String id;

    @JsonProperty("title")
    public String title;

    @JsonProperty("dimensions")
    public Data dimensions;

    public static class dimensions {
        @JsonProperty("width")
        public String width;

        @JsonProperty("height")
        public String height;

        @JsonProperty("depth")
        public String depth;
        
    }
}
