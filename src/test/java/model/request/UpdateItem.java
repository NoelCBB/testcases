package model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateItem {
    @JsonProperty("title")
    public String title;

    @JsonProperty("dimensions")
    public Data dimensions;

    public static class Data {
        @JsonProperty("width")
        public String width;

        @JsonProperty("height")
        public String height;

        @JsonProperty("depth")
        public String depth;
        
    }   
}
