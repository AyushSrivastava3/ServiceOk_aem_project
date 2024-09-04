package com.adobe.aem.bcg.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
@Model(adaptables = Resource.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class EmployeeModel {
    @ValueMapValue
    private String title;
    @ValueMapValue
    private String id;
    @ValueMapValue
    private String Description;
    @ValueMapValue
    private String linkURL;
    @ValueMapValue
    private String category;
    @ValueMapValue
    private String tags;
    @ValueMapValue
    private String employmentType;

    public String getTags() {
        return tags;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return Description;
    }

    public String getLinkURL() {
        return linkURL;
    }

    public String getCategory() {
        return category;
    }
}
