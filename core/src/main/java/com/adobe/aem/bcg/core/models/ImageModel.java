package com.adobe.aem.bcg.core.models;

public interface ImageModel {
    String getTitle();

    String getDescription();

    String getImage();

    String getAltText();

    String getMobileImage();

    boolean isImageEmpty();
}
