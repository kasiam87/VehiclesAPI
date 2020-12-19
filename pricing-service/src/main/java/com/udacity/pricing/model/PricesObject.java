package com.udacity.pricing.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PricesObject {

    @JsonProperty("_embedded")
    public Embedded embedded;
    @JsonProperty("_links")
    public Links links;
    public Page page;

}
