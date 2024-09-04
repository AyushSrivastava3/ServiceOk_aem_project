package com.adobe.aem.bcg.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;
import static org.apache.sling.models.annotations.DefaultInjectionStrategy.OPTIONAL;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, defaultInjectionStrategy = OPTIONAL)
public class CardsV2Model{

    @ChildResource
    private List<CardModel> cards;

    @ValueMapValue
    private String cardsLabel;

    @PostConstruct
    public void init() {
        cards = !isEmpty(cards) ? cards.stream().filter(Objects::nonNull).collect(Collectors.toList()) : emptyList();
    }

    public List<CardModel> getCardsList() {
        return cards;
    }

    public String getCardsLabel() {
        return cardsLabel;
    }
}