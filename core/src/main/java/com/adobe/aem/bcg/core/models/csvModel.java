package com.adobe.aem.bcg.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class csvModel {
    @ValueMapValue
    private String text;

    public String getText() {
        return text;
    }

    @ValueMapValue
    private String id;

    public String getId() {
        return id;
    }

}
