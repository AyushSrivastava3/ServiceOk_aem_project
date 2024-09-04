package com.adobe.aem.bcg.core.servlets;

import com.adobe.aem.bcg.core.models.BasePageModel;
import com.adobe.aem.bcg.core.utils.PageUtils;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;
import javax.servlet.Servlet;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.adobe.aem.bcg.core.constants.CommonConstants.*;
import static com.adobe.aemds.guide.utils.JcrResourceConstants.CQ_PAGE;
import static com.day.cq.commons.jcr.JcrConstants.JCR_SCORE;
import static com.day.cq.search.Predicate.ORDER_BY;
import static com.day.cq.search.Predicate.SORT_DESCENDING;
import static java.lang.String.valueOf;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static org.apache.commons.collections.CollectionUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.*;
import static org.apache.http.client.cache.HeaderConstants.CACHE_CONTROL_NO_CACHE;
import static org.apache.jackrabbit.JcrConstants.JCR_CONTENT;


@Component(service = Servlet.class, property = {Constants.SERVICE_DESCRIPTION + "=Site Search Servlet",
        "sling.servlet.methods=" + HttpConstants.METHOD_GET,
        "sling.servlet.resourceTypes=" + "cq/Page",
        "sling.servlet.selectors=" + "site-search", "sling.servlet.extensions=" + "json"})
public class SiteSearchServlet extends SlingSafeMethodsServlet {

    private static final long serialVersionUID = 1L;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Reference
    private transient QueryBuilder queryBuilder;

    /**
     * @param request
     * @param response
     */
    @Override
    protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
            throws IOException {

        String[] selectors = request.getRequestPathInfo().getSelectors();
        String searchText = request.getParameter("fulltext").toString();

        if (selectors.length < 1 && isAnyBlank(searchText)) {
            writeJsonResponse(response, SC_BAD_REQUEST, EMPTY_JSON_RESPONSE);
            return;
        }
        try (ResourceResolver resourceResolver = request.getResourceResolver()) {
            Map<String, String> paramMap = buildQueryParametersByFullText("/content/service-oklahoma", searchText);
            writeJsonResponse(response, SC_OK, getSiteSearchResults(resourceResolver, paramMap, searchText));
        }
    }

    private Map<String, Object> getSiteSearchResults(ResourceResolver resourceResolver, Map<String, String> paramMap, String keyWord) {

        Query query =
                queryBuilder.createQuery(PredicateGroup.create(paramMap), resourceResolver.adaptTo(Session.class));
        logger.debug("query :{}", query.getPredicates().toURL());
        SearchResult searchResult = query.getResult();
        Map<String, Object> resObj = new HashMap<>();

        if (isEmpty(searchResult.getHits())) {
            resObj.put("results", "");
            resObj.put("total", 0);
            return resObj;
        }
        List<Resource> results = new ArrayList<>();
        Iterator<Resource> resources = searchResult.getResources();
        resources.forEachRemaining(results::add);

        List<JsonObject> resultList = results.stream()
                .map(resource -> resourceResolver.getResource(resource.getPath() + FORWARD_SLASH + JCR_CONTENT))
                .filter(Objects::nonNull)
                .map(resource -> resource.adaptTo(BasePageModel.class))
                .filter(Objects::nonNull)
                .map(pageModel -> convertPageModelToSearchResult(pageModel, resourceResolver, keyWord))
                .collect(Collectors.toList());

        resObj.put("results", resultList);
        resObj.put("total", searchResult.getTotalMatches());
        return resObj;
    }

    private JsonObject convertPageModelToSearchResult(BasePageModel pageModel, ResourceResolver resourceResolver, String keyWord) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("title", isNotBlank(pageModel.getNavTitle()) ? pageModel.getNavTitle() : pageModel.getTitle());
        jsonObject.addProperty("description", isNotBlank(pageModel.getDescription()) ? pageModel.getDescription() : EMPTY);
        jsonObject.addProperty("link", resourceResolver.map(PageUtils.appendHtmlToInternalLink(
                substringBefore(pageModel.getPath(), String.format("%s%s", FORWARD_SLASH, JCR_CONTENT)))));
        jsonObject.addProperty("keyWord", keyWord);
        return jsonObject;
    }

    private Map<String, String> buildQueryParametersByFullText(String searchRootPath, String searchKey) {
        Map<String, String> params = new HashMap<>();
        params.put(PARAM_PATH, searchRootPath);
        params.put(PARAM_TYPE, CQ_PAGE);
        params.put(PARAM_FULL_TEXT, String.format("*%s*", searchKey));
        params.put("group.1_property", "jcr:content/hideInSearch");
        params.put("group.1_property.operation", "equals");
        params.put("group.1_property.value", "false");
        params.put("group.p.or", "true");
        params.put("group.2_property", "jcr:content/hideInSearch");
        params.put("group.2_property.operation", "not");
        params.put(PARAM_LIMIT, valueOf(-1));
        params.put(ORDER_BY, String.format(ORDER_BY_PROPERTY, JCR_SCORE));
        params.put(ORDERBY_SORT, SORT_DESCENDING);
        return params;
    }

    private void writeJsonResponse(SlingHttpServletResponse response, int statusCode, Object object)
            throws IOException {
        response.setStatus(statusCode);
        response.setContentType(APPLICATION_JSON_CHARSET_UTF_8);
        response.setHeader(REQUEST_HEADER_DISPATCHER, CACHE_CONTROL_NO_CACHE);
        if (object.getClass() == String.class) {
            response.getWriter().print(object);
        } else {
            response.getWriter().print(new Gson().toJson(object));
        }
    }
}
