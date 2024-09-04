package com.adobe.aem.bcg.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import static org.apache.sling.models.annotations.DefaultInjectionStrategy.OPTIONAL;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, defaultInjectionStrategy = OPTIONAL)
public class HeroModel extends BaseImageModel {

    @ValueMapValue
    @Default(values = "big_hero")
    private String heroType;

    @ValueMapValue
    private String subTitle;

    public String getHeroType() {
        return heroType;
    }

    public String getSubTitle() {
        return subTitle;
    }
}