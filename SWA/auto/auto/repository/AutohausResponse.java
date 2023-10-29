package com.acme.auto.repository;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


@SuppressWarnings({"WriteTag", "MissingJavadocType", "MissingJavaDocMethod", "FinalParameters"})
public class AutohausResponse {
    @JsonProperty("_embedded")
    private Embedded embedded;

    public List<Autohaus> getAutohaeuser() {
        return embedded != null ? embedded.getAutohaeuser() : null;
    }

    private static class Embedded {
        private List<Autohaus> autohaeuser;

        public List<Autohaus> getAutohaeuser() {
            return autohaeuser;
        }

        public void setAutohaeuser(List<Autohaus> autohaeuser) {
            this.autohaeuser = autohaeuser;
        }
    }
}
